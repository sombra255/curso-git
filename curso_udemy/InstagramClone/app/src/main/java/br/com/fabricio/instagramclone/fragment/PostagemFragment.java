package br.com.fabricio.instagramclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.fabricio.instagramclone.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostagemFragment extends Fragment {


    public PostagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postagem, container, false);
    }

}
