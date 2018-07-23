package br.com.fabricio.whatsappclonecursoudemy.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.adapter.MensagensAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Mensagem;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.Base64Custom;
import de.hdodenhof.circleimageview.CircleImageView;

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

        idUsuarioRemetente = UsuarioFirebase.getUsuarioFirebase();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recuperar dados do usuario
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            usuarioDestinatario = (Usuario) bundle.getSerializable("chatContato");
            textViewNomeChat.setText(usuarioDestinatario.getNome());

            String foto = usuarioDestinatario.getFoto();
            if(foto != null){
                Uri uri = Uri.parse(usuarioDestinatario.getFoto());
                Glide.with(ChatActivity.this).load(uri).into(circleImageViewChat);
            }else {
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

    }

    public void enviarMensagem(View view){
        String textoMensagem = edtMensagem.getText().toString();
        if(!textoMensagem.isEmpty()){
            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(idUsuarioRemetente);
            mensagem.setMensagem(edtMensagem.getText().toString());

            //salvar mensagem usuario remetente
            salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);

            //salvar mensagem usuario destinatario
            salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

        }else {
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

    private void recuperaMensagens(){
        childEventListenerMensagens = databaseReference.addChildEventListener(new ChildEventListener() {
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
