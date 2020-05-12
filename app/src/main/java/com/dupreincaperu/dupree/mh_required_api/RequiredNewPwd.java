package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 28/8/17.
 */

public class RequiredNewPwd {
    private String codigo;
    private String clave;
    private String confirmar_clave;

    public RequiredNewPwd(String codigo, String clave, String confirmar_clave) {
        this.codigo = codigo;
        this.clave = clave;
        this.confirmar_clave = confirmar_clave;
    }
}
