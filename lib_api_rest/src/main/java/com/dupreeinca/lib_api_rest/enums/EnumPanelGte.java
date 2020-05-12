package com.dupreeinca.lib_api_rest.enums;

/**
 * Created by marwuinh@gmail.com on 2/28/19.
 */

public enum EnumPanelGte {
    COLOR_VERDE("1"),
    COLOR_ROJO("3");

    private String key;

    EnumPanelGte(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
