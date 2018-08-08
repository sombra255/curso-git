package br.com.fabricio.instagramclone.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilAmigoActivity extends AppCompatActivity {
    private Usuario usuarioSelecionado;
    private Usuario usuarioLogado;
    private Button btnEditarPerfil; //vou sobreever o mesmo botao da tela de editar
    private CircleImageView imageView;
    private DatabaseReference firebaseRef;
    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioAmigoRef;
    private DatabaseReference usuarioLogadoRef;
    private DatabaseReference seguidoresRef;
    private ValueEventListener eventListenerPeriflAmigo;
    private TextView txtPublicacoes, txtSeguidores, txtSeguindo;

    @Override
    protected void onStart() {
        super.onStart();
        recuperarDadosPerfilAmigo();
        recuperarDadosUsuariosLogado();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioAmigoRef.removeEventListener(eventListenerPeriflAmigo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_amigo);

        firebaseRef = FirebaseHelper.getDatabaseReference();
        usuariosRef = firebaseRef.child("usuarios");
        seguidoresRef = firebaseRef.child("seguidores");
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        inicializarComponentes();

        btnEditarPerfil.setText("Seguir");

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            usuarioSelecionado = (Usuario) bundle.getSerializable("usuarioSelecionado");
            getSupportActionBar().setTitle(usuarioSelecionado.getNome());

            if(usuarioSelecionado.getCaminhoFoto() != null){
                Uri uri = Uri.parse(usuarioSelecionado.getCaminhoFoto());
                Glide.with(getApplicationContext()).load(uri).into(imageView);
            }else {
                imageView.setImageResource(R.drawable.avatar);
            }
        }
    }

    private void inicializarComponentes() {
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        imageView = findViewById(R.id.imageViewFotoPerfil);
        txtPublicacoes = findViewById(R.id.txtPublicacoes);
        txtSeguidores = findViewById(R.id.txtSeguidores);
        txtSeguindo = findViewById(R.id.txtSeguindo);
        btnEditarPerfil.setText("Carregando");
    }

    private void recuperarDadosPerfilAmigo(){
        usuarioAmigoRef = usuariosRef.child(usuarioSelecionado.getId());
        eventListenerPeriflAmigo = usuarioAmigoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                String postagens = String.valueOf(usuario.getPostagens());
                String seguidores = String.valueOf(usuario.getSeguidores());
                String seguindo = String.valueOf(usuario.getSeguindo());

                txtPublicacoes.setText(postagens);
                txtSeguidores.setText(seguidores);
                txtSeguindo.setText(seguindo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void verificaSegueUsuarioAmigo(){
        DatabaseReference seguidorRef = seguidoresRef
                .child(usuarioLogado.getId())
                .child(usuarioSelecionado.getId());

        seguidorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    //esta sendo seguido
                    habilitaBotaoSeguir(true);
                }else {
                    //ainda nao esta sendo seguido
                    habilitaBotaoSeguir(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void habilitaBotaoSeguir(boolean segueUsuario){
        if(segueUsuario){
            btnEditarPerfil.setText("Seguindo");
        }else {
            btnEditarPerfil.setText("Seguir");

            btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salvarSeguidor(usuarioLogado, usuarioSelecionado);
                }
            });
        }
    }

    private void recuperarDadosUsuariosLogado(){
        usuarioLogadoRef = usuariosRef.child(usuarioLogado.getId());
        usuarioLogadoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioLogado = dataSnapshot.getValue(Usuario.class);
                verificaSegueUsuarioAmigo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void salvarSeguidor(Usuario uLogado, Usuario uAmigo) {

        HashMap<String, Object> dadosAmigo = new HashMap<>();
        dadosAmigo.put("nome", uAmigo.getNome());
        dadosAmigo.put("caminhoFoto", uAmigo.getCaminhoFoto());

        DatabaseReference seguidorRef = seguidoresRef
                .child(uLogado.getId())
                .child(uAmigo.getId());

        seguidorRef.setValue(dadosAmigo);

        btnEditarPerfil.setText("Seguindo");
        btnEditarPerfil.setOnClickListener(null);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
