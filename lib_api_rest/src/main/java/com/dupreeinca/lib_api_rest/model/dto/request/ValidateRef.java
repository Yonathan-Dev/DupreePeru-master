package com.dupreeinca.lib_api_rest.model.dto.request;

/**
 * Created by cloudemotion on 28/8/17.
 */

public class ValidateRef {
    String cedula;
    String token;

    public ValidateRef(String cedula, String token) {
        this.cedula = cedula;
        this.token = token;
    }
}
