package br.com.fabricio.entendendothreads;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar;
    private int numero;
    private Handler handler = new Handler();
    private boolean pararExecucao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciaThread);
    }

    private void iniciarThread(View view){

        //nao pode executar esse trecho na thread principal porque da erro pois não pode ocupar a thread principal
//        for (int i = 0; i < 15; i++) {
//            Log.d("Thread", "iniciarThread: "+ i);
//
//            try {
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }

//        MyThread myThread = new MyThread();
//        myThread.start();

        pararExecucao = false;
        MyRunnable runnable = new MyRunnable();
        new Thread(runnable).start();
    }

    private void pararExecucao(View view){
        pararExecucao = true;
    }

    class MyRunnable implements Runnable{

        @Override
        public void run() {

            for (int i = 0; i < 15; i++) {

                if(pararExecucao)
                    return;

                numero = i;
                Log.d("Thread", "iniciarThread: "+ i);
//                btnIniciar.setText("contador" + i); //ocasiona erro pois vc nao pode alterar um componente de interface na uithread de uma thread secundaria

                //esse metodo envia os comandos para que eles sejam alterados na thread principal e dessa maneira é possivel realizar essas alterações
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        btnIniciar.setText("contador" + numero);
//                    }
//                });

                //alternativa para a execucao acima, utilizando o handler vc pode determinar um tempo de delay para que a execucao seja realizada
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnIniciar.setText("contador" + numero);
                    }
                });

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 15; i++) {
                Log.d("Thread", "iniciarThread: "+ i);

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
