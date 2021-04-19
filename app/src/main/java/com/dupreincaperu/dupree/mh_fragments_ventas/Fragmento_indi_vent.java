package com.dupreincaperu.dupree.mh_fragments_ventas;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;


public class Fragmento_indi_vent extends Fragment {


    Fragmento_indi_vent contexto;
    View vista;

    String codi_camp, fech_inic, fech_fina;
    TextView txt_codi_camp, txt_fech_inic, txt_fech_fina;
    String porc_inco, cant_inco, obje_inco;
    TextView txt_porc_inco, txt_cant_inco, txt_obje_inco;
    String porc_rete, cant_rete, obje_rete;
    TextView txt_porc_rete, txt_cant_rete, txt_obje_rete;
    String porc_cobr, vent_cobr, sald_cobr;
    TextView txt_porc_cobr, txt_vent_cobr, txt_sald_cobr;
    String porc_acti, tota_capi;
    TextView txt_porc_acti, txt_tota_capi;

    String v_nume_iden, v_codi_usua;

    private ProgressDialog pdp = null;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contexto = this;
        request= Volley.newRequestQueue(getContext());

        vista = inflater.inflate(R.layout.fragment_indi_vent, container, false);

        txt_codi_camp = (TextView) vista.findViewById(R.id.txt_codi_camp);
        txt_fech_inic = (TextView) vista.findViewById(R.id.txt_fech_inic);
        txt_fech_fina = (TextView) vista.findViewById(R.id.txt_fech_fina);
        txt_porc_inco = (TextView) vista.findViewById(R.id.txt_porc_inco);
        txt_obje_inco = (TextView) vista.findViewById(R.id.txt_obje_inco);
        txt_cant_inco = (TextView) vista.findViewById(R.id.txt_cant_inco);
        txt_porc_rete = (TextView) vista.findViewById(R.id.txt_porc_rete);
        txt_obje_rete = (TextView) vista.findViewById(R.id.txt_obje_rete);
        txt_cant_rete = (TextView) vista.findViewById(R.id.txt_cant_rete);
        txt_porc_cobr = (TextView) vista.findViewById(R.id.txt_porc_cobr);
        txt_vent_cobr = (TextView) vista.findViewById(R.id.txt_vent_cobr);
        txt_sald_cobr = (TextView) vista.findViewById(R.id.txt_sald_cobr);
        txt_porc_acti = (TextView) vista.findViewById(R.id.txt_porc_acti);
        txt_tota_capi = (TextView) vista.findViewById(R.id.txt_tota_capi);

        ConnectivityManager connectivityManager = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            camp_actu();
        } else{
            new dialogo_personal(getContext(),"Para mostrar los indicadores debe contar con conexion a Internet.");
        }

        return vista;
    }


    private void camp_actu() {

        String url = getString(R.string.url_empr)+"indicadores/camp?";
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject indi_camp = response.getJSONObject(0);
                    codi_camp = indi_camp.getString("codi_camp").trim();
                    fech_inic = indi_camp.getString("fech_inic").trim();
                    fech_fina = indi_camp.getString("fech_fina").trim();

                    String[] parts_fech_inic = fech_inic.split("-");
                    String[] parts_fech_fina = fech_fina.split("-");
                    String mes_inic = parts_fech_inic[1]; // Mes
                    String mes_fina = parts_fech_fina[1]; // Mes

                    switch(mes_inic){
                        case "01":
                            mes_inic = "ENERO";
                            break;
                        case "02":
                            mes_inic = "FEBRERO";
                            break;
                        case "03":
                            mes_inic = "MARZO";
                            break;
                        case "04":
                            mes_inic = "ABRIL";
                            break;
                        case "05":
                            mes_inic = "MAYO";
                            break;
                        case "06":
                            mes_inic = "JUNIO";
                            break;
                        case "07":
                            mes_inic = "JULIO";
                            break;
                        case "08":
                            mes_inic = "AGOSTO";
                            break;
                        case "09":
                            mes_inic = "SETIEMBRE";
                            break;
                        case "10":
                            mes_inic = "OCTUBRE";
                            break;
                        case "11":
                            mes_inic = "NOVIEMBRE";
                            break;
                        case "12":
                            mes_inic = "DICIEMBRE";
                            break;
                    }

                    switch(mes_fina){
                        case "01":
                            mes_fina = "ENERO";
                            break;
                        case "02":
                            mes_fina = "FEBRERO";
                            break;
                        case "03":
                            mes_fina = "MARZO";
                            break;
                        case "04":
                            mes_fina = "ABRIL";
                            break;
                        case "05":
                            mes_fina = "MAYO";
                            break;
                        case "06":
                            mes_fina = "JUNIO";
                            break;
                        case "07":
                            mes_fina = "JULIO";
                            break;
                        case "08":
                            mes_fina = "AGOSTO";
                            break;
                        case "09":
                            mes_fina = "SETIEMBRE";
                            break;
                        case "10":
                            mes_fina = "OCTUBRE";
                            break;
                        case "11":
                            mes_fina = "NOVIEMBRE";
                            break;
                        case "12":
                            mes_fina = "DICIEMBRE";
                            break;
                    }
                    txt_codi_camp.setText(codi_camp);
                    txt_fech_inic.setText(parts_fech_inic[2]+" DE "+ mes_inic);
                    txt_fech_fina.setText(parts_fech_fina[2]+" DE "+ mes_fina);

                    indi_vent(codi_camp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    Toast.makeText(getContext(),mensaje.getString("mensaje"),Toast.LENGTH_SHORT).show();
                    pdp.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
                pdp.dismiss();
            }
        });

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_sami);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        request.add(jsonArrayRequest);
    }


    private void indi_vent(String codi_camp) {

        String url = getString(R.string.url_empr)+"indicadores/vent?nume_iden="+v_nume_iden+"&codi_usua="+v_codi_usua+"&codi_camp="+codi_camp;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject indi = response.getJSONObject(0);
                    porc_inco = indi.getString("porc_inco").trim();
                    obje_inco = indi.getString("obje_inco").trim();
                    cant_inco = indi.getString("cant_inco").trim();
                    porc_rete = indi.getString("porc_rete").trim();
                    obje_rete = indi.getString("obje_rete").trim();
                    cant_rete = indi.getString("cant_rete").trim();
                    porc_cobr = indi.getString("porc_cobr").trim();
                    vent_cobr = indi.getString("vent_cobr").trim();
                    sald_cobr = indi.getString("sald_cobr").trim();
                    porc_acti = indi.getString("porc_acti").trim();
                    tota_capi = indi.getString("tota_capi").trim();

                    txt_cant_inco.setText(cant_inco);
                    txt_obje_inco.setText(obje_inco);
                    txt_porc_inco.setText(porc_inco+" %");
                    txt_porc_rete.setText(porc_rete+" %");
                    txt_obje_rete.setText(obje_rete);
                    txt_cant_rete.setText(cant_rete);
                    txt_porc_cobr.setText(porc_cobr+" %");
                    txt_vent_cobr.setText("S/. "+vent_cobr);
                    txt_sald_cobr.setText("S/. "+sald_cobr);
                    txt_porc_acti.setText(porc_acti+" %");
                    txt_tota_capi.setText(tota_capi);

                    cerr_circ();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    Toast.makeText(getContext(),mensaje.getString("mensaje"),Toast.LENGTH_SHORT).show();
                    pdp.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
                pdp.dismiss();
            }
        });


        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(MY_DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void cerr_circ() {
        if (  !txt_porc_inco.getText().toString().equalsIgnoreCase("-") && !txt_obje_inco.getText().toString().equalsIgnoreCase("-")
                && !txt_cant_inco.getText().toString().equalsIgnoreCase("-") && !txt_porc_rete.getText().toString().equalsIgnoreCase("-")
                && !txt_obje_rete.getText().toString().equalsIgnoreCase("-") && !txt_cant_rete.getText().toString().equalsIgnoreCase("-")
                && !txt_porc_cobr.getText().toString().equalsIgnoreCase("-") && !txt_vent_cobr.getText().toString().equalsIgnoreCase("-")
                && !txt_sald_cobr.getText().toString().equalsIgnoreCase("-") && !txt_porc_acti.getText().toString().equalsIgnoreCase("-")
                && !txt_tota_capi.getText().toString().equalsIgnoreCase("-")){
            pdp.dismiss();
        }
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String codi_usua      = preferences.getString("codi_usua","");
        String nume_iden      = preferences.getString("nume_iden","");

        v_codi_usua = codi_usua;
        v_nume_iden = nume_iden;
    }

    @Override
    public void onResume() {
        cargarpreferencias();
        super.onResume();
    }
}
