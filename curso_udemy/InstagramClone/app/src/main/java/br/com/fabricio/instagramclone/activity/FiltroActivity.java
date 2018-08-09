package br.com.fabricio.instagramclone.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;

import br.com.fabricio.instagramclone.R;

public class FiltroActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    private ImageView fotoEscolhida;
    private Bitmap imagem;
    private Bitmap imagemFiltro;
    private TextInputEditText txtDescricao;
    private RecyclerView recyclerViewFiltros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Filtros");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


        inicializarComponentes();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewFiltros.setLayoutManager(layoutManager);
//        recyclerViewFiltros.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
            fotoEscolhida.setImageBitmap(imagem);

            imagemFiltro = imagem.copy(imagem.getConfig(), true);

            Filter filter = FilterPack.getAdeleFilter(getApplicationContext());
            fotoEscolhida.setImageBitmap(filter.processFilter(imagemFiltro));
        }
    }

    private void inicializarComponentes() {
        fotoEscolhida = findViewById(R.id.filtroFotoEscolhida);
        txtDescricao = findViewById(R.id.filtro_txt_descricao);
        recyclerViewFiltros = findViewById(R.id.recyclerFiltros);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filtro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ic_salvar_postagem:
                break;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;

    }
}
