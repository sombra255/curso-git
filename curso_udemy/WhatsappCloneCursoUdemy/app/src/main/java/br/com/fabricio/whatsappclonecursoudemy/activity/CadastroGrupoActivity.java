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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.adapter.GrupoSelecionadoAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Grupo;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

import static br.com.fabricio.whatsappclonecursoudemy.activity.ConfiguracoesActivity.SELECAO_GALERIA;

public class CadastroGrupoActivity extends AppCompatActivity {

    private EditText edtNomeGrupo;
    private TextView txtTotalParticipantes;
    private CircleImageView imagemGrupo;
    private RecyclerView recyclerViewCadastroGrupo;
    private List<Usuario> lsMembrosSelecionados = new ArrayList<>();
    private GrupoSelecionadoAdapter adapter;
    private StorageReference storageReference;
    private Grupo grupo;
    private FloatingActionButton fabSalvarGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_grupo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo grupo");
        toolbar.setSubtitle("Defina o nome");
        setSupportActionBar(toolbar);

        edtNomeGrupo = findViewById(R.id.editTextoNomeGrupo);
        txtTotalParticipantes = findViewById(R.id.textViewTotalParticipantes);
        imagemGrupo = findViewById(R.id.imagemGrupo);
        recyclerViewCadastroGrupo = findViewById(R.id.recyclerCadastroGrupo);
        fabSalvarGrupo = findViewById(R.id.fabSalvarGrupo);
        grupo = new Grupo();

        storageReference = FirebaseHelper.getFirebaseStorage();

        imagemGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECAO_GALERIA);
                }
            }
        });

        fabSalvarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeGrupo = edtNomeGrupo.getText().toString();

                if(!nomeGrupo.isEmpty()){
                    grupo.setNome(nomeGrupo);
                    lsMembrosSelecionados.add(UsuarioFirebase.getUsuarioLogado());
                    grupo.setLsMembros(lsMembrosSelecionados);
                    grupo.salvar();
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recupera lista de membros
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            List<Usuario> lsUsuarios = (List<Usuario>) bundle.getSerializable("membrosSelecionados");
            lsMembrosSelecionados.addAll(lsUsuarios);

            txtTotalParticipantes.setText("Participantes: " + lsMembrosSelecionados.size());
        }

        adapter = new GrupoSelecionadoAdapter(lsMembrosSelecionados, getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCadastroGrupo.setLayoutManager(layoutManager);
        recyclerViewCadastroGrupo.setHasFixedSize(true);
        recyclerViewCadastroGrupo.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagemBitmap = null;

            try {
                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagemBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

                if (imagemBitmap != null) {
                    imagemGrupo.setImageBitmap(imagemBitmap);

                    //recuperar dados da imagem para o firebase
                    //Comprimindo imagem para algum formato png/jpg
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagemBitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

                    //converte o baos para pixel brutos em uma matriz de bytes
                    byte[] dadosImagem = baos.toByteArray();

                    //salvar imagem no firebase
                    StorageReference imagemRef = storageReference.child("imagens")
                            .child("grupos")
                            .child(grupo.getId()+".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CadastroGrupoActivity.this, "Falha ao realizar upload da imagem", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CadastroGrupoActivity.this, "Sucesso ao realizar upload da imagem", Toast.LENGTH_SHORT).show();

                            Uri url = taskSnapshot.getDownloadUrl();
                            grupo.setFoto(url.toString());
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
