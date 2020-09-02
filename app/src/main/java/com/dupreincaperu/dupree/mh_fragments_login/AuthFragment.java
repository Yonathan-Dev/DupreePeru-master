package com.dupreincaperu.dupree.mh_fragments_login;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.BuildConfig;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dialogs.MH_Dialogs_Login;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
import com.dupreincaperu.dupree.mh_required_api.RequiredAuth;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;
import com.dupreincaperu.dupree.mh_sqlite.Tab_prog_moviContract;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.google.firebase.iid.FirebaseInstanceId;

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
public class AuthFragment extends Fragment {
    private final String TAG = "AuthFragment";

    private String tokenDevice=null;

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    EditText txtUsername, txtPwd;

    static final Integer PHONESTATS = 0x1;
    String URL_EMPRESA="";
    TextView txt_vers_name;
    CheckBox chk_reco_usua;
    String  imei="";

    String nomb_empl = "";
    String apel_empl = "";
    String corr_empl = "";
    String nume_iden = "";
    String nomb_comp = "";
    String tipo_vinc = "";

    String[] reporte;
    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    private ProgressDialog pdp = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_auth, container, false);


        txtUsername   = (EditText) v.findViewById(R.id.txtUsername);
        txtPwd        = (EditText) v.findViewById(R.id.txtPwd);
        txt_vers_name = (TextView) v.findViewById(R.id.txt_vers_name);
        chk_reco_usua = (CheckBox) v.findViewById(R.id.chk_reco_usua);

        Button btnLogin   = (Button)  v.findViewById(R.id.btnLogin);
        TextView tvForgot = (TextView) v.findViewById(R.id.tvForgot);

        verifica_base();

        dato_gene URL = new dato_gene();
        URL_EMPRESA = URL.getURL_EMPRESA();


        request = Volley.newRequestQueue(getContext());

        //txtUsername.setText("1015434512"); //asesora
        //txtPwd.setText("1015434512");

        //txtUsername.setText("ana_yanquen");
        //txtPwd.setText("868Ac*4301");

        //txtUsername.setText("1015434512"); //Asesora
        //txtPwd.setText("1015434512");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), MenuActivity.class));

                if(validateAuth()){
                    guardarpreferencia();
                    txtUsername.requestFocus();
                    verificar_usuario();
                    //httpAuth();
                }
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gotoPage(PAGE_IDENTY);
                publishResult(MH_Dialogs_Login.BROACAST_LOGIN_BTNFORGOT);
                txtPwd.setText("");
                guardarpreferencia();
            }
        });

        txt_vers_name.setText("Versión: "+getVersionName());

        return v;
    }

    public boolean validateAuth(){
        Validate valid=new Validate();
        if(txtUsername.getText().toString().trim().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtUsername);
            return false;
        } else if(valid.isValidPwd(txtPwd.getText().toString().trim())){
            valid.setLoginError(getResources().getString(R.string.clave_invalida), txtPwd);
            return false;
        }
        return true;
    }

    private void httpAuth(){
        tokenDevice = mPreferences.getTokenFirebase(getActivity());
        if(tokenDevice==null) {//si no se ha generado un token nuevo
            tokenDevice = FirebaseInstanceId.getInstance().getToken();//se  obtiene el anterior
            Log.e("last tokenDevice","> "+tokenDevice);
        } else {
            Log.e("obtained tokenDevice","> "+tokenDevice);
        }
        new Http(getActivity()).Auth(new RequiredAuth(txtUsername.getText().toString().trim(), txtPwd.getText().toString().trim(), tokenDevice));
    }

    private void publishResult(String object){
        Log.i(TAG,"publishResult: "+object);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                new Intent(MH_Dialogs_Login.BROACAST_LOGIN)
                        .putExtra(MH_Dialogs_Login.BROACAST_LOGIN, object));
                        //.putExtra(MH_Dialogs_Login.BROACAST_LOGIN_DATA, data));

    }

    public String getVersionName(){
        Log.i("VERSION NAME", String.valueOf(BuildConfig.VERSION_NAME));
        return BuildConfig.VERSION_NAME;
    }

    private void verifica_base() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    private void cargarpreferencias() {
        SharedPreferences preferences =getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String  codi_usua  = preferences.getString("codi_usua","");
        String  clav_usua  = preferences.getString("clav_usua","");
        Boolean reco_usua  = preferences.getBoolean("reco_usua",false);

        if (reco_usua){
            txtUsername.setText(codi_usua);
            txtPwd.setText(clav_usua);
            chk_reco_usua.setChecked(reco_usua);
        }

    }


    private void guardarpreferencia() {

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String codi_usua    = txtUsername.getText().toString().trim();
        String clav_usua    = txtPwd.getText().toString().trim();
        Boolean reco_usua   = chk_reco_usua.isChecked();
        String nume_iden    = this.nume_iden;
        String nomb_comp    = this.nomb_comp;
        String corr_empl    = this.corr_empl;
        String tipo_vinc    = this.tipo_vinc;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("codi_usua",  codi_usua);
        editor.putString("clav_usua",  clav_usua);
        editor.putBoolean("reco_usua", reco_usua);
        editor.putString("nume_iden",  nume_iden);
        editor.putString("nomb_comp",  nomb_comp);
        editor.putString("corr_empl",  corr_empl);
        editor.putString("tipo_vinc",  tipo_vinc);

        editor.commit();
    }

    private void consultarPermiso(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {
            obtenerPermisos();
        }
    }

    @SuppressLint("MissingPermission")
    private void obtenerPermisos() {

        /*PERMISO PARA EL GPS*/
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }

        /*PERMISO DE TELEFONO*/
        TelephonyManager tMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        permiso_camara();
        /*PERMISO */

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {

                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    obtenerPermisos();
                } else {
                    Toast.makeText(getContext(), "Has negado el permiso a la aplicación", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void verificar_usuario() {

        String url = URL_EMPRESA+"usuario/veri?codi_usua="+txtUsername.getText().toString().trim()+"&clav_usua="+txtPwd.getText().toString().trim();
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {

                    JSONObject verifica = response.getJSONObject(0);
                    String mensaje = verifica.getString("mensaje");
                    if (mensaje.equalsIgnoreCase("OK")) {
                        //Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                        logearse_usuario();
                    } else if(mensaje.equalsIgnoreCase("CL")){
                        //Toast.makeText(getContext(), "CL", Toast.LENGTH_SHORT).show();
                        logearse_cliente();
                    } else{
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                        borr_tab_prog_movi();
                        //httpAuth();
                        pdp.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),""+error, Toast.LENGTH_SHORT).show();
            }
        });

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_logi);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    //Guardar datos y los programas que esta asignado al Usuario.
    private void logearse_usuario() {

        String url = URL_EMPRESA+"usuario/usua?codi_usua="+txtUsername.getText().toString().trim()+"&clav_usua="+txtPwd.getText().toString().trim();
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject empleado = response.getJSONObject(0);
                    nomb_empl = empleado.getString("nomb_empl").trim();
                    apel_empl = empleado.getString("apel_empl").trim();
                    corr_empl = empleado.getString("corr_empl").trim();
                    nume_iden = empleado.getString("nume_iden").trim();
                    nomb_comp = (nomb_empl+" "+apel_empl).toUpperCase();
                    if (!empleado.getString("tipo_vinc").equalsIgnoreCase(null))
                        tipo_vinc = empleado.getString("tipo_vinc");

                    guardarpreferencia();

                    //Declara el tamano de array de tipo String para almacenar los reportes que tiene asginado el usuario
                    if (response.length()>0){
                        borr_tab_prog_movi();
                    }
                    reporte = new String[response.length()];
                    for (int i=0;i<response.length();i++){
                        JSONObject acce_repo = response.getJSONObject(i);
                        reporte[i]=String.valueOf(acce_repo.getString("codi_prog"));
                        alma_tab_prog_movi(String.valueOf(acce_repo.getString("codi_prog")));
                    }
                    pdp.dismiss();
                    httpAuth();


                } catch (JSONException e) {
                    pdp.dismiss();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Log.i("Autenticacion","No se puede conectar con el servidor");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void logearse_cliente() {

        String url = URL_EMPRESA+"usuario/clie?codi_usua="+txtUsername.getText().toString().trim()+"&clav_usua="+txtPwd.getText().toString().trim();
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject empleado = response.getJSONObject(0);
                    nomb_empl = empleado.getString("nomb_terc").trim();
                    apel_empl = empleado.getString("apel_terc").trim();
                    corr_empl = empleado.getString("mail_ases").trim();
                    nume_iden = empleado.getString("nume_iden").trim();
                    nomb_comp = (nomb_empl+" "+apel_empl).toUpperCase();

                    guardarpreferencia();

                    //Declara el tamano de array de tipo String para almacenar los reportes que tiene asginado el usuario
                    if (response.length()>0){
                        borr_tab_prog_movi();
                    }
                    reporte = new String[response.length()];
                    for (int i=0;i<response.length();i++){
                        JSONObject acce_repo = response.getJSONObject(i);
                        reporte[i]=String.valueOf(acce_repo.getString("codi_prog"));
                        alma_tab_prog_movi(String.valueOf(acce_repo.getString("codi_prog")));
                    }
                    pdp.dismiss();
                    httpAuth();

                } catch (JSONException e) {
                    pdp.dismiss();
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Log.i("Autenticacion","No se puede conectar con el servidor");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void borr_tab_prog_movi() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();
        Cursor c = db1.rawQuery("SELECT codi_prog FROM tab_prog_movi", null);
        if (c.getCount()>0 && db1!=null){
            db1.execSQL("DELETE FROM "+ Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME );
        }
    }

    private void alma_tab_prog_movi(String codi_prog) {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db1 = dbHelper.getWritableDatabase();

        Cursor c = db1.rawQuery("SELECT codi_prog FROM tab_prog_movi WHERE codi_prog= '"+codi_prog+"'", null);

        if (c.getCount()>0 && db1!=null){
            if (c.moveToNext()){
                codi_prog  = c.getString(0) ;
            }
            db1.execSQL("DELETE FROM "+ Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME +" WHERE codi_prog = '"+codi_prog+"' ");
        }

        if (db1!=null){
            db1.execSQL("INSERT INTO "+ Tab_prog_moviContract.Tab_prog_moviEntry.TABLE_NAME +
                    "("+ Tab_prog_moviContract.Tab_prog_moviEntry.COLUMN_CODI_PROG+ ")" +
                    "VALUES ('"+codi_prog+"')");
        }

    }


    private boolean permiso_camara() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ){
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1000);
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        cargarpreferencias();
        consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        super.onResume();
    }


}
