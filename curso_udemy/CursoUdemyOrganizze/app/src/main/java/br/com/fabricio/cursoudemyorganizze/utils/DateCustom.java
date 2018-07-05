package br.com.fabricio.cursoudemyorganizze.utils;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtualCustom(){
        Long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(currentTimeMillis);
    }

    public static String mesAnoDataEscolhida(String dataEscolhida){

        String [] data = dataEscolhida.split("/");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];

        String mesAno = mes + ano;

        return mesAno;
    }
}
