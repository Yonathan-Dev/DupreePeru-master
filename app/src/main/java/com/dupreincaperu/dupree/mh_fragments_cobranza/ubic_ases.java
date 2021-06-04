package com.dupreincaperu.dupree.mh_fragments_cobranza;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class ubic_ases extends AppCompatActivity implements PermissionsListener {

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    Context contexto;


    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private MapboxMap mapboxMap;
    private MapView mapView;
    Button btn_grab_ubic;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

    String nume_iden = "";
    String mensaje= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_ubic_ases);

        contexto = this;

        request = Volley.newRequestQueue(getBaseContext());

        Bundle nume_iden_rete = this.getIntent().getExtras();
        if(nume_iden_rete !=null){
            nume_iden = nume_iden_rete.getString("nume_iden");
        }

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        ubicacion_asesora(nume_iden);

    }


    private void ubicacion_asesora(String nume_iden) {

        String url = getString(R.string.url_empr)+"asesora/ubic?nume_iden="+nume_iden;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                try {
                    JSONObject geo_clie = response.getJSONObject(0);
                    mensaje  = geo_clie.getString("mensaje").trim();

                    String lat = geo_clie.getString("cy").trim() ;
                    String lon = geo_clie.getString("cx").trim() ;
                    String codi_zona = geo_clie.getString("codi_zona").trim();
                    //String nomb_empl = geo_clie.getString("nomb_terc").trim()+" "+geo_clie.getString("apel_terc");

                    Double latitud = Double.parseDouble(lat);
                    Double longitud = Double.parseDouble(lon);

                    mapa_asesora(latitud, longitud, codi_zona);

                    //mapa_asesora(latitud, longitud, codi_zona, nomb_empl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(),"No se puede conectar con el servidor."+error,Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }


    private void mapa_asesora(final Double latitud, final Double longitud, final  String codi_zona) {

        //Camara del mapa en referencia a un punto y el zoom
        MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null).
                camera(new CameraPosition.Builder().target(new LatLng(latitud,longitud)).
                        zoom(14).build());

        mapView = new MapView(this, options);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                //Punto agregado para la ubicacion del dispositivo.
                ubic_ases.this.mapboxMap = mapboxMap;

                //Punto en el Mapa
                List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
                symbolLayerIconFeatureList.add(Feature.fromGeometry(Point.fromLngLat(longitud, latitud)));


                //Agregar ubicaciones con titulo y descripcion
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitud, longitud))
                        .title("Asesora")
                        .snippet("DNI  : "+nume_iden +"\n"+
                                "ZONA : "+codi_zona )
                );



                /* Agregar puntos en el mapa
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(-11.915459, -77.049858))
                        .title("Asesora")
                        .snippet("DNI: 08426378"));

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(-12.108891, -77.003781))
                        .title("Asesora")
                        .snippet("DNI: 70052301")); */

                //Estilo de mapa
                //mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz")
                        /*.withImage(ICON_ID, BitmapFactory.decodeResource(
                                ubic_ases.this.getResources(), R.drawable.ubic_ases))*/
                        .withSource(new GeoJsonSource(SOURCE_ID,
                                FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                        .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                                .withProperties(PropertyFactory.iconImage(ICON_ID),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[] {0f, -9f}))
                        ), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        //Metodo para agregar un punto de ubicacion del disposito en el mapa
                        enableLocationComponent(style);

                    }
                });

            }
        });

        setContentView(mapView);

    }




    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            //Agrega un enfogue de color azul
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this)
                    .elevation(5)
                    .accuracyAlpha(.10f)
                    .accuracyColor(Color.BLUE)
                    .build();

            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions) //Activar el enfonque
                            .build());

            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);




        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "NEed", Toast.LENGTH_LONG).show();
    }

    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, "Aqui", Toast.LENGTH_LONG).show();
            finish();
        }
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
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
