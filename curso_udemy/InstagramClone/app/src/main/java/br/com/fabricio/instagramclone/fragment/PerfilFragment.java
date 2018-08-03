package br.com.fabricio.instagramclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.activity.EditarPerfilActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView txtPublicacoes, txtSeguidores, txtSeguindo;
    private CircleImageView imagemPerfil;
    private Button btnEditarPerfil;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        inicializaComponentes(view);

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
    }

}
