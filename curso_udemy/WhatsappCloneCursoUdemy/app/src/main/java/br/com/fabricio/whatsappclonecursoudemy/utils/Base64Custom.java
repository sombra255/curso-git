package br.com.fabricio.whatsappclonecursoudemy.utils;

import android.util.Base64;

public class Base64Custom {

    public static String encodeToString(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String decodeToString(String textoCodificado){
        return new String(Base64.decode(textoCodificado, Base64.DEFAULT));
    }
}
