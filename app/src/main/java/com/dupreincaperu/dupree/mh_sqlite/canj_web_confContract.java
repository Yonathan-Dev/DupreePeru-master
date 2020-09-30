package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public final class canj_web_confContract {
    private canj_web_confContract(){}

    public static class canj_web_confEntry implements BaseColumns{
        public static final String TABLE_NAME = "canj_web_conf";

        public static final String COLUMN_CONS_CANJ = "cons_canj";
        //public static final String COLUMN_ACTI_FECH = "acti_fech";
        public static final String COLUMN_NUME_SERV = "nume_serv";
        public static final String COLUMN_CODI_PROD = "codi_prod";
        public static final String COLUMN_CANT_MOVI = "cant_movi";
        public static final String COLUMN_OBSE_APRO = "obse_apro";
        public static final String COLUMN_ACTI_HORA = "acti_hora";
        public static final String COLUMN_NUME_IDEN = "nume_iden";
    }

}
