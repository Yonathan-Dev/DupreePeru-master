package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public final class CajaContract {

    private CajaContract(){}

    public static class CajaEntry implements BaseColumns {
        public static final String TABLE_NAME = "caja";

        public static final String COLUMN_CONS_CAJA = "cons_caja";
        public static final String COLUMN_CODI_CAJA = "codi_caja";
        public static final String COLUMN_REMI_SRI = "remi_sri";
        public static final String COLUMN_DESC_CAJA = "desc_caja";

    }
}
