package br.com.fabricio.youtubeclone.api;

import br.com.fabricio.youtubeclone.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*
     https://www.googleapis.com/youtube/v3/search
     ?part=snippet
     &order=date
     &maxResults=20
     &key=AIzaSyByjtW4OSh1-nHfFtl00xkrY0r7M9jBTFQ
     &channelId=UCul_1rve3LY2-VX-gEkVhpw
     &q=texto pesquisa
    * */

    @GET("search")
    Call<Resultado> recuperarVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("q") String q
    );
}
