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
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_pedido_track;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

import java.util.ArrayList;

public class pedido_track extends AppCompatActivity {

    ArrayList<String> ListNume;
    ArrayList<String> ListPedi;
    ArrayList<String> ListEsta;
    ArrayList<String> ListMoti;
    ArrayList<String> ListHora;
    RecyclerView recycler;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_track);

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
        ListPedi = new ArrayList<String>();
        ListEsta = new ArrayList<String>();
        ListMoti = new ArrayList<String>();
        ListHora = new ArrayList<String>();
        int i = 1;

        if(db!=null) {
            Cursor c = db.rawQuery("SELECT cliente.nume_fact, pedi_conf.nomb_moti, pedi_conf.acti_hora FROM cliente LEFT JOIN pedi_conf ON cliente.nume_fact = pedi_conf.nume_fact ORDER BY acti_hora DESC", null);
            int p = c.getCount();

            if (p>0){

                if (c.moveToNext()){

                    do {
                        String nume_fact   = c.getString(0);
                        String nomb_moti   = String.valueOf(c.getString(1)) ;
                        String acti_hora   = c.getString(2);

                        ListNume.add(String.valueOf(i));
                        ListPedi.add(nume_fact.trim());


                        if (acti_hora == null || acti_hora ==""){
                            ListEsta.add("ASIGNADO");
                        } else if(nomb_moti.length()>1){
                            ListEsta.add("MOTIVADO");
                        } else {
                            ListEsta.add("CONFIRMADO");
                        }

                        if (nomb_moti.trim() == "null" || nomb_moti.trim().equalsIgnoreCase("") ){
                            ListMoti.add("-");
                        } else{
                            ListMoti.add(nomb_moti.trim());
                        }

                        if (acti_hora == null){
                            ListHora.add("-");
                        } else {
                            ListHora.add(acti_hora.trim());
                        }

                        Adaptador_pedido_track adapter = new Adaptador_pedido_track(ListNume, ListPedi, ListEsta, ListMoti, ListHora);
                        recycler.setAdapter(adapter);

                        i++;
                    } while (c.moveToNext());
                }
                pdp.dismiss();

            } else {
                pdp.dismiss();
                Toast.makeText(getBaseContext(), "No cuenta con pedidos cargados.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
