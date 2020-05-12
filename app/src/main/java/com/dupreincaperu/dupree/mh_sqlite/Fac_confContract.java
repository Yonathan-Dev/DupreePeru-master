package com.dupreincaperu.dupree.mh_sqlite;

import android.provider.BaseColumns;

public class Fac_confContract {

    private Fac_confContract(){}

    public static class Fac_confEntry implements BaseColumns{


        public static final String TABLE_NAME = "fac_conf";

        public static final String COLUMN_CONS_FAC_CONF = "cons_fac_conf";
        public static final String COLUMN_FACT_SRI      = "fact_sri";
        public static final String COLUMN_FAC_LATI      = "fac_lati";
        public static final String COLUMN_FAC_LONG      = "fac_long";
        public static final String COLUMN_FAC_DIRE      = "fac_dire";
        public static final String COLUMN_ACTI_USUA     = "acti_usua";
        public static final String COLUMN_ACTI_HORA     = "acti_hora";
        public static final String COLUMN_NOMB_MOTI     = "nomb_moti";
        public static final String COLUMN_FAC_IMAG      = "fac_imag";
        public static final String COLUMN_NUME_FACT     = "nume_fact";
        public static final String COLUMN_CODI_VERS     = "codi_vers";
        public static final String COLUMN_NUME_IDEN     = "nume_iden";
        public static final String COLUMN_CODI_CAMP     = "codi_camp";

        public static final String COLUMN_MODI_DIRE     = "modi_dire";
        public static final String COLUMN_MODI_REFE     = "modi_refe";

    }
}
