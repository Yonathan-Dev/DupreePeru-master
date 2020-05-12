package com.dupreeinca.lib_api_rest.model.view;

/**
 * Created by cloudemotion on 27/8/17.
 */

public class Profile {
    public final static String ADESORA = "A";
    public final static String LIDER = "L";
    public final static String GERENTE_ZONA = "Z";
    public final static String GERENTE_REGION = "R";
    public final static String TRASNPORTISTA = "T";

    private String perfil = "";
    private String valor = "";
    private boolean catalogo;
    private String imagen_perfil = "";

    public String getPerfil() {
        return perfil;
    }

    public String getValor() {
        return valor;
    }

    public boolean isCatalogo() {
        return catalogo;
    }

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCatalogo(boolean catalogo) {
        this.catalogo = catalogo;
    }
}
