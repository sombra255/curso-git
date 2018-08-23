package br.com.fabricio.olxclone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.adapter.AnuncioAdapter;
import br.com.fabricio.olxclone.helper.FirebaseHelper;
import br.com.fabricio.olxclone.helper.RecyclerItemClickListener;
import br.com.fabricio.olxclone.helper.UsuarioFirebase;
import br.com.fabricio.olxclone.model.Anuncio;
import dmax.dialog.SpotsDialog;

public class MeusAnunciosActivity extends AppCompatActivity {

    private RecyclerView recyclerAnuncios;
    private AnuncioAdapter adapter;
    private DatabaseReference anunciosRef;
    private List<Anuncio> lsAnuncio = new ArrayList<>();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        inicializarComponentes();

        anunciosRef = FirebaseHelper.getFirebaseDatabase().child("meus_anuncios").child(UsuarioFirebase.getUsuarioFirebase().getUid());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new AnuncioAdapter(getApplicationContext(), lsAnuncio);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerAnuncios.setLayoutManager(layoutManager);
        recyclerAnuncios.setHasFixedSize(true);
        recyclerAnuncios.setAdapter(adapter);

        recuperarAnuncios();

        recyclerAnuncios.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerAnuncios, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, final int position) {

                final Anuncio anuncioSelecionado = lsAnuncio.get(position);

                AlertDialog.Builder dialogConfirma = new AlertDialog.Builder(MeusAnunciosActivity.this);
                dialogConfirma.setTitle("Exclusão");
                dialogConfirma.setMessage("Tem certeza que deseja excluir o anúncio?");
                dialogConfirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        anuncioSelecionado.remover();
                        adapter.notifyDataSetChanged();
                    }
                });
                dialogConfirma.setNegativeButton("Não", null);

                AlertDialog alertDialog = dialogConfirma.create();
                alertDialog.show();

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

    }

    private void recuperarAnuncios() {

        dialog = new SpotsDialog.Builder()
                .setContext(MeusAnunciosActivity.this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();

        dialog.show();

        anunciosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsAnuncio.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Anuncio anuncio = ds.getValue(Anuncio.class);
                    lsAnuncio.add(anuncio);
                }

                Collections.reverse(lsAnuncio);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes() {
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);
    }

}
