package com.dupreincaperu.dupree.mh_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "distribucion.db";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
                Geo_clieContract.Geo_clieEntry.TABLE_NAME + "("+
                Geo_clieContract.Geo_clieEntry.COLUMN_CEDULA +" TEXT,"+
                Geo_clieContract.Geo_clieEntry.COLUMN_CX +" TEXT,"+
                Geo_clieContract.Geo_clieEntry.COLUMN_CY+ " TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Tab_empl_tempContract.Tab_empl_tempEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Geo_clieContract.Geo_clieEntry.TABLE_NAME);

        onCreate(db);
    }

}
