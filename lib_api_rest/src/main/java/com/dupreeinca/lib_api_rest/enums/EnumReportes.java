package com.dupreeinca.lib_api_rest.enums;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public enum EnumReportes {
    REPORTE_CDR(0),
    REPORTE_SEGUIMIENTO(1),
    REPORTE_FACTURAS(2),
    REPORTE_PAGOS(3),
    REPORTE_CONFERENCIA(4);

    private int key;

    EnumReportes(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
