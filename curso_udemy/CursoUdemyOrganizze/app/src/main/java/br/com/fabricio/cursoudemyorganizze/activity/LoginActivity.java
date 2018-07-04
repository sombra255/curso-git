package br.com.fabricio.cursoudemyorganizze.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import br.com.fabricio.cursoudemyorganizze.R;
import br.com.fabricio.cursoudemyorganizze.config.ConfiguracaoFirebase;
import br.com.fabricio.cursoudemyorganizze.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.loginEdtEmail);
        edtSenha = findViewById(R.id.loginEdtSenha);
        btnEntrar = findViewById(R.id.loginBtnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = edtEmail.getText().toString();
                String textoSenha = edtSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        logarUsuario(usuario);
                    } else {
                        Toast.makeText(LoginActivity.this, "Campo senha vazio!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Campo email vazio!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void logarUsuario(Usuario usuario) {
        auth = ConfiguracaoFirebase.autenticacaoFirebase();

        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            abrirTelaPrincipal();
                        } else {
                            String excecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                excecao = "Usuário não cadastrado!";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                excecao = "Email e senha não correspondem a um usuário cadastrado!";
                            } catch (Exception e) {
                                excecao = "Erro ao logar usuário "+ e.getMessage();
                                e.printStackTrace();
                            }


                            Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void abrirTelaPrincipal() {
        startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
        finish();
    }
}
