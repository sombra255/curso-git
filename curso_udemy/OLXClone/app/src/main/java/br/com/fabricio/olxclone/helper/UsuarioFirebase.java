package br.com.fabricio.olxclone.helper;

import com.google.firebase.auth.FirebaseUser;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioFirebase(){
        return FirebaseHelper.getFirebaseAuth().getCurrentUser();
    }
}
