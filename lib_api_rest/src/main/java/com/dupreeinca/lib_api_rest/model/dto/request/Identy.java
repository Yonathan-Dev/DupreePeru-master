package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by marwuinh@gmail.com on 28/8/17.
 */

public class Identy {
    private String cedula;

    public Identy(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }
}
