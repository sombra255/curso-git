package br.com.fabricio.olxclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.helper.FirebaseHelper;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnAcessar;
    private Switch switchAcesso;
    private FirebaseAuth firebaseAuth;
    private String email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        firebaseAuth = FirebaseHelper.getFirebaseAuth();
        inicializarComponentes();

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                senha = edtSenha.getText().toString();

                if(email.equals("")){
                    Toast.makeText(CadastroActivity.this, "Campo e-mail vazio, favor preencher", Toast.LENGTH_SHORT).show();
                }else {
                    if(senha.equals("")) {
                        Toast.makeText(CadastroActivity.this, "Campo senha vazio, favor preencher", Toast.LENGTH_SHORT).show();
                    }else {
                        if(switchAcesso.isChecked()){
                            cadastrarUsuario();
                        }else {
                            logarUsuario();
                        }
                    }
                }

            }
        });

    }

    private void logarUsuario() {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                            //direciono para a tela principal
                            startActivity(new Intent(getApplicationContext(), AnunciosActivity.class));

                        }else {
                            Toast.makeText(CadastroActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void cadastrarUsuario() {
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            //direciono para a tela principal
                        }else {
                            String erroExcecao = "";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Por favor, digite uma e-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Conta já cadastrada!";
                            }catch (Exception e) {
                                erroExcecao = "erro ao cadastrar" + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this, "Erro: "+erroExcecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void inicializarComponentes() {
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnAcessar = findViewById(R.id.btnAcessar);
        switchAcesso = findViewById(R.id.switchAcesso);
    }
}
