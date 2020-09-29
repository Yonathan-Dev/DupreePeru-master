package com.dupreincaperu.dupree.mh_fragments_distribucion;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.cuadro_dialogo;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoCanjes;
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_personal;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;

public class Canjesdevoluciones extends AppCompatActivity {
    String nume_iden="";
    Boolean modo;
    int cont_inic = 0, cont_maxi=0, nume_serv =0;
    SearchView sv_codi_prod;
    EditText edt_nomb_prod, edt_codi_prod, edt_obse_apro;
    ImageButton img_remo_cont, img_agre_cont;
    TextView txt_cant_movi;
    ListView lv_codi_prod;
    LinearLayout lny_cuad_prod, lny_cuad_guar;
    com.getbase.floatingactionbutton.FloatingActionButton fab_regi_prod;
    String TAG = "Canjesdevoluciones";

    ArrayList<String> productos = new ArrayList<String>();
    ArrayAdapter<CharSequence> adapter;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canjesdevoluciones);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            nume_iden = parametros.getString("nume_iden");
            modo      = parametros.getBoolean("modo");
            Toast.makeText(getBaseContext(),""+modo, Toast.LENGTH_SHORT).show();
        }

        request = Volley.newRequestQueue(getBaseContext());

        sv_codi_prod  = (SearchView)    findViewById(R.id.sv_codi_prod);
        lv_codi_prod  = (ListView)      findViewById(R.id.lv_codi_prod);
        lny_cuad_prod = (LinearLayout)  findViewById(R.id.lny_cuad_prod);
        lny_cuad_guar = (LinearLayout)  findViewById(R.id.lny_cuad_guar);
        img_agre_cont = (ImageButton)   findViewById(R.id.img_agre_cont);
        img_remo_cont = (ImageButton)   findViewById(R.id.img_remo_cont);
        edt_nomb_prod = (EditText)      findViewById(R.id.edt_nomb_prod);
        edt_codi_prod = (EditText)      findViewById(R.id.edt_codi_prod);
        edt_obse_apro = (EditText)      findViewById(R.id.edt_obse_apro);
        txt_cant_movi = (TextView)      findViewById(R.id.txt_cant_movi);
        fab_regi_prod = (FloatingActionButton) findViewById(R.id.fab_regi_prod);

        listaProductos(nume_iden);


        img_agre_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont_inic < cont_maxi){
                    cont_inic   =   cont_inic + 1;
                    txt_cant_movi.setText(String.valueOf(cont_inic));
                }
            }
        });

        img_remo_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cont_inic > 1){
                    cont_inic   =   cont_inic - 1;
                    txt_cant_movi.setText(String.valueOf(cont_inic));
                }
            }
        });


        sv_codi_prod.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nume_iden) {

                sv_codi_prod.setQuery("", false);
                sv_codi_prod.setIconified(true);
                sv_codi_prod.clearFocus();
                lv_codi_prod.setVisibility(View.VISIBLE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (productos.isEmpty()){
                    listaProductos(nume_iden);
                } else if(newText.equalsIgnoreCase("")){
                    lv_codi_prod.setVisibility(View.GONE);
                } else {
                    lv_codi_prod.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(newText);
                }

                if (newText.length()>0) {
                    limpiarControles();
                }
                return true;
            }
        });


        lv_codi_prod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String codi_prod = String.valueOf(adapter.getItem(position)).replace("\n", ",");
                String[] prod  = codi_prod.split(",");

                lecturaProducto(prod[0]);
                sv_codi_prod.setQuery("", false);
                sv_codi_prod.setIconified(true);
                sv_codi_prod.clearFocus();
                lv_codi_prod.setVisibility(View.GONE);
            }
        });

        fab_regi_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProductos(nume_serv, edt_codi_prod.getText().toString(), txt_cant_movi.getText().toString(), edt_obse_apro.getText().toString());
            }
        });

    }

    private void listaProductos(String nume_iden) {

        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        if (db!=null){
            Cursor c = db.rawQuery(" SELECT DISTINCT codi_prod FROM canjesdevoluciones WHERE nume_iden = '"+nume_iden+"' ", null);

            if (c.getCount()>0){

                if (c.moveToNext()){
                    do {
                        String codi_prod = String.valueOf(c.getString(0));
                        productos.add(codi_prod);
                    } while (c.moveToNext());

                    adapter = new ArrayAdapter(getBaseContext(),android.R.layout.select_dialog_item, productos);
                    lv_codi_prod.setAdapter(adapter);
                }
                c.close();
            }
        }
        db.close();
    }


    @SuppressLint("RestrictedApi")
    private void lecturaProducto (String codi_prod) {

        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        if (db!=null){
            Cursor c = db.rawQuery("SELECT nomb_prod, codi_prod, cant_movi, nume_serv FROM canjesdevoluciones WHERE nume_iden = '"+nume_iden+"' and codi_prod = '"+codi_prod+"' ", null);

            if (c.getCount()>0){
                if (c.moveToNext()){
                    edt_nomb_prod.setText(String.valueOf(c.getString(0)));
                    edt_codi_prod.setText(String.valueOf(c.getInt(1)));
                    txt_cant_movi.setText(String.valueOf(c.getString(2)));
                    cont_maxi = Integer.parseInt(c.getString(2));
                    nume_serv = Integer.parseInt(c.getString(3));
                    cont_inic = Integer.parseInt(txt_cant_movi.getText().toString());
                    lny_cuad_prod.setVisibility(View.VISIBLE);
                    lny_cuad_guar.setVisibility(View.VISIBLE);
                }
            }
            c.close();
        }
        db.close();
    }


    private void registrarProductos(int nume_serv, String codi_prod, String cant_movi, String obse_apro){

        if (modo){
            String url = getString(R.string.url_empr)+"distribucion/registrarProductos?nume_serv="+nume_serv+"&codi_prod="+codi_prod+"&cant_movi="+cant_movi+"&obse_apro="+obse_apro;
            jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    limpiarControles();
                    JSONObject regi_prod = null;
                    try {
                        regi_prod = response.getJSONObject(0);
                        String mensaje =  regi_prod.getString("mensaje");
                        Toast.makeText(getBaseContext(),mensaje, Toast.LENGTH_SHORT).show();
                        //new dialogo_personal(getBaseContext(),mensaje);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "REGIPROD: No se puede conectar con el  servidor."+error);
                }
            });

            jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_DEFAULT_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            request.add(jsonArrayRequest);

        } else{
            Toast.makeText(getBaseContext(),"El registro es OFFLINE", Toast.LENGTH_SHORT).show();
        }


    }

    private void limpiarControles(){
        edt_nomb_prod.setText("");
        edt_codi_prod.setText("");
        edt_obse_apro.setText("");
        txt_cant_movi.setText("0");
        cont_maxi = 0;
        nume_serv = 0;
        lny_cuad_prod.setVisibility(View.GONE);
        lny_cuad_guar.setVisibility(View.GONE);
    }

}