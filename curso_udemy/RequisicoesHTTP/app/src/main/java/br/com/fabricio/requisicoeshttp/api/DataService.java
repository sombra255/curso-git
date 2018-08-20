package br.com.fabricio.requisicoeshttp.api;

import java.util.List;

import br.com.fabricio.requisicoeshttp.model.Foto;
import br.com.fabricio.requisicoeshttp.model.Postagem;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {

    @GET("/photos")
    Call<List<Foto>> recuperarFotos();

    @GET("/posts")
    Call<List<Postagem>> recuperarPostagens();
}
