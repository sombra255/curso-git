package br.com.fabricio.appguanabaracasa.model;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.fabricio.appguanabaracasa.R;
import br.com.fabricio.appguanabaracasa.activities.FormularioActivity;

/**
 * Created by Fabricio on 08/10/2016.
 */

public class FormularioHelper {

    private final RatingBar rating_nota;
    private final EditText edt_nome;
    private final EditText edt_telefone;
    private final EditText edt_endereco;
    private final EditText edt_email;

    public FormularioHelper(FormularioActivity activity){
        edt_nome = (EditText) activity.findViewById(R.id.formulario_edt_nome);
        edt_telefone = (EditText) activity.findViewById(R.id.formulario_edt_telefone);
        edt_email = (EditText) activity.findViewById(R.id.formulario_edt_email);
        edt_endereco = (EditText) activity.findViewById(R.id.formulario_edt_endereco);
        rating_nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
    }

    public Aluno pegarAluno(){
        Aluno aluno = new Aluno();
        aluno.setNome(edt_nome.getText().toString());
        aluno.setEmail(edt_email.getText().toString());
        aluno.setEndereco(edt_endereco.getText().toString());
        aluno.setTelefone(edt_telefone.getText().toString());
        aluno.setNota(Double.valueOf(rating_nota.getProgress()));

        return aluno;
    }
}
