package br.com.fabricio.instagramclone.fragment;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.fabricio.instagramclone.R;
import br.com.fabricio.instagramclone.activity.EditarPerfilActivity;
import br.com.fabricio.instagramclone.activity.FiltroActivity;
import br.com.fabricio.instagramclone.utils.Permissao;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostagemFragment extends Fragment {

    private static final int SELECAO_GALERIA = 200;
    private static final int SELECAO_CAMERA = 100;
    private Button btnGaleria;
    private Button btnCamera;
    private String[] arrayPermissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    public PostagemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_postagem, container, false);

        Permissao.validarPermissoes(arrayPermissoes, getActivity(), 1);

        inicializaComponentes(view);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_CAMERA);
                }
            }
        });
        
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(i.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });


        return view;
    }

    private void inicializaComponentes(View view) {
        btnGaleria = view.findViewById(R.id.postagens_btn_galeria);
        btnCamera = view.findViewById(R.id.postagens_btn_camera);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK  ){
            Bitmap imagem = null;

            try {
                switch (requestCode){
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;


                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), localImagemSelecionada);
                        break;
                }

                if(imagem != null ){

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 85, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    Intent intent = new Intent(getActivity(), FiltroActivity.class);
                    intent.putExtra("fotoEscolhida", dadosImagem);
                    startActivity(intent);
//
//                    StorageReference imagemRef = storageRef
//                            .child("imagens")
//                            .child("perfil")
//                            .child(usuarioPerfil.getUid()+".jpeg");
//
//                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
//                    uploadTask.addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getActivity(), "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
//                        }
//                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Uri url = taskSnapshot.getDownloadUrl();
//                            atualizarFotoUsuario(url);
//                            Toast.makeText(getActivity(), "Sucesso ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
//                        }
//                    });

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
