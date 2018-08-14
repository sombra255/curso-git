package br.com.fabricio.instagramclone.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.adapter.MiniaturaAdapter;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Postagem;
import br.com.fabricio.instagramclone.model.Usuario;
import br.com.fabricio.instagramclone.utils.RecyclerItemClickListener;

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
    private List<ThumbnailItem> listaFiltros = new ArrayList<>();
    private MiniaturaAdapter adapter;
    private Usuario usuarioLogado;
    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioLogadoRef;
    private AlertDialog dialog;
//    private ProgressBar progressBarFiltro;
//    private boolean estaCarregado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);

        usuariosRef = FirebaseHelper.getDatabaseReference().child("usuarios");
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        recuperarDadosUsuarioLogado();


        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Filtros");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);


        inicializarComponentes();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            byte[] dadosImagem = bundle.getByteArray("fotoEscolhida");
            imagem = BitmapFactory.decodeByteArray(dadosImagem, 0, dadosImagem.length);
            fotoEscolhida.setImageBitmap(imagem);

            imagemFiltro = imagem.copy(imagem.getConfig(), true);

            adapter = new MiniaturaAdapter(listaFiltros, getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewFiltros.setLayoutManager(layoutManager);
            recyclerViewFiltros.setAdapter(adapter);

            recyclerViewFiltros.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerViewFiltros, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ThumbnailItem item = listaFiltros.get(position);

                    imagemFiltro = imagem.copy(imagem.getConfig(), true);
                    Filter filter = item.filter;
                    fotoEscolhida.setImageBitmap(filter.processFilter(imagemFiltro));
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            }));


            recuperarFiltros();
        }
    }

    private void recuperarDadosUsuarioLogado(){
//        carregando(true);
        abrirDialogCarregamento("Carregando dados, aguarde!");
        usuarioLogadoRef = usuariosRef.child(usuarioLogado.getId());
        usuarioLogadoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioLogado = dataSnapshot.getValue(Usuario.class);
//                carregando(false);
                dialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void abrirDialogCarregamento(String titulo){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setCancelable(false);
        alert.setView(R.layout.carregamento);
        dialog = alert.create();
        dialog.show();

    }


//    private void carregando(boolean estado){
//        if(estado){
//            estaCarregado = true;
//            progressBarFiltro.setVisibility(View.VISIBLE);
//        }else{
//            estaCarregado = false;
//            progressBarFiltro.setVisibility(View.GONE);
//        }
//    }

    private void recuperarFiltros() {
        ThumbnailsManager.clearThumbs();
        listaFiltros.clear();

        ThumbnailItem item = new ThumbnailItem();
        item.image = imagem;
        item.filterName = "Normal";
        ThumbnailsManager.addThumb(item);

        List<Filter> filtros = FilterPack.getFilterPack(getApplicationContext());
        for(Filter filtro : filtros){
            ThumbnailItem itemFiltro = new ThumbnailItem();
            itemFiltro.image = imagem;
            itemFiltro.filter = filtro;
            itemFiltro.filterName = filtro.getName();

            ThumbnailsManager.addThumb(itemFiltro);
        }

        listaFiltros.addAll(ThumbnailsManager.processThumbs(getApplicationContext()));
        adapter.notifyDataSetChanged();

    }

    private void inicializarComponentes() {
        fotoEscolhida = findViewById(R.id.filtroFotoEscolhida);
        txtDescricao = findViewById(R.id.filtro_txt_descricao);
        recyclerViewFiltros = findViewById(R.id.recyclerFiltros);
//        progressBarFiltro = findViewById(R.id.progressBarFiltro);

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
                publicarPostagem();
                break;
        }
        return true;
    }

    private void publicarPostagem() {

//        if(estaCarregado){
//            Toast.makeText(getApplicationContext(), "Carregando dados, aguarde!", Toast.LENGTH_SHORT).show();
//        } else {

            abrirDialogCarregamento("Salvando postagem");
            final Postagem postagem = new Postagem();
            postagem.setIdUsuario(usuarioLogado.getId());
            postagem.setDescricao(txtDescricao.getText().toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagemFiltro.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            byte[] dadosImagem = baos.toByteArray();

            StorageReference storageReference = FirebaseHelper.getFirebaseStorage();
            StorageReference imagemRef = storageReference
                    .child("imagens")
                    .child("postagens")
                    .child(postagem.getId() + ".jpeg");

            UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FiltroActivity.this, "Erro ao salvar imagem", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri url = taskSnapshot.getDownloadUrl();
                    postagem.setCaminhoFoto(url.toString());
                    if (postagem.salvar()) {
                        int qntPostagens = usuarioLogado.getPostagens() + 1;
                        usuarioLogado.setPostagens(qntPostagens);
                        usuarioLogado.atualizarQuantidadePostagens();
                        Toast.makeText(FiltroActivity.this, "Sucesso ao salvar imagem", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        finish();
                    } else {
                        Toast.makeText(FiltroActivity.this, "Erro ao salvar imagem", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(FiltroActivity.this, "Sucesso ao salvar postagem", Toast.LENGTH_SHORT).show();
                }
            });
//        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;

    }
}
