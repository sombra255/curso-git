package br.com.fabricio.appguanabaracasa.activities;

import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.fabricio.appguanabaracasa.R;
import br.com.fabricio.appguanabaracasa.json.BuscaOrigemTask;
import br.com.fabricio.appguanabaracasa.json.WebClient;

public class CompraActivity extends AppCompatActivity {

    private Button btn_somente_ida;
    private Button btn_ida_volta;
    private Button btn_data_retorno;
    private Button btn_origem;
    private Button btn_destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        btn_somente_ida = (Button) findViewById(R.id.compra_btn_ida);
        btn_ida_volta = (Button) findViewById(R.id.compra_btn_ida_volta);
        btn_data_retorno = (Button) findViewById(R.id.compra_btn_data_retorno);
        btn_origem = (Button) findViewById(R.id.compra_btn_origem);
        btn_destino = (Button) findViewById(R.id.compra_btn_destino);


        btn_somente_ida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayerDrawable fundo_botao_ida = (LayerDrawable) getResources().getDrawable(R.drawable.fundo_botao_somente_ida_ativo);
                btn_somente_ida.setBackground(fundo_botao_ida);

                LayerDrawable fundo_botao_ida_volta = (LayerDrawable) getResources().getDrawable(R.drawable.fundo_botao_ida_volta_inativo);
                btn_ida_volta.setBackground(fundo_botao_ida_volta);

                btn_data_retorno.setEnabled(Boolean.FALSE);
                btn_data_retorno.setVisibility(View.INVISIBLE);

                btn_origem.setText("Rio de Janeiro");
                btn_destino.setText("Santa Catarina");
            }
        });

        btn_ida_volta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayerDrawable fundo_botao_ida = (LayerDrawable) getResources().getDrawable(R.drawable.fundo_botao_somente_ida_inativo);
                btn_somente_ida.setBackground(fundo_botao_ida);

                LayerDrawable fundo_botao_ida_volta = (LayerDrawable) getResources().getDrawable(R.drawable.fundo_botao_ida_volta_ativo);
                btn_ida_volta.setBackground(fundo_botao_ida_volta);

                btn_data_retorno.setEnabled(Boolean.TRUE);
                btn_data_retorno.setVisibility(View.VISIBLE);

                btn_origem.setText("Belo Horizonte");
                btn_destino.setText("São Paulo ");
            }
        });

        btn_origem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BuscaOrigemTask(CompraActivity.this).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_compras, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
