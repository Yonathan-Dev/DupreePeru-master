package com.dupreincaperu.dupree.mh_fragments_distribucion;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.dupreincaperu.dupree.mh_dial_peru.cuadro_mensaje_bd;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;


public class Fragmento_geoc_inve extends Fragment {

    View vista;
    Fragmento_geoc_inve contexto;
    String URL_EMPRESA="";

    private int dia, mes, ano;
    private ProgressDialog pdp = null;
    ImageView cale_fac_conf, geoc_fac_conf;
    TextView acti_hora_fac_conf;
    EditText edt_codi_zona;
    String acti_hora="", cons_fac_conf="",fac_lati="", fac_long="", fac_dire="", codi_zona="", acti_usua="", acti_usua_vaci="" ;
    int cont_geoc=0, cont_auxi=0;
    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dato_gene URL = new dato_gene();
        dato_gene SSL = new dato_gene();

        URL_EMPRESA = URL.getURL_EMPRESA();
        /*
        if (String.valueOf(SSL.getSSL_EMPRESA()).equalsIgnoreCase("PROD")){
            request = Volley.newRequestQueue(getContext(), new HurlStack(null, getSocketFactory()));
        } else{
            request = Volley.newRequestQueue(getContext(), new HurlStack(null, getSocketFactory_test()));
        } */
        request = Volley.newRequestQueue(getContext());

        contexto = this;
        request= Volley.newRequestQueue(getContext());
        vista = inflater.inflate(R.layout.fragment_geoc_inve, container, false);

        cale_fac_conf      = (ImageView) vista.findViewById(R.id.img_acti_hora_fac_conf);
        geoc_fac_conf      = (ImageView) vista.findViewById(R.id.img_geoc_fac_conf);
        acti_hora_fac_conf = (TextView)  vista.findViewById(R.id.txt_acti_hora_fac_conf);
        edt_codi_zona      = (EditText)  vista.findViewById(R.id.edt_codi_zona);

        acti_hora_fac_conf.setText(getDate());
        acti_hora = getDate();

        cale_fac_conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                dia = c.get (Calendar.DAY_OF_MONTH);
                mes = c.get (Calendar.MONTH);
                ano = c.get (Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        acti_hora_fac_conf.setText((month+1)+"/"+dayOfMonth+"/"+year);
                        acti_hora = (month+1)+"/"+dayOfMonth+"/"+year;
                    }
                }
                        ,dia,mes,ano);
                datePickerDialog.show();
            }
        });


        geoc_fac_conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(edt_codi_zona.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"*Error ingrese la zona.",Toast.LENGTH_SHORT).show();
                } else  */
                if (acti_hora.equalsIgnoreCase("")){
                    Toast.makeText(getContext(),"*Error ingrese la fecha",Toast.LENGTH_SHORT).show();
                } else {
                    codi_zona = edt_codi_zona.getText().toString();
                    obtener_coordenadas(acti_hora, codi_zona);
                }
            }
        });

        return vista;
    }


    public String direccion(String fac_lati, String fac_long ){

        String fac_dire = "";

        if (Double.parseDouble(fac_lati) != 0.0 && Double.parseDouble(fac_long) != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        Double.parseDouble(fac_lati), Double.parseDouble(fac_long), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    fac_dire = String.valueOf(DirCalle.getAddressLine(0));

                    fac_dire = fac_dire.replace("'", " ").replace("&","y").replace("("," ").replace("#"," ").replace("%", " ").
                            replace(">", " ").replace("<", " ").replace("!"," ").replace("$"," ").replace(";"," ").replace("*"," ").
                            replace("?"," ").replace("^"," ").replace("{"," ").replace("}"," ").replace("+"," ").replace("-"," ").
                            replace(":"," ").replace("`"," ").replace("."," ").replace("|"," ").replace(",", " ").replace("@", " ").
                            replace("^"," ").replace("_"," ").replace("?"," ").replace("["," ").replace("]"," ").replace("Ú","U").replace("ú","u").
                            replace("Ó","O").replace("ó","o").replace("Í","i").replace("í","i").replace("É","E").replace("é","e").replace("Á","A").replace("á","a").
                            replaceAll("[^a-zA-Z0-9@.#$%^&*_&?$()]"," ").trim();

                    if (fac_dire.trim().length()<11){
                        fac_dire = "";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                Toast.makeText(getContext(),"IllegalArgumentException"+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return fac_dire;
    }

    private void obtener_coordenadas(final String acti_hora, String codi_zona) {

        String url = URL_EMPRESA+"distribucion/geoc_inve?acti_hora="+acti_hora+"&codi_zona="+codi_zona;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int i = response.length();
                    cont_auxi = i;

                    while (i >= 1){

                        JSONObject geoc = response.getJSONObject(i-1);
                        cons_fac_conf = geoc.getString("cons_fac_conf");
                        fac_lati      = geoc.getString("fac_lati").trim();
                        fac_long      = geoc.getString("fac_long").trim();

                        fac_dire = direccion(fac_lati , fac_long).trim();
                        if (!fac_dire.equalsIgnoreCase(""))
                            geocodificacion_inversa(cons_fac_conf, fac_dire);

                        i--;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("Error",""+e);
                }
                try {
                    pdp.dismiss();
                    JSONObject mensaje = response.getJSONObject(0);
                    new cuadro_mensaje_bd(getContext(), mensaje.getString("mensaje"),3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                Toast.makeText(getContext(),"No se puede conectar con el  servidor."+e,Toast.LENGTH_SHORT).show();
                cont_geoc = 0;
                pdp.dismiss();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_geocodificar);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void geocodificacion_inversa(String cons_fac_conf, String fac_dire) {

        String url = URL_EMPRESA+"distribucion/actu_dire?cons_fac_conf="+cons_fac_conf+"&fac_dire="+fac_dire;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                cont_geoc++;
                if (cont_auxi == cont_geoc){
                    most_cant_geoc(cont_geoc);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                cont_geoc = 0;
                Toast.makeText(getContext(),"No se puede conectar con el  servidor."+e,Toast.LENGTH_SHORT).show();
                pdp.dismiss();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        request.add(jsonArrayRequest);
    }

    private void most_cant_geoc(int cont_geoc) {
        if (cont_geoc==1){
            pdp.dismiss();
            new cuadro_mensaje_bd(getContext(), "Se realizo la geocodificación inversa de "+cont_geoc+" pedido !!",4);
            this.cont_geoc = 0;
        } else {
            pdp.dismiss();
            new cuadro_mensaje_bd(getContext(), "Se realizo la geocodificación inversa de "+cont_geoc+" pedidos !!",4);
            this.cont_geoc = 0;
        }
    }

    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    private SSLSocketFactory getSocketFactory() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.movildupreepe);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.e("CERT", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);


            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    Log.e("CipherUsed", session.getCipherSuite());
                    return hostname.compareTo("movil.dupree.pe")==0; //The Hostname of your server

                }
            };


            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

            SSLSocketFactory sf = context.getSocketFactory();


            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return  null;
    }


    private SSLSocketFactory getSocketFactory_test() {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            InputStream caInput = getResources().openRawResource(R.raw.azzortico);
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                Log.e("CERT", "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);


            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {

                    Log.e("CipherUsed", session.getCipherSuite());
                    return hostname.compareTo("servicioweb2per.dupreincaperu.co")==0; //The Hostname of your server

                }
            };

            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            SSLContext context = null;
            context = SSLContext.getInstance("TLS");

            context.init(null, tmf.getTrustManagers(), null);
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());

            SSLSocketFactory sf = context.getSocketFactory();

            return sf;

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
