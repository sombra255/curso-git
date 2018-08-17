package br.com.fabricio.instagramclone.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.activity.EditarPerfilActivity;
import br.com.fabricio.instagramclone.adapter.GridAdapter;
import br.com.fabricio.instagramclone.helper.FirebaseHelper;
import br.com.fabricio.instagramclone.helper.UsuarioFirebase;
import br.com.fabricio.instagramclone.model.Postagem;
import br.com.fabricio.instagramclone.model.Usuario;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView txtPublicacoes, txtSeguidores, txtSeguindo;
    private CircleImageView imagemPerfil;
    private Button btnEditarPerfil;
    private Usuario usuarioLogado;
    private DatabaseReference postagensUsuarioRef;
    private DatabaseReference usuariosRef;
    private DatabaseReference usuarioLogadoRef;
    private GridView gridView;
    private GridAdapter adapterGrid;
    private DatabaseReference firebaseRef;
    private ValueEventListener eventListenerUsuarioLogado;


    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        recuperarDadosPerfil();

        if(usuarioLogado != null){
            if(usuarioLogado.getCaminhoFoto() != null){
                Uri uri = Uri.parse(usuarioLogado.getCaminhoFoto());
                Glide.with(getActivity()).load(uri).into(imagemPerfil);
            }else {
                imagemPerfil.setImageResource(R.drawable.avatar);
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        usuarioLogadoRef.removeEventListener(eventListenerUsuarioLogado);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        firebaseRef = FirebaseHelper.getDatabaseReference();
        usuariosRef = firebaseRef.child("usuarios");

        inicializaComponentes(view);

        inicializarImageLoader();
        carregarFotosPostagens();

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void inicializaComponentes(View view) {

        txtPublicacoes = view.findViewById(R.id.txtPublicacoes);
        txtSeguidores = view.findViewById(R.id.txtSeguidores);
        txtSeguindo = view.findViewById(R.id.txtSeguindo);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfil);
        imagemPerfil = view.findViewById(R.id.imageViewFotoPerfil);
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        gridView = view.findViewById(R.id.gridViewPerfil);
    }

    public void inicializarImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void carregarFotosPostagens(){

        postagensUsuarioRef = firebaseRef.child("postagens").child(usuarioLogado.getId());
        postagensUsuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int tamanhoGrid = getResources().getDisplayMetrics().widthPixels;
                int tamanhoImagem = tamanhoGrid / 3;
                gridView.setColumnWidth(tamanhoImagem);

                List<String> lsUrlFotos = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Postagem postagem = ds.getValue(Postagem.class);
                    lsUrlFotos.add(postagem.getCaminhoFoto());
                }

//                int qntPostagens = lsUrlFotos.size();
//                txtPublicacoes.setText(String.valueOf(qntPostagens));

                adapterGrid = new GridAdapter(getActivity(), R.layout.list_item_postagem, lsUrlFotos);
                gridView.setAdapter(adapterGrid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void recuperarDadosPerfil(){
        usuarioLogadoRef = usuariosRef.child(usuarioLogado.getId());
        eventListenerUsuarioLogado = usuarioLogadoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                String postagens = String.valueOf(usuario.getPostagens());
                String seguidores = String.valueOf(usuario.getSeguidores());
                String seguindo = String.valueOf(usuario.getSeguindo());

                txtPublicacoes.setText(postagens);
                txtSeguidores.setText(seguidores);
                txtSeguindo.setText(seguindo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
