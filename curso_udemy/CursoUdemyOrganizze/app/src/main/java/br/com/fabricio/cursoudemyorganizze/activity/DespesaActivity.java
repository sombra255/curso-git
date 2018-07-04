package br.com.fabricio.cursoudemyorganizze.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import br.com.fabricio.cursoudemyorganizze.R;
import br.com.fabricio.cursoudemyorganizze.utils.DateCustom;

public class DespesaActivity extends AppCompatActivity {

    private EditText edtDespesa;
    private TextInputEditText edtDataDespesa, edtCategoriaDespesa, edtDescricaoDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        edtDespesa = findViewById(R.id.edtDespesa);
        edtDataDespesa = findViewById(R.id.edtDataDespesa);
        edtCategoriaDespesa = findViewById(R.id.edtCategoriaDespesa);
        edtDescricaoDespesa = findViewById(R.id.edtDescricaoDespesa);

        edtDataDespesa.setText(DateCustom.dataAtualCustom());
    }
}
