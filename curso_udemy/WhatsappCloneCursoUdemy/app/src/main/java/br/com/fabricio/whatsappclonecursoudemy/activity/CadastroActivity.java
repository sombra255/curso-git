package br.com.fabricio.whatsappclonecursoudemy.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.Base64Custom;

public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;
    private FirebaseAuth auth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.cadastroEdtNome);
        edtEmail = findViewById(R.id.cadastroEdtEmail);
        edtSenha = findViewById(R.id.cadastroEdtSenha);
        btnCadastrar = findViewById(R.id.cadastroBtnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCadastrarClick();
            }
        });


    }

    private void btnCadastrarClick() {
        String textoNome = edtNome.getText().toString();
        String textoEmail = edtEmail.getText().toString();
        String textoSenha = edtSenha.getText().toString();

        if (!textoNome.isEmpty()) {
            if (!textoEmail.isEmpty()) {
                if (!textoSenha.isEmpty()) {
                    usuario = new Usuario();
                    usuario.setEmail(textoEmail);
                    usuario.setNome(textoNome);
                    usuario.setSenha(textoSenha);
                    cadastrarUsuario(usuario);
                } else {
                    Toast.makeText(CadastroActivity.this, "Campo senha vazio!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CadastroActivity.this, "Campo email vazio!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(CadastroActivity.this, "Campo nome vazio!", Toast.LENGTH_LONG).show();
        }
    }

    private void cadastrarUsuario(final Usuario usuario) {
        auth = FirebaseHelper.autenticacaoFirebase();

        auth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String idUsuario = Base64Custom.encodeToString(usuario.getEmail());
                            usuario.setId(idUsuario);
                            usuario.salvar();
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_LONG).show();
                            UsuarioFirebase.atualizaNomeUsuario(usuario.getNome());
                            finish();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Email inválido!";
                            } catch (FirebaseAuthUserCollisionException e) {
                                excecao = "Usuário já cadastrado";
                            } catch (Exception e) {
                                excecao = "Erro ao cadastrar usuário " + e.getMessage();
                                e.printStackTrace();
                            }


                            Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
