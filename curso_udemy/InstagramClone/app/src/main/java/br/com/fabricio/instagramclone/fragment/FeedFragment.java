package br.com.fabricio.instagramclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.adapter.FeedAdapter;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Feed;
import br.com.fabricio.instagramclone.model.Postagem;
import br.com.fabricio.instagramclone.model.Usuario;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {


    private RecyclerView recyclerViewFeed;
    private FeedAdapter adapter;
    private List<Feed> lsFeed = new ArrayList<>();
    private ValueEventListener valueEventListenerFeed;
    private DatabaseReference firebaseRef;
    private DatabaseReference feedRef;
    private Usuario usuarioLogado;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        lsFeed.clear();
        listarFeed();
    }

    @Override
    public void onStop() {
        super.onStop();
        feedRef.removeEventListener(valueEventListenerFeed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        firebaseRef = FirebaseHelper.getDatabaseReference();
        feedRef = firebaseRef.child("feed").child(usuarioLogado.getId());

        inicializarComponentes(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setHasFixedSize(true);
        adapter = new FeedAdapter(getActivity(), lsFeed);
        recyclerViewFeed.setAdapter(adapter);

        return view;
    }


    private void listarFeed(){
        valueEventListenerFeed = feedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Feed f = ds.getValue(Feed.class);
                    lsFeed.add(f);
                }
                Collections.reverse(lsFeed);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(View view) {
        recyclerViewFeed = view.findViewById(R.id.recyclerViewFeed);
    }

}
