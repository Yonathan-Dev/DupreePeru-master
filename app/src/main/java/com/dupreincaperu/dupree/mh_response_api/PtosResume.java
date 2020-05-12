package com.dupreincaperu.dupree.mh_response_api;

/**
 * Created by cloudemotion on 21/8/17.
 */

public class PtosResume {
    private int Efectivos;
    private int Redimidos;
    private int Disponibles;
    private int Pendientes_Pago;
    private String asesora;

    public PtosResume(int efectivos, int redimidos, int disponibles, int pendientes_Pago, String asesora) {
        Efectivos = efectivos;
        Redimidos = redimidos;
        Disponibles = disponibles;
        Pendientes_Pago = pendientes_Pago;
        this.asesora = asesora;
    }

    public int getEfectivos() {
        return Efectivos;
    }

    public int getRedimidos() {
        return Redimidos;
    }

    public int getDisponibles() {
        return Disponibles;
    }

    public int getPendientes_Pago() {
        return Pendientes_Pago;
    }

    public String getAsesora() {
        return asesora;
    }
}
