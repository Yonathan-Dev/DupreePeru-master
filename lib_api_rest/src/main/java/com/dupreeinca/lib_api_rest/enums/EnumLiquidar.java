package com.dupreeinca.lib_api_rest.enums;

/**
 * Created by marwuinh@gmail.com on 3/6/19.
 */

public enum EnumLiquidar {
    DEBAJO_MONTO("E001"),
    OK("S001");

    private String key;

    EnumLiquidar(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
