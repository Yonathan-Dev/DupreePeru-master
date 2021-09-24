package com.dupreincaperu.dupree.mh_fragments_azzortimaps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.NonNull;

import com.dupreincaperu.dupree.mh_dial_peru.dialogoMensaje;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dupreincaperu.dupree.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
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
import com.mapbox.mapboxsdk.snapshotter.MapSnapshot;
import com.mapbox.mapboxsdk.snapshotter.MapSnapshotter;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.dupreincaperu.dupree.Constants.MY_DEFAULT_TIMEOUT;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class list_ases extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener {

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;

    Context contexto;
    String[][] list_ases;

    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private MapSnapshotter mapSnapshotter;
    private boolean hasStartedSnapshotGeneration;

    private ProgressDialog pdp = null;

    /*VARIABLES PARA CALCULAR LA RUTA*/
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;

    String codi_zona="", codi_camp="", codi_sect="", codi_usua="", styleZona="";
    String tipo_clie_cons, tipo_clie_inco, tipo_clie_peg21, tipo_clie_peg42, tipo_clie_peg63, tipo_clie_posi_reincor, tipo_clie_posi_reingre;
    String tipo_clie_reinco, tipo_clie_reingr, tipo_clie_ret_peg21, tipo_clie_ret_peg42, tipo_clie_ret_peg63, tipo_clie_sin_pedi;
    Double LatOri = 0.0;
    Double LonOri = 0.0;
    Double LatDes = 0.0;
    Double LonDes = 0.0;

    FloatingActionButton fab_nav_ases, fab_snap_shot;

    View main;
    ImageView img_scre_shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_list_ases);

        contexto = this;

        request = Volley.newRequestQueue(getBaseContext());

        Bundle list_ases = this.getIntent().getExtras();
        if(list_ases !=null){
            codi_zona              = list_ases.getString("codi_zona");
            codi_camp              = list_ases.getString("codi_camp");
            codi_sect              = list_ases.getString("codi_sect");
            codi_usua              = list_ases.getString("codi_usua");
            tipo_clie_cons         = list_ases.getString("tipo_clie_cons");
            tipo_clie_inco         = list_ases.getString("tipo_clie_inco");
            tipo_clie_peg21        = list_ases.getString("tipo_clie_peg21");
            tipo_clie_peg42        = list_ases.getString("tipo_clie_peg42");
            tipo_clie_peg63        = list_ases.getString("tipo_clie_peg63");
            tipo_clie_posi_reincor = list_ases.getString("tipo_clie_posi_reincor");
            tipo_clie_posi_reingre = list_ases.getString("tipo_clie_posi_reingre");
            tipo_clie_reinco       = list_ases.getString("tipo_clie_reinco");
            tipo_clie_reingr       = list_ases.getString("tipo_clie_reingr");
            tipo_clie_ret_peg21    = list_ases.getString("tipo_clie_ret_peg21");
            tipo_clie_ret_peg42    = list_ases.getString("tipo_clie_ret_peg42");
            tipo_clie_ret_peg63    = list_ases.getString("tipo_clie_ret_peg63");
            tipo_clie_sin_pedi     = list_ases.getString("tipo_clie_sin_pedi");
            styleZona              = list_ases.getString("styleZona");
        }

        pdp = new ProgressDialog(this);
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog_list_ases);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fab_nav_ases = findViewById(R.id.fab_nave_ases);
        fab_snap_shot = findViewById(R.id.fab_snap_shot);
        fab_nav_ases.setVisibility(View.GONE);

        main                         =  findViewById(R.id.main);
        img_scre_shot                = (ImageView)findViewById(R.id.img_scre_shot);
        hasStartedSnapshotGeneration = false;
    }


    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(new Style.Builder().fromUri(styleZona), new Style.OnStyleLoaded() {

            @Override
            public void onStyleLoaded(@NonNull Style style) {
                //Punto del GPS (ubicacion actual) se puede comentar para hacer pruebas
                enableLocationComponent(style);

                if (!codi_camp.equalsIgnoreCase("")){
                    listado_asesora_sami(codi_camp, codi_zona, codi_sect, codi_usua, tipo_clie_cons, tipo_clie_inco, tipo_clie_peg21,tipo_clie_peg42, tipo_clie_peg63, tipo_clie_posi_reincor, tipo_clie_posi_reingre, tipo_clie_reinco, tipo_clie_reingr, tipo_clie_ret_peg21, tipo_clie_ret_peg42, tipo_clie_ret_peg63, tipo_clie_sin_pedi);
                } else {
                    mapboxMap.addOnMapClickListener(com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this);
                    pdp.dismiss();
                }

                //Estilo del marcador(simbolo) cuando se dibuje la ruta
                addDestinationIconSymbolLayer(style);

                fab_nav_ases.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Muestra la ruta en google map false significa no simulado y true simulado
                        boolean simulateRoute = false;
                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                .directionsRoute(currentRoute)
                                .shouldSimulateRoute(simulateRoute)
                                .build();
                        // Llamar a este método con contexto desde una actividad
                        NavigationLauncher.startNavigation(com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this, options);

                    }
                });

                fab_snap_shot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!hasStartedSnapshotGeneration) {
                            hasStartedSnapshotGeneration = true;
                            Snackbar.make(v, "GENERANDO SNAPSHOT...", Snackbar.LENGTH_LONG).show();

                            startSnapShot(
                                    mapboxMap.getProjection().getVisibleRegion().latLngBounds,
                                    mapView.getMeasuredHeight(),
                                    mapView.getMeasuredWidth());
                        } else {
                            Snackbar.make(v, "UN MOMENTO CARGANDO SNAPSHOT...", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
        });

    }


    private void listado_asesora_sami(String codi_camp, String codi_zona, String codi_sect, String codi_usua, String tipo_clie_cons, String tipo_clie_inco, String tipo_clie_peg21,String tipo_clie_peg42, String tipo_clie_peg63, String tipo_clie_posi_reincor, String tipo_clie_posi_reingre, String tipo_clie_reinco, String tipo_clie_reingr, String tipo_clie_ret_peg21, String tipo_clie_ret_peg42, String tipo_clie_ret_peg63, String tipo_clie_sin_pedi) {

        String url = getString(R.string.url_empr)+"gerentes/listsami?codi_camp="+codi_camp+"&codi_zona="+codi_zona+"&codi_sect="+codi_sect+"&codi_usua="+codi_usua+"&tipo_clie_cons="+tipo_clie_cons+"&tipo_clie_inco="+tipo_clie_inco+"&tipo_clie_peg21="+tipo_clie_peg21+"&tipo_clie_peg42="+tipo_clie_peg42+"&tipo_clie_peg63="+tipo_clie_peg63+"&tipo_clie_posi_reincor="+tipo_clie_posi_reincor+"&tipo_clie_posi_reingre="+tipo_clie_posi_reingre+"&tipo_clie_reinco="+tipo_clie_reinco+"&tipo_clie_reingr="+tipo_clie_reingr+"&tipo_clie_ret_peg21="+tipo_clie_ret_peg21+"&tipo_clie_ret_peg42="+tipo_clie_ret_peg42+"&tipo_clie_ret_peg63="+tipo_clie_ret_peg63+"&tipo_clie_sin_pedi="+tipo_clie_sin_pedi;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    if(mensaje.getString("mensaje").trim().equalsIgnoreCase("ERROR")){
                        guardarpreferenciaerrores("Lista vacia");
                        finish();
                    } else {
                        int fila  = response.length();
                        list_ases = new String[response.length()][17];

                        for (int i = 0; i < fila; i++) {
                            JSONObject geo_clie = response.getJSONObject(i);

                            list_ases[i][0] = geo_clie.getString("nume_iden").trim();
                            list_ases[i][1] = geo_clie.getString("codi_sect").trim();
                            list_ases[i][2] = geo_clie.getString("codi_zona").trim();
                            list_ases[i][3] = geo_clie.getString("nomb_terc").trim();
                            list_ases[i][4] = geo_clie.getString("apel_terc").trim();
                            list_ases[i][5] = geo_clie.getString("camp_ingr").trim();
                            list_ases[i][6] = geo_clie.getString("cy").trim();
                            list_ases[i][7] = geo_clie.getString("cx").trim();
                            list_ases[i][8] = geo_clie.getString("sald_docu").trim();
                            list_ases[i][9] = geo_clie.getString("tipo_clie").trim();
                            list_ases[i][10] = geo_clie.getString("nomb_barr").trim();
                            list_ases[i][11] = geo_clie.getString("dire_terc").trim();
                            list_ases[i][12] = geo_clie.getString("fech_naci").trim();
                            list_ases[i][13] = geo_clie.getString("tele_ter1").trim();
                            list_ases[i][14] = geo_clie.getString("tele_ter2").trim();
                            list_ases[i][15] = geo_clie.getString("codi_camp_1").trim();
                            if (list_ases[i][15].equalsIgnoreCase(""))
                                list_ases[i][15] = "-";
                            try {
                                list_ases[i][16] = geo_clie.getString("esta_visi").trim();
                            }catch (Exception e){
                                list_ases[i][16] = "-";
                            }

                        }
                        mapa_asesora(list_ases, fila);
                    }
                    pdp.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    guardarpreferenciaerrores("Catch "+e);
                    finish();
                    pdp.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                pdp.dismiss();
                guardarpreferenciaerrores(""+e);
                finish();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        request.add(jsonArrayRequest);
    }

    private void mapa_asesora(final String[][] list_ases, final int fila) {

        final Double latitud  = Double.parseDouble(list_ases[0][6]);
        final Double longitud = Double.parseDouble(list_ases[0][7]);

        if (fila>1){
            new dialogoMensaje(contexto,"Se ubicarón a "+fila+" asesoras");
        } else {
            new dialogoMensaje(contexto,"Se ubicó a "+fila+" asesora");
        }

        //Camara del mapa en referencia a un punto y el zoom
        MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null).
                camera(new CameraPosition.Builder().target(new LatLng(latitud,longitud)).
                        zoom(14).build());

        //Punto agregado para la ubicacion del dispositivo.
        com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this.mapboxMap = mapboxMap;

        //Punto agregado para un clic en el mapa y obtener la ruta
        mapboxMap.addOnMapClickListener(com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this);

        //Agregar ubicaciones con titulo y descripcion de las asesoras
        IconFactory iconFactory = IconFactory.getInstance(com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this);
        Icon icon = null;

        for (int i =0; i<fila ; i++){

            switch(list_ases[i][9]){
                case "CONSECUTIVA":
                    icon = iconFactory.fromResource(R.drawable.consecutiva);
                    break;
                case "INCORPORACION":
                    icon = iconFactory.fromResource(R.drawable.incorporacion);
                    break;
                case "PEG21":
                    icon = iconFactory.fromResource(R.drawable.peg21);
                    break;
                case "PEG42":
                    icon = iconFactory.fromResource(R.drawable.peg42);
                    break;
                case "PEG63":
                    icon = iconFactory.fromResource(R.drawable.peg63);
                    break;
                case "POSIBLE REINCORPORACION":
                    icon = iconFactory.fromResource(R.drawable.posible_reincorporacion);
                    break;
                case "POSIBLE REINGRESO":
                    icon = iconFactory.fromResource(R.drawable.posible_reingreso);
                    break;
                case "REINCORPORACION":
                    icon = iconFactory.fromResource(R.drawable.reincorporacion);
                    break;
                case "REINGRESO":
                    icon = iconFactory.fromResource(R.drawable.reingreso);
                    break;
                case "RET. PEG21":
                    icon = iconFactory.fromResource(R.drawable.ret_peg21);
                    break;
                case "RET. PEG42":
                    icon = iconFactory.fromResource(R.drawable.ret_peg42);
                    break;
                case "RET. PEG63":
                    icon = iconFactory.fromResource(R.drawable.ret_peg63);
                    break;
                case "SIN PEDIDO":
                    icon = iconFactory.fromResource(R.drawable.mapbox_marker_icon_default);
                    break;
            }

            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(list_ases[i][6]), Double.parseDouble(list_ases[i][7])))
                    .icon(icon)
                    .title(list_ases[i][3]+" "+list_ases[i][4])
                    .snippet("SECTOR  : "     +list_ases[i][1]+  "\n"+
                            "DNI     : "      +list_ases[i][0]+  "\n"+
                            "DISTRITO   : "   +list_ases[i][10]+ "\n"+
                            "DIRECCION  : "   +list_ases[i][11]+ "\n"+
                            "TELEF : "        +list_ases[i][13]+" - "+list_ases[i][14]+"\n"+
                            "STATUS     : "   +list_ases[i][9]+  "\n"+
                            "SALDO      : "   +list_ases[i][8]+  "\n"+
                            "CAMP INGR. : "   +list_ases[i][5]+  "\n"+
                            "ULT CAMP1 : "    +list_ases[i][15]+ "\n"+
                            "CUMPLEAÑOS : "   +list_ases[i][12]+ "\n"+
                            "ESTADO: "        +list_ases[i][16])
            );
        }
    }


    //Estilo para el marcador cunado se haga clic
    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.map_marker_light));
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
        //Obtengo las cordernas del destino
        LatDes = point.getLatitude();
        LonDes = point.getLongitude();

        //Obtiene las coordenadas de la ubicacion actual del dispostivo o usuario
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());
        //Obtengo las cordernas del origen
        LatOri = locationComponent.getLastKnownLocation().getLatitude();
        LonOri = locationComponent.getLastKnownLocation().getLongitude();

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        //Llama al envento getrout para dibujar la ruta
        getRoute(originPoint, destinationPoint);
        return true;
    }

    //Obtener la ruta desde el punto de origen al punto destino
    private void getRoute(Point origin, Point destination) {

        //Muestra el punto cuando realizar un clic en un punto
        fab_nav_ases.setVisibility(View.VISIBLE);

        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, retrofit2.Response<DirectionsResponse> response) {
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
    //Obtener el punto de ubicacion del dispostivo (punto origen)
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
            finish();
        }
    }


    private void startSnapShot(final LatLngBounds latLngBounds, final int height, final int width) {
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                if (mapSnapshotter == null) {
                    MapSnapshotter.Options options =
                            new MapSnapshotter.Options(width, height)
                                    .withRegion(latLngBounds)
                                    .withCameraPosition(mapboxMap.getCameraPosition())
                                    .withLogo(false)
                                    .withStyle(style.getUri());
                    mapSnapshotter = new MapSnapshotter(com.dupreincaperu.dupree.mh_fragments_azzortimaps.list_ases.this, options);
                } else {
                    mapSnapshotter.setSize(width, height);
                    mapSnapshotter.setRegion(latLngBounds);
                    mapSnapshotter.setCameraPosition(mapboxMap.getCameraPosition());
                }

                mapSnapshotter.start(new MapSnapshotter.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(MapSnapshot snapshot) {

                        Bitmap bitmapOfMapSnapshotImage = snapshot.getBitmap();

                        Uri bmpUri = getLocalBitmapUri(bitmapOfMapSnapshotImage);

                        Intent shareIntent = new Intent();
                        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                        shareIntent.setType("imageperu/png");
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Compartir imagen del mapa"));

                        hasStartedSnapshotGeneration = false;
                    }
                });
            }
        });
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            try {
                out.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                exception.printStackTrace();
            }
            bmpUri = Uri.fromFile(file);
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return bmpUri;
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

        hasStartedSnapshotGeneration = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

        if (mapSnapshotter != null) {
            mapSnapshotter.cancel();
        }

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

    private void guardarpreferenciaerrores(String e) {
        SharedPreferences preferences = getSharedPreferences("errores", Context.MODE_PRIVATE);
        String error = e;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("error", error);
        editor.commit();
    }
}