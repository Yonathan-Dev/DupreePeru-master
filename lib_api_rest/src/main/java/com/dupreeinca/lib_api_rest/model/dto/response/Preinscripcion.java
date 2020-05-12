package com.dupreeinca.lib_api_rest.model.dto.response;

/**
 * Created by cloudemotion on 18/8/17.
 */

public class Preinscripcion {
    private String id_preinscripcion;
    private String tipo;
    private String cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private String id_ciudad;
    private String name_ciudad;
    private String barrio;
    private String estado;
    private String fecha;
    private boolean indi_consulta;
    private String observacion;
    private String celular;
    private String usuario;
    private String formato_direccion = "";

//    public Preinscripcion(String id_preinscripcion, String tipo, String cedula, String nombre, String apellido, String direccion, String id_ciudad, String name_ciudad, String barrio, String estado, String fecha, boolean indi_consulta, String observacion, String celular) {
//        this.id_preinscripcion = id_preinscripcion;
//        this.tipo = tipo;
//        this.cedula = cedula;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.direccion = direccion;
//        this.id_ciudad = id_ciudad;
//        this.name_ciudad = name_ciudad;
//        this.barrio = barrio;
//        this.estado = estado;
//        this.fecha = fecha;
//        this.indi_consulta = indi_consulta;
//        this.observacion = observacion;
//        this.celular = celular;
//    }

    public String getUsuario() {
        return usuario;
    }

    public String getId_preinscripcion() {
        return id_preinscripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getId_ciudad() {
        return id_ciudad;
    }

    public String getName_ciudad() {
        return name_ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isIndi_consulta() {
        return indi_consulta;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getCelular() {
        return celular;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFormato_direccion() {
        return formato_direccion;
    }
}
