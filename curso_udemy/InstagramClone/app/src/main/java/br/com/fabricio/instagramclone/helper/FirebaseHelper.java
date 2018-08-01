package br.com.fabricio.instagramclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Fabricio on 01/08/2018.
 */

public class FirebaseHelper {

    private static FirebaseAuth auth;
    private static DatabaseReference reference;

    public static FirebaseAuth getFirebaseAuth(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

    public static DatabaseReference getDatabaseReference(){
        if(reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }
}
