package br.com.fabricio.olxclone.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.model.Anuncio;

public class DetalhesProdutoActivity extends AppCompatActivity {

    private CarouselView carouselView;
    private TextView txtTitulo, txtValor, txtEstado, txtDescricao;
    private Button btnVerTelefone;
    private Anuncio anuncioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        inicializarComponentes();

        getSupportActionBar().setTitle("Detalhe Produto");

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            anuncioSelecionado = (Anuncio) bundle.getSerializable("anuncioSelecionado");
            if(anuncioSelecionado != null){
                preencheDadosAnuncio();
            }
        }

        btnVerTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(), null));
                startActivity(intent);
            }
        });
    }

    private void preencheDadosAnuncio() {
        txtTitulo.setText(anuncioSelecionado.getTitulo());
        txtValor.setText(anuncioSelecionado.getValor());
        txtDescricao.setText(anuncioSelecionado.getDescricao());
        anuncioSelecionado.setEstado(anuncioSelecionado.getEstado());

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                String urlString = anuncioSelecionado.getLsFotos().get(position);
                Glide.with(getApplicationContext()).load(urlString).into(imageView);
            }
        };

        carouselView.setPageCount(anuncioSelecionado.getLsFotos().size());
        carouselView.setImageListener(imageListener);
    }

    private void inicializarComponentes() {
        carouselView = findViewById(R.id.carouselView);
        txtTitulo = findViewById(R.id.detalhe_produtos_txtTitulo);
        txtValor = findViewById(R.id.detalhe_produtos_txtValor);
        txtEstado = findViewById(R.id.detalhe_produtos_txtEstado);
        txtDescricao = findViewById(R.id.detalhe_produtos_txtDescricao);
        btnVerTelefone = findViewById(R.id.detalhe_produtos_btnTelefone);
    }
}
