package com.dupreincaperu.dupree.mh_fragments_canjes;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_personal;
import com.dupreincaperu.dupree.mh_fragments_distribucion.Fragmento_proc_dist_conf_manu;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;


public class Fragment_canjes extends Fragment {
    View vista;
    Fragment_canjes contexto;
    SearchView sv_nume_iden;
    Spinner spn_codi_prod;
    EditText edt_codi_prod, edt_nomb_prod, edt_obse_apro;
    TextView txt_sele_codi, txt_cant_movi;
    LinearLayout lny_cuad_prod, lny_cuad_guar;
    ImageButton img_remo_cont, img_agre_cont;
    int cont_inic = 0, cont_maxi=0;
    String nume_serv="", nume_iden="";
    com.getbase.floatingactionbutton.FloatingActionButton fab_regi_prod;

    ArrayList<String> listanume_iden = new ArrayList<>();
    ArrayList<String> listanume_serv = new ArrayList<>();
    ArrayList<String> listacodi_prod = new ArrayList<String>();
    ArrayList<String> listanomb_prod = new ArrayList<>();
    ArrayList<String> listacant_movi = new ArrayList<>();

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contexto = this;
        vista = inflater.inflate(R.layout.fragment_canjes, container, false);

        request = Volley.newRequestQueue(getContext());

        sv_nume_iden  = (SearchView)   vista.findViewById(R.id.sv_nume_iden);
        spn_codi_prod = (Spinner)      vista.findViewById(R.id.spn_codi_prod);
        txt_sele_codi = (TextView)     vista.findViewById(R.id.txt_sele_codi);
        txt_cant_movi = (TextView)     vista.findViewById(R.id.txt_cant_movi);
        edt_codi_prod = (EditText)     vista.findViewById(R.id.edt_codi_prod);
        edt_nomb_prod = (EditText)     vista.findViewById(R.id.edt_nomb_prod);
        edt_obse_apro = (EditText)     vista.findViewById(R.id.edt_obse_apro);
        lny_cuad_prod = (LinearLayout) vista.findViewById(R.id.lny_cuad_prod);
        lny_cuad_guar = (LinearLayout) vista.findViewById(R.id.lny_cuad_guar);
        img_agre_cont = (ImageButton)  vista.findViewById(R.id.img_agre_cont);
        img_remo_cont = (ImageButton)  vista.findViewById(R.id.img_remo_cont);
        fab_regi_prod = (FloatingActionButton) vista.findViewById(R.id.fab_regi_prod);

        sv_nume_iden.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nume_iden) {
                buscarCanjes(nume_iden);
                sv_nume_iden.setQuery("", false);
                sv_nume_iden.setIconified(true);
                sv_nume_iden.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length()>0) {
                    limpiarControles();
                }
                return true;
            }
        });

        spn_codi_prod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    lny_cuad_prod.setVisibility(View.VISIBLE);
                    lny_cuad_guar.setVisibility(View.VISIBLE);

                    edt_codi_prod.setText(listacodi_prod.get(pos));
                    edt_nomb_prod.setText(listanomb_prod.get(pos));
                    txt_cant_movi.setText(listacant_movi.get(pos));
                    edt_obse_apro.setText("");
                    cont_inic = Integer.parseInt (txt_cant_movi.getText().toString());
                    cont_maxi = Integer.parseInt (txt_cant_movi.getText().toString());
                    nume_serv = listanume_serv.get(pos);
                    nume_iden = listanume_iden.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        fab_regi_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProductos(nume_serv, edt_codi_prod.getText().toString(), txt_cant_movi.getText().toString(), edt_obse_apro.getText().toString(), nume_iden);
            }
        });

        return vista;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void buscarCanjes(String nume_iden){

        String url = getString(R.string.url_empr)+"distribucion/buscarCanjes?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    if (mensaje.getString("mensaje").equalsIgnoreCase("")){
                        for (int i=0; i<response.length();i++){
                            JSONObject respuesta = response.getJSONObject(i);
                            String cons_terc = respuesta.getString("cons_terc");
                            String nume_iden = respuesta.getString("nume_iden");
                            String nume_serv = respuesta.getString("nume_serv");
                            String codi_prod = respuesta.getString("codi_prod");
                            String nomb_prod = respuesta.getString("nomb_prod");
                            String cant_movi = respuesta.getString("cant_movi");

                            listanume_iden.add(nume_iden);
                            listanume_serv.add(nume_serv);
                            listacodi_prod.add(codi_prod);
                            listanomb_prod.add(nomb_prod);
                            listacant_movi.add(cant_movi);

                            spn_codi_prod.setVisibility(View.VISIBLE);
                            txt_sele_codi.setVisibility(View.VISIBLE);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listacodi_prod);
                            spn_codi_prod.setAdapter(adapter);
                        }
                    } else {
                        new dialogo_personal(getContext(), mensaje.getString("mensaje"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);

    }


    private void registrarProductos(String nume_serv, String codi_prod, String cant_movi, String obse_apro, String nume_iden){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Atención");
        dialogo1.setMessage("¿ Esta seguro que desea realizar el registro ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar",   new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                String url = getString(R.string.url_empr)+"distribucion/registrarProductos?nume_serv="+nume_serv+"&codi_prod="+codi_prod+"&cant_movi="+cant_movi+"&obse_apro="+obse_apro+"&nume_iden="+nume_iden;
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        limpiarCuadros();

                        MyDbHelper dbHelper = new MyDbHelper(getContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String acti_hora = getDateTime();
                        String codi_cons = "";
                        String serv_cons = "";

                        Cursor c = db.rawQuery("SELECT codi_prod, nume_serv FROM canj_web_conf WHERE codi_prod = '"+codi_prod+"' and nume_serv = '"+nume_serv+"' ", null);

                        if (c.getCount()>0 && db!=null){
                            if (c.moveToNext()){
                                codi_cons = c.getString(0);
                                serv_cons = c.getString(1);
                            }
                            db.execSQL("DELETE FROM canj_web_conf WHERE codi_prod = '"+codi_cons+"' and nume_serv = '"+serv_cons+"' ");
                        }

                        if (db!=null){
                            limpiarCuadros();
                            db.execSQL("INSERT INTO canj_web_conf (nume_serv, codi_prod, cant_movi, obse_apro, acti_hora, nume_iden, modo_regi) VALUES ('"+nume_serv+"','"+codi_prod+"','"+cant_movi+"','"+obse_apro+"','"+acti_hora+"','"+nume_iden+"','ON')");
                        }
                        c.close();
                        db.close();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onErrorResponse", "REGIPROD: No se puede conectar con el  servidor."+error);
                    }
                });

                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_DEFAULT_TIMEOUT,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                request.add(jsonArrayRequest);

            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
                limpiarCuadros();
            }
        });
        dialogo1.show();

    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void limpiarControles(){
        txt_sele_codi.setVisibility(View.GONE);
        spn_codi_prod.setVisibility(View.GONE);
        lny_cuad_prod.setVisibility(View.GONE);
        lny_cuad_guar.setVisibility(View.GONE);
        txt_cant_movi.setText("0");
        edt_obse_apro.setText("");
        cont_maxi = 0;
        cont_inic = 0;
        nume_iden = "";
        listacodi_prod.clear();
        listanomb_prod.clear();
        listacant_movi.clear();
        listanume_serv.clear();
        listanume_iden.clear();
    }

    private void limpiarCuadros(){
        lny_cuad_prod.setVisibility(View.GONE);
        lny_cuad_guar.setVisibility(View.GONE);
        edt_obse_apro.setText("");
    }

}