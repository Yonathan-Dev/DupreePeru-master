package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 29/8/17.
 */

public class RequiredRegister_NEW_2018 {
    private String nombre;
    //private String apellido;

    private String nombre_departamento;
    private String nombre_ciudad;

    private String telefono;
    private String correo;

    private String comentarios;

    public RequiredRegister_NEW_2018(String nombre, String nombre_departamento, String nombre_ciudad, String telefono, String correo, String comentarios) {
        this.nombre = nombre;
        this.nombre_departamento = nombre_departamento;
        this.nombre_ciudad = nombre_ciudad;
        this.telefono = telefono;
        this.correo = correo;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombre_departamento() {
        return nombre_departamento;
    }

    public String getNombre_ciudad() {
        return nombre_ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getComentarios() {
        return comentarios;
    }
}
