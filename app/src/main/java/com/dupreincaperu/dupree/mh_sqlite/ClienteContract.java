package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public final class ClienteContract {
    private ClienteContract(){}

    public static class ClienteEntry implements BaseColumns{
        public static final String TABLE_NAME = "cliente";

        public static final String COLUMN_CONS_CLIE = "cons_clie";
        public static final String COLUMN_ACTI_FECH = "acti_fech";
        public static final String COLUMN_NUME_IDEN = "nume_iden";
        public static final String COLUMN_NOMB_TERC = "nomb_terc";
        public static final String COLUMN_APEL_TERC = "apel_terc";
        public static final String COLUMN_DIRE_TERC = "dire_terc";
        public static final String COLUMN_DIRE_REFE = "dire_refe";
        public static final String COLUMN_NUME_FACT = "nume_fact";
        public static final String COLUMN_REMI_SRI  = "remi_sri";
        public static final String COLUMN_CODI_CAMP = "codi_camp";
        public static final String COLUMN_CONS_TERC = "cons_terc";
        public static final String COLUMN_CY        = "cy";
        public static final String COLUMN_CX        = "cx";
        public static final String COLUMN_DIST_ZONA = "dist_zona";
        public static final String COLUMN_CELU_TER1 = "celu_ter1";
    }


}
