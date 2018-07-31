package br.com.fabricio.whatsappclonecursoudemy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.R;
import br.com.fabricio.whatsappclonecursoudemy.activity.ChatActivity;
import br.com.fabricio.whatsappclonecursoudemy.adapter.ConversasAdapter;
import br.com.fabricio.whatsappclonecursoudemy.helper.FirebaseHelper;
import br.com.fabricio.whatsappclonecursoudemy.helper.UsuarioFirebase;
import br.com.fabricio.whatsappclonecursoudemy.model.Conversa;
import br.com.fabricio.whatsappclonecursoudemy.utils.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private RecyclerView recyclerViewConversas;
    private ConversasAdapter adapter;
    private List<Conversa> lsConversa = new ArrayList<>();
    private DatabaseReference databaseReference;
    private DatabaseReference conversasRef;
    private ChildEventListener valueEventListenerConversas;

    public ConversasFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        lsConversa.clear();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasRef.removeEventListener(valueEventListenerConversas);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewConversas = view.findViewById(R.id.recyclerConversas);

        //configurar o adapter
        adapter = new ConversasAdapter(lsConversa, getContext());
        //configurar o recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter(adapter);


        recyclerViewConversas.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), recyclerViewConversas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                List<Conversa>lsConversaAtualizada = adapter.getConversas();
                Conversa conversaSelecionada = lsConversaAtualizada.get(position);

                if(conversaSelecionada.getIsGroup().equals("true")){
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("chatGrupo", conversaSelecionada.getGrupo());
                    startActivity(i);
                }else {
                    Intent i = new Intent(getActivity(), ChatActivity.class);
                    i.putExtra("chatContato", conversaSelecionada.getUsuarioExibicao());
                    startActivity(i);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));


        String identificadorUsuario = UsuarioFirebase.getUsuarioFirebase();
        databaseReference = FirebaseHelper.getFirebaseDatabase();
        conversasRef = databaseReference.child("conversas").child(identificadorUsuario);

        return view;
    }

    public void pesquisarConversas(String texto){

        List<Conversa>lsConversasBusca = new ArrayList<>();
        for(Conversa c : lsConversa){

            if(c.getUsuarioExibicao() != null){
                String nome = c.getUsuarioExibicao().getNome().toLowerCase();
                String mensagem = c.getUltimaMensagem();

                if(nome.contains(texto) || mensagem.contains(texto)){
                    lsConversasBusca.add(c);
                }
            }else {
                String nome = c.getGrupo().getNome().toLowerCase();
                String mensagem = c.getUltimaMensagem();

                if(nome.contains(texto) || mensagem.contains(texto)){
                    lsConversasBusca.add(c);
                }
            }
        }

        adapter = new ConversasAdapter(lsConversasBusca, getActivity());
        recyclerViewConversas.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void recarregarConversas(){
        adapter = new ConversasAdapter(lsConversa, getActivity());
        recyclerViewConversas.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void recuperarConversas(){

        valueEventListenerConversas = conversasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Conversa c = dataSnapshot.getValue(Conversa.class);
                lsConversa.add(c);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
