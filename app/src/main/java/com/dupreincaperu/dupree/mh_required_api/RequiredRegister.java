package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 29/8/17.
 */

public class RequiredRegister {
    private String code_gsm;
    private String tipo_documento_id;
    private String cedula;
    private String nombre;
    private String apellido;

    //private String estado;
    //private String hijos;
    private String nacimiento;
    private String zona_seccion;
    private String tipo_via;
    private String numero1;
    private String letra1;
    private String bis;
    private String numero2;
    private String letra2;
    private String numero3;
    private String pcardinal;
    private String complemento;
    private String direreferencia;
    private String direccion_concatenada;

    private String departamento;
    private String id_ciudad;
    private String name_ciudad;
    private String barrio;
    private String id_barrio;

    private String direccion_envio;
    private String id_barrio_envio;

    private String telefono;
    private String celular;
    private String correo;
    private String imei;

    public RequiredRegister(String code_gsm, String tipo_documento_id, String cedula, String nombre,
                            String apellido, String departamento, String zona_seccion, String id_ciudad,
                            String name_ciudad, String barrio, String id_barrio, String telefono,
                            String celular, String correo, String nacimiento, String imei, String tipo_via,
                            String numero1, String letra1, String bis, String numero2, String letra2,
                            String numero3, String pcardinal, String complemento, String direreferencia, String direccion_concatenada,
                            String direccion_envio, String id_barrio_envio) {
        this.code_gsm = code_gsm;
        this.tipo_documento_id = tipo_documento_id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.departamento = departamento;
        this.id_ciudad = id_ciudad;
        this.name_ciudad = name_ciudad;
        this.barrio = barrio;
        this.id_barrio = id_barrio;
        this.telefono = telefono;
        this.celular = celular;
        this.correo = correo;
        this.nacimiento = nacimiento;
        this.zona_seccion = zona_seccion;
        this.imei = imei;
        this.tipo_via = tipo_via;
        this.numero1 = numero1;
        this.letra1 = letra1;
        this.bis = bis;
        this.numero2 = numero2;
        this.letra2 = letra2;
        this.numero3 = numero3;
        this.pcardinal = pcardinal;
        this.complemento = complemento;
        this.direreferencia = direreferencia;
        this.direccion_concatenada = direccion_concatenada;
        this.direccion_envio = direccion_envio;
        this.id_barrio_envio = id_barrio_envio;
    }

    public RequiredRegister() {
    }

    public void setCode_gsm(String code_gsm) {
        this.code_gsm = code_gsm;
    }

    public void setTipo_documento_id(String tipo_documento_id) {
        this.tipo_documento_id = tipo_documento_id;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setId_ciudad(String id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

    public void setName_ciudad(String name_ciudad) {
        this.name_ciudad = name_ciudad;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setId_barrio(String id_barrio) {
        this.id_barrio = id_barrio;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setZona_seccion(String zona_seccion) {
        this.zona_seccion = zona_seccion;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setTipo_via(String tipo_via) {
        this.tipo_via = tipo_via;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public void setLetra1(String letra1) {
        this.letra1 = letra1;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public void setLetra2(String letra2) {
        this.letra2 = letra2;
    }

    public void setNumero3(String numero3) {
        this.numero3 = numero3;
    }

    public void setPcardinal(String pcardinal) {
        this.pcardinal = pcardinal;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setDirereferencia(String direreferencia) {
        this.direreferencia = direreferencia;
    }

    public void setDireccion_concatenada(String direccion_concatenada) {
        this.direccion_concatenada = direccion_concatenada;
    }

    public void setDireccion_envio(String direccion_envio) {
        this.direccion_envio = direccion_envio;
    }

    public void setId_barrio_envio(String id_barrio_envio) {
        this.id_barrio_envio = id_barrio_envio;
    }

    public String getCode_gsm() {
        return code_gsm;
    }

    public String getTipo_documento_id() {
        return tipo_documento_id;
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

    public String getDepartamento() {
        return departamento;
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

    public String getId_barrio() {
        return id_barrio;
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

    public String getNacimiento() {
        return nacimiento;
    }

    public String getZona_seccion() {
        return zona_seccion;
    }

    public String getImei() {
        return imei;
    }

    public String getTipo_via() {
        return tipo_via;
    }

    public String getNumero1() {
        return numero1;
    }

    public String getLetra1() {
        return letra1;
    }

    public String getBis() {
        return bis;
    }

    public String getNumero2() {
        return numero2;
    }

    public String getLetra2() {
        return letra2;
    }

    public String getNumero3() {
        return numero3;
    }

    public String getPcardinal() {
        return pcardinal;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getDirereferencia() {
        return direreferencia;
    }

    public String getDireccion_concatenada() {
        return direccion_concatenada;
    }

    public String getDireccion_envio() {
        return direccion_envio;
    }

    public String getId_barrio_envio() {
        return id_barrio_envio;
    }
}
