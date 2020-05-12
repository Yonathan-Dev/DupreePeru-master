package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 30/8/17.
 */

public class DataUser {
    private String nombre;
    private String apellido;
    private String telefono;
    private String celular;
    private String correo;

    public DataUser(String nombre, String apellido, String telefono, String celular, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCelular() {
        return celular;
    }

    public String getCorreo() {
        return correo;
    }
}
