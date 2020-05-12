package com.dupreeinca.lib_api_rest.enums;

/**
 * Created by marwuinh@gmail.com on 2/28/19.
 */

public enum EnumTracking {
    RECIBIDO("Recibido"),
    FACTURADO("Facturado"),
    EMBALADO("Embalado"),
    DESPACHADO("Despachado"),
    ENTREGADO("Entregado");

    private String key;

    EnumTracking(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
