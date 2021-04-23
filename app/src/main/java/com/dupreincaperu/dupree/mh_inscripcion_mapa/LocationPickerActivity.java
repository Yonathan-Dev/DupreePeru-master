package com.dupreincaperu.dupree.mh_inscripcion_mapa;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_sqlite.MyDbHelper;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

/**
 * Drop a marker at a specific location and then perform
 * reverse geocoding to retrieve and display the location's address
 */
public class LocationPickerActivity extends AppCompatActivity implements PermissionsListener, OnMapReadyCallback {

    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private ImageView hoveringMarker;
    private Layer droppedMarkerLayer;
    double latitud=0.0;
    double longitud=0.0;
    ImageView img_sele_ubic, img_guar_ubic;
    String cedula;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_lab_location_picker);

        Bundle parametros = this.getIntent().getExtras();
        cedula = parametros.getString("cedula");

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        pdp = new ProgressDialog(LocationPickerActivity.this);
        pdp.show();
        pdp.setContentView(R.layout.progress_dialog);
        pdp.setCancelable(false);
        pdp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        LocationPickerActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull final Style style) {
                enableLocationPlugin(style);

                hoveringMarker = new ImageView(LocationPickerActivity.this);
                hoveringMarker.setImageResource(R.drawable.ic_ubicacion);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                hoveringMarker.setLayoutParams(params);
                mapView.addView(hoveringMarker);

                initDroppedMarker(style);

                img_sele_ubic = findViewById(R.id.img_sele_ubic);
                img_guar_ubic = findViewById(R.id.img_guar_ubic);

                img_sele_ubic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (hoveringMarker.getVisibility() == View.VISIBLE) {

                            final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;
                            hoveringMarker.setVisibility(View.INVISIBLE);
                            img_guar_ubic.setVisibility(View.VISIBLE);

                            /*img_sele_ubic.setBackgroundColor(
                                    ContextCompat.getColor(LocationPickerActivity.this, R.color.colorborde));*/
                            img_sele_ubic.setImageResource(R.drawable.ic_cancelar);

                            if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                                GeoJsonSource source = style.getSourceAs("dropped-marker-source-id");
                                if (source != null) {
                                    source.setGeoJson(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));
                                }
                                droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
                                if (droppedMarkerLayer != null) {
                                    droppedMarkerLayer.setProperties(visibility(VISIBLE));
                                }
                            }
                            latitud = mapTargetLatLng.getLatitude();
                            longitud = mapTargetLatLng.getLongitude();

                            direccion(String.valueOf(latitud), String.valueOf(longitud));

                            //reverseGeocode(Point.fromLngLat(mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude()));

                        } else {
                            /*img_sele_ubic.setBackgroundColor(
                                     ContextCompat.getColor(LocationPickerActivity.this, R.color.colorborde));*/
                            img_sele_ubic.setImageResource(R.drawable.ic_seleccionar);
                            img_guar_ubic.setVisibility(View.GONE);

                            hoveringMarker.setVisibility(View.VISIBLE);

                            droppedMarkerLayer = style.getLayer(DROPPED_MARKER_LAYER_ID);
                            if (droppedMarkerLayer != null) {
                                droppedMarkerLayer.setProperties(visibility(NONE));
                            }
                        }
                    }
                });


                img_guar_ubic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDbHelper dbHelper = new MyDbHelper(getBaseContext());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        db.execSQL("DELETE FROM geo_clie WHERE 1 = 1 ");

                        if (db!=null){
                            db.execSQL("INSERT INTO geo_clie (cedula, cx, cy) VALUES ('"+cedula+"','"+latitud+"','"+longitud+"')");
                        }
                        db.close();

                        finish();

                    }
                });

            }
        });
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
// Add the marker image to map
        loadedMapStyle.addImage("dropped-icon-image", BitmapFactory.decodeResource(
                getResources(), R.drawable.map_marker_light));
        loadedMapStyle.addSource(new GeoJsonSource("dropped-marker-source-id"));
        loadedMapStyle.addLayer(new SymbolLayer(DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id").withProperties(
                iconImage("dropped-icon-image"),
                visibility(NONE),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        ));

        pdp.dismiss();

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "R.string.user_location_permission_explanation", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted && mapboxMap != null) {
            Style style = mapboxMap.getStyle();
            if (style != null) {
                enableLocationPlugin(style);
            }
        } else {
            Toast.makeText(this, "R.string.user_location_permission_not_granted", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * This method is used to reverse geocode where the user has dropped the marker.
     *
     * @param point The location to use for the search
     */

    private void reverseGeocode(final Point point) {
        try {
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(point.longitude(), point.latitude()))
                    .geocodingTypes(GeocodingCriteria.TYPE_ADDRESS)
                    .build();

            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    if (response.body() != null) {
                        List<CarmenFeature> results = response.body().features();
                        if (results.size() > 0) {
                            CarmenFeature feature = results.get(0);

                            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    if (style.getLayer(DROPPED_MARKER_LAYER_ID) != null) {
                                        Toast.makeText(LocationPickerActivity.this,
                                                String.format("getString(R.string.location_picker_place_name_result)",
                                                        feature.placeName()), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(LocationPickerActivity.this,
                                    "No se encontro resultados en la busqueda", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    Timber.e("Geocoding Failure: %s", throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            Timber.e("Error geocoding: %s", servicesException.toString());
            servicesException.printStackTrace();
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component. Adding in LocationComponentOptions is also an optional
// parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(
                    this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.NORMAL);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

    }


    public void direccion(String fac_lati, String fac_long ){

        String fac_dire = "";

        if (Double.parseDouble(fac_lati) != 0.0 && Double.parseDouble(fac_long) != 0.0) {

            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        Double.parseDouble(fac_lati), Double.parseDouble(fac_long), 3);
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
                Toast.makeText(getApplicationContext(),"IllegalArgumentException"+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        Toast.makeText(this, fac_dire, Toast.LENGTH_SHORT).show();
    }


}