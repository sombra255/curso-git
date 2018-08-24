package br.com.fabricio.olxclone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.fabricio.olxclone.R;
import br.com.fabricio.olxclone.adapter.AnuncioAdapter;
import br.com.fabricio.olxclone.helper.FirebaseHelper;
import br.com.fabricio.olxclone.model.Anuncio;
import dmax.dialog.SpotsDialog;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnCategoria, btnRegiao;
    private RecyclerView recyclerViewMeusAnuncios;
    private List<Anuncio> lsAnuncios = new ArrayList<>();
    private AnuncioAdapter adapter;
    private DatabaseReference anunciosPublicosRef;
    private AlertDialog dialog;
    private String filtroEstado = "";
    private String filtroCategoria = "";
    private boolean filtroPorEstado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        inicializarComponentes();
        firebaseAuth = FirebaseHelper.getFirebaseAuth();
        anunciosPublicosRef = FirebaseHelper.getFirebaseDatabase()
                .child("anuncios");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewMeusAnuncios.setLayoutManager(linearLayoutManager);
        recyclerViewMeusAnuncios.setHasFixedSize(true);
        adapter = new AnuncioAdapter(this, lsAnuncios);
        recyclerViewMeusAnuncios.setAdapter(adapter);

        carregarAnunciosPublicos();
    }

    public void filtrarPorEstado(View view){
        AlertDialog.Builder dialogEstado = new AlertDialog.Builder(this);
        dialogEstado.setTitle("Selecione o estado desejado");
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        final Spinner spinnerEstados = viewSpinner.findViewById(R.id.dialog_spinnerEstados);

        String[] estados = getResources().getStringArray(R.array.estados);

        ArrayAdapter<String> adapterEstado = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estados);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstados.setAdapter(adapterEstado);


        dialogEstado.setView(viewSpinner);
        dialogEstado.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filtroEstado = spinnerEstados.getSelectedItem().toString();
                recuperarAnunciosPorEstado();
                filtroPorEstado = true;
            }
        });
        dialogEstado.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = dialogEstado.create();
        dialog.show();
    }

    public void filtrarPorCategoria(View view){

        if(filtroPorEstado){
            AlertDialog.Builder dialogCategoria = new AlertDialog.Builder(this);
            dialogCategoria.setTitle("Selecione a categoria desejada");
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            final Spinner spinnerCategorias = viewSpinner.findViewById(R.id.dialog_spinnerEstados);

            String[] categorias = getResources().getStringArray(R.array.categoria);

            ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);
            adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategorias.setAdapter(adapterCategoria);


            dialogCategoria.setView(viewSpinner);
            dialogCategoria.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    filtroCategoria = spinnerCategorias.getSelectedItem().toString();
                    recuperarAnunciosPorCategoria();
                }
            });
            dialogCategoria.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = dialogCategoria.create();
            dialog.show();
        }else {
            Toast.makeText(this, "Escolha primeiro uma região", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperarAnunciosPorEstado() {

        dialog = new SpotsDialog.Builder()
                .setContext(AnunciosActivity.this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        anunciosPublicosRef = FirebaseHelper.getFirebaseDatabase()
                .child("anuncios")
                .child(filtroEstado);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsAnuncios.clear();
                for(DataSnapshot categoria : dataSnapshot.getChildren()){
                    for(DataSnapshot anuncios : categoria.getChildren()){
                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        lsAnuncios.add(anuncio);
                    }
                }
                Collections.reverse(lsAnuncios);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void recuperarAnunciosPorCategoria() {

        dialog = new SpotsDialog.Builder()
                .setContext(AnunciosActivity.this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        anunciosPublicosRef = FirebaseHelper.getFirebaseDatabase()
                .child("anuncios")
                .child(filtroEstado)
        .child(filtroCategoria);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lsAnuncios.clear();
                for(DataSnapshot anuncios : dataSnapshot.getChildren()){
                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        lsAnuncios.add(anuncio);
                }
                Collections.reverse(lsAnuncios);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void carregarAnunciosPublicos() {

        dialog = new SpotsDialog.Builder()
                .setContext(AnunciosActivity.this)
                .setMessage("Recuperando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        lsAnuncios.clear();
        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot estados : dataSnapshot.getChildren()){
                    for (DataSnapshot categoria : estados.getChildren()){
                        for (DataSnapshot anuncios : categoria.getChildren()){
                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            lsAnuncios.add(anuncio);
                        }
                    }
                }
                Collections.reverse(lsAnuncios);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dialog.dismiss();
    }

    private void inicializarComponentes() {
        btnCategoria = findViewById(R.id.btnCategoria);
        btnRegiao = findViewById(R.id.btnRegiao);
        recyclerViewMeusAnuncios = findViewById(R.id.recyclerMeusAnuncios);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //chamado antes do menu ser exibido
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(firebaseAuth.getCurrentUser() == null){
            menu.setGroupVisible(R.id.group_deslogado, true);
        }else {
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_acessar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;

            case R.id.menu_sair:
                firebaseAuth.signOut();
                invalidateOptionsMenu(); //chama o onPrepareOptionsMenu e recarrega os menus corretamente
                break;

            case R.id.menu_meus_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
        }
        return true;
    }
}
