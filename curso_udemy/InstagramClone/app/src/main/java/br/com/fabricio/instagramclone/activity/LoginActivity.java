package br.com.fabricio.instagramclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnLogar;
    private ProgressBar progressBarLogin;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();
        inicializaComponentes();

        progressBarLogin.setVisibility(View.GONE);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaCamposLogin();
            }
        });

    }

    private void verificaCamposLogin() {
        String textoEmail = edtEmail.getText().toString();
        String textoSenha = edtSenha.getText().toString();

        if (!textoEmail.isEmpty()) {
            if (!textoSenha.isEmpty()) {
                logar(textoEmail, textoSenha);
            } else {
                Toast.makeText(LoginActivity.this, "Campo senha vazio!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Campo email vazio!", Toast.LENGTH_SHORT).show();
        }
    }

    private void logar(String email, String senha) {
        progressBarLogin.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseHelper.getFirebaseAuth();
        firebaseAuth.signInWithEmailAndPassword(email, senha)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBarLogin.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else {
                    progressBarLogin.setVisibility(View.GONE);
                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Email não cadastrado ou desativado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Senha inválida";
                    }catch (Exception e){
                        excecao = "Erro ao efetuar login "+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificarUsuarioLogado(){
        firebaseAuth = FirebaseHelper.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void inicializaComponentes() {
        edtEmail = findViewById(R.id.login_edt_email);
        edtSenha = findViewById(R.id.login_edt_senha);
        btnLogar = findViewById(R.id.login_btn_entrar);
        progressBarLogin = findViewById(R.id.login_progress_bar);

        edtEmail.requestFocus();
    }
}
