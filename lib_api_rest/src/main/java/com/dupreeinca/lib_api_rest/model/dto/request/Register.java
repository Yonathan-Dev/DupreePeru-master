package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 29/8/17.
 */

public class Register {
    private String tipo_documento_id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String sexo;

    private String img_terminos;
    private int    id;

    public Register(String tipo_documento_id, String cedula, String nombre, String apellido, String img_terminos, String sexo ) {
        this.tipo_documento_id = tipo_documento_id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.img_terminos = img_terminos;
   //     this.id = id;
        this.sexo = sexo;
    }

    public String getCedula() {
        return cedula;
    }
    public int getId() {  return id;   }

    public String getSexo() {
        return sexo;
    }
}
