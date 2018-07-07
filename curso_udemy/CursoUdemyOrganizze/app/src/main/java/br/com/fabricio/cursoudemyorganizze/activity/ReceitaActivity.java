package br.com.fabricio.cursoudemyorganizze.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.fabricio.cursoudemyorganizze.R;
import br.com.fabricio.cursoudemyorganizze.config.ConfiguracaoFirebase;
import br.com.fabricio.cursoudemyorganizze.enums.TipoMovimentacao;
import br.com.fabricio.cursoudemyorganizze.model.Movimentacao;
import br.com.fabricio.cursoudemyorganizze.model.Usuario;
import br.com.fabricio.cursoudemyorganizze.utils.Base64Custom;
import br.com.fabricio.cursoudemyorganizze.utils.DateCustom;

public class ReceitaActivity extends AppCompatActivity {

    private EditText edtReceita;
    private TextInputEditText edtDataReceita, edtCategoriaReceita, edtDescricaoReceita;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.autenticacaoFirebase();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        edtReceita = findViewById(R.id.edtReceita);
        edtDataReceita = findViewById(R.id.edtDataReceita);
        edtCategoriaReceita = findViewById(R.id.edtCategoriaReceita);
        edtDescricaoReceita = findViewById(R.id.edtDescricaoReceita);

        edtDataReceita.setText(DateCustom.dataAtualCustom());

        recuperaReceitaTotal();
    }

    public void salvarReceita(View view){

        if(validarCamposReceita()) {
            movimentacao = new Movimentacao();
            String data = edtDataReceita.getText().toString();
            Double valorRecuperado = Double.parseDouble(edtReceita.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(edtCategoriaReceita.getText().toString());
            movimentacao.setDescricao(edtDescricaoReceita.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo(TipoMovimentacao.RECEITA.getDescricao());

            Double receitaAtualizada = receitaTotal + valorRecuperado;
            atualizarReceita(receitaAtualizada);
            movimentacao.salvar(data);
            finish();
        }
    }

    private boolean validarCamposReceita() {
        String textoReceita = edtReceita.getText().toString();
        String textoData = edtDataReceita.getText().toString();
        String textoCategoria = edtCategoriaReceita.getText().toString();
        String textoDescricao = edtDescricaoReceita.getText().toString();

        if (!textoReceita.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoCategoria.isEmpty()) {
                    if (!textoDescricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(ReceitaActivity.this, "Campo descrição vazio!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else {
                    Toast.makeText(ReceitaActivity.this, "Campo categoria vazio!", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(ReceitaActivity.this, "Campo data vazio!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(ReceitaActivity.this, "Campo receita vazio!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void recuperaReceitaTotal(){
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());;
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarReceita(Double receita){
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(receita);



    }
}
