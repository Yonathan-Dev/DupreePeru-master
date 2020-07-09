package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public final class CajaContract {

    private CajaContract(){}

    public static class CajaEntry implements BaseColumns {
        public static final String TABLE_NAME = "caja";

        public static final String COLUMN_CONS_CAJA = "cons_caja";
        public static final String COLUMN_NUME_FACT = "nume_fact";
        public static final String COLUMN_CANT_CAJA = "cant_caja";
        public static final String COLUMN_CANT_FUER = "cant_fuer";
        public static final String COLUMN_CANT_BOLS = "cant_bols";
        public static final String COLUMN_ACTI_FECH = "acti_fech";

    }
}
