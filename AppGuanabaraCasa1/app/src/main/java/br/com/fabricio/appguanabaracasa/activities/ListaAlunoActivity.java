package br.com.fabricio.appguanabaracasa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.fabricio.appguanabaracasa.R;
import br.com.fabricio.appguanabaracasa.adapter.AdapterAluno;
import br.com.fabricio.appguanabaracasa.model.Aluno;

public class ListaAlunoActivity extends AppCompatActivity {

    private List<Aluno> listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aluno);

        ListView lsAlunos = (ListView) findViewById(R.id.listaAluno_lsAlunos);
        lsAlunos.setFastScrollEnabled(true);

        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.cabecalho_lista_aluno, lsAlunos, false);
        lsAlunos.addHeaderView(headerView);
//        String[] alunos = {"a", "b", "c","d", "e", "f","g", "h", "i", "j", "k", "l", "m", "n"};
//        ArrayAdapter<String> listaDeAluno = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , alunos);

        criarAlunos();

        Collections.sort(listaAlunos, new Comparator<Aluno>(){
            public int compare(Aluno p1, Aluno p2){
                return p1.getNome().compareTo(p2.getNome());
            }
        });

        AdapterAluno adapterAluno = new AdapterAluno(this, listaAlunos);
        lsAlunos.setAdapter(adapterAluno);

        Button btn_novo_aluno = (Button) findViewById(R.id.listaAluno_btn_novo_aluno);
        btn_novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunoActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void criarAlunos() {
        listaAlunos = new ArrayList<Aluno>();

        for(int i = 0; i< 15; i++) {

            Aluno a = new Aluno(1L, "Fabricio", "333333333", "eumesmo@oi.com.br", "Rua Teste", 10.0);
            Aluno b = new Aluno(2L, "Jose", "444444444444", "eumesmo@oi.com.br", "Rua Teste", 11.0);
            Aluno c = new Aluno(3L, "Pedro", "5555555555", "eumesmo@oi.com.br", "Rua Teste", 12.0);
            Aluno d = new Aluno(4L, "Joao", "66666666", "eumesmo@oi.com.br", "Rua Teste", 13.0);
            Aluno e = new Aluno(5L, "Alberto", "777777777777", "eumesmo@oi.com.br", "Rua Teste", 13.0);
            listaAlunos.add(a);
            listaAlunos.add(b);
            listaAlunos.add(c);
            listaAlunos.add(d);
            listaAlunos.add(e);
        }
    }
}
