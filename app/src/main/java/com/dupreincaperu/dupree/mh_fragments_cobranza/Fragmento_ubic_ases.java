package com.dupreincaperu.dupree.mh_fragments_cobranza;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_fragments_ventas.ubic_ases;
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

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragmento_ubic_ases extends Fragment {
    View vista;


    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    Fragmento_ubic_ases contexto;

    private ProgressDialog pdp = null;

    SearchView sv_nume_iden;
    SearchView sv_nume_iden_ruta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contexto = this;
        vista = inflater.inflate(R.layout.fragmento_ubic_ases, container, false);

        sv_nume_iden      = (SearchView) vista.findViewById(R.id.sv_nume_iden);
        sv_nume_iden_ruta = (SearchView) vista.findViewById(R.id.sv_nume_iden_ruta);

        contexto = this;

        request = Volley.newRequestQueue(getContext());

        sv_nume_iden.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nume_iden) {

                validar_identidad(nume_iden, "1");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equalsIgnoreCase("")){
                    sv_nume_iden.clearFocus();
                }

                return true;
            }
        });


        sv_nume_iden_ruta.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nume_iden) {

                validar_identidad(nume_iden, "2");

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return  vista;
    }

    private void validar_identidad(final String nume_iden, final String i) {

        String url = getString(R.string.url_empr)+"asesora/ubic?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    JSONObject geo_clie = response.getJSONObject(0);
                    String mensaje  = geo_clie.getString("mensaje").trim();

                    if (mensaje.equalsIgnoreCase("")){

                        String cy = geo_clie.getString("cy").trim() ;
                        String cx = geo_clie.getString("cx").trim() ;
                        String cons_terc = geo_clie.getString("cons_terc").trim() ;

                        if (cy.equalsIgnoreCase("") || cx.equalsIgnoreCase("") || cx.equalsIgnoreCase("null") || cy.equalsIgnoreCase("null")) {
                            pdp.dismiss();
                            Toast.makeText(getContext(), "* La asesora no tiene una ubicación definida en el Mapcity", Toast.LENGTH_SHORT).show();

                            Intent p = new Intent(getContext(), ruta.class);
                            p.putExtra("nume_iden",nume_iden);
                            p.putExtra("cy", "0.0");
                            p.putExtra("cx", "0.0");
                            p.putExtra("cons_terc", cons_terc);
                            p.putExtra("band","0");
                            startActivity(p);
                            sv_nume_iden_ruta.clearFocus();


                        } else {
                            pdp.dismiss();

                            switch (i){
                                case "1":
                                    Intent t = new Intent(getContext(), ubic_ases.class);
                                    t.putExtra("nume_iden",nume_iden);
                                    startActivity(t);
                                    sv_nume_iden.clearFocus();
                                    break;

                                case "2":
                                    Intent p = new Intent(getContext(), ruta.class);
                                    p.putExtra("nume_iden",nume_iden);
                                    p.putExtra("cy", cy);
                                    p.putExtra("cx", cx);
                                    p.putExtra("cons_terc", cons_terc);
                                    p.putExtra("band", "1");
                                    startActivity(p);
                                    sv_nume_iden_ruta.clearFocus();
                                    break;
                            }


                        }
                    } else{
                        pdp.dismiss();
                        Toast.makeText(getContext(), "*Error la identificación no existe.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pdp.dismiss();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),"No se puede conectar con el servidor."+error, Toast.LENGTH_SHORT).show();
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
