package com.dupreincaperu.dupree.mh_fragments_asesora;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento_repo_ases_deud extends Fragment {
    
    View vista;

    SearchView ases_busc;
    TextView nomb_apel, codi_zona, codi_camp, sald_docu, txt_zona, txt_campa, txt_deud ;
    ImageView foto, raya,img_comp;

    String nume_iden="";
    String nomb_apel_bd="";
    String zona="";
    String campana="";
    String deuda="";
    String sexo="";

    private ProgressDialog pdp = null;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    Fragmento_repo_ases_deud contexto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contexto = this;
        vista = inflater.inflate(R.layout.fragment_repo_ases_deud, container, false);

        request= Volley.newRequestQueue(getContext());

        ases_busc = (SearchView) vista.findViewById(R.id.sear_ases_busc);
        nomb_apel = (TextView) vista.findViewById(R.id.txt_nomb_ases);
        codi_zona = (TextView) vista.findViewById(R.id.txt_zona_ases);
        codi_camp = (TextView) vista.findViewById(R.id.txt_camp_ases);
        sald_docu = (TextView) vista.findViewById(R.id.txt_deud_ases);
        foto    = (ImageView) vista.findViewById(R.id.img_foto_ases);
        txt_campa = (TextView) vista.findViewById(R.id.txt_texto_campana);
        txt_deud = (TextView) vista.findViewById(R.id.txt_texto_deuda);
        txt_zona = (TextView) vista.findViewById(R.id.txt_texto_zona);
        raya = (ImageView) vista.findViewById(R.id.imgraya);
        img_comp = (ImageView) vista.findViewById(R.id.img_comp);

        ases_busc.setQueryHint("DNI");

        ases_busc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ases_busc.setIconified(false);
            }
        });

        img_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compartir();
            }
        });

        ases_busc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String dni) {
                verifica_asesora(dni);
                ases_busc.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                String campo_searview = newText;
                if (campo_searview.equalsIgnoreCase("")){
                    foto.setImageResource(R.drawable.buscar);
                    raya.setVisibility(View.INVISIBLE);
                    codi_zona.setVisibility(View.INVISIBLE);
                    txt_zona.setVisibility(View.INVISIBLE);
                    codi_camp.setVisibility(View.INVISIBLE);
                    txt_campa.setVisibility(View.INVISIBLE);
                    sald_docu.setVisibility(View.INVISIBLE);
                    txt_deud.setVisibility(View.INVISIBLE);
                    img_comp.setVisibility(View.INVISIBLE);
                    nomb_apel.setText("Ingrese el documento de identidad para obtener los datos de la asesora");
                }
                return false;
            }
        });

        //handleSSLHandshake();

        return vista;
    }

    private void consulta_asesora(String dni) {

        nume_iden=dni;
        String url = getString(R.string.url_empr)+"asesora/ases?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pdp.dismiss();
                try {
                    JSONObject asesora = response.getJSONObject(0);
                    nomb_apel_bd =  asesora.getString("nomb_terc").trim()+" "+asesora.getString("apel_terc").trim();
                    zona = asesora.getString("codi_zona");
                    campana = asesora.getString("codi_camp");
                    deuda = asesora.getString("sald_docu");
                    sexo = asesora.getString("tipo_gene");
                    foto.setVisibility(View.VISIBLE);
                    if (sexo.equalsIgnoreCase("F")){
                        foto.setImageResource(R.drawable.femenina);
                    }
                    else{
                        foto.setImageResource(R.drawable.masculino);
                    }
                    raya.setVisibility(View.VISIBLE);
                    nomb_apel.setVisibility(View.VISIBLE);
                    nomb_apel.setText(nomb_apel_bd);
                    codi_zona.setVisibility(View.VISIBLE);
                    codi_zona.setText(": "+" "+zona);
                    txt_zona.setVisibility(View.VISIBLE);
                    codi_camp.setVisibility(View.VISIBLE);
                    codi_camp.setText(": "+" "+campana);
                    txt_campa.setVisibility(View.VISIBLE);
                    sald_docu.setVisibility(View.VISIBLE);
                    sald_docu.setText(":  S/."+deuda);
                    txt_deud.setVisibility(View.VISIBLE);
                    img_comp.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast t = Toast.makeText(getContext(),"No se puede conectar con el servidor.",Toast.LENGTH_SHORT);
                t.show();
            }
        });
        request.add(jsonArrayRequest);
    }

    private void verifica_asesora(String dni) {

        final String nume_iden=dni;
        String url = getString(R.string.url_empr)+"asesora/veri?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pdp.dismiss();
                try {
                    JSONObject identi = response.getJSONObject(0);
                    String mensaje = identi.getString("mensaje");

                    if (mensaje.equalsIgnoreCase("OK")){
                        consulta_asesora(nume_iden);
                    }
                    else {
                        nomb_apel.setVisibility(View.VISIBLE);
                        nomb_apel.setText(mensaje);
                        raya.setVisibility(View.INVISIBLE);
                        codi_zona.setVisibility(View.INVISIBLE);
                        txt_zona.setVisibility(View.INVISIBLE);
                        codi_camp.setVisibility(View.INVISIBLE);
                        txt_campa.setVisibility(View.INVISIBLE);
                        sald_docu.setVisibility(View.INVISIBLE);
                        txt_deud.setVisibility(View.INVISIBLE);
                        //cuadro.setVisibility(View.INVISIBLE);
                        foto.setVisibility(View.VISIBLE);
                        foto.setImageResource(R.drawable.no_datos);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast t = Toast.makeText(getContext(),"No se puede conectar con el servidor.",Toast.LENGTH_SHORT);
                t.show();
            }
        });

        request.add(jsonArrayRequest);

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void compartir() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,""+ Html.fromHtml("CONSULTA ASESOR (A) DUPREE-MÓVIL<br/>"+"--------------------------------------------------------------<br/>"+
                "DNI : "+nume_iden+"<br/>Asesor (a) : "+nomb_apel_bd+"<br/>"+
                "Zona : "+zona+"<br/>Campaña : "+campana+"<br/>Deuda : S/. "+deuda));
        startActivity(Intent.createChooser(intent, "Compartir con :"));
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
