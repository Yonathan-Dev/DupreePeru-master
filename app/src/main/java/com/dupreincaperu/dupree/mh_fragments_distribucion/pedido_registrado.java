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
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_pedido_registrado;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

import java.util.ArrayList;

public class pedido_registrado extends AppCompatActivity {

    ArrayList<String> ListGuia;
    ArrayList<String> ListPedido;
    ArrayList<String> ListUbicacion;
    ArrayList<String> ListCont;
    ArrayList<String> ListFecha;
    RecyclerView recycler;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_registrado);
        recycler = (RecyclerView) findViewById(R.id.RecyclerPedi);
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL,false));

        pedidos();
    }

    private void pedidos() {


        pdp = new ProgressDialog(this);
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_carg);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db7 = dbHelper.getWritableDatabase();

        if(db7!=null) {

            ListGuia        = new ArrayList<String>();
            ListPedido      = new ArrayList<String>();
            ListUbicacion   = new ArrayList<String>();
            ListCont        = new ArrayList<String>();
            ListFecha       = new ArrayList<String>();


            Cursor c = db7.rawQuery("SELECT fact_sri , nume_fact, fac_lati, fac_long, nomb_moti, acti_hora FROM fac_conf ORDER BY acti_hora", null);
            int p = c.getCount();
            int i=0;
            if (p>0){

                if (c.moveToNext()){
                    do {
                        i++;
                        String fact_sri   = c.getString(0);
                        String nume_fact  = c.getString(1);
                        String fac_lati   = c.getString(2);
                        String fac_long   = c.getString(3);
                        String nomb_moti  = c.getString(4);
                        int  cont        = i;
                        String acti_hora  = c.getString(5);

                        ListGuia.add(fact_sri.trim());
                        ListPedido.add(nume_fact.trim());
                        ListUbicacion.add(fac_lati.substring(0,6)+", "+fac_long.substring(0,6));
                        ListCont.add(String.valueOf(i));
                        ListFecha.add(acti_hora.trim());

                        Adaptador_pedido_registrado adapter = new Adaptador_pedido_registrado(ListGuia, ListPedido, ListUbicacion,ListCont, ListFecha);
                        recycler.setAdapter(adapter);

                    } while (c.moveToNext());

                }
                pdp.dismiss();

            } else {
                pdp.dismiss();
                Toast.makeText(getBaseContext(), "No cuenta con pedidos pendientes a sincronizar.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }



    }

}
