package com.dupreeinca.lib_api_rest.model.dto.response.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Posibles_Nuevas extends RealmObject{
    @PrimaryKey
    private int id;//se usa en la db
    private String tipo_docu;
    private String cedula;
    private String nombre;
    private String apellido;
    private String movil1;
    private String movil2;
    private String estado;
    private String direccion;
    private String barrio;
    private int pagina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_docu() {
        return tipo_docu;
    }

    public void setTipo_docu(String tipo_docu) {
        this.tipo_docu = tipo_docu;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMovil1() {
        return movil1;
    }

    public void setMovil1(String movil1) {
        this.movil1 = movil1;
    }

    public String getMovil2() {
        return movil2;
    }

    public void setMovil2(String movil2) {
        this.movil2 = movil2;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPagina() { return pagina; }

    public void setPagina(int pagina) { this.pagina = pagina; }

    public String getEstado() { return estado; }

    public String getDireccion() { return direccion;   }

    public void setDireccion(String direccion) { this.direccion = direccion;   }

    public String getBarrio() {        return barrio;    }

    public void setBarrio(String barrio) {        this.barrio = barrio;    }
}