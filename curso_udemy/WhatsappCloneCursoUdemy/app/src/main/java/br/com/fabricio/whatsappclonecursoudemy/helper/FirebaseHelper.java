package br.com.fabricio.whatsappclonecursoudemy.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private static FirebaseAuth auth;
    private static DatabaseReference firebase;

    public static DatabaseReference getFirebaseDatabase(){
        if (firebase == null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    public static FirebaseAuth autenticacaoFirebase() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }


}
