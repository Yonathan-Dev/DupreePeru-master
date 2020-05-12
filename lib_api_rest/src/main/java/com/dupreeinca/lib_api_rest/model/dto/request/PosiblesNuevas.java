package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 4/9/17.
 */

public class PosiblesNuevas {
    private String tipo_docu;
    private String nume_iden;
    private String nomb_terc;
    private String apel_terc;
    private String celu_ter1;
    private String celu_ter2;
    private String acti_esta;
    private String direccion;
    private String barrio;
    private int    id;

    public PosiblesNuevas(String tipo_docu, String nume_iden, String nomb_terc, String apel_terc, String celu_ter1, String celu_ter2, String acti_esta,int id,String direccion,String barrio) {
        this.tipo_docu = tipo_docu;
        this.nume_iden = nume_iden;
        this.nomb_terc = nomb_terc;
        this.apel_terc = apel_terc;
        this.celu_ter1 = celu_ter1;
        this.celu_ter2 = celu_ter2;
        this.acti_esta = acti_esta;
        this.id        = id;
        this.direccion = direccion;
        this.barrio    = barrio;
    }


    public String getTipo_docu() {
        return tipo_docu;
    }

    public String getNume_iden() {
        return nume_iden;
    }

    public String getNomb_terc() {
        return nomb_terc;
    }

    public String getApel_terc() {
        return apel_terc;
    }

    public String getCelu_ter1() {
        return celu_ter1;
    }

    public String getCelu_ter2() {
        return celu_ter2;
    }

    public int getId() {           return id;        }
}
