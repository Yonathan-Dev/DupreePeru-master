package com.dupreincaperu.dupree.mh_required_api;

/**
 * Created by cloudemotion on 27/8/17.
 */

public class RequiredAuth {
    private String username;
    private String password;
    private String token;

    public RequiredAuth(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
}
