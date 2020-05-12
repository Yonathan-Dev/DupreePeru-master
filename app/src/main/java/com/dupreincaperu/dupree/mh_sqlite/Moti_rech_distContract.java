package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public class Moti_rech_distContract {

    private Moti_rech_distContract(){}

    public static class Moti_rech_distEntry implements BaseColumns{
        public static final String TABLE_NAME = "moti_rech_dist";

        public static final String COLUMN_NOMB_MOTI = "nomb_moti";
    }
}
