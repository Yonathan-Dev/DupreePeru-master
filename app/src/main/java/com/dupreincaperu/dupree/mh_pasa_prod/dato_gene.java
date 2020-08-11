package com.dupreincaperu.dupree.mh_pasa_prod;


public class dato_gene {

    private String URL_EMPRESA;
        private String puesta="TEST";

    public String getURL_EMPRESA(){
        if (puesta.equalsIgnoreCase("TEST")) {
            URL_EMPRESA="https://servicioweb2per.azzorti.co:443/hmvc/index.php/rest/";
        } else if (puesta.equalsIgnoreCase("PROD")) {
            URL_EMPRESA="https://servicioweb.dupree.pe:443/hmvc/index.php/rest/";
        }
        return URL_EMPRESA;
    }

}