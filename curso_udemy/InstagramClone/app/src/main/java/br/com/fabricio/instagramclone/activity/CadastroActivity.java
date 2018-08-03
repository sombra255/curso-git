package br.com.fabricio.instagramclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;
    private ProgressBar progressBarCadastro;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarCadastro.setVisibility(View.GONE);
                verificaCamposCadastro();
            }
        });

    }

    private void verificaCamposCadastro() {
        String textoNome = edtNome.getText().toString();
        String textoEmail = edtEmail.getText().toString();
        String textoSenha = edtSenha.getText().toString();

        if(!textoNome.isEmpty()){
            if(!textoEmail.isEmpty()){
                if(!textoSenha.isEmpty()){
                    usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    cadastrar(usuario);
                }else {
                    Toast.makeText(CadastroActivity.this, "Campo senha vazio!", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(CadastroActivity.this, "Campo email vazio!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(CadastroActivity.this, "Campo nome vazio!", Toast.LENGTH_SHORT).show();
        }
    }


    private void cadastrar(final Usuario usuario){
        progressBarCadastro.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseHelper.getFirebaseAuth();
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            try{
                                progressBarCadastro.setVisibility(View.GONE);
                                String idUsuario = task.getResult().getUser().getUid();
                                usuario.setId(idUsuario);
                                usuario.salvar();
                                Toast.makeText(CadastroActivity.this, "Usuario Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else {
                            progressBarCadastro.setVisibility(View.GONE);
                            String erroExcecao = "";

                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "Digite um e-mail válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Conta já cadastrada";
                            }catch (Exception e){
                                erroExcecao = "ao cadastrar usuario" + e.getMessage();
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this, "Erro: "+erroExcecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void inicializaComponentes() {
        edtNome = findViewById(R.id.cadastro_edt_nome);
        edtEmail = findViewById(R.id.cadastro_edt_email);
        edtSenha = findViewById(R.id.cadastro_edt_senha);
        btnCadastrar = findViewById(R.id.cadastro_btn_cadastrar);
        progressBarCadastro = findViewById(R.id.cadastro_progress_bar);
    }
}
