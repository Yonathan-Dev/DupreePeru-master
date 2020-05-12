package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public class Tab_empl_tempContract {

    private Tab_empl_tempContract(){}


    public static class Tab_empl_tempEntry implements BaseColumns {
        public static final String TABLE_NAME = "tab_empl_temp";

        public static final String COLUMN_CODI_USUA = "codi_usua";
        public static final String COLUMN_CLAV_USUA = "clav_usua";
        public static final String COLUMN_NOMB_APEL = "nomb_apel";
        public static final String COLUMN_ESTA_EMPL = "esta_empl";
        public static final String COLUMN_CORR_EMPL = "corr_empl";
        public static final String COLUMN_CODI_IMEI = "codi_imei";

    }
}
