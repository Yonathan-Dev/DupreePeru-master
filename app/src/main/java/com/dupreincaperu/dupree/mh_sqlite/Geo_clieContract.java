package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public class Geo_clieContract {

    private Geo_clieContract(){}

    public static class Geo_clieEntry implements BaseColumns {
        public static final String TABLE_NAME = "geo_clie";

        public static final String COLUMN_CEDULA = "cedula";
        public static final String COLUMN_CX = "cx";
        public static final String COLUMN_CY = "cy";
    }

}
