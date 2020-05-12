package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 9/10/17.
 */

public class RequiredRefreshToken {
    private String token_fmc;
    private String valo_usua;

    public RequiredRefreshToken(String token_fmc, String valo_usua) {
        this.token_fmc = token_fmc;
        this.valo_usua = valo_usua;
    }
}
