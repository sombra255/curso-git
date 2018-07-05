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

public class DespesaActivity extends AppCompatActivity {

    private EditText edtDespesa;
    private TextInputEditText edtDataDespesa, edtCategoriaDespesa, edtDescricaoDespesa;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.autenticacaoFirebase();
    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        edtDespesa = findViewById(R.id.edtDespesa);
        edtDataDespesa = findViewById(R.id.edtDataDespesa);
        edtCategoriaDespesa = findViewById(R.id.edtCategoriaDespesa);
        edtDescricaoDespesa = findViewById(R.id.edtDescricaoDespesa);

        edtDataDespesa.setText(DateCustom.dataAtualCustom());

        recuperaDespesaTotal();
    }

    public void salvarDespesa(View view){

        if(validarCamposDespesa()) {
            movimentacao = new Movimentacao();
            String data = edtDataDespesa.getText().toString();
            Double valorRecuperado = Double.parseDouble(edtDespesa.getText().toString());
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(edtCategoriaDespesa.getText().toString());
            movimentacao.setDescricao(edtDescricaoDespesa.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo(TipoMovimentacao.DESPESA.getDescricao());

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesa(despesaAtualizada);
            movimentacao.salvar(data);
        }
    }

    private boolean validarCamposDespesa() {
        String textoDespesa = edtDespesa.getText().toString();
        String textoData = edtDataDespesa.getText().toString();
        String textoCategoria = edtCategoriaDespesa.getText().toString();
        String textoDescricao = edtDescricaoDespesa.getText().toString();

        if (!textoDespesa.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoCategoria.isEmpty()) {
                    if (!textoDescricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(DespesaActivity.this, "Campo descrição vazio!", Toast.LENGTH_LONG).show();
                        return false;
                    }
                } else {
                    Toast.makeText(DespesaActivity.this, "Campo categoria vazio!", Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(DespesaActivity.this, "Campo data vazio!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(DespesaActivity.this, "Campo despesa vazio!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void recuperaDespesaTotal(){
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());;
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarDespesa(Double despesa){
        String idUsuario = Base64Custom.encodeToString(firebaseAuth.getCurrentUser().getEmail());;
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);



    }
}
