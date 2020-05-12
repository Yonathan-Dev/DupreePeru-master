package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public class Pedi_confContract {

    private Pedi_confContract(){}

    public static class Pedi_confEntry implements BaseColumns{

        public static final String TABLE_NAME           = "pedi_conf";
        public static final String COLUMN_NUME_FACT     = "nume_fact";
        public static final String COLUMN_NOMB_MOTI     = "nomb_moti";
        public static final String COLUMN_ACTI_HORA     = "acti_hora";
    }
}
