package br.com.fabricio.whatsappclonecursoudemy.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.fabricio.whatsappclonecursoudemy.activity.ConfiguracoesActivity;

public class Permissao {

    public static boolean validarPermissoes(String[] permissoes, Activity activity, int requestCode){
        if(Build.VERSION.SDK_INT >= 23){
            List<String> lsPermissoes = new ArrayList<>();

            /*percorre todas as permissoes verificando uma por uma as que já foram liberadas*/
            for(String permissao : permissoes){
                Boolean temPermissao =  ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if(!temPermissao){
                    lsPermissoes.add(permissao);
                }
            }
            //Caso a lista esteja vazia nao precisamos solicitar nenhuma permissao
            if(lsPermissoes.isEmpty()){
                return true;
            }

            String[] novasPermissoes = new String[lsPermissoes.size()];
            lsPermissoes.toArray(novasPermissoes);

            //Solicita permissoes
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }
        return true;
    }
}
