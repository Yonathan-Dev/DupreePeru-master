package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 29/8/17.
 */

public class RequiredRegister_NewAPI {
    private String tipo_documento_id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String metodo;
    private String img_terminos;

    public RequiredRegister_NewAPI(String tipo_documento_id, String cedula, String nombre, String apellido, String img_terminos, String metodo) {
        this.tipo_documento_id = tipo_documento_id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.img_terminos = img_terminos;
        this.metodo =metodo;
    }


}
