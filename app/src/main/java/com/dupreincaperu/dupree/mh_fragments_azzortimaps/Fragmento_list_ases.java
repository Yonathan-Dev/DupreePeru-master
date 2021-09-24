package com.dupreincaperu.dupree.mh_fragments_azzortimaps;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoMensaje;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento_list_ases extends Fragment implements View.OnClickListener {

    View vista;
    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    Fragmento_list_ases contexto;
    private ProgressDialog pdp = null;

    String cedu_vend="", codi_usua="", styleZona="";
    int band=0;
    Button btn_gene_list_ases, btn_visu_mapa, btn_visi_ases;
    ImageButton img_visi_efec, img_visu_mapa, img_visi_ases;
    EditText edt_codi_camp, edt_codi_zona, edt_codi_sect;
    CheckBox chk_cons, chk_inco, chk_peg21, chk_peg42, chk_peg63, chk_posi_reincor, chk_posi_reingre, chk_reinco, chk_reingr;
    CheckBox chk_ret_peg21, chk_ret_peg42, chk_ret_peg63, chk_sin_pedi;
    String error = "";
    String tipo_clie_cons, tipo_clie_inco, tipo_clie_peg21, tipo_clie_peg42, tipo_clie_peg63, tipo_clie_posi_reincor, tipo_clie_posi_reingre;
    String tipo_clie_reinco, tipo_clie_reingr, tipo_clie_ret_peg21, tipo_clie_ret_peg42, tipo_clie_ret_peg63, tipo_clie_sin_pedi;
    LinearLayout lny_visi_efec, lny_codi_camp, lny_codi_zona, lny_codi_sect, lny_gene_mapa, lny_opci_boto, lny_gene_ases;
    TextView txt_status;
    ScrollView sv_status;
    AutoCompleteTextView actv_iden_nomb;

    ArrayList<String> campanas = new ArrayList<String>();
    ArrayList<String> zonas = new ArrayList<String>();
    ArrayList<String> sectores = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contexto = this;
        cargarpreferencias();
        vista = inflater.inflate(R.layout.fragment_list_ases, container, false);

        request = Volley.newRequestQueue(getContext());

        btn_gene_list_ases = vista.findViewById(R.id.btn_gene_list_ases);
        btn_visu_mapa      = vista.findViewById(R.id.btn_visu_mapa);
        btn_visi_ases      = vista.findViewById(R.id.btn_visi_ases);
        img_visi_efec      = vista.findViewById(R.id.img_visi_efec);
        img_visu_mapa      = vista.findViewById(R.id.img_visu_mapa);
        img_visi_ases      = vista.findViewById(R.id.img_visi_ases);
        edt_codi_camp      = vista.findViewById(R.id.edt_codi_camp);
        edt_codi_zona      = vista.findViewById(R.id.edt_codi_zona);
        edt_codi_sect      = vista.findViewById(R.id.edt_codi_sect);
        chk_cons           = vista.findViewById(R.id.chk_cons);
        chk_inco           = vista.findViewById(R.id.chk_inco);
        chk_peg21          = vista.findViewById(R.id.chk_peg21);
        chk_peg42          = vista.findViewById(R.id.chk_peg42);
        chk_peg63          = vista.findViewById(R.id.chk_peg63);
        chk_posi_reincor   = vista.findViewById(R.id.chk_posi_reincor);
        chk_posi_reingre   = vista.findViewById(R.id.chk_posi_reingre);
        chk_reinco         = vista.findViewById(R.id.chk_reinco);
        chk_reingr         = vista.findViewById(R.id.chk_reingr);
        chk_ret_peg21      = vista.findViewById(R.id.chk_ret_peg21);
        chk_ret_peg42      = vista.findViewById(R.id.chk_ret_peg42);
        chk_ret_peg63      = vista.findViewById(R.id.chk_ret_peg63);
        chk_sin_pedi       = vista.findViewById(R.id.chk_sin_pedi);
        lny_visi_efec      = vista.findViewById(R.id.lny_visi_efec);
        lny_codi_camp      = vista.findViewById(R.id.lny_codi_camp);
        lny_codi_zona      = vista.findViewById(R.id.lny_codi_zona);
        lny_codi_sect      = vista.findViewById(R.id.lny_codi_sect);
        lny_gene_mapa      = vista.findViewById(R.id.lny_gene_mapa);
        lny_opci_boto      = vista.findViewById(R.id.lny_opci_boto);
        lny_gene_ases      = vista.findViewById(R.id.lny_gene_ases);
        txt_status         = vista.findViewById(R.id.txt_status);
        sv_status          = vista.findViewById(R.id.sv_status);
        actv_iden_nomb     = vista.findViewById(R.id.actv_iden_nomb);

        chk_cons.setOnClickListener(this);
        chk_inco.setOnClickListener(this);
        chk_peg21.setOnClickListener(this);
        chk_peg42.setOnClickListener(this);
        chk_peg63.setOnClickListener(this);
        chk_posi_reincor.setOnClickListener(this);
        chk_posi_reingre.setOnClickListener(this);
        chk_reinco.setOnClickListener(this);
        chk_reingr.setOnClickListener(this);
        chk_ret_peg21.setOnClickListener(this);
        chk_ret_peg42.setOnClickListener(this);
        chk_ret_peg63.setOnClickListener(this);
        chk_sin_pedi.setOnClickListener(this);

        //Visita efectiva
        btn_gene_list_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codi_camp = edt_codi_camp.getText().toString().trim();
                String codi_zona = edt_codi_zona.getText().toString().trim();
                String codi_sect = edt_codi_sect.getText().toString().trim();

                if (codi_sect.equalsIgnoreCase("Sector")){
                    codi_sect = "";
                }

                if (codi_camp.equalsIgnoreCase("Campaña")){
                    new dialogoMensaje(getContext(),"Error ingrese campaña");
                } else if(codi_zona.equalsIgnoreCase("Zona")){
                    new dialogoMensaje(getContext(),"Error ingrese Zona");
                } else if (band==0){
                    new dialogoMensaje(getContext(),"Error ingrese Status");
                } else if (band>3){
                    new dialogoMensaje(getContext(),"Error solo puede seleccionar 3 Status");
                } else {
                    Intent t = new Intent(getContext(), list_ases.class);
                        t.putExtra("codi_camp",             codi_camp);
                        t.putExtra("codi_zona",             codi_zona);
                        t.putExtra("codi_sect",             codi_sect);
                        t.putExtra("codi_usua",             codi_usua);
                        t.putExtra("tipo_clie_cons",        tipo_clie_cons);
                        t.putExtra("tipo_clie_inco",        tipo_clie_inco);
                        t.putExtra("tipo_clie_peg21",       tipo_clie_peg21);
                        t.putExtra("tipo_clie_peg42",       tipo_clie_peg42);
                        t.putExtra("tipo_clie_peg63",       tipo_clie_peg63);
                        t.putExtra("tipo_clie_posi_reincor",tipo_clie_posi_reincor);
                        t.putExtra("tipo_clie_posi_reingre",tipo_clie_posi_reingre);
                        t.putExtra("tipo_clie_reinco",      tipo_clie_reinco);
                        t.putExtra("tipo_clie_reingr",      tipo_clie_reingr);
                        t.putExtra("tipo_clie_ret_peg21",   tipo_clie_ret_peg21);
                        t.putExtra("tipo_clie_ret_peg42",   tipo_clie_ret_peg42);
                        t.putExtra("tipo_clie_ret_peg63",   tipo_clie_ret_peg63);
                        t.putExtra("tipo_clie_sin_pedi",    tipo_clie_sin_pedi);
                        t.putExtra("styleZona",             styleZona);
                    startActivity(t);
                }
            }
        });

        //Visualizar zona
        btn_visu_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codi_camp = "";
                String codi_zona = edt_codi_zona.getText().toString().trim();
                String codi_sect = "";

                if(codi_zona.equalsIgnoreCase("Zona")){
                    new dialogoMensaje(getContext(),"Error ingrese Zona");
                }  else {
                    Intent t = new Intent(getContext(), list_ases.class);
                        t.putExtra("codi_camp", codi_camp);
                        t.putExtra("codi_zona", codi_zona);
                        t.putExtra("codi_sect", codi_sect);
                        t.putExtra("codi_usua", codi_usua);
                        t.putExtra("tipo_clie_cons",         tipo_clie_cons);
                        t.putExtra("tipo_clie_inco",         tipo_clie_inco);
                        t.putExtra("tipo_clie_peg21",        tipo_clie_peg21);
                        t.putExtra("tipo_clie_peg42",        tipo_clie_peg42);
                        t.putExtra("tipo_clie_peg63",        tipo_clie_peg63);
                        t.putExtra("tipo_clie_posi_reincor", tipo_clie_posi_reincor);
                        t.putExtra("tipo_clie_posi_reingre", tipo_clie_posi_reingre);
                        t.putExtra("tipo_clie_reinco",       tipo_clie_reinco);
                        t.putExtra("tipo_clie_reingr",       tipo_clie_reingr);
                        t.putExtra("tipo_clie_ret_peg21",    tipo_clie_ret_peg21);
                        t.putExtra("tipo_clie_ret_peg42",    tipo_clie_ret_peg42);
                        t.putExtra("tipo_clie_ret_peg63",    tipo_clie_ret_peg63);
                        t.putExtra("tipo_clie_sin_pedi",     tipo_clie_sin_pedi);
                        t.putExtra("styleZona",              styleZona);
                    startActivity(t);
                }
            }
        });

        //Visita asesora
        btn_visi_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codi_camp = edt_codi_camp.getText().toString().trim();

                String dato_clie = actv_iden_nomb.getText().toString();
                String[] arrOfStr = dato_clie.split("\\|");

                if (codi_camp.equalsIgnoreCase("Campaña")){
                    new dialogoMensaje(getContext(),"Error ingrese campaña");
                } else if (dato_clie.equalsIgnoreCase("")){
                    new dialogoMensaje(getContext(),"Error ingrese datos asesora");
                } else if (arrOfStr[0].trim().length()!=8 && arrOfStr[0].trim().length()!=11){
                    new dialogoMensaje(getContext(),"Error longitud de dato incorrecto");
                } else {
                    tipo_clie_cons = "CONSECUTIVA";
                    tipo_clie_inco = "INCORPORACION";
                    tipo_clie_peg21 = "PEG21";
                    tipo_clie_peg42 = "PEG42";
                    tipo_clie_peg63 = "PEG63";
                    tipo_clie_posi_reincor = "POSIBLE REINCORPORACION";
                    tipo_clie_posi_reingre = "POSIBLE REINGRESO";
                    tipo_clie_reinco = "REINCORPORACION";
                    tipo_clie_reingr = "REINGRESO";
                    tipo_clie_ret_peg21 = "RET. PEG21";
                    tipo_clie_ret_peg42 = "RET. PEG42";
                    tipo_clie_ret_peg63 = "RET. PEG63";
                    tipo_clie_sin_pedi = "SIN PEDIDO";
                    String nume_iden = arrOfStr[0].trim();
                    String codi_zona = arrOfStr[2].trim();

                    generarMapa(codi_zona);

                    listado_asesora_sami(codi_camp, codi_zona, "", nume_iden, codi_usua, tipo_clie_cons, tipo_clie_inco, tipo_clie_peg21,tipo_clie_peg42, tipo_clie_peg63,tipo_clie_posi_reincor, tipo_clie_posi_reingre, tipo_clie_reinco, tipo_clie_reingr, tipo_clie_ret_peg21, tipo_clie_ret_peg42, tipo_clie_ret_peg63, tipo_clie_sin_pedi);

                }

            }
        });

        edt_codi_camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] miarray = new String[campanas.size()];
                miarray = campanas.toArray(miarray);
                final String[] finalMiarray = miarray;

                InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setIcon(R.drawable.ic_check);

                builder.setTitle("Seleccionar campaña")
                        .setItems(miarray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edt_codi_camp.setText(finalMiarray[which]);
                            }
                        });
                builder.show();
            }
        });

        edt_codi_zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] miarray = new String[zonas.size()];
                miarray = zonas.toArray(miarray);
                final String[] finalMiarray = miarray;

                InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setIcon(R.drawable.ic_check);

                builder.setTitle("Seleccionar zona")
                        .setItems(miarray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edt_codi_zona.setText(finalMiarray[which]);
                                edt_codi_sect.setText("");
                                cargarsect(edt_codi_zona.getText().toString().trim());
                                generarMapa(edt_codi_zona.getText().toString().trim());
                            }
                        });
                builder.show();
            }
        });

        edt_codi_sect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] miarray = new String[sectores.size()];
                miarray = sectores.toArray(miarray);
                final String[] finalMiarray = miarray;

                InputMethodManager imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setIcon(R.drawable.ic_check);

                builder.setTitle("Seleccionar sectores")
                        .setItems(miarray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                edt_codi_sect.setText(finalMiarray[which]);
                            }
                        });
                builder.show();
            }
        });


        img_visu_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lny_visi_efec.setVisibility(View.VISIBLE);
                lny_codi_camp.setVisibility(View.GONE);
                lny_codi_zona.setVisibility(View.VISIBLE);
                lny_codi_sect.setVisibility(View.GONE);
                lny_gene_mapa.setVisibility(View.VISIBLE);
                txt_status.setVisibility(View.GONE);
                sv_status.setVisibility(View.GONE);
                lny_opci_boto.setVisibility(View.GONE);
            }
        });

        img_visi_efec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lny_visi_efec.setVisibility(View.VISIBLE);
                lny_codi_camp.setVisibility(View.VISIBLE);
                lny_codi_zona.setVisibility(View.VISIBLE);
                lny_codi_sect.setVisibility(View.VISIBLE);
                lny_gene_mapa.setVisibility(View.GONE);
                txt_status.setVisibility(View.VISIBLE);
                sv_status.setVisibility(View.VISIBLE);
                lny_opci_boto.setVisibility(View.GONE);
            }
        });

        img_visi_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lny_visi_efec.setVisibility(View.VISIBLE);
                lny_codi_camp.setVisibility(View.VISIBLE);
                lny_codi_zona.setVisibility(View.GONE);
                lny_codi_sect.setVisibility(View.GONE);
                lny_gene_mapa.setVisibility(View.GONE);
                txt_status.setVisibility(View.GONE);
                sv_status.setVisibility(View.GONE);
                lny_opci_boto.setVisibility(View.GONE);
                lny_gene_ases.setVisibility(View.VISIBLE);

                cargarclientes(cedu_vend);
            }
        });

        return vista;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.chk_cons:
                if (chk_cons.isChecked()){
                    tipo_clie_cons = "CONSECUTIVA";
                    band++;
                } else {
                    tipo_clie_cons = "";
                    band--;
                }
                break;
            case R.id.chk_inco:
                if (chk_inco.isChecked()){
                    tipo_clie_inco = "INCORPORACION";
                    band++;
                } else {
                    tipo_clie_inco = "";
                    band--;
                }
                break;
            case R.id.chk_peg21:
                if (chk_peg21.isChecked()){
                    tipo_clie_peg21 = "PEG21";
                    band++;
                } else {
                    tipo_clie_peg21 = "";
                    band--;
                }
                break;
            case R.id.chk_peg42:
                if (chk_peg42.isChecked()){
                    tipo_clie_peg42 = "PEG42";
                    band++;
                } else {
                    tipo_clie_peg42 = "";
                    band--;
                }
                break;
            case R.id.chk_peg63:
                if (chk_peg63.isChecked()){
                    tipo_clie_peg63 = "PEG63";
                    band++;
                } else {
                    tipo_clie_peg63 = "";
                    band--;
                }
                break;
            case R.id.chk_posi_reincor:
                if (chk_posi_reincor.isChecked()){
                    tipo_clie_posi_reincor = "POSIBLE REINCORPORACION";
                    band++;
                } else {
                    tipo_clie_posi_reincor = "";
                    band--;
                }
                break;
            case R.id.chk_posi_reingre:
                if (chk_posi_reingre.isChecked()){
                    tipo_clie_posi_reingre = "POSIBLE REINGRESO";
                    band++;
                } else {
                    tipo_clie_posi_reingre = "";
                    band--;
                }
                break;
            case R.id.chk_reinco:
                if (chk_reinco.isChecked()){
                    tipo_clie_reinco = "REINCORPORACION";
                    band++;
                } else {
                    tipo_clie_reinco = "";
                    band--;
                }
                break;
            case R.id.chk_reingr:
                if (chk_reingr.isChecked()){
                    tipo_clie_reingr = "REINGRESO";
                    band++;
                } else {
                    tipo_clie_reingr = "";
                    band--;
                }
                break;
            case R.id.chk_ret_peg21:
                if (chk_ret_peg21.isChecked()){
                    tipo_clie_ret_peg21 = "RET. PEG21";
                    band++;
                } else {
                    tipo_clie_ret_peg21 = "";
                    band--;
                }
                break;
            case R.id.chk_ret_peg42:
                if (chk_ret_peg42.isChecked()){
                    tipo_clie_ret_peg42 = "RET. PEG42";
                    band++;
                } else {
                    tipo_clie_ret_peg42 = "";
                    band--;
                }
                break;
            case R.id.chk_ret_peg63:
                if (chk_ret_peg63.isChecked()){
                    tipo_clie_ret_peg63 = "RET. PEG63";
                    band++;
                } else {
                    tipo_clie_ret_peg63 = "";
                    band--;
                }
                break;
            case R.id.chk_sin_pedi:
                if (chk_sin_pedi.isChecked()){
                    tipo_clie_sin_pedi = "SIN PEDIDO";
                    band++;
                } else {
                    tipo_clie_sin_pedi = "";
                    band--;
                }
                break;
        }

    }

    private void cargarcamp() {
        String url = getString(R.string.url_empr)+"gerentes/camp?";
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    campanas.clear();
                    for (int i=0; i<response.length(); i++){
                        JSONObject camp = response.getJSONObject(i);
                        String codi_camp  = camp.getString("codi_camp").trim();
                        campanas.add(codi_camp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                new dialogoMensaje(getContext(), "No se puede conectar con el servidor");
            }
        });


        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);

    }

    private void cargarzona(String cedu_vend) {

        String url = getString(R.string.url_empr)+"gerentes/zona?cedu_vend="+cedu_vend;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    zonas.clear();
                    for (int i=0; i<response.length(); i++){
                        JSONObject zona = response.getJSONObject(i);
                        String codi_zona  = zona.getString("codi_zona").trim();
                        zonas.add(codi_zona);
                    }

                    pdp.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    pdp.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Log.i("ZONA", "No se puede conectar con el servidor");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void cargarsect(String codi_zona) {
        String url = getString(R.string.url_empr)+"gerentes/sect?codi_zona="+codi_zona;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    sectores.clear();
                    for (int i=0; i<response.length(); i++){
                        JSONObject sector = response.getJSONObject(i);
                        String codi_sect  = sector.getString("codi_sect").trim();
                        sectores.add(codi_sect);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Log.i("SECTOR", "No se puede conectar con el servidor");
            }
        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(jsonArrayRequest);
    }

    private void cargarclientes(String cedu_vend) {
        String url = getString(R.string.url_empr)+"gerentes/clie?cedu_vend="+cedu_vend;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> clientes= new ArrayList<String>();
                    for (int i=0; i<response.length(); i++){
                        JSONObject clie = response.getJSONObject(i);
                        String dato_clie  = clie.getString("nume_iden").trim()+" | "+clie.getString("nomb_clie").trim()+" | "+clie.getString("codi_zona");
                        clientes.add(dato_clie);
                    }

                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(),android.R.layout.select_dialog_item, clientes);
                    actv_iden_nomb.setAdapter(adapter);
                    pdp.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    pdp.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Log.i("SECTOR", "No se puede conectar con el servidor");
            }
        });

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(jsonArrayRequest);
    }


    private void listado_asesora_sami(String codi_camp, String codi_zona, String codi_sect, String nume_iden, String codi_usua, String tipo_clie_cons, String tipo_clie_inco, String tipo_clie_peg21,String tipo_clie_peg42, String tipo_clie_peg63, String tipo_clie_posi_reincor, String tipo_clie_posi_reingre, String tipo_clie_reinco, String tipo_clie_reingr, String tipo_clie_ret_peg21, String tipo_clie_ret_peg42, String tipo_clie_ret_peg63, String tipo_clie_sin_pedi) {

        String url = getString(R.string.url_empr)+"gerentes/listsami?codi_camp="+codi_camp+"&codi_zona="+codi_zona+"&codi_sect="+codi_sect+"&nume_iden="+nume_iden+"&codi_usua="+codi_usua+"&tipo_clie_cons="+tipo_clie_cons+"&tipo_clie_inco="+tipo_clie_inco+"&tipo_clie_peg21="+tipo_clie_peg21+"&tipo_clie_peg42="+tipo_clie_peg42+"&tipo_clie_peg63="+tipo_clie_peg63+"&tipo_clie_posi_reincor="+tipo_clie_posi_reincor+"&tipo_clie_posi_reingre="+tipo_clie_posi_reingre+"&tipo_clie_reinco="+tipo_clie_reinco+"&tipo_clie_reingr="+tipo_clie_reingr+"&tipo_clie_ret_peg21="+tipo_clie_ret_peg21+"&tipo_clie_ret_peg42="+tipo_clie_ret_peg42+"&tipo_clie_ret_peg63="+tipo_clie_ret_peg63+"&tipo_clie_sin_pedi="+tipo_clie_sin_pedi;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    if(mensaje.getString("mensaje").trim().equalsIgnoreCase("ERROR")){
                        new dialogoMensaje(getContext(),"LISTA VACIA");
                    } else {
                        JSONObject geo_clie = response.getJSONObject(0);

                        String nume_iden   = geo_clie.getString("nume_iden").trim();
                        String codi_sect   = geo_clie.getString("codi_sect").trim();
                        String codi_zona   = geo_clie.getString("codi_zona").trim();
                        String nomb_terc   = geo_clie.getString("nomb_terc").trim();
                        String apel_terc   = geo_clie.getString("apel_terc").trim();
                        String camp_ingr   = geo_clie.getString("camp_ingr").trim();
                        String cy          = geo_clie.getString("cy").trim();
                        String cx          = geo_clie.getString("cx").trim();
                        String sald_docu   = geo_clie.getString("sald_docu").trim();
                        String tipo_clie   = geo_clie.getString("tipo_clie").trim();
                        String nomb_barr   = geo_clie.getString("nomb_barr").trim();
                        String dire_terc   = geo_clie.getString("dire_terc").trim();
                        String fech_naci   = geo_clie.getString("fech_naci").trim();
                        String tele_ter1   = geo_clie.getString("tele_ter1").trim();
                        String tele_ter2   = geo_clie.getString("tele_ter2").trim();
                        String codi_camp_1 = geo_clie.getString("codi_camp_1").trim();
                        if (codi_camp_1.equalsIgnoreCase(""))
                            codi_camp_1 = "-";
                        String esta_visi ="";
                        try {
                            esta_visi   = geo_clie.getString("esta_visi").trim();
                        } catch (Exception e){
                            esta_visi   = "-";
                        }


                        Intent i = new Intent(getContext(), visita.class);
                            i.putExtra("nume_iden", nume_iden);
                            i.putExtra("codi_sect", codi_sect);
                            i.putExtra("codi_zona", codi_zona);
                            i.putExtra("nomb_terc", nomb_terc);
                            i.putExtra("apel_terc", apel_terc);
                            i.putExtra("camp_ingr", camp_ingr);
                            i.putExtra("cy", cy);
                            i.putExtra("cx", cx);
                            i.putExtra("sald_docu", sald_docu);
                            i.putExtra("tipo_clie", tipo_clie);
                            i.putExtra("nomb_barr", nomb_barr);
                            i.putExtra("dire_terc", dire_terc);
                            i.putExtra("fech_naci", fech_naci);
                            i.putExtra("tele_ter1", tele_ter1);
                            i.putExtra("tele_ter2", tele_ter2);
                            i.putExtra("codi_camp_1", codi_camp_1);
                            i.putExtra("esta_visi", esta_visi);
                            i.putExtra("styleZona", styleZona);
                        startActivity(i);

                    }
                    pdp.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    pdp.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pdp.dismiss();
            }
        });

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }



    @Override
    public void onResume() {
        cargarpreferencias();
        cargarcamp();
        cargarzona(cedu_vend);
        cargarpreferenciaserrores();

        if (!error.equalsIgnoreCase("")){
            new dialogoMensaje(getContext(),error);
            guardarpreferenciaerrores("");
        }

        limpiar();
        super.onResume();
    }


    private void cargarpreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String nume_iden = preferences.getString("nume_iden", "");
        String codi_usua = preferences.getString("codi_usua", "");

        cedu_vend = nume_iden;
        this.codi_usua = codi_usua;
    }

    private void cargarpreferenciaserrores() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("errores", Context.MODE_PRIVATE);
        String error = preferences.getString("error", "");
        this.error = error;
    }

    private void guardarpreferenciaerrores(String e) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("errores", Context.MODE_PRIVATE);
        String error = e;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("error", error);

        editor.commit();
    }

    private void generarMapa(String codi_zona) {
        String url = getString(R.string.url_empr)+"gerentes/generarMapa?nomb_apli=AZZORTI&codi_zona="+codi_zona;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject ruta = response.getJSONObject(0);
                    styleZona = ruta.getString("ruta_styl").trim();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("generarMapa","No tiene conexion"+e.getMessage());
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void limpiar(){
        chk_cons.setChecked(false);
        chk_inco.setChecked(false);
        chk_peg21.setChecked(false);
        chk_peg42.setChecked(false);
        chk_peg63.setChecked(false);
        chk_posi_reincor.setChecked(false);
        chk_posi_reingre.setChecked(false);
        chk_reinco.setChecked(false);
        chk_reingr.setChecked(false);
        chk_ret_peg21.setChecked(false);
        chk_ret_peg42.setChecked(false);
        chk_ret_peg63.setChecked(false);
        chk_sin_pedi.setChecked(false);

        tipo_clie_cons="";
        tipo_clie_inco="";
        tipo_clie_peg21="";
        tipo_clie_peg42="";
        tipo_clie_peg63="";
        tipo_clie_posi_reincor="";
        tipo_clie_posi_reingre="";
        tipo_clie_reinco="";
        tipo_clie_reingr="";
        tipo_clie_ret_peg21="";
        tipo_clie_ret_peg42="";
        tipo_clie_ret_peg63="";
        tipo_clie_sin_pedi="";
        band=0;
        error="";

        actv_iden_nomb.setText("");

        edt_codi_camp.setText("");
        edt_codi_zona.setText("");
        edt_codi_sect.setText("");
    }

}
