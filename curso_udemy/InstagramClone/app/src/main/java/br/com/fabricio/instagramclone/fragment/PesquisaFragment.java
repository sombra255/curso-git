package br.com.fabricio.instagramclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.activity.PerfilAmigoActivity;
import br.com.fabricio.instagramclone.adapter.PesquisaAdapter;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.model.Usuario;
import br.com.fabricio.instagramclone.utils.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesquisaFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Usuario> lsUsuarios;
    private DatabaseReference usuarioRef;
    private PesquisaAdapter adapter;


    public PesquisaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pesquisa, container, false);
        inicializaComponentes(view);

        lsUsuarios = new ArrayList<>();
        usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PesquisaAdapter(getActivity(), lsUsuarios);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                Usuario usuarioSelecionado = lsUsuarios.get(position);
//                                Intent i = new Intent(getActivity(), PerfilAmigoActivity.class);
//                                i.putExtra("usuarioSelecionado", usuarioSelecionado);
//                                startActivity(i);
                            }

                            @Override
                            public void onItemClick(View view, int position) {
                                Usuario usuarioSelecionado = lsUsuarios.get(position);
                                Intent i = new Intent(getActivity(), PerfilAmigoActivity.class);
                                i.putExtra("usuarioSelecionado", usuarioSelecionado);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }
                        })
        );


        //configura searchView
        searchView.setQueryHint("Buscar usuários");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String textoDigitado = newText.toUpperCase();
                pesquisarUsuarios(textoDigitado);
                return true;
            }
        });


        return view;
    }

    private void pesquisarUsuarios(String textoDigitado) {
        lsUsuarios.clear();

        if (textoDigitado.length() >= 2) {
            Query query = usuarioRef.orderByChild("nomeMaiusculo")
                    .startAt(textoDigitado)
                    .endAt(textoDigitado + "\uf8ff");

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lsUsuarios.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        lsUsuarios.add(ds.getValue(Usuario.class));
                    }

                    adapter.notifyDataSetChanged();
//                    int total = lsUsuarios.size();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void inicializaComponentes(View view) {
        recyclerView = view.findViewById(R.id.pesquisaRecyclerView);
        searchView = view.findViewById(R.id.pesquisaSearchView);
    }

}
