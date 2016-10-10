package br.com.fabricio.appguanabaracasa.json;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Fabricio on 09/10/2016.
 */

public class BuscaOrigemTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    public BuscaOrigemTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient webClient = new WebClient();
        String resposta = webClient.post("");
        return resposta;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde", "Testando...", true, true);
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
