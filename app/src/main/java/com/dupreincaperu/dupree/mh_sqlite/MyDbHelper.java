package com.dupreincaperu.dupree.mh_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "distribucion.db";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+
                    ClienteContract.ClienteEntry.TABLE_NAME + "("+
                    ClienteContract.ClienteEntry.COLUMN_CONS_CLIE +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    ClienteContract.ClienteEntry.COLUMN_ACTI_FECH +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_NUME_IDEN +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_NOMB_TERC +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_APEL_TERC +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_DIRE_TERC +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_NUME_FACT +" INTEGER,"+
                    ClienteContract.ClienteEntry.COLUMN_REMI_SRI  +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_CODI_CAMP +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_CONS_TERC +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_CY  +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_CX  +" TEXT,"+
                    ClienteContract.ClienteEntry.COLUMN_DIST_ZONA  +" TEXT)"
        );

        db.execSQL("CREATE TABLE "+
                    CajaContract.CajaEntry.TABLE_NAME + "("+
                    CajaContract.CajaEntry.COLUMN_CONS_CAJA +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    CajaContract.CajaEntry.COLUMN_CODI_CAJA +" TEXT,"+
                    CajaContract.CajaEntry.COLUMN_REMI_SRI + " TEXT,"+
                    CajaContract.CajaEntry.COLUMN_DESC_CAJA + " TEXT)"
                  );

        db.execSQL("CREATE TABLE "+
                    Fac_confContract.Fac_confEntry.TABLE_NAME + "("+
                    Fac_confContract.Fac_confEntry.COLUMN_CONS_FAC_CONF+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    Fac_confContract.Fac_confEntry.COLUMN_FACT_SRI +" TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_FAC_LATI + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_FAC_LONG +" TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_FAC_DIRE + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_ACTI_USUA +" TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_ACTI_HORA + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_NUME_FACT + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_NOMB_MOTI + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_FAC_IMAG + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_CODI_VERS + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_NUME_IDEN + " TEXT,"+

                    Fac_confContract.Fac_confEntry.COLUMN_CODI_CAMP + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_MODI_DIRE + " TEXT,"+
                    Fac_confContract.Fac_confEntry.COLUMN_MODI_REFE + " TEXT)"
        );

        db.execSQL("CREATE TABLE "+
                Tab_empl_tempContract.Tab_empl_tempEntry.TABLE_NAME + "("+
                Tab_empl_tempContract.Tab_empl_tempEntry.COLUMN_CODI_USUA +" TEXT,"+
                Tab_empl_tempContract.Tab_empl_tempEntry.COLUMN_CLAV_USUA +" TEXT,"+
                Tab_empl_tempContract.Tab_empl_tempEntry.COLUMN_NOMB_APEL + " TEXT,"+
                Tab_empl_tempContract.Tab_empl_tempEntry.COLUMN_CORR_EMPL +" TEXT,"+
                Tab_empl_tempContract.Tab_empl_tempEntry.COLUMN_CODI_IMEI + " TEXT)"
        );

        db.execSQL("CREATE TABLE "+
                Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME + "("+
                Tab_prog_moviContract.Tab_prog_moviEntry.COLUMN_CODI_PROG+ " TEXT)"
        );

        db.execSQL("CREATE TABLE "+
                Moti_rech_distContract.Moti_rech_distEntry.TABLE_NAME + "("+
                Moti_rech_distContract.Moti_rech_distEntry.COLUMN_NOMB_MOTI+ " TEXT)"
        );

        db.execSQL("CREATE TABLE "+
                Pedi_confContract.Pedi_confEntry.TABLE_NAME + "("+
                Pedi_confContract.Pedi_confEntry.COLUMN_NUME_FACT +" TEXT,"+
                Pedi_confContract.Pedi_confEntry.COLUMN_NOMB_MOTI +" TEXT,"+
                Pedi_confContract.Pedi_confEntry.COLUMN_ACTI_HORA + " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ClienteContract.ClienteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CajaContract.CajaEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Fac_confContract.Fac_confEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Tab_empl_tempContract.Tab_empl_tempEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Moti_rech_distContract.Moti_rech_distEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Pedi_confContract.Pedi_confEntry.TABLE_NAME);

        onCreate(db);
    }

}
