package br.com.fabricio.appguanabaracasa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import br.com.fabricio.appguanabaracasa.R;

public class ListaAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        ListView lsAlunos = (ListView) findViewById(R.id.listaAluno_lsAlunos);
        String[] alunos = {"a", "b", "c","d", "e", "f","g", "h", "i"};
        ArrayAdapter<String> listaDeAluno = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , alunos);
        lsAlunos.setAdapter(listaDeAluno);

        Button btn_novo_aluno = (Button) findViewById(R.id.listaAluno_btn_novo_aluno);
        btn_novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }
}
