package br.com.fabricio.requisicoeshttp.api;

import br.com.fabricio.requisicoeshttp.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    @GET("{cep}/json/")
    Call<CEP> recuperarCEP(@Path("cep") String cep);
}
