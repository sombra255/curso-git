package br.com.fabricio.instagramclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.adapter.ComentarioAdapter;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Comentario;
import br.com.fabricio.instagramclone.model.Usuario;

public class ComentariosActivity extends AppCompatActivity {

    private EditText edtComentario;
    private String idPostagem;
    private Usuario usuarioLogado;
    private RecyclerView recyclerViewComentarios;
    private ValueEventListener valueEventListenerComentarios;
    private DatabaseReference firebaseRef;
    private DatabaseReference comentariosRef;
    private List<Comentario> lsComentarios = new ArrayList<>();
    private ComentarioAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        lsComentarios.clear();
        carregaListaComentarios();
    }


    @Override
    protected void onStop() {
        super.onStop();
        comentariosRef.removeEventListener(valueEventListenerComentarios);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        inicializarComponentes();

        firebaseRef = FirebaseHelper.getDatabaseReference();
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Comentários");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            idPostagem = bundle.getString("idPostagem");
        }


        adapter = new ComentarioAdapter(getApplicationContext(), lsComentarios);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewComentarios.setLayoutManager(layoutManager);
        recyclerViewComentarios.setHasFixedSize(true);
        recyclerViewComentarios.setAdapter(adapter);


    }

    private void inicializarComponentes() {
        edtComentario = findViewById(R.id.edtComentario);
        recyclerViewComentarios = findViewById(R.id.recyclerViewComentarios);
    }

    public void salvarComentario(View view){

        String textoComentario = edtComentario.getText().toString();

        if(textoComentario != null && !textoComentario.equals("")){
            Comentario comentario = new Comentario();
            comentario.setIdPostagem(idPostagem);
            comentario.setIdUsuario(usuarioLogado.getId());
            comentario.setNomeUsuario(usuarioLogado.getNome());
            comentario.setCaminhoFoto(usuarioLogado.getCaminhoFoto());
            comentario.setComentario(textoComentario);

            if(comentario.salvar()){
                Toast.makeText(this, "Comentário salvo com sucesso!", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Insira o comentário antes de salvar!", Toast.LENGTH_SHORT).show();
        }

        edtComentario.setText("");


    }

    private void carregaListaComentarios() {

        comentariosRef = firebaseRef.child("comentarios").child(idPostagem);
        valueEventListenerComentarios = comentariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsComentarios.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds != null) {
                        Comentario comentario = ds.getValue(Comentario.class);
                        lsComentarios.add(comentario);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
