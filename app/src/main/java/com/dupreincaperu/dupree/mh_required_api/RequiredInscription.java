package com.dupreincaperu.dupree.mh_required_api;

import com.dupreeinca.lib_api_rest.model.dto.request.Referencia;

import java.util.List;

/**
 * Created by cloudemotion on 1/9/17.
 */

public class RequiredInscription {
    private String cedula;
    private String user;
    private List<String> img_cedula;
    private List<String> pagare;
    private String referenciado_por;
    private List<Referencia> referencia;

    public RequiredInscription() {
    }

    public RequiredInscription(String cedula, String user, List<String> img_cedula, List<String> pagare, String referenciado_por, List<Referencia> referencia) {
        this.cedula = cedula;
        this.user = user;
        this.img_cedula = img_cedula;
        this.pagare = pagare;
        this.referenciado_por = referenciado_por;
        this.referencia = referencia;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setImg_cedula(List<String> img_cedula) {
        this.img_cedula = img_cedula;
    }

    public void setPagare(List<String> pagare) {
        this.pagare = pagare;
    }

    public void setReferenciado_por(String referenciado_por) {
        this.referenciado_por = referenciado_por;
    }

    public void setReferencia(List<Referencia> referencia) {
        this.referencia = referencia;
    }
}
