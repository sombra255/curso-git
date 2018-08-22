package br.com.fabricio.olxclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseHelper {

    public static DatabaseReference databaseReference;
    public static FirebaseAuth firebaseAuth;
    public static StorageReference storageReference;


    public static DatabaseReference getFirebaseDatabase(){
        if(databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static StorageReference getStorageReference() {
        if(storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }
}
