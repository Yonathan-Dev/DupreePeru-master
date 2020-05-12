package com.dupreeinca.lib_api_rest.enums;

/**
 * Created by marwuinh@gmail.com on 2/28/19.
 */

public enum EnumFormatDireccion {
    FORMATO_1("1"),
    FORMATO_2("2"),
    FORMATO_3("3"),
    FORMATO_4("4");

    private String key;

    EnumFormatDireccion(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
