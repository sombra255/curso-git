package br.com.fabricio.requisicoeshttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.requisicoeshttp.api.CEPService;
import br.com.fabricio.requisicoeshttp.api.DataService;
import br.com.fabricio.requisicoeshttp.model.CEP;
import br.com.fabricio.requisicoeshttp.model.Foto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnRecuperar;
    private TextView txtRecuperar;
    private Retrofit retrofit;
    private Retrofit retrofitFotos;
    private List<Foto> lsFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecuperar = findViewById(R.id.btnRecuperar);
        txtRecuperar = findViewById(R.id.txtRecuperar);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitFotos = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recuperarCepRetrofit();
                recuperarListaRetrofit();



//                MyTask myTask = new MyTask();
//                myTask.execute("https://blockchain.info/ticker");
//                myTask.execute("https://viacep.com.br/ws/30830120/json/");
            }
        });

    }

    private void recuperarCepRetrofit(){
        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> cepCall = cepService.recuperarCEP("30830120");
        cepCall.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cep = response.body();
                    txtRecuperar.setText(cep.getLogradouro() + "/" + cep.getBairro() + "/" + cep.getCep());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
            }
        });
    }

    private void recuperarListaRetrofit(){
        DataService dataService = retrofitFotos.create(DataService.class);
        Call<List<Foto>> listCallFotos = dataService.recuperarFotos();
        listCallFotos.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    lsFotos = response.body();

                    for(Foto f : lsFotos){
                        Log.d("FOTO", f.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });
    }









    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            InputStream inputStream = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            StringBuilder texto = new StringBuilder();

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                inputStream = conexao.getInputStream();
                //le os dados em bytes e decodifica em caracteres
                reader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(reader);
                String linha = "";
                while ((linha = bufferedReader.readLine()) != null){
                    texto.append(linha);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return texto.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            String logradouro = null;
//            String cep = null;
//            String complemento = null;
//            String bairro = null;
//            String localidade = null;
//            String uf = null;
            String valor = null;
            String valorMoeda = null;
            String simbolo = null;

            try {
//                JSONObject jsonObject = new JSONObject(s);
//                logradouro = jsonObject.getString("logradouro");
//                cep = jsonObject.getString("cep");
//                complemento = jsonObject.getString("complemento");
//                bairro = jsonObject.getString("bairro");
//                localidade = jsonObject.getString("localidade");
//                uf = jsonObject.getString("uf");

                JSONObject jsonObject = new JSONObject(s);
                valor = jsonObject.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(valor);
                valorMoeda = jsonObjectReal.getString("last");
                simbolo = jsonObjectReal.getString("symbol");

            } catch (JSONException e) {
                e.printStackTrace();
            }


//            txtRecuperar.setText(logradouro+"/"+cep+"/"+complemento);
            txtRecuperar.setText(valorMoeda +"/"+ simbolo);

        }

    }
}
