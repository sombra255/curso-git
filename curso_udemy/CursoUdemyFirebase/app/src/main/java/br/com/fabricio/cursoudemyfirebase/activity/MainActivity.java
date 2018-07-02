package br.com.fabricio.cursoudemyfirebase.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import br.com.fabricio.cursoudemyfirebase.R;
import br.com.fabricio.cursoudemyfirebase.model.Produtos;
import br.com.fabricio.cursoudemyfirebase.model.Usuarios;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ImageView imageView;
    private Button buttonUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        buttonUpload = findViewById(R.id.btnUpload);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // configuracao imagem ser salva em memoria
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();

                //Recupera o bitmap da imagem
                Bitmap bitmap = imageView.getDrawingCache();

                //Comprimindo imagem para algum formato png/jpg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

                //converte o baos para pixel brutos em uma matriz de bytes
                byte[] dadosImagem = baos.toByteArray();

                //Define os nos para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");
                StorageReference imagemRef = imagens.child("celular.jpeg");


                Glide.with(MainActivity.this)
                        .using(new FirebaseImageLoader())
                        .load(imagemRef)
                        .into(imageView);



//                StorageReference imagemRef = imagens.child("e841a7ed-ce99-4dcb-8bad-ecb8a42eed3d.jpeg");
//                imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Erro ao deletar Imagem" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
//                    }
//                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(MainActivity.this, "Sucesso ao deletar imagem", Toast.LENGTH_LONG).show();
//                    }
//                });

                /*
                //Nome Da Imagem
                String nomeArquivo = UUID.randomUUID().toString();
                StorageReference imagemRef = imagens.child(nomeArquivo+".jpeg");

                //Retorna o objeto que ira controlar o upload
                UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Toast.makeText(MainActivity.this, "Upload de Imagem Falhou"+ e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri url = taskSnapshot.getDownloadUrl();
                        Toast.makeText(MainActivity.this, "Sucesso Upload de Imagem" + url.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                    */
            }
        });




        /* Criacao de filtros
        DatabaseReference usuarios = referencia.child("usuarios");
//        DatabaseReference usuarioPesquisa = usuarios.child("-LGN9oGPxn-stHMY4Y2s");
//        Query usuarioPesquisa = usuarios.orderByChild("nome").equalTo("Fabricio");
//        Query usuarioPesquisa = usuarios.orderByKey().limitToFirst(2);
//        Query usuarioPesquisa = usuarios.orderByKey().limitToLast(2);

//        Query usuarioPesquisa = usuarios.orderByChild("idade").startAt(28).endAt(28);
//        Query usuarioPesquisa = usuarios.orderByChild("nome")
//                .startAt("Fa")
//                .endAt("Fe"+ "\uf8ff");


        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios dadosUsuarios = dataSnapshot.getValue(Usuarios.class);
                Log.i("DadosUsuario", dataSnapshot.getValue().toString());
                Log.i("DadosUsuario1", dadosUsuarios.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        */



        /* Criar id unico
        DatabaseReference usuarios = referencia.child("usuarios");
        Usuarios u = new Usuarios();
        u.setNome("Ana Maria");
        u.setSobrenome("Joaquina");
        u.setIdade(68);

        usuarios.push().setValue(u); //push cria um id unico

        */

        /* Deslogar/Autenticar Usuario
//        firebaseAuth.signOut(); //desloga o usuario

        firebaseAuth.signInWithEmailAndPassword("fabricio.oliveira255@gmail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("SignIn", "Sucesso ao logar usuario");
                        }else {
                            Log.i("SignIn", "Falha ao logar usuario");
                        }
                    }
                });

//        if(firebaseAuth.getCurrentUser() != null){
//            Log.i("UsuarioLogado", "true");
//        }else {
//            Log.i("UsuarioLogado", "false");
//        }


            */



        /* Verifica Usuario Logado
        if(firebaseAuth.getCurrentUser() != null){
            Log.i("UsuarioLogado", "true");
        }else {
            Log.i("UsuarioLogado", "false");
        }

        */

        /* Cadastro Usuario */
//        firebaseAuth.createUserWithEmailAndPassword("fabricio4.oliveira255@gmail.com", "123456")
//                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Log.i("CreateUser", "Sucesso ao cadastrar");
//                        }else {
//                            Log.i("CreateUser", "Falha ao cadastrar");
//                        }
//                    }
//                });




        /* //Recuperando dados no FireBase

//        DatabaseReference usuarios = referencia.child("usuarios"); //recupera todos os usuarios
        DatabaseReference usuarios = referencia.child("usuarios").child("001"); //recupera um usuario especifico
        DatabaseReference produtos = referencia.child("produtos");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/


/*  //Salvando dados no FireBase

//        referencia.child("usuarios2").child("001").child("nome").setValue("Fabricio");

        Usuarios u = new Usuarios();
        u.setNome("Fabricio");
        u.setSobrenome("Oliveira");
        u.setIdade(28);

        DatabaseReference usuarios = referencia.child("usuarios");
        usuarios.child("001").setValue(u);

        Usuarios u = new Usuarios();
        u.setNome("Joao");
        u.setSobrenome("Silva");
        u.setIdade(54);

        DatabaseReference usuarios = referencia.child("usuarios");
        usuarios.child("002").setValue(u);

        Produtos p = new Produtos();
        p.setDescricao("Iphone");
        p.setMarca("Apple");
        p.setPreco(123.34);

        DatabaseReference produtos = referencia.child("produtos");
        produtos.child("001").setValue(p);

        */

    }
}
