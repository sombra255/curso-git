package br.com.fabricio.cursoudemyorganizze.config;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Fabricio on 02/07/2018.
 */

public class ConfiguracaoFirebase {

    private static FirebaseAuth auth;

    public static FirebaseAuth autenticacaoFirebase() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }


}
