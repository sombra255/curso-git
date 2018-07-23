package br.com.fabricio.whatsappclonecursoudemy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.adapter.MensagensAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Conversa;
import br.com.fabricio.whatsappclonecursoudemy.model.Mensagem;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.Base64Custom;
import de.hdodenhof.circleimageview.CircleImageView;

import static br.com.fabricio.whatsappclonecursoudemy.activity.ConfiguracoesActivity.SELECAO_CAMERA;

public class ChatActivity extends AppCompatActivity {

    private CircleImageView circleImageViewChat;
    private TextView textViewNomeChat;
    private Usuario usuarioDestinatario;
    private EditText edtMensagem;
    private String idUsuarioRemetente;
    private String idUsuarioDestinatario;
    private RecyclerView recyclerViewMensagens;
    private MensagensAdapter adapter;
    private List<Mensagem> lsMensagens = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceMensagens;
    private ChildEventListener childEventListenerMensagens;
    private ImageView imageViewFotoChat;
    private StorageReference storage;

    @Override
    protected void onStart() {
        super.onStart();
        recuperaMensagens();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListenerMensagens);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        circleImageViewChat = findViewById(R.id.circleImageFotoChat);
        textViewNomeChat = findViewById(R.id.textViewNomeChat);
        edtMensagem = findViewById(R.id.edtMensagem);
        recyclerViewMensagens = findViewById(R.id.recyclerMensagens);
        imageViewFotoChat = findViewById(R.id.imageViewFotoChat);

        idUsuarioRemetente = UsuarioFirebase.getUsuarioFirebase();
        storage = FirebaseHelper.getFirebaseStorage();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recuperar dados do usuario
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
            textViewNomeChat.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();
            if (foto != null) {
                Uri uri = Uri.parse(usuarioDestinatario.getFoto());
                Glide.with(ChatActivity.this).load(uri).into(circleImageViewChat);
            } else {
                circleImageViewChat.setImageResource(R.drawable.padrao);
            }

            idUsuarioDestinatario = Base64Custom.encodeToString(usuarioDestinatario.getEmail());
        }

        //configuracao adapter
        adapter = new MensagensAdapter(lsMensagens, getApplicationContext());
        //configuracao recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMensagens.setLayoutManager(layoutManager);
        recyclerViewMensagens.setHasFixedSize(true);
        recyclerViewMensagens.setAdapter(adapter);

        databaseReference = FirebaseHelper.getFirebaseDatabase();
        databaseReferenceMensagens = databaseReference.child("mensagens")
                .child(idUsuarioRemetente)
                .child(idUsuarioDestinatario);

        imageViewFotoChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECAO_CAMERA);
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagemBitmap = null;

            try {
                switch (requestCode) {
                    case SELECAO_CAMERA:
                        imagemBitmap = (Bitmap) data.getExtras().get("data");
                        break;
                }

                if (imagemBitmap != null) {
                    //recuperar dados da imagem para o firebase
                    //Comprimindo imagem para algum formato png/jpg
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagemBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

                    //converte o baos para pixel brutos em uma matriz de bytes
                    byte[] dadosImagem = baos.toByteArray();

                    UUID nomeImagem = UUID.randomUUID();

                    //salvar imagem no firebase
                    StorageReference imagemRef = storage.child("imagens")
                            .child("fotos")
                            .child(idUsuarioRemetente)
                            .child(nomeImagem + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ChatActivity.this, "Falha ao realizar upload da imagem", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Uri url = taskSnapshot.getDownloadUrl();

                            Mensagem mensagem = new Mensagem();
                            mensagem.setIdUsuario(idUsuarioRemetente);
                            mensagem.setMensagem("imagem.jpeg");
                            mensagem.setImagem(url.toString());

                            salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                            salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

                            //salvar conversa
                            salvarConversa(mensagem);


                            Toast.makeText(ChatActivity.this, "Sucesso ao enviar imagem", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void salvarConversa(Mensagem msg) {
        Conversa conversaRemetente = new Conversa();
        conversaRemetente.setIdRemetente(idUsuarioRemetente);
        conversaRemetente.setIdDestinatario(idUsuarioDestinatario);
        conversaRemetente.setUltimaMensagem(msg.getMensagem());
        conversaRemetente.setUsuarioExibicao(usuarioDestinatario);

        conversaRemetente.salvar();

    }

    public void enviarMensagem(View view) {
        String textoMensagem = edtMensagem.getText().toString();
        if (!textoMensagem.isEmpty()) {
            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(idUsuarioRemetente);
            mensagem.setMensagem(edtMensagem.getText().toString());

            //salvar mensagem usuario remetente
            salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);

            //salvar mensagem usuario destinatario
            salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

            //salvar conversa
            salvarConversa(mensagem);

        } else {
            Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarMensagem(String idRemetente, String idDestinatario, Mensagem msg) {
        DatabaseReference database = FirebaseHelper.getFirebaseDatabase();
        DatabaseReference mensagemRef = database.child("mensagens");

        mensagemRef.child(idRemetente)
                .child(idDestinatario)
                .push()
                .setValue(msg);

        edtMensagem.setText("");
    }

    private void recuperaMensagens() {
        childEventListenerMensagens = databaseReferenceMensagens.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                lsMensagens.add(mensagem);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
