package com.dupreincaperu.dupree.mh_fragments_distribucion;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.BuildConfig;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.cuadro_confirma;
import com.dupreincaperu.dupree.mh_dial_peru.cuadro_dialogo;
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_acceso;
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_celular;
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_datos;
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_personal;
import com.dupreincaperu.dupree.mh_fragments_ventas.ubic_ases;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
import com.dupreincaperu.dupree.mh_sqlite.CajaContract;
import com.dupreincaperu.dupree.mh_sqlite.ClienteContract;
import com.dupreincaperu.dupree.mh_sqlite.Fac_confContract;
import com.dupreincaperu.dupree.mh_sqlite.Moti_rech_distContract;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragmento_proc_dist_conf_manu extends Fragment implements cuadro_confirma.FinalizoCuadroDialogo, dialogo_celular.CelularCuadroDialogo, dialogo_datos.DatosCuadroDialogo{

    public static final String STRING_PREFERENCES = "DatosUsuario";
    public static final String PREFERENCES_ESTADO_SESION = "EstadoSesion";

    static final Integer PHONESTATS = 0x1;

    TextView dni_ases;
    TextView asesora;
    TextView nume_factura;
    TextView cant_cajas;
    TextView txt_nume_celu;
    TextView txt_dist_geog;
    TextView txt_modi_dire;
    TextView txt_modi_refe;
    Button btn_conf_manu, btn_rech_manu;

    TextView direccion;
    String acti_usua;
    View vista;

    ImageView gps;

    SwitchCompat switchForActionBar;

    SearchView searchView_nume_fact;
    Fragmento_proc_dist_conf_manu contexto;
    String fac_lati="", fac_long="", fac_dire="", fact_sri="", nomb_moti="", fac_imag="";
    int codi_vers =0;
    String  acti_fech,nume_iden,nomb_terc,apel_terc, dire_terc, nume_factc, remi_sri, codi_camp, cons_terc, cy, cx, dist_zona, cant_caja, codi_caja, desc_caja, docu_sri, nume_celu, esta_celu;
    int cons_fac_conf;
    String sinc_fact_sri="", sinc_fac_lati="", sinc_fac_long="", sinc_fac_dire="", sinc_acti_usua="", sinc_acti_hora="", sinc_nomb_moti="", sinc_nume_fact="", sinc_fac_imag="", acti_hora_veri="", sinc_codi_vers="", sinc_nume_iden="", sinc_codi_camp="", sinc_modi_dire="", sinc_modi_refe="";
    String path_sinc_imag ="", fact_sri_sinc_imag="", nume_fact_sinc_imag;
    FloatingActionButton fab_ubic_ases, fab_edit_dire;

    private long mLastClickTime = 0;

    int cont=0;
    int cant_sinc = 0;
    int dist = 0;
    int distancia = 0;

    String mensaje="";
    String estado="";

    TextView resultadoQR;
    TextView txt_codi_camp;
    String array_nomb_moti[];

    Bitmap bitmap;
    boolean modo;

    private ProgressDialog pdp = null;
    private ProgressDialog progress = null;
    private Handler updateBarHandler;

    AlertDialog.Builder builder;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    String URL_EMPRESA="";

    Button btnOrder;
    TextView tvItemSelected;
    String[] listItems;
    boolean[] checkendItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dato_gene URL = new dato_gene();
        URL_EMPRESA = URL.getURL_EMPRESA();

        contexto = this;
        vista = inflater.inflate(R.layout.fragment_proc_dist_conf_manu, container, false);

        cargarpreferencias();

        request = Volley.newRequestQueue(getContext());

        dni_ases                = (TextView) vista.findViewById(R.id.txtdni_ases);
        asesora                 = (TextView) vista.findViewById(R.id.txt_nomb_aseso);
        direccion               = (TextView) vista.findViewById(R.id.txt_direccion);
        nume_factura            = (TextView) vista.findViewById(R.id.txt_nume_pedido);
        searchView_nume_fact    = (SearchView) vista.findViewById(R.id.searchview_nume_fact);
        resultadoQR             = (TextView) vista.findViewById(R.id.txt_bole_elec);
        gps                     = (ImageView) vista.findViewById(R.id.imggps);
        switchForActionBar      = (SwitchCompat)vista.findViewById(R.id.switchForActionBar);
        cant_cajas              = (TextView)vista.findViewById(R.id.txt_cant_caja);
        btn_conf_manu           = (Button) vista.findViewById(R.id.btn_conf_manu);
        btn_rech_manu           = (Button) vista.findViewById(R.id.btn_rech_manu);
        fab_ubic_ases           = (FloatingActionButton) vista.findViewById(R.id.fab_ubic_ases);
        fab_edit_dire           = (FloatingActionButton) vista.findViewById(R.id.fab_edit_dire);
        txt_codi_camp           = (TextView) vista.findViewById(R.id.txt_codi_camp);
        txt_nume_celu           = (TextView) vista.findViewById(R.id.txt_nume_celu);
        txt_dist_geog           = (TextView) vista.findViewById(R.id.txt_dist_geog);
        txt_modi_dire           = (TextView) vista.findViewById(R.id.txt_modi_dire);
        txt_modi_refe           = (TextView) vista.findViewById(R.id.txt_modi_refe);

        btnOrder                = (Button)   vista.findViewById(R.id.btnOrder);
        tvItemSelected          = (TextView) vista.findViewById(R.id.tvItemSelected);
        listItems               = getResources().getStringArray(R.array.shopping_item);
        checkendItems           = new boolean[listItems.length];

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("Canjes y/o devolución");
                mBuilder.setMultiChoiceItems(listItems, checkendItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isCheked) {
                        if (isCheked){
                            if(!mUserItems.contains(position)){
                                mUserItems.add(position);
                            }
                        } else if(mUserItems.contains(position)){
                                mUserItems.remove((Integer)position);
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i =0; i<mUserItems.size() ; i++){
                            item = item + listItems[mUserItems.get(i)];
                            if (i!=mUserItems.size()-1){
                                item = item + ",";
                            }
                        }
                        tvItemSelected.setText(item);
                    }
                });

                mBuilder.setNeutralButton("Limpiar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0;i<checkendItems.length;i++){
                            checkendItems[i] = false;
                            mUserItems.clear();
                            tvItemSelected.setText("");
                        }

                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });




        searchView_nume_fact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String nume_fact) {

                elim_clie_dato();
                elim_pedi_conf();

                lectura_pedido(nume_fact);
                searchView_nume_fact.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equalsIgnoreCase("")){
                    limpiar_datos();
                    searchView_nume_fact.clearFocus();
                    fab_ubic_ases.setVisibility(View.GONE);
                    fab_edit_dire.setVisibility(View.GONE);
                }

                return true;
            }
        });


        btn_conf_manu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((SystemClock.elapsedRealtime() - mLastClickTime) > 1000){

                    if (resultadoQR.getText().toString().equalsIgnoreCase("")){
                        new dialogo_personal(getContext(),"ERROR DATOS VACIOS");
                        limpiar_datos();
                    } else if (nume_factura.getText().toString().equalsIgnoreCase("")){
                        new dialogo_personal(getContext(),"ERROR PEDIDO VACIO");
                        limpiar_datos();
                    } else if (fac_lati.equalsIgnoreCase("") || fac_long.equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ESPERE UN MOMENTO CAPTURANDO LAS COORDENADAS GEOGRAFICAS");
                        limpiar_datos();
                    } else if(dni_ases.getText().toString().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR DNI VACIO");
                        limpiar_datos();
                    } else if(getDateTime().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR FECHA Y HORA VACIO");
                        limpiar_datos();
                    } else if(txt_codi_camp.getText().toString().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR CAMPAÑA VACIO");
                        limpiar_datos();
                    } else if(String.valueOf(getCodiVersionCode()).equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR VERSIÓN VACIO");
                        limpiar_datos();
                    } else if (dist>distancia){
                        new dialogo_personal(getContext(),"EL PUNTO DE ENTREGA SE ENCUENTRA A "+dist+" METROS");
                    } else {
                        estado = "Entrega";
                        nomb_moti = "";
                        guardar_distribucion();
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        });

        btn_rech_manu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((SystemClock.elapsedRealtime() - mLastClickTime) > 1000) {

                    if (resultadoQR.getText().toString().equalsIgnoreCase("")){
                        new dialogo_personal(getContext(),"ERROR DATOS VACIOS");
                        limpiar_datos();
                    } else if (nume_factura.getText().toString().equalsIgnoreCase("")){
                        new dialogo_personal(getContext(),"ERROR PEDIDO VACIO");
                        limpiar_datos();
                    } else if (fac_lati.equalsIgnoreCase("") || fac_long.equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ESPERE UN MOMENTO CAPTURANDO LAS COORDENADAS GEOGRAFICAS");
                        limpiar_datos();
                    } else if(dni_ases.getText().toString().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR DNI VACIO");
                        limpiar_datos();
                    } else if(getDateTime().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR FECHA Y HORA VACIO");
                        limpiar_datos();
                    } else if(txt_codi_camp.getText().toString().equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR CAMPAÑA VACIO");
                        limpiar_datos();
                    } else if(String.valueOf(getCodiVersionCode()).equalsIgnoreCase("")) {
                        new dialogo_personal(getContext(),"ERROR VERSIÓN VACIO");
                        limpiar_datos();
                    } else {
                        new cuadro_confirma(getContext(), Fragmento_proc_dist_conf_manu.this, array_nomb_moti);
                        estado = "Devolución";
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
        });

        fab_ubic_ases.setVisibility(View.GONE);
        fab_edit_dire.setVisibility(View.GONE);

        fab_ubic_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nume_iden = dni_ases.getText().toString();

                if (nume_iden.equalsIgnoreCase("")){
                    new dialogo_personal(getContext(),"ERROR DNI VACIO");
                    searchView_nume_fact.clearFocus();
                } else {
                    validar_identidad(nume_iden);
                    searchView_nume_fact.clearFocus();
                }

                Snackbar.make(vista,"CARGANDO UBICACIÓN DE ASESORA", Snackbar.LENGTH_SHORT).show();
            }
        });

        fab_edit_dire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new dialogo_datos(getContext(), Fragmento_proc_dist_conf_manu.this);
            }
        });

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        if (isNetDisponible()) {
            motivos_devolucion();
        } else {
            carg_moti_rech_moti();
        }

        modo = true;
        return vista;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @SuppressLint("ResourceType")
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fac_conf_manu_menu, menu);
        MenuItem item = menu.findItem(R.id.myswitch);
        item.setActionView(R.layout.switch_layout);

        SwitchCompat mySwitch = item.getActionView().findViewById(R.id.switchForActionBar);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something based on isChecked
                if (isChecked){
                    modo = true;
                    limpiar_datos();
                    Toast.makeText(getContext(),"OnLine", Toast.LENGTH_SHORT).show();
                } else {
                    modo = false;
                    limpiar_datos();
                    Toast.makeText(getContext(),"OffLine", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nuevo:
                limpiar_datos();
                new dialogo_personal(getContext(),"Se limpiaron los datos");
                return true;

            case R.id.cargar:

                if (isNetDisponible()) {
                    cons_clie_dato();
                    cons_caja_dato();
                } else {
                    new dialogo_personal(getContext(), "No cuenta conexión a Internet");
                }
                return true;

            case R.id.sincronizar:
                if ((SystemClock.elapsedRealtime() - mLastClickTime) > 1000){
                    if (isNetDisponible()) {
                        sinc_fac_conf();
                    } else {
                        new dialogo_personal(getContext(), "No cuenta conexión a Internet");
                    }
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                return true;

            case R.id.myswitch:
                return true;

            case R.id.sqlite:
                Intent i  = new Intent(getContext(), pedido_registrado.class);
                startActivity(i);
                return true;

            case R.id.track:
                Intent t  = new Intent(getContext(), pedido_track.class);
                startActivity(t);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Log.i("onStartFragment","onStart");
        super.onStart();
    }

    public void onResume() {
        Log.i("onResumeFragment","onResume");
        elim_fac_conf();
        elim_clie_dato();
        elim_pedi_conf();
        sinc_pedi_falt();
        cargarpreferencias();
        verificar_celu(nume_celu);
        consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
        limpiar_datos();
        super.onResume();
    }

    public void onPause() {
        Log.i("onPauseFragment","onPause");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.i("onStopFragment","onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.i("onDestroyFragment","onDestroy");
        super.onDestroy();
    }

    private void locationStart() {

        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setFragmento_proc_dist_conf_manu(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    @Override
    public void ResultadoCuadroDialogo(String motivo) {
        nomb_moti = motivo;
        guardar_distribucion();
    }

    public void ResultadoNumeroCelular(String nume_celu) {
        this.nume_celu = nume_celu;
        guardarpreferencia();
        verificar_celu(nume_celu);
    }

    @SuppressLint("RestrictedApi")
    public void ResultadoDatos(String modi_dire, String modi_refe) {
        txt_modi_dire.setText(modi_dire);
        txt_modi_refe.setText(modi_refe);
        dist=0;
        fab_edit_dire.setVisibility(View.GONE);
    }

    public class Localizacion implements LocationListener {
        Fragmento_proc_dist_conf_manu fragmento_proc_dist_conf_manu;
        public Fragmento_proc_dist_conf_manu getFragmento_proc_dist_conf_manu() {
            return fragmento_proc_dist_conf_manu;
        }
        public void setFragmento_proc_dist_conf_manu(Fragmento_proc_dist_conf_manu fragmento_proc_dist_conf_manu) {
            this.fragmento_proc_dist_conf_manu = fragmento_proc_dist_conf_manu;
        }
        @Override
        public void onLocationChanged(Location loc) {

            fac_lati = String.valueOf(loc.getLatitude()) ;
            fac_long = String.valueOf(loc.getLongitude()) ;
            gps.setImageResource(R.drawable.gpsact);
        }
        @Override
        public void onProviderDisabled(String provider) {
            gps.setImageResource(R.drawable.gpsina);
            fac_lati = "";
            fac_long = "";
        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
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
            }
        }
        return fac_dire;
    }

    private void guardar_distribucion() {

        fac_dire = direccion(fac_lati, fac_long);
        codi_vers = getCodiVersionCode();

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Atención");
        dialogo1.setMessage("¿ Esta seguro que desea registrar la "+estado+" ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar",   new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                if (isNetDisponible() && modo == true) {

                    if (fac_lati.equalsIgnoreCase("") || fac_long.equalsIgnoreCase("") || resultadoQR.getText().toString().equalsIgnoreCase("") || getDateTime().equalsIgnoreCase("") || nume_factura.getText().toString().equalsIgnoreCase("") || String.valueOf(getCodiVersionCode()).equalsIgnoreCase("")  || dni_ases.getText().toString().equalsIgnoreCase("") ||txt_codi_camp.getText().toString().equalsIgnoreCase("") ){
                        new dialogo_personal(getContext(),"ERROR DATOS INCOMPLETOS");
                        limpiar_datos();
                    } else {

                        String url = URL_EMPRESA+"distribucion/regi?fac_lati="+fac_lati+"&fac_long="+fac_long+"&fac_dire="+fac_dire+"&fact_sri="+resultadoQR.getText().toString()+"&nomb_moti="+nomb_moti+"&fac_imag="+fac_imag+"&acti_usua="+acti_usua+"&nume_fact="+nume_factura.getText().toString()+"&codi_vers="+getCodiVersionCode()+"&nume_iden="+dni_ases.getText().toString()+"&codi_camp="+txt_codi_camp.getText().toString()+"&modi_dire="+txt_modi_dire.getText().toString().trim()+"&modi_refe="+txt_modi_refe.getText().toString().trim();
                        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onResponse(JSONArray response) {
                                pdp.dismiss();
                                try {
                                    JSONObject distribucion = response.getJSONObject(0);
                                    mensaje =  distribucion.getString("mensaje");

                                    if (!mensaje.equalsIgnoreCase("Registrado.") ){
                                        Toast.makeText(getContext(),""+mensaje,Toast.LENGTH_SHORT).show();
                                        estado="";

                                    } else {
                                        if (estado.equalsIgnoreCase("Devolución")){
                                            new dialogo_personal(getContext(),""+mensaje);
                                            alma_pedi_conf(nume_factura.getText().toString(), nomb_moti);
                                            Intent t = new Intent(getContext(), toma_foto.class);
                                            t.putExtra("nume_fact", nume_factura.getText().toString());
                                            t.putExtra("fact_sri",  resultadoQR.getText().toString());
                                            t.putExtra("modo", modo);
                                            startActivity(t);
                                        }
                                        else{
                                            new dialogo_personal(getContext(),""+mensaje);
                                            alma_pedi_conf(nume_factura.getText().toString(), nomb_moti);
                                        }
                                        limpiar_datos();
                                        estado="";
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pdp.dismiss();

                                Toast.makeText(getContext(),"REGI: No se puede conectar con el servidor."+error,Toast.LENGTH_SHORT).show();

                                estado="";
                            }
                        });

                        request.add(jsonArrayRequest);
                        pdp = new ProgressDialog(getContext());
                        pdp.show();
                        pdp.setContentView(R.layout.progress_dialog);
                        pdp.setCancelable(false);
                        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }

                } else if (modo == false){

                    if (fac_lati.equalsIgnoreCase("") || fac_long.equalsIgnoreCase("") || resultadoQR.getText().toString().equalsIgnoreCase("") || getDateTime().equalsIgnoreCase("") || nume_factura.getText().toString().equalsIgnoreCase("") || String.valueOf(getCodiVersionCode()).equalsIgnoreCase("")  || dni_ases.getText().toString().equalsIgnoreCase("") ||txt_codi_camp.getText().toString().equalsIgnoreCase("")){
                        new dialogo_personal(getContext(),"ERROR DATOS INCOMPLETOS");
                        limpiar_datos();
                    } else {
                        alma_fac_conf(fac_lati, fac_long, fac_dire, resultadoQR.getText().toString(), nomb_moti, acti_usua, nume_factura.getText().toString(), String.valueOf(getCodiVersionCode()), dni_ases.getText().toString(), txt_codi_camp.getText().toString(), txt_modi_dire.getText().toString().trim(), txt_modi_refe.getText().toString().trim());
                        new dialogo_personal(getContext(),"Registrado");

                        alma_pedi_conf(nume_factura.getText().toString(),nomb_moti);

                        if (estado.equalsIgnoreCase("Devolución")){
                            Intent t = new Intent(getContext(), toma_foto.class);
                            t.putExtra("nume_fact",nume_factura.getText().toString());
                            t.putExtra("fact_sri", resultadoQR.getText().toString());
                            t.putExtra("modo", modo);
                            startActivity(t);
                        }
                        estado="";
                        limpiar_datos();
                    }
                }
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
                estado="";
            }
        });
        dialogo1.show();
    }

    private void motivos_devolucion() {
        String url = URL_EMPRESA+"distribucion/moti";
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    borr_moti_rech_dist();
                    int t = response.length();
                    array_nomb_moti = new String[t];
                    for (int i=0; i< t;i++){
                        JSONObject devolucion = response.getJSONObject(i);
                        array_nomb_moti[i]=devolucion.getString("nomb_moti").trim();
                        String nomb_moti = array_nomb_moti[i];
                        alma_moti_rech_dist(nomb_moti);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    Toast.makeText(getContext(),mensaje.getString("mensaje"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),"MOTI: No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonArrayRequest);
    }


    @SuppressLint("RestrictedApi")
    private void limpiar_datos() {
        dni_ases.setText("");
        nume_factura.setText("");
        asesora.setText("");
        direccion.setText("");
        resultadoQR.setText("");
        cant_cajas.setText("");
        txt_codi_camp.setText("");
        txt_dist_geog.setText("");
        txt_modi_dire.setText("");
        txt_modi_refe.setText("");
        searchView_nume_fact.clearFocus();
        fac_lati = "";
        fac_long = "";
        fac_dire = "";
        fact_sri = "";
        nomb_moti= "";
        fab_ubic_ases.setVisibility(View.GONE);
        fab_edit_dire.setVisibility(View.GONE);
        dist = 0;
        distancia = 0;
    }


    @SuppressLint("RestrictedApi")
    private void lectura_pedido (String nume_fact) {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        if (db!=null){
            Cursor count = db.rawQuery(" SELECT nume_fact FROM cliente ", null);

            if (count.getCount()>0){

                Cursor c = db.rawQuery("SELECT nomb_terc || ' ' || apel_terc as nomb_clie, nume_fact, nume_iden, dire_terc, remi_sri, acti_fech, codi_camp, cy, cx, dist_zona FROM cliente WHERE nume_fact  = '"+nume_fact.trim()+"' ", null);

                if (c.getCount()>0){
                    if (c.moveToFirst()){

                        if (String.valueOf(c.getString(5)).equalsIgnoreCase(getDate())) {
                            asesora.setText(String.valueOf(c.getString(0)));
                            nume_factura.setText(String.valueOf(c.getInt(1)));
                            dni_ases.setText(String.valueOf(c.getString(2)));
                            direccion.setText(String.valueOf(c.getString(3)));
                            resultadoQR.setText(String.valueOf(c.getString(4)));
                            txt_codi_camp.setText(String.valueOf(c.getString(6)));
                            obtener_caja_off(String.valueOf(c.getString(4)));
                            txt_dist_geog.setText( haversineGreatCircleDistance(String.valueOf(c.getString(7)), String.valueOf(c.getString(8)), fac_lati ,fac_long ) );

                            if (!c.getString(9).equalsIgnoreCase("")){
                                distancia = Integer.parseInt(c.getString(9));
                            } else{
                                distancia = dist+1;
                            }

                            if (txt_dist_geog.getText().toString().trim().equalsIgnoreCase("") && !txt_dist_geog.getText().toString().trim().equalsIgnoreCase("SP") ){
                                fab_ubic_ases.setVisibility(View.GONE);
                                fab_edit_dire.setVisibility(View.GONE);
                            } else if (dist>distancia){
                                new dialogo_personal(getContext(),"EL PUNTO DE ENTREGA SE ENCUENTRA A "+dist+" METROS");
                                fab_ubic_ases.setVisibility(View.VISIBLE);
                                fab_edit_dire.setVisibility(View.VISIBLE);
                            } else{
                                fab_ubic_ases.setVisibility(View.VISIBLE);
                                fab_edit_dire.setVisibility(View.GONE);
                            }
                        }
                        else {
                            new cuadro_dialogo(getContext(), "Error la fecha de asignación es "+(c.getString(5))+" y la fecha del equipo es "+getDate());
                        }
                    }
                } else {
                    limpiar_datos();
                    new dialogo_personal(getContext(), "Pedido "+nume_fact+" no asignado" );
                }
            } else {

                if (isNetDisponible()) {
                    cons_clie_dato();
                    cons_caja_dato();
                } else {
                    limpiar_datos();
                    new dialogo_personal(getContext(),"El pedido no se encuentra asignado para esta fecha");
                    //new cuadro_mensaje_bd(getContext(), "El pedido no se encuentra asignado para esta fecha. !!",3);

                }
            }
        }
    }

    private void obtener_caja_off(String remi_sri) {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();

        if (db!=null){
            Cursor c = db.rawQuery("SELECT  count(*) cant FROM caja WHERE remi_sri  = '"+remi_sri+"' ", null);
            if (c.moveToNext()){
                do {
                    cant_cajas.setText(String.valueOf(c.getString(0)));
                } while (c.moveToNext());
            }
        }
    }

    private String haversineGreatCircleDistance(String cy, String cx, String fac_lati, String fac_long){
        double earthRadius = 6371000;
        String resu="";
        dist = 0;

        if (cy.equalsIgnoreCase("") || cx.equalsIgnoreCase("")) {
            resu = "";
        } else if (fac_lati.equalsIgnoreCase("") || fac_long.equalsIgnoreCase("")) {
            new dialogo_personal(getContext(),"ESPERE UN MOMENTO CAPTURANDO LAS COORDENADAS GEOGRAFICAS");
            limpiar_datos();
        } else if (!cy.equalsIgnoreCase("") && !cx.equalsIgnoreCase("") && !fac_lati.equalsIgnoreCase("") && !fac_long.equalsIgnoreCase("")) {
            double latFrom = Math.toRadians(Double.parseDouble(cy));
            double lonFrom = Math.toRadians(Double.parseDouble(cx));
            double latTo = Math.toRadians(Double.parseDouble(fac_lati));
            double lonTo = Math.toRadians(Double.parseDouble(fac_long));
            double latDelta = latTo - latFrom;
            double lonDelta = lonTo - lonFrom;
            double angle = 2 * asin(sqrt(pow(sin(latDelta / 2), 2) + cos(latFrom) * cos(latTo) * pow(sin(lonDelta / 2), 2)));
            dist = (int) round(angle * earthRadius);
            resu = String.valueOf(dist);
        }

        return resu;
    }

    private void cons_clie_dato() {
        String url = URL_EMPRESA+"distribucion/descpedi?nume_celu="+nume_celu;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int i = response.length();
                try {
                    while (i >= 1){
                        JSONObject carg_pedi = response.getJSONObject(i-1);
                        acti_fech  = carg_pedi.getString("acti_fech").trim();
                        nume_iden  = carg_pedi.getString("nume_iden").trim();
                        nomb_terc  = carg_pedi.getString("nomb_terc").trim();
                        apel_terc  = carg_pedi.getString("apel_terc").trim();
                        dire_terc  = carg_pedi.getString("dire_terc").trim();
                        nume_factc = carg_pedi.getString("nume_fact").trim();
                        remi_sri   = carg_pedi.getString("remi_sri").trim();
                        codi_camp  = carg_pedi.getString("codi_camp").trim();
                        cons_terc  = carg_pedi.getString("cons_terc").trim();
                        cy         = carg_pedi.getString("cy").trim();
                        cx         = carg_pedi.getString("cx").trim();
                        dist_zona  = carg_pedi.getString("dist_zona").trim();

                        if (acti_fech.equalsIgnoreCase(getDate())){
                            carg_clie_dato(acti_fech,nume_iden,nomb_terc,apel_terc,dire_terc,nume_factc,remi_sri, codi_camp, cons_terc, cy, cx, dist_zona);
                            carg_canj_devo(cons_terc);
                        } else {
                            cont = -1;
                            break;
                        }
                        i--;
                    }
                    pdp.dismiss();

                    if (cont==0){
                        new dialogo_personal(getContext(), "No se cuenta con pedidos para cargar");
                        //new cuadro_mensaje_bd(getContext(), "No se cuenta con pedidos para cargar !!",1);
                    } else if(cont==-1){
                        new dialogo_personal(getContext(),"Error fecha de equipo es "+getDate()+" y fecha de asignación es "+acti_fech);
                        //new cuadro_dialogo(getContext(), "*Error la fecha del equipo es "+getDate()+" y la fecha de asignación es "+acti_fech);
                    } else if (cont==1){
                        new dialogo_personal(getContext(),"Se cargó "+cont+" pedido con exito");
                        //new cuadro_mensaje_bd(getContext(), "Se cargó "+cont+" pedido con exito !!",1);
                    } else {
                        new dialogo_personal(getContext(),"Se cargaron "+cont+" pedidos con exito");
                        //new cuadro_mensaje_bd(getContext(), "Se cargaron "+cont+" pedidos con exito !!",1);
                    }
                    cont=0;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    pdp.dismiss();
                    JSONObject mensaje = response.getJSONObject(0);
                    new dialogo_personal(getContext(), mensaje.getString("mensaje"));
                    //new cuadro_mensaje_bd(getContext(), String.valueOf(mensaje.getString("mensaje")) ,3);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),"CARG: No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);

        pdp = new ProgressDialog(getContext());
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_carg);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void carg_clie_dato (String acti_fech, String nume_iden, String nomb_terc, String apel_terc, String dire_terc, String nume_factc, String remi_sri, String codi_camp, String cons_terc, String cy, String cx, String dist_zona) {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int f = 0;
        Cursor c = db.rawQuery("SELECT count(*) as CONT FROM cliente WHERE TRIM(nume_fact) = '"+nume_factc.trim()+"' ", null);

        if (c.moveToFirst()){
            f = Integer.parseInt(c.getString(c.getColumnIndex("CONT")));
        }

        if (db!=null && f==0){
            cont ++;
            db.execSQL("INSERT INTO "+ ClienteContract.ClienteEntry.TABLE_NAME +
                    "("+ ClienteContract.ClienteEntry.COLUMN_ACTI_FECH+","
                    + ClienteContract.ClienteEntry.COLUMN_NUME_IDEN+","
                    + ClienteContract.ClienteEntry.COLUMN_NOMB_TERC+","
                    + ClienteContract.ClienteEntry.COLUMN_APEL_TERC+","
                    + ClienteContract.ClienteEntry.COLUMN_DIRE_TERC+","
                    + ClienteContract.ClienteEntry.COLUMN_NUME_FACT+","
                    + ClienteContract.ClienteEntry.COLUMN_REMI_SRI+","
                    + ClienteContract.ClienteEntry.COLUMN_CODI_CAMP+","
                    + ClienteContract.ClienteEntry.COLUMN_CONS_TERC+","
                    + ClienteContract.ClienteEntry.COLUMN_CY+","
                    + ClienteContract.ClienteEntry.COLUMN_CX+","
                    + ClienteContract.ClienteEntry.COLUMN_DIST_ZONA+ ")" +
                    "VALUES ('"+acti_fech+"','"+nume_iden+"','"+nomb_terc+"','"+apel_terc+"','"+dire_terc+"','"+nume_factc+"','"+remi_sri+"','"+codi_camp+"','"+cons_terc+"','"+cy+"','"+cx+"','"+dist_zona+"')");
        }

    }

    private void carg_canj_devo(String cons_terc){

        String url = URL_EMPRESA+"distribucion/carg_canj_devo?cons_terc="+cons_terc;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject canje = response.getJSONObject(i);
                        if (!canje.getString("cons_terc").equalsIgnoreCase("NO")){
                            String cons_terc = canje.getString("cons_terc");
                            String nume_serv = canje.getString("nume_serv");
                            String codi_camp = canje.getString("codi_camp");
                            String codi_prod = canje.getString("codi_prod");
                            String nomb_prod = canje.getString("nomb_prod");
                            String acti_esta = canje.getString("acti_esta");

                            alma_canj_devo(cons_terc, nume_serv, codi_camp, codi_prod, nomb_prod, acti_esta);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),"CANJ: No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonArrayRequest);


    }

    private void alma_canj_devo(String cons_terc, String nume_serv, String codi_camp,  String codi_prod, String nomb_prod, String acti_esta){
        Toast.makeText(getContext(),"cons_terc "+cons_terc+" nume_serv "+nume_serv+" codi_Camp "+codi_camp+" codi_prod "+codi_prod+" nomb_prod "+nomb_prod+" acti_esta "+acti_esta, Toast.LENGTH_SHORT).show();
    }

    private void elim_clie_dato () {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db!=null){

            db.execSQL("DELETE FROM cliente WHERE acti_fech <> '"+getDate()+"' ");
            db.execSQL("DELETE FROM caja WHERE remi_sri in " +
                    "(SELECT caja.remi_sri FROM caja LEFT JOIN cliente ON caja.remi_sri = cliente.remi_sri WHERE cliente.remi_sri is null)");
        }
    }


    private void elim_pedi_conf () {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db!=null){
            db.execSQL("DELETE FROM pedi_conf WHERE date (acti_hora) <> '"+getDate()+"' ");
        }
    }

    private void cons_caja_dato() {
        String url = URL_EMPRESA+"distribucion/desccaja?nume_celu="+nume_celu;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response", String.valueOf(response.length()));
                int i = response.length();
                try {
                    while (i >= 1){
                        JSONObject carg_caja = response.getJSONObject(i-1);
                        cant_caja = carg_caja.getString("cant_caja").trim();
                        codi_caja = carg_caja.getString("codi_caja").trim();
                        docu_sri = carg_caja.getString("remi_sri").trim();
                        desc_caja = carg_caja.getString("desc_caja").trim();
                        carg_caja_dato(codi_caja,docu_sri,desc_caja, cant_caja);
                        i--;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdp.dismiss();
                Toast.makeText(getContext(),"CARG: No se puede conectar con el  servidor."+error,Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonArrayRequest);
    }


    private void carg_caja_dato (String codi_caja, String docu_sri, String desc_caja, String cant_caja) {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db3 = dbHelper.getWritableDatabase();
        int f = 0;
        Cursor c = db3.rawQuery("SELECT count(*) as CONT FROM caja WHERE TRIM(remi_sri) = '"+docu_sri.trim()+"' ", null);

        if (c.moveToFirst()){
            f = Integer.parseInt(c.getString(c.getColumnIndex("CONT")))  ;
        }

        if (db3!=null && f < Integer.parseInt(cant_caja)){
            db3.execSQL("INSERT INTO "+ CajaContract.CajaEntry.TABLE_NAME +
                    "("+ CajaContract.CajaEntry.COLUMN_CODI_CAJA+","
                    + CajaContract.CajaEntry.COLUMN_REMI_SRI+","
                    + CajaContract.CajaEntry.COLUMN_DESC_CAJA+ ")" +
                    "VALUES ('"+codi_caja+"','"+docu_sri+"','"+desc_caja+"')");
        }
    }


    private void alma_fac_conf(String fac_lati, String fac_long, String fac_dire, String fact_sri, String nomb_moti, String acti_usua, String nume_fact, String codi_vers, String nume_iden, String codi_camp, String modi_dire, String modi_refe) {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db4 = dbHelper.getWritableDatabase();

        Cursor c = db4.rawQuery("SELECT cons_fac_conf FROM fac_conf WHERE fact_sri = '"+fact_sri+"'", null);

        if (c.getCount()>0 && db4!=null){
            if (c.moveToNext()){
                cons_fac_conf  = c.getInt(0) ;
            }
            db4.execSQL("DELETE FROM "+ Fac_confContract.Fac_confEntry.TABLE_NAME+" WHERE cons_fac_conf = '"+cons_fac_conf+"' ");
        }

        if (db4!=null){
            db4.execSQL("INSERT INTO "+ Fac_confContract.Fac_confEntry.TABLE_NAME +
                    "("+ Fac_confContract.Fac_confEntry.COLUMN_FACT_SRI+","
                    + Fac_confContract.Fac_confEntry.COLUMN_FAC_LATI+","
                    + Fac_confContract.Fac_confEntry.COLUMN_FAC_LONG+","
                    + Fac_confContract.Fac_confEntry.COLUMN_FAC_DIRE+","
                    + Fac_confContract.Fac_confEntry.COLUMN_ACTI_USUA+","
                    + Fac_confContract.Fac_confEntry.COLUMN_ACTI_HORA+","
                    + Fac_confContract.Fac_confEntry.COLUMN_NUME_FACT+","
                    + Fac_confContract.Fac_confEntry.COLUMN_NOMB_MOTI+","
                    + Fac_confContract.Fac_confEntry.COLUMN_CODI_VERS+","
                    + Fac_confContract.Fac_confEntry.COLUMN_NUME_IDEN+","
                    + Fac_confContract.Fac_confEntry.COLUMN_CODI_CAMP+","
                    + Fac_confContract.Fac_confEntry.COLUMN_MODI_DIRE+","
                    + Fac_confContract.Fac_confEntry.COLUMN_MODI_REFE+ ")" +
                    "VALUES ('"+fact_sri+"','"+fac_lati+"','"+fac_long+"','"+fac_dire+"','"+acti_usua+"','"+getDateTime()+"','"+nume_fact+"','"+nomb_moti+"','"+codi_vers+"','"+nume_iden+"','"+codi_camp+"','"+modi_dire+"','"+modi_refe+"' )");
        }
    }


    private void alma_pedi_conf(String nume_fact, String nomb_moti) {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String pedido="";
        String acti_hora = getDateTime();

        Cursor c = db.rawQuery("SELECT nume_fact FROM pedi_conf WHERE nume_fact = '"+nume_fact+"'", null);

        if (c.getCount()>0 && db!=null){
            if (c.moveToNext()){
                pedido  = c.getString(0) ;
            }
            db.execSQL("DELETE FROM pedi_conf WHERE nume_fact = '"+pedido+"' ");
        }

        if (db!=null){
            db.execSQL("INSERT INTO pedi_conf (nume_fact, nomb_moti, acti_hora) VALUES ('"+nume_fact+"','"+nomb_moti+"','"+acti_hora+"')");
        }

    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



    private void sinc_fac_conf() {

        cant_sinc=0;

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        final SQLiteDatabase db5 = dbHelper.getWritableDatabase();
        Cursor c = db5.rawQuery("SELECT fact_sri, fac_lati, fac_long, fac_dire, acti_usua, acti_hora, nomb_moti, nume_fact, codi_vers, nume_iden, codi_camp, modi_dire, modi_refe FROM fac_conf ", null);
        //int i=0;
        if(c.getCount()>0 && db5!=null){
            if (c.moveToFirst()){
                do {
                    sinc_fact_sri  = String.valueOf(c.getString(0));
                    sinc_fac_lati  = String.valueOf(c.getString(1));
                    sinc_fac_long  = String.valueOf(c.getString(2));
                    sinc_fac_dire  = String.valueOf(c.getString(3));
                    sinc_acti_usua = String.valueOf(c.getString(4));
                    sinc_acti_hora = String.valueOf(c.getString(5));
                    sinc_nomb_moti = String.valueOf(c.getString(6));
                    sinc_nume_fact = String.valueOf(c.getString(7));
                    sinc_codi_vers = String.valueOf(c.getString(8));
                    sinc_nume_iden = String.valueOf(c.getString(9));
                    sinc_codi_camp = String.valueOf(c.getString(10));
                    sinc_modi_dire = String.valueOf(c.getString(11));
                    sinc_modi_refe = String.valueOf(c.getString(12));

                    String acti_hora_part = sinc_acti_hora.substring(0, 10).trim();
                    String[] split = acti_hora_part.split("-");
                    acti_hora_veri = split[1] + "/" + split[2] + "/" + split[0];

                    Log.i("acti_hora_veri", "-->" + sinc_fact_sri+"--"+acti_hora_veri);

                    String url = URL_EMPRESA + "distribucion/regi_sinc?fact_sri=" + sinc_fact_sri + "&fac_lati=" + sinc_fac_lati + "&fac_long=" + sinc_fac_long + "&fac_dire=" + sinc_fac_dire + "&acti_usua=" + sinc_acti_usua + "&acti_hora=" + sinc_acti_hora + "&nomb_moti=" + sinc_nomb_moti + "&acti_hora_veri=" + acti_hora_veri.trim()+ "&nume_fact=" + sinc_nume_fact+"&codi_vers="+sinc_codi_vers+"&nume_iden="+sinc_nume_iden+"&codi_camp="+sinc_codi_camp+"&modi_dire="+sinc_modi_dire+"&modi_refe="+sinc_modi_refe;
                    jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                JSONObject mensaje = response.getJSONObject(0);
                                String resp = mensaje.getString("mensaje").trim();
                                //El numero de guia tiene longitud maximo de 13 digitos(la respuesta puede ser la guia o mensaje)
                                if (resp.length()>13){
                                    Toast.makeText(getContext(), resp.toUpperCase(), Toast.LENGTH_SHORT).show();
                                } else {
                                    //Snackbar.make(vista, "Sincronizando "+cant_sinc, Snackbar.LENGTH_LONG).show();
                                    sinc_fac_conf_imag_indi(resp, acti_hora_veri);
                                    borr_fac_conf_indi(resp);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "SINC: No se puede conectar con el servidor.", Toast.LENGTH_SHORT).show();
                            pdp.dismiss();
                        }
                    });

                    jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                            MY_DEFAULT_TIMEOUT,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    request.add(jsonArrayRequest);
                    //i++;
                } while (c.moveToNext());

                pdp = new ProgressDialog(getContext());
                pdp.show();
                pdp.setContentView(R.layout.progress_dialog_bd);
                pdp.setCancelable(false);
                pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            //most_cant_sinc(i);
        } else{
            new dialogo_personal(getContext(), "No existe pedidos para sincronizar");
        }
    }

    private void sinc_fac_conf_imag_indi(String fact_sri, String acti_hora_veri) {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db5 = dbHelper.getWritableDatabase();
        Cursor c = db5.rawQuery("SELECT fac_conf.fac_imag, fac_conf.fact_sri, cliente.nume_fact " +
                "FROM fac_conf, cliente WHERE " +
                "fac_conf.fact_sri =  cliente.remi_sri and fac_conf.fact_sri =  '"+fact_sri+"' and fac_conf.fac_imag is not null", null);

        if (c.getCount() > 0 && db5 != null) {
            if (c.moveToFirst()) {
                do {
                    path_sinc_imag = String.valueOf(c.getString(0));
                    fact_sri_sinc_imag = String.valueOf(c.getString(1));
                    nume_fact_sinc_imag = c.getString(2) + ".jpg";
                    Log.i("RUTA", ""+path_sinc_imag);
                    bitmap = BitmapFactory.decodeFile(path_sinc_imag);
                    bitmap = redimensionar_imagen(bitmap, 500, 500);
                    carg_fac_conf_imag(fact_sri_sinc_imag, nume_fact_sinc_imag, bitmap, acti_hora_veri);

                } while (c.moveToNext());
            }
        } else {

            pdp.dismiss();
        }

    }


    private  void carg_fac_conf_imag(final String fact_sri_sinc_imag, final String nume_fact_sinc_imag, final Bitmap bitmap, final String acti_hora_veri) {

        String url = URL_EMPRESA+"distribucion/imag?";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        pdp.dismiss();
                        //Snackbar.make(vista,"",Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getContext(),response.trim(),Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdp.dismiss();
                        Toast.makeText(getContext(),"No se puede conectar con el servidor."+error,Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                String imag_captura = ConvertirStringImag (bitmap);
                Map<String, String>  params = new HashMap<>();
                params.put("fact_sri", fact_sri_sinc_imag);
                params.put("fac_imag", nume_fact_sinc_imag);
                params.put("acti_hora_veri", acti_hora_veri);
                params.put("imagen", imag_captura);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(postRequest);
    }

    private Bitmap redimensionar_imagen(Bitmap bitmap, float  anchoNuevo, float altoNuevo) {

        int ancho = bitmap.getWidth();
        int alto = bitmap.getHeight();

        if (ancho>anchoNuevo || alto>altoNuevo){
            float escalaancho = anchoNuevo / ancho;
            float escalalto = altoNuevo / alto;

            Matrix matrix = new Matrix();
            matrix.postScale(escalaancho, escalalto);

            return Bitmap.createBitmap(bitmap, 0, 0 , ancho, alto, matrix, false);
        }
        else{
            return bitmap;
        }
    }

    private String ConvertirStringImag(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress (Bitmap.CompressFormat.JPEG,100,array);
        byte [] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString (imagenByte, Base64.DEFAULT);
        return imagenString;
    }


    private void borr_fac_conf_indi(String fact_sri) {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db6 =  dbHelper.getReadableDatabase();
        if (db6!=null){
            db6.execSQL("DELETE FROM fac_conf WHERE fact_sri = '"+fact_sri+"'");
        }

        cant_sinc++;

        Toast toast = new Toast(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup) vista.findViewById(R.id.lytLayout));
        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        if (cant_sinc==1){
            txtMsg.setText(cant_sinc+" pedido sincronizado");
        } else{
            txtMsg.setText(cant_sinc+" pedidos sincronizados");
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }


    private void alma_moti_rech_dist(String nomb_moti) {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db7 = dbHelper.getWritableDatabase();

        Cursor c = db7.rawQuery("SELECT nomb_moti FROM moti_rech_dist WHERE nomb_moti = '"+nomb_moti+"'", null);

        if(db7!=null && c.getCount()==0 ) {
            db7.execSQL("INSERT INTO "+ Moti_rech_distContract.Moti_rech_distEntry.TABLE_NAME +
                    "(" + Moti_rech_distContract.Moti_rech_distEntry.COLUMN_NOMB_MOTI+ ")" +
                    "VALUES ('"+nomb_moti+"')");
        }
    }

    private void borr_moti_rech_dist() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db8 =  dbHelper.getReadableDatabase();
        if (db8!=null){
            db8.execSQL("DELETE FROM "+ Moti_rech_distContract.Moti_rech_distEntry.TABLE_NAME);
        }
    }

    private void carg_moti_rech_moti() {

        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db9 =  dbHelper.getReadableDatabase();
        if (db9!=null){
            Cursor c = db9.rawQuery("SELECT nomb_moti FROM "+ Moti_rech_distContract.Moti_rech_distEntry.TABLE_NAME, null);
            int t = c.getCount();
            array_nomb_moti = new String[t];

            if (c.moveToNext()){
                int i = 0;
                do {
                    String moti_rech   = c.getString(0) ;
                    array_nomb_moti[i] = moti_rech;
                    i++;
                } while (c.moveToNext());
            }
        }
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String codi_usua = preferences.getString("codi_usua","");
        String nume_celu = preferences.getString("nume_celu", "");
        String esta_celu = preferences.getString("esta_celu", "");

        acti_usua = codi_usua;
        this.nume_celu = nume_celu;
        this.esta_celu = esta_celu;
    }


    private void sinc_pedi_falt() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        if (db!=null){
            Cursor c = db.rawQuery("SELECT date (acti_hora) from fac_conf where date (acti_hora) <>  '"+getDate()+"' ", null);
            int t = c.getCount();

            if (t>0){
                if (isNetDisponible()) {
                    sinc_fac_conf();
                } else {
                    Toast.makeText(getContext(), "Tiene "+t+" pedido(s) por sincronizar.",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void elim_fac_conf() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        if (db!=null){
            db.execSQL("DELETE FROM fac_conf  WHERE Cast ((JulianDay('"+getDate()+"') - JulianDay(DATE(acti_hora)) ) As Integer)> 1 ");
        }
    }

    private void eliminarDatos() {
        MyDbHelper dbHelper = new MyDbHelper(getContext());
        SQLiteDatabase db =  dbHelper.getReadableDatabase();
        if (db!=null){
            db.execSQL("DELETE FROM fac_conf");
            db.execSQL("DELETE FROM cliente");
            db.execSQL("DELETE FROM caja");
            db.execSQL("DELETE FROM pedi_conf");
        }
    }


    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }


    private void validar_identidad(final String nume_iden) {

        String url = URL_EMPRESA+"asesora/ubic?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    JSONObject geo_clie = response.getJSONObject(0);
                    String mensaje  = geo_clie.getString("mensaje").trim();

                    if (mensaje.equalsIgnoreCase("")){

                        String lat = geo_clie.getString("cy").trim();
                        String lon = geo_clie.getString("cx").trim();

                        if (lat.equalsIgnoreCase("") || lon.equalsIgnoreCase("") || lon.equalsIgnoreCase("null") || lat.equalsIgnoreCase("null")) {
                            pdp.dismiss();
                            new dialogo_personal(getContext(),"La asesora no tiene coordenadas en el Mapcity");

                        } else {
                            pdp.dismiss();

                            Intent j  = new Intent(getContext(), ubic_ases.class);
                            j.putExtra("nume_iden",nume_iden);
                            startActivity(j);
                            searchView_nume_fact.clearFocus();
                        }
                    } else{
                        pdp.dismiss();
                        new dialogo_personal(getContext(),"Error la identificación no existe");
                        //Toast.makeText(getContext(), "*Error la identificación no existe.",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(),"No se puede conectar con el servidor."+error,Toast.LENGTH_SHORT).show();
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

    private int getCodiVersionCode(){
        Log.i("VERSION CODE", String.valueOf(BuildConfig.VERSION_CODE));
        return BuildConfig.VERSION_CODE;
    }


    private void verificar_celu(final String v_nume_celu) {

        if (v_nume_celu.equalsIgnoreCase("")){
            new dialogo_celular(getContext(), Fragmento_proc_dist_conf_manu.this);
        } else {
            String url = URL_EMPRESA+"usuario/celu?nume_celu="+v_nume_celu;

            jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject veri_celu = response.getJSONObject(0);
                        String mensaje = veri_celu.getString("mensaje").trim();
                        txt_nume_celu.setVisibility(View.GONE);
                        if (mensaje.equalsIgnoreCase("NO REGISTRADO")){
                            txt_nume_celu.setText("");
                            new dialogo_acceso(getContext(),"CELULAR SIN ACCESO");
                            eliminarDatos();
                            nume_celu="";
                            esta_celu="";
                        } else if (mensaje.equalsIgnoreCase("ACT")){
                            txt_nume_celu.setVisibility(View.VISIBLE);
                            txt_nume_celu.setText(v_nume_celu);
                            esta_celu="ACT";
                            Snackbar.make(vista, "BIENVENIDO A AZZORTITRACK", Snackbar.LENGTH_SHORT).show();
                        }
                        guardarpreferencia();
                        pdp.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pdp.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    txt_nume_celu.setVisibility(View.GONE);
                    if (esta_celu.equalsIgnoreCase("")){
                        nume_celu="";
                        esta_celu="";
                        guardarpreferencia();
                        new dialogo_acceso(getContext(),"CELULAR SIN ACCESO");
                        eliminarDatos();
                    } else{
                        txt_nume_celu.setVisibility(View.VISIBLE);
                        txt_nume_celu.setText(nume_celu);
                        Snackbar.make(vista, "BIENVENIDO A AZZORTITRACK", Snackbar.LENGTH_SHORT).show();
                    }
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
    }


    private void consultarPermiso(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            }
        }
    }

    private void guardarpreferencia() {
        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String nume_celu    = this.nume_celu;
        String esta_celu    = this.esta_celu;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nume_celu",nume_celu);
        editor.putString("esta_celu",esta_celu);
        editor.commit();
    }


}
