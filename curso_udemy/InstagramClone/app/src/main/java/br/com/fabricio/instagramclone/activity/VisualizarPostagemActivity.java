package br.com.fabricio.instagramclone.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.model.Postagem;
import br.com.fabricio.instagramclone.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

public class VisualizarPostagemActivity extends AppCompatActivity {

    private TextView txtNomeUsuario, txtCurtidas, txtDescricao, txtComentario;
    private ImageView fotoPostagem;
    private CircleImageView imagem;
    private Postagem postagem;
    private Usuario usuarioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_postagem);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Visualizar postagem");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        inicializarComponentes();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            postagem = (Postagem) bundle.getSerializable("postagem");
            usuarioSelecionado = (Usuario) bundle.getSerializable("usuarioSelecionado");

            if(usuarioSelecionado.getCaminhoFoto() != null) {
                Uri uri = Uri.parse(usuarioSelecionado.getCaminhoFoto());
                Glide.with(VisualizarPostagemActivity.this).load(uri).into(imagem);
            }
            txtNomeUsuario.setText(usuarioSelecionado.getNome());

            Uri uriPostagem = Uri.parse(postagem.getCaminhoFoto());
            Glide.with(VisualizarPostagemActivity.this).load(uriPostagem).into(fotoPostagem);
            txtDescricao.setText(postagem.getDescricao());

        }
    }

    private void inicializarComponentes() {
        txtNomeUsuario = findViewById(R.id.visualiza_postagem_txtNome);
//        txtComentario = findViewById(R.id.visualiza_postagem_txtComentario);
        txtCurtidas = findViewById(R.id.visualiza_postagem_txtCurtidas);
        txtDescricao = findViewById(R.id.visualiza_postagem_txtDescricao);
        fotoPostagem = findViewById(R.id.visualiza_postagem_fotoPostagem);
        imagem = findViewById(R.id.visualiza_postagem_imagem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
