package com.dupreincaperu.dupree.mh_fragments_azzortimaps;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoMensaje;
import com.dupreincaperu.dupree.mh_dial_peru.motivo_visita;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class visita extends AppCompatActivity implements motivo_visita.FinalizoMotivoDialogo, OnMapReadyCallback, PermissionsListener {
    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;

    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    private ImageView img_nave_ruta, img_ubic_ases, img_guar_ubic, img_comp_data;
    String nume_iden,codi_sect, codi_zona, nomb_terc,apel_terc, camp_ingr, sald_docu, tipo_clie, nomb_barr, dire_terc, fech_naci, tele_ter1, tele_ter2, codi_camp_1, esta_visi;
    Double cy, cx;
    String acti_usua, styleZona="";


    AlertDialog.Builder builder;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    Context contexto;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_visita);

        contexto = this;

        request = Volley.newRequestQueue(this);

        Bundle para_ases = this.getIntent().getExtras();
        if(para_ases !=null){
            nume_iden   = para_ases.getString("nume_iden");
            codi_sect   = para_ases.getString("codi_sect");
            codi_zona   = para_ases.getString("codi_zona");
            nomb_terc   = para_ases.getString("nomb_terc");
            apel_terc   = para_ases.getString("apel_terc");
            camp_ingr   = para_ases.getString("camp_ingr");
            cy          = Double.parseDouble(para_ases.getString("cy")) ;
            cx          = Double.parseDouble(para_ases.getString("cx")) ;
            sald_docu   = para_ases.getString("sald_docu");
            tipo_clie   = para_ases.getString("tipo_clie");
            nomb_barr   = para_ases.getString("nomb_barr");
            dire_terc   = para_ases.getString("dire_terc");
            fech_naci   = para_ases.getString("fech_naci");
            tele_ter1   = para_ases.getString("tele_ter1");
            tele_ter2   = para_ases.getString("tele_ter2");
            codi_camp_1 = para_ases.getString("codi_camp_1");
            esta_visi   = para_ases.getString("esta_visi");
            styleZona   = para_ases.getString("styleZona");
        }

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        img_guar_ubic = findViewById(R.id.img_guar_ubic);
        img_ubic_ases = findViewById(R.id.img_ubic_ases);
        img_nave_ruta = findViewById(R.id.img_nave_ruta);
        img_comp_data = findViewById(R.id.img_comp_data);

        img_ubic_ases.setVisibility(View.VISIBLE);
        img_nave_ruta.setVisibility(View.GONE);

        img_ubic_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta_pedido(cy, cx);
            }
        });

        img_guar_ubic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new motivo_visita(contexto, visita.this);
            }
        });

        cargarpreferencias();
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri(styleZona), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //pruebas
                enableLocationComponent(style);

                //Estilo del marcador(simbolo) cuando se dibuje la ruta
                addDestinationIconSymbolLayer(style);

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(cy, cx))
                        .title(nomb_terc+" "+apel_terc)
                        .snippet("SECTOR  : "+codi_sect+  "\n"+
                                "DNI     : "+nume_iden+  "\n"+
                                "DISTRITO   : "+nomb_barr+ "\n"+
                                "DIRECCION  : "+dire_terc+ "\n"+
                                "TELEF : "+tele_ter1+" - "+tele_ter2+"\n"+
                                "STATUS     : "+tipo_clie+"\n"+
                                "SALDO      : "+sald_docu+  "\n"+
                                "CAMP INGR. : "+camp_ingr+  "\n"+
                                "ULT CAMP1 : "+codi_camp_1+ "\n"+
                                "CUMPLEAÑOS : "+fech_naci+ "\n"+
                                "ESTADO : "+esta_visi )
                );

                img_nave_ruta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean simulateRoute = false;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                        NavigationLauncher.startNavigation(visita.this, options);
                    }
                });

                img_comp_data.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, ""+
                                Html.fromHtml("ASESORA DUPREE AZZORTI <br/>"+"------------------------------------------------------------<br/>"+
                                "DNI : "+nume_iden+"<br/>"+
                                "ASESOR(A) : "+nomb_terc+" "+apel_terc+"<br/>"+
                                "DISTRITO : "+nomb_barr+"<br/>"+
                                "DIRECCIÓN : "+dire_terc+"<br/>"+
                                "TELEF. : "+tele_ter1+" - "+tele_ter2+"<br/>"+
                                "STATUS : "+tipo_clie+"<br/>"+
                                "CAMP. INGR : "+camp_ingr+"<br/>"+
                                "ULTI CAMP : "+codi_camp_1+"<br/>"+
                                "CUMPLEAÑOS : "+fech_naci+"<br/>"+
                                "ESTADO : "+esta_visi+"<br/>"+
                                "UBICACIÓN : https://maps.google.com/?q="+cy+","+cx));
                        startActivity(Intent.createChooser(intent, "Compartir"));
                    }
                });
            }
        });
    }


    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    //Enviar el destino del pedido para iniciar la ruta con el origen
    @SuppressWarnings( {"MissingPermission"})

    public void ruta_pedido(Double cy, Double cx) {
        //Obtiene las coordenadas de la latitud y longitud del destino cuando se hace clic en el boton
        Point destinationPoint = Point.fromLngLat(cx, cy);

        //Obtiene las coordenadas de la ubicacion actual del dispostivo o usuario
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        //Llama al envento getrout para dibujar la ruta
        getRoute(originPoint, destinationPoint);

        img_ubic_ases.setVisibility(View.GONE);
        img_nave_ruta.setVisibility(View.VISIBLE);
    }


    //Obtener la ruta desde el punto de origen al punto destino
    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                    //Puede obtener la información genérica de HTTP sobre la respuesta
                        Log.d(TAG, "Respuesta del codigo: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No se encontraron rutas, asegúrese de configurar el token de usuario y acceso correcto.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No se encontraron rutas");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Dibuja la ruta en el mapa
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }

                });
    }

    //Ubicacion del dispostivo
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
            // Comprobar los permisos caso contrario se solicitara
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Se activa para que muestre la ubicascion del movil
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            // Establecer el modo de la camara
            locationComponent.setCameraMode(CameraMode.TRACKING);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.permiso_ubicacion, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.permiso, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void guardar_ubicacion(String esta_visi, String dire_terc, String dire_refe) {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(visita.this);
        dialogo1.setTitle("Atención");
        dialogo1.setMessage("¿ Esta seguro que desea guardar la visita ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //Obtiene las ccordenas del punto de origen
                Double cy  = locationComponent.getLastKnownLocation().getLatitude();
                Double cx =  locationComponent.getLastKnownLocation().getLongitude();
                String desc_orig = "GZ";

                String url = getString(R.string.url_empr)+"georefere/visi?nume_iden="+nume_iden+"&codi_zona="+codi_zona+"&cx="+cx+"&cy="+cy+"&dire_terc="+dire_terc+"&dire_refe="+dire_refe+"&desc_orig="+desc_orig+"&acti_usua="+acti_usua+"&esta_visi="+esta_visi;
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onResponse(JSONArray response) {
                        pdp.dismiss();
                        try {
                            JSONObject visi = response.getJSONObject(0);
                            String mensaje =  visi.getString("mensaje");
                            new dialogoMensaje(contexto,mensaje);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdp.dismiss();
                        Toast.makeText(getBaseContext(),"REGI: No se puede conectar con el servidor."+error, Toast.LENGTH_SHORT).show();
                    }
                });

                pdp = new ProgressDialog(visita.this);
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
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();
    }

    @Override
    public void ResultadoMotivoDialogo(String esta_visi, String dire_terc, String dire_refe){
        dire_terc = dire_terc.replace("'", " ").replace("&","y").replace("("," ").replace("#"," ").replace("%", " ").
                replace(">", " ").replace("<", " ").replace("!"," ").replace("$"," ").replace(";"," ").replace("*"," ").
                replace("?"," ").replace("^"," ").replace("{"," ").replace("}"," ").replace("+"," ").replace("-"," ").
                replace(":"," ").replace("`"," ").replace("."," ").replace("|"," ").replace(",", " ").replace("@", " ").
                replace("^"," ").replace("_"," ").replace("?"," ").replace("["," ").replace("]"," ").replace("Ú","U").replace("ú","u").
                replace("Ó","O").replace("ó","o").replace("Í","i").replace("í","i").replace("É","E").replace("é","e").replace("Á","A").replace("á","a").
                replaceAll("[^a-zA-Z0-9@.#$%^&*_&?$()]"," ").trim();

        dire_refe = dire_refe.replace("'", " ").replace("&","y").replace("("," ").replace("#"," ").replace("%", " ").
                replace(">", " ").replace("<", " ").replace("!"," ").replace("$"," ").replace(";"," ").replace("*"," ").
                replace("?"," ").replace("^"," ").replace("{"," ").replace("}"," ").replace("+"," ").replace("-"," ").
                replace(":"," ").replace("`"," ").replace("."," ").replace("|"," ").replace(",", " ").replace("@", " ").
                replace("^"," ").replace("_"," ").replace("?"," ").replace("["," ").replace("]"," ").replace("Ú","U").replace("ú","u").
                replace("Ó","O").replace("ó","o").replace("Í","i").replace("í","i").replace("É","E").replace("é","e").replace("Á","A").replace("á","a").
                replaceAll("[^a-zA-Z0-9@.#$%^&*_&?$()]"," ").trim();

        guardar_ubicacion(esta_visi, dire_terc, dire_refe);
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String codi_usua = preferences.getString("codi_usua","");
        acti_usua = codi_usua;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
