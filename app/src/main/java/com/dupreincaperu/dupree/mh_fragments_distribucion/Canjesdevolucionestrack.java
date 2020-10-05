package com.dupreincaperu.dupree.mh_fragments_distribucion;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_canjes_track;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

import java.util.ArrayList;

public class Canjesdevolucionestrack extends AppCompatActivity {

    ArrayList<String> ListNume;
    ArrayList<String> ListIden;
    ArrayList<String> ListCodi;
    ArrayList<String> ListDesc;
    ArrayList<String> ListReco;
    ArrayList<String> ListReci;

    RecyclerView recycler;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canjesdevolucionestrack);

        recycler = (RecyclerView) findViewById(R.id.RecyclerTrack);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL,false));
        tracking();
    }

    private void tracking() {

        pdp = new ProgressDialog(this);
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_carg);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ListNume = new ArrayList<String>();
        ListIden = new ArrayList<String>();
        ListCodi = new ArrayList<String>();
        ListDesc = new ArrayList<String>();
        ListReco = new ArrayList<String>();
        ListReci = new ArrayList<String>();
        int i = 1;

        if(db!=null) {
            Cursor c = db.rawQuery("SELECT canjesdevoluciones.nume_iden,canjesdevoluciones.codi_prod,canjesdevoluciones.nomb_prod,canjesdevoluciones.cant_movi,canj_web_conf.cant_movi as cant_reci FROM canjesdevoluciones LEFT JOIN canj_web_conf ON canjesdevoluciones.codi_prod=canj_web_conf.codi_prod and canjesdevoluciones.nume_iden=canj_web_conf.nume_iden ", null);
            int p = c.getCount();

            if (p>0){

                if (c.moveToNext()){

                    do {
                        String nume_iden   = c.getString(0).trim();
                        String codi_prod   = c.getString(1).trim();
                        String nomb_prod   = c.getString(2).trim();
                        String cant_movi   = c.getString(3).trim();
                        String cant_reci   = String.valueOf(c.getString(4)) ;
                        if (cant_reci.equalsIgnoreCase("null")){
                            cant_reci = "0";
                        }

                        ListNume.add(String.valueOf(i));
                        ListIden.add(nume_iden.trim());
                        ListCodi.add(codi_prod.trim());


                        /*if (acti_hora == null || acti_hora ==""){
                            ListDesc.add("ASIGNADO");
                        } else if(nomb_moti.length()>1){
                            ListDesc.add("MOTIVADO");
                        } else {
                            ListDesc.add("CONFIRMADO");
                        }*/
                        ListDesc.add(nomb_prod.trim());

                        /*
                        if (nomb_moti.trim() == "null" || nomb_moti.trim().equalsIgnoreCase("") ){
                            ListReco.add("-");
                        } else{
                            ListReco.add(nomb_moti.trim());
                        }*/
                        ListReco.add(cant_movi.trim());

                        /*
                        if (acti_hora == null){
                            ListReci.add("-");
                        } else {
                            ListReci.add(acti_hora.trim());
                        }*/
                        ListReci.add(cant_reci.trim());

                        Adaptador_canjes_track adapter = new Adaptador_canjes_track(ListIden, ListNume, ListCodi, ListDesc, ListReco, ListReci);
                        recycler.setAdapter(adapter);

                        i++;
                    } while (c.moveToNext());
                }
                pdp.dismiss();

            } else {
                pdp.dismiss();
                Toast.makeText(getBaseContext(), "No cuenta con canjes y/o devoluciones por recoger.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
