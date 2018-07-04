package br.com.fabricio.cursoudemyorganizze.utils;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtualCustom(){
        Long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(currentTimeMillis);
    }
}
