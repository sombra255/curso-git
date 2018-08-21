package br.com.fabricio.youtubeclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.youtubeclone.R;
import br.com.fabricio.youtubeclone.adapter.AdapterVideos;
import br.com.fabricio.youtubeclone.api.YoutubeService;
import br.com.fabricio.youtubeclone.helper.RecyclerItemClickListener;
import br.com.fabricio.youtubeclone.helper.RetrofitConfig;
import br.com.fabricio.youtubeclone.helper.YoutubeConfig;
import br.com.fabricio.youtubeclone.model.Items;
import br.com.fabricio.youtubeclone.model.Resultado;
import br.com.fabricio.youtubeclone.model.Video;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVideos;
    private AdapterVideos adapter;
    private List<Video> lsVideos = new ArrayList<>();
    private List<Items> lsItems = new ArrayList<>();
    private MaterialSearchView searchView;
    private Retrofit retrofit;

    @Override
    protected void onStart() {
        super.onStart();
        recuperaVideos("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.search_view);

        retrofit = RetrofitConfig.getRetrofit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

//        adapter = new AdapterVideos(getApplicationContext(), lsVideos);
//        recyclerViewVideos.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerViewVideos.setLayoutManager(layoutManager);
//        recyclerViewVideos.setAdapter(adapter);

        searchView.setVoiceSearch(true);

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperaVideos("");
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperaVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void recuperaVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);
        youtubeService.recuperarVideos("snippet","date","20", YoutubeConfig.CHAVE_YOUTUBE_API,YoutubeConfig.CANAL_ID, q)
                .enqueue(new Callback<Resultado>() {
                    @Override
                    public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                        if(response.isSuccessful()){
                            Resultado resultado = response.body();
                            lsItems = resultado.getItems();
                            recuperaRecyclerView();
//                            Log.d("RESPOSTA", "onResponse: "+resultado.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Resultado> call, Throwable t) {

                    }
                });
    }

    private void recuperaRecyclerView(){
        adapter = new AdapterVideos(getApplicationContext(), lsItems);
        recyclerViewVideos.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewVideos.setLayoutManager(layoutManager);
        recyclerViewVideos.setAdapter(adapter);

        recyclerViewVideos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerViewVideos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Items items = lsItems.get(position);
                String idVideo = items.getId().getVideoId();

                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("idVideo", idVideo);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.menu_busca);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_busca:
                break;
        }
        return true;
    }
}
