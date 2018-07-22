package br.com.fabricio.whatsappclonecursoudemy.helper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import br.com.fabricio.whatsappclonecursoudemy.model.Usuario;
import br.com.fabricio.whatsappclonecursoudemy.utils.Base64Custom;

public class UsuarioFirebase {

    public static String getUsuarioFirebase(){
        FirebaseAuth firebaseAuth = FirebaseHelper.autenticacaoFirebase();
        FirebaseUser usuario = firebaseAuth.getCurrentUser();
        String emailUsuarioBase64 = Base64Custom.encodeToString(usuario.getEmail());

        return emailUsuarioBase64;
    }

    public static FirebaseUser getFirebaseUser(){
        FirebaseAuth firebaseAuth = FirebaseHelper.autenticacaoFirebase();
        return firebaseAuth.getCurrentUser();
    }

    public static boolean atualizaFotoUsuario(Uri url){
        try {
            FirebaseUser user = getFirebaseUser();
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(url).build();
            user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar foto do usuário");
                    }
                }
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public static boolean atualizaNomeUsuario(String nome){
        try {
            FirebaseUser user = getFirebaseUser();
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
            user.updateProfile(userProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful()){
                        Log.d("Perfil", "Erro ao atualizar nome do usuário");
                    }
                }
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario getUsuarioLogado(){
        FirebaseUser firebaseUser = getFirebaseUser();
        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getDisplayName());

        if(firebaseUser.getPhotoUrl() == null){
            usuario.setFoto("");
        }else {
            usuario.setFoto(firebaseUser.getPhotoUrl().toString());
        }
        return usuario;
    }
}
