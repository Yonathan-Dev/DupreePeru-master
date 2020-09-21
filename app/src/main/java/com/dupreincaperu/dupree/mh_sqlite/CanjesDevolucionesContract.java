package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public final class CanjesDevolucionesContract {
    private CanjesDevolucionesContract(){}

    public static class CanjesDevolucionesEntry implements BaseColumns{
        public static final String TABLE_NAME = "canjesdevoluciones";

        public static final String COLUMN_CONS_CANJ = "cons_canj";
        public static final String COLUMN_CONS_TERC = "cons_terc";
        public static final String COLUMN_NUME_SERV = "nume_serv";
        public static final String COLUMN_CODI_CAMP = "codi_camp";
        public static final String COLUMN_NUME_FACT = "nume_fact";
        public static final String COLUMN_CODI_PROD = "codi_prod";
        public static final String COLUMN_NOMB_PROD = "nomb_prod";
    }


}
