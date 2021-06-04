package com.dupreincaperu.dupree.mh_fragments_cobranza;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
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
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

//clases necesarias para inicializar el mapa
//clases necesarias para agregar el componente de ubicación
//clases necesarias para agregar un marcador
//clases para calcular una ruta
//clases necesarias para iniciar la interfaz de usuario de navegación


public class ruta extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {


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


    private ImageView img_nave_ruta, img_ubic_ases, img_guar_ubic;
    String nume_iden, acti_usua, cons_terc, band;
    Double cy, cx;

    AlertDialog.Builder builder;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    Context contexto;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_ruta);

        contexto = this;

        request = Volley.newRequestQueue(this);

        Bundle para_ases = this.getIntent().getExtras();
        if(para_ases !=null){
            nume_iden = para_ases.getString("nume_iden");
            cons_terc = para_ases.getString("cons_terc") ;
            cy        = Double.parseDouble(para_ases.getString("cy")) ;
            cx        = Double.parseDouble(para_ases.getString("cx")) ;
            band      = para_ases.getString("band");
        }


        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        img_guar_ubic = findViewById(R.id.img_guar_ubic);
        img_ubic_ases = findViewById(R.id.img_ubic_ases);
        img_nave_ruta = findViewById(R.id.img_nave_ruta);

        img_nave_ruta.setVisibility(View.GONE);
        img_guar_ubic.setVisibility(View.VISIBLE);

        if (band.equalsIgnoreCase("0")){
            img_ubic_ases.setVisibility(View.GONE);
        } else {
            img_ubic_ases.setVisibility(View.VISIBLE);
        }

        img_ubic_ases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta_pedido(cy, cx);
            }
        });

        img_guar_ubic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar_ubicacion();
            }
        });

        cargarpreferencias();
    }



    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                //Estilo del marcador(simbolo) cuando se dibuje la ruta
                addDestinationIconSymbolLayer(style);

                //Punto agregado para que responda con un clic en el mapa y obtener la ruta
                mapboxMap.addOnMapClickListener(ruta.this);


                img_nave_ruta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean simulateRoute = false;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                            // Llamar a este método con contexto desde una actividad
                        NavigationLauncher.startNavigation(ruta.this, options);
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


    //Obtener el destino (latitud y longitud) del destino con un clic en el mapa
    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        //Obtiene las coordenadas de la latitud y longitud del destino cuando se hace clic
        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        //Obtiene las coordenadas de la ubicacion actual del dispostivo o usuario
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        //Llama al envento getrout para dibujar la ruta
        getRoute(originPoint, destinationPoint);

        //img_nave_ruta.setEnabled(true);
        //Cambia de color el button cuando se activa
        //img_nave_ruta.setBackgroundResource(R.color.mapboxButton);
        return true;
    }



    //Enviar el destino del pedido para iniciar la ruta con el origen
    @SuppressWarnings( {"MissingPermission"})

    public void ruta_pedido(Double cy, Double cx) {
        //Obtiene las coordenadas de la latitud y longitud del destino cuando se hace clic en el boton
        Point destinationPoint = Point.fromLngLat(cx, cy);

        //Obtiene las coordenadas de la ubicacion actual del dispostivo o usuario
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        /*Obtiene las ccordenas del punto de origen
        Double latitud  = locationComponent.getLastKnownLocation().getLatitude();
        Double longitud = locationComponent.getLastKnownLocation().getLongitude();

        Toast.makeText(getBaseContext(),"Latitud : "+latitud+" Longitud: "+longitud, Toast.LENGTH_SHORT).show();*/

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        //Llama al envento getrout para dibujar la ruta
        getRoute(originPoint, destinationPoint);

        img_ubic_ases.setVisibility(View.GONE);
        img_nave_ruta.setVisibility(View.VISIBLE);
        //img_nave_ruta.setEnabled(true);
        //Cambia de color el button cuando se activa
        //img_nave_ruta.setBackgroundResource(R.color.mapboxButton);
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

    private void guardar_ubicacion() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(ruta.this);
        dialogo1.setTitle("Atención");
        dialogo1.setMessage("¿ Esta seguro que desea guardar la ubicación de la asesora ?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Aceptar",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                //Obtiene las ccordenas del punto de origen
                Double cy  = locationComponent.getLastKnownLocation().getLatitude();
                Double cx =  locationComponent.getLastKnownLocation().getLongitude();

                String url = getString(R.string.url_empr)+"georefere/regi?nume_iden="+nume_iden+"&cons_terc="+cons_terc+"&cy="+cy+"&cx="+cx+"&acti_usua="+acti_usua;
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onResponse(JSONArray response) {

                        pdp.dismiss();
                        try {
                            JSONObject geo = response.getJSONObject(0);
                            String mensaje =  geo.getString("mensaje");
                            Toast.makeText(getBaseContext(),mensaje, Toast.LENGTH_SHORT).show();
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

                pdp = new ProgressDialog(ruta.this);
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


    //certificado para el modo produccion
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


    //Certificado para el ambiente de calidad (pruebas)
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
