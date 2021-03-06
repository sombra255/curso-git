package br.com.fabricio.whatsappclonecursoudemy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.activity.ChatActivity;
import br.com.fabricio.whatsappclonecursoudemy.activity.GrupoActivity;
import br.com.fabricio.whatsappclonecursoudemy.adapter.ContatosAdapter;
import br.com.fabricio.whatsappclonecursoudemy.adapter.ConversasAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Conversa;
import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private RecyclerView recyclerViewContatos;
    private ContatosAdapter adapter;
    private List<Usuario> lsUsuarios = new ArrayList<>();
    private DatabaseReference usuariosRef;
    private ValueEventListener valueEventListenerContatos;
    private FirebaseUser usuarioAtual;

    public ContatosFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        lsUsuarios.clear();
        criarCabecalho();
        recuperarContatos();
    }

    @Override
    public void onStop() {
        super.onStop();
        usuariosRef.removeEventListener(valueEventListenerContatos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        recyclerViewContatos = view.findViewById(R.id.recyclerContatos);
        usuarioAtual = UsuarioFirebase.getFirebaseUser();
        usuariosRef = FirebaseHelper.getFirebaseDatabase().child("usuarios");


        adapter = new ContatosAdapter(lsUsuarios, getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewContatos.setLayoutManager(layoutManager);
        recyclerViewContatos.setHasFixedSize(true);
        recyclerViewContatos.setAdapter(adapter);

        recyclerViewContatos.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewContatos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                List<Usuario> lsUsuariosSelecionados = adapter.getContatos();
                Usuario usuarioSelecionado = lsUsuariosSelecionados.get(position);

                boolean cabecalho = usuarioSelecionado.getEmail().isEmpty();

                if (cabecalho) {
                    Intent i = new Intent(getActivity(), GrupoActivity.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("chatContato", usuarioSelecionado);
                    startActivity(i);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        criarCabecalho();


        return view;
    }

    private void criarCabecalho() {
        //cria um usuario sem email que será o cabecalho, a campo email vazio é oq define o campo como cabecalho
        Usuario usuarioGrupo = new Usuario();
        usuarioGrupo.setNome("Novo Grupo");
        usuarioGrupo.setEmail("");
        lsUsuarios.add(usuarioGrupo);
    }

    public void pesquisarContatos(String texto) {

        List<Usuario> lsUsuariosBusca = new ArrayList<>();
        for (Usuario u : lsUsuariosBusca) {

            String nome = u.getNome().toLowerCase();
            String email = u.getEmail().toLowerCase();

            if (nome.contains(texto) || email.contains(texto)) {
                lsUsuariosBusca.add(u);
            }
        }

        adapter = new ContatosAdapter(lsUsuariosBusca, getActivity());
        recyclerViewContatos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarUsuarios() {
        adapter = new ContatosAdapter(lsUsuarios, getActivity());
        recyclerViewContatos.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recuperarContatos() {
        valueEventListenerContatos = usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Usuario usuario = d.getValue(Usuario.class);

                    if (!usuarioAtual.getEmail().equals(usuario.getEmail())) {
                        lsUsuarios.add(usuario);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
