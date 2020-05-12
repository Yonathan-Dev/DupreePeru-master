package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 31/8/17.
 */

public class ListPre {
    private String token;
    private String perfil;
    private String valor;
    private int index_pages;

    public ListPre(String token, String perfil, String valor, int index_pages) {
        this.token = token;
        this.perfil = perfil;
        this.valor = valor;
        this.index_pages = index_pages;
    }

    public String getToken() {
        return token;
    }

    public String getPerfil() {
        return perfil;
    }

    public String getValor() {
        return valor;
    }

    public int getIndex_pages() {
        return index_pages;
    }

    public void setIndex_pages(int index_pages) {
        this.index_pages = index_pages;
    }
}
