package br.com.fabricio.tarefasassincronas;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
    }

    private void iniciar(View view){

    }

    /**
     * 1 = parametro a ser passado para a classe
     * 2 = tipo de valor que sera utilizado para o progresso da tareja
     * 3 = retorno apos finalizar a execucao da tarefa
     */
    class MyAsyncTask extends AsyncTask<Integer, Integer, String>{

        @Override
        protected String doInBackground(Integer... integers) {
            return null;
        }
    }

}
