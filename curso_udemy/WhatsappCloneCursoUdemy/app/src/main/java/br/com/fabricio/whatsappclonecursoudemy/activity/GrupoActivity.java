package br.com.fabricio.whatsappclonecursoudemy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.adapter.ContatosAdapter;
import br.com.fabricio.whatsappclonecursoudemy.adapter.GrupoSelecionadoAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.RecyclerItemClickListener;

public class GrupoActivity extends AppCompatActivity {

    private RecyclerView recyclerMembros, recyclerMembrosSelecionados;
    private ContatosAdapter adapterContatos;
    private List<Usuario> lsMembros = new ArrayList<>();
    private List<Usuario> lsMembrosSelecionados = new ArrayList<>();
    private DatabaseReference usuariosRef;
    private ValueEventListener valueEventListenerMembros;
    private FirebaseUser usuarioAtual;
    private GrupoSelecionadoAdapter adapterContatosSelecionados;
    private Toolbar toolbar;
    private FloatingActionButton fabAvancarCadastro;

    @Override
    protected void onStart() {
        super.onStart();
        recuperarContatos();
    }


    @Override
    protected void onStop() {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListenerMembros);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        fabAvancarCadastro = findViewById(R.id.fabAvancarCadastro);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Novo Grupo");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerMembros = findViewById(R.id.recyclerMembros);
        recyclerMembrosSelecionados = findViewById(R.id.recyclerMembrosSelecionados);
        usuarioAtual = UsuarioFirebase.getFirebaseUser();
        usuariosRef = FirebaseHelper.getFirebaseDatabase().child("usuarios");

        adapterContatos = new ContatosAdapter(lsMembros, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMembros.setLayoutManager(layoutManager);
        recyclerMembros.setHasFixedSize(true);
        recyclerMembros.setAdapter(adapterContatos);


        recyclerMembros.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerMembros, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Usuario usuarioSelecionado = lsMembros.get(position);

                //remover usuario selecionado
                lsMembros.remove(usuarioSelecionado);
                adapterContatos.notifyDataSetChanged();

                //adiciona o usuario na nova lista
                lsMembrosSelecionados.add(usuarioSelecionado);
                adapterContatosSelecionados.notifyDataSetChanged();

                atualizarMembrosToolbar();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        adapterContatosSelecionados = new GrupoSelecionadoAdapter(lsMembrosSelecionados, getApplicationContext());

        RecyclerView.LayoutManager layoutManagerMembrosSelecionados = new LinearLayoutManager(getApplicationContext(), LinearLayout.HORIZONTAL, false);
        recyclerMembrosSelecionados.setLayoutManager(layoutManagerMembrosSelecionados);
        recyclerMembrosSelecionados.setHasFixedSize(true);
        recyclerMembrosSelecionados.setAdapter(adapterContatosSelecionados);

        recyclerMembrosSelecionados.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerMembrosSelecionados, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //remove lista membros selecionados
                Usuario usuarioSelecionado = lsMembrosSelecionados.get(position);
                lsMembrosSelecionados.remove(usuarioSelecionado);
                adapterContatosSelecionados.notifyDataSetChanged();

                //adiciona lista de membros
                lsMembros.add(usuarioSelecionado);
                adapterContatos.notifyDataSetChanged();
                atualizarMembrosToolbar();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        fabAvancarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GrupoActivity.this, CadastroGrupoActivity.class);
                i.putExtra("membrosSelecionados", (Serializable) lsMembrosSelecionados);
                startActivity(i);
            }
        });

    }

    private void atualizarMembrosToolbar() {
        int totalSelecionados = lsMembrosSelecionados.size();
        int total = lsMembros.size() + totalSelecionados;
        toolbar.setSubtitle(totalSelecionados + " de " + total + " selecionados");
    }

    public void recuperarContatos(){
        valueEventListenerMembros = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lsMembros.clear();

                for (DataSnapshot d : dataSnapshot.getChildren()){
                    Usuario usuario = d.getValue(Usuario.class);

                    if(!usuarioAtual.getEmail().equals(usuario.getEmail())){
                        lsMembros.add(usuario);
                    }
                }

                adapterContatos.notifyDataSetChanged();
                atualizarMembrosToolbar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
