package com.dupreeinca.lib_api_rest.model.dto.response;

public class RequeridUbicacion {

    private String coor_x;
    private String coor_y;
    private String acti_usua;
    private String tipo_perf;
    private String alo_perf;
    private String cedula;

    public RequeridUbicacion(String coor_x, String coor_y, String acti_usua, String tipo_perf, String alo_perf, String cedula) {
        this.coor_x = coor_x;
        this.coor_y = coor_y;
        this.acti_usua = acti_usua;
        this.tipo_perf = tipo_perf;
        this.alo_perf = alo_perf;
        this.cedula = cedula;
    }

    public String getCoor_x() {
        return coor_x;
    }

    public void setCoor_x(String coor_x) {
        this.coor_x = coor_x;
    }

    public String getCoor_y() {
        return coor_y;
    }

    public void setCoor_y(String coor_y) {
        this.coor_y = coor_y;
    }

    public String getActi_usua() {
        return acti_usua;
    }

    public void setActi_usua(String acti_usua) {
        this.acti_usua = acti_usua;
    }

    public String getTipo_perf() {
        return tipo_perf;
    }

    public void setTipo_perf(String tipo_perf) {
        this.tipo_perf = tipo_perf;
    }

    public String getAlo_perf() {
        return alo_perf;
    }

    public void setAlo_perf(String alo_perf) {
        this.alo_perf = alo_perf;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
