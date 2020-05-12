package com.dupreincaperu.dupree.mh_fragments_ventas;

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
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_personal;
import com.dupreincaperu.dupree.mh_dial_peru.motivo_visita;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
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
    String acti_usua, cons_terc, band;


    AlertDialog.Builder builder;

    RequestQueue request;
    JsonArrayRequest jsonArrayRequest;
    String URL_EMPRESA="";
    Context contexto;

    private ProgressDialog pdp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_visita);

        dato_gene URL = new dato_gene();
        dato_gene SSL = new dato_gene();

        URL_EMPRESA = URL.getURL_EMPRESA();
        //URL_EMPRESA="https://servicioweb2per.azzorti.co:443/hmvc_movil/index.php/rest/";

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

        String styleZona="mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";

        switch (codi_zona){
            case "001":
                styleZona = "mapbox://styles/dupreeazzorti/ck4y9mm7vgg1b1cp49kmkzyvr";
                break;
            case "002":
                styleZona = "mapbox://styles/dupreeazzorti/ck4yl58oyhxtl1co4rt7z7m4l";
                break;
            case "003":
                styleZona = "mapbox://styles/dupreeazzorti/ck52ufwfm1xnk1cmn9lo9hjbg";
                break;
            case "004":
                styleZona = "mapbox://styles/dupreeazzorti/ck52w0ir90a8w1cpaj005r4yy";
                break;
            case "005":
                styleZona = "mapbox://styles/dupreeazzorti/ck52wjeu508b61cp1ilp87vlw";
                break;
            case "006":
                styleZona = "mapbox://styles/dupreeazzorti/ck52xi03409821cn63c45ty4d";
                break;
            case "007":
                styleZona = "mapbox://styles/dupreeazzorti/ck52y3lw93vca1dlkztjqat13";
                break;
            case "008":
                styleZona = "mapbox://styles/dupreeazzorti/ck52yj68i0a6u1cn6tv5nfpzl";
                break;
            case "009":
                styleZona = "mapbox://styles/dupreeazzorti/ck52z70h60d831cpa0fcvd4ii";
                break;
            case "010":
                styleZona = "mapbox://styles/dupreeazzorti/ck52znk31j3ha1cmuwy31116o";
                break;
            case "011":
                styleZona = "mapbox://styles/dupreeazzorti/ck53008o10ap91cslkd9a1g8g";
                break;
            case "012":
                styleZona = "mapbox://styles/dupreeazzorti/ck530eh450bro1cperdllg73x";
                break;
            case "013":
                styleZona = "mapbox://styles/dupreeazzorti/ck530prcl0c9u1cs60i603jgp";
                break;
            case "014":
                styleZona = "mapbox://styles/dupreeazzorti/ck531ekf40cwk1cs68cc5bkyp";
                break;
            case "015":
                styleZona = "mapbox://styles/dupreeazzorti/ck531yca424o71cmnjp2cbgea";
                break;
            case "016":
                styleZona = "mapbox://styles/dupreeazzorti/ck5328qr40crw1cslihtfru8k";
                break;
            case "017":
                styleZona = "mapbox://styles/dupreeazzorti/ck532hksf0du51cmret8ja4dw";
                break;
            case "018":
                styleZona = "mapbox://styles/dupreeazzorti/ck532q6wh0xfp1cro51zwfvax";
                break;
            case "020":
                styleZona = "mapbox://styles/dupreeazzorti/ck532y3130e941cmrj26vaa9z";
                break;
            case "021":
                styleZona = "mapbox://styles/dupreeazzorti/ck533aab10h2v1cpaw84wuhq2";
                break;
            case "022":
                styleZona = "mapbox://styles/dupreeazzorti/ck533iptl0et21cp10l5tw08h";
                break;
            case "023":
                styleZona = "mapbox://styles/dupreeazzorti/ck53vy6zv0f321ct1zqz5r6dd";
                break;
            case "024":
                styleZona = "mapbox://styles/dupreeazzorti/ck53wbtz81rli1dllqab1buus";
                break;
            case "025":
                styleZona = "mapbox://styles/dupreeazzorti/ck5401nil0gy71co6iu701j5o";
                break;
            case "026":
                styleZona = "mapbox://styles/dupreeazzorti/ck540b4gx0nzb1co9wzo50sq4";
                break;
            case "027":
                styleZona = "mapbox://styles/dupreeazzorti/ck540mx640ggk1dro6nvhrrya";
                break;
            case "028":
                styleZona = "mapbox://styles/dupreeazzorti/ck5412we10i2a1cmkxrr94bak";
                break;
            case "029":
                styleZona = "mapbox://styles/dupreeazzorti/ck54238ba1au41cpe9nh6zpee";
                break;
            case "031":
                styleZona = "mapbox://styles/dupreeazzorti/ck542gck80kyn1cn41evgoprn";
                break;
            case "032":
                styleZona = "mapbox://styles/dupreeazzorti/ck543sbhx1cnf1cs6btkbltwc";
                break;
            case "033":
                styleZona = "mapbox://styles/dupreeazzorti/ck544243blw3l1cp4y4r4btd3";
                break;
            case "034":
                styleZona = "mapbox://styles/dupreeazzorti/ck5449qrq0jue1drop6gpg4ng";
                break;
            case "035":
                styleZona = "mapbox://styles/dupreeazzorti/ck544hz74lwio1cp45w0j0xyn";
                break;
            case "036":
                styleZona = "mapbox://styles/dupreeazzorti/ck546sait1fcm1cp1t1xb1n1f";
                break;
            case "037":
                styleZona = "mapbox://styles/dupreeazzorti/ck546zioi33dj1ckv01h4lfzz";
                break;
            case "038":
                styleZona = "mapbox://styles/dupreeazzorti/ck5476mxwk87b1cmu1i9776ny";
                break;
            case "039":
                styleZona = "mapbox://styles/dupreeazzorti/ck547f480k8eu1cmuusmqpjvd";
                break;
            case "041":
                styleZona = "mapbox://styles/dupreeazzorti/ck547n49n21g01ck5f3lp9r29";
                break;
            case "042":
                styleZona = "mapbox://styles/dupreeazzorti/ck547vmgf0v121co9mfewnc39";
                break;
            case "043":
                styleZona = "mapbox://styles/dupreeazzorti/ck5482k2b0qd71ct1129gn46f";
                break;
            case "044":
                styleZona = "mapbox://styles/dupreeazzorti/ck548g0qk525m1cqgsqnlw32u";
                break;
            case "051":
                styleZona = "mapbox://styles/dupreeazzorti/ck6ht12wi0vwf1jl7xqbyqoue";
                break;
            case "061":
                styleZona = "mapbox://styles/dupreeazzorti/ck548ne3s0qx41ct1p12gti4e";
                break;
            case "062":
                styleZona = "mapbox://styles/dupreeazzorti/ck548w9dh0o4m1drobg5ndn6d";
                break;
            case "063":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "101":
                styleZona = "mapbox://styles/dupreeazzorti/ck5dywfcw0uqc1ioafw3paopj";
                break;
            case "102":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e0tbo90t9o1ile7yukdg8p";
                break;
            case "103":
                styleZona = "mapbox://styles/dupreeazzorti/ck5ft7zaf0xxc1ioa5eip7948";
                break;
            case "104":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e1psu80xbq1ill1rbcl32l";
                break;
            case "105":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e27jqy0xrx1illsbiq5rtp";
                break;
            case "106":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e2ltva0y4y1illmzewm5yx";
                break;
            case "107":
                styleZona = "mapbox://styles/dupreeazzorti/ck5fu9wvj005o1io5cjxrsc4o";
                break;
            case "108":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e2zryf0ly01jo66vetc1nr";
                break;
            case "109":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lnw6kl2el71ip5rr6uzrvv";
                break;
            case "110":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lo5shi0w2r1iovph8q96tm";
                break;
            case "111":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "112":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "113":
                styleZona = "mapbox://styles/dupreeazzorti/ck5l99rgg0mgs1imzg8iwvedt";
                break;
            case "114":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkjgpx202c1iokz0l7b5ky";
                break;
            case "130":
                styleZona = "mapbox://styles/dupreeazzorti/ck6heskyx0iqq1imu8c09tq2q";
                break;
            case "131":
                styleZona = "mapbox://styles/dupreeazzorti/ck5fueh1j00ar1irodq7hphvm";
                break;
            case "132":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lk5d2a0x941io3ddxqivdi";
                break;
            case "140":
                styleZona = "mapbox://styles/dupreeazzorti/ck5mllmgp1tnl1ilpvgklequz";
                break;
            case "141":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "142":
                styleZona = "mapbox://styles/dupreeazzorti/ck5l9t21v0lyf1itfliknj9r8";
                break;
            case "143":
                styleZona = "mapbox://styles/dupreeazzorti/ck5ejbm2e1dmk1illcl8rgmub";
                break;
            case "144":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "145":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkqlij3x231iqtfp9w4lrc";
                break;
            case "146":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "147":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "148":
                styleZona = "mapbox://styles/dupreeazzorti/ck5efkr960m9p1iqmirrboccx";
                break;
            case "149":
                styleZona = "mapbox://styles/dupreeazzorti/ck5ljeyvw0u441ilp5foa11u9";
                break;
            case "201":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "203":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "205":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "206":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "207":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "208":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lb6z8i0jwx1ink7kkxzv7y";
                break;
            case "209":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lbqiqp0kem1inkqsg6rs46";
                break;
            case "210":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gwagyf01g01imx8mpb1lr9";
                break;
            case "211":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gwtyr801ya1io8fkj619z8";
                break;
            case "212":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gwz2rl023n1io8lerr6lpi";
                break;
            case "213":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gx4m2y02961io8waum0ccy";
                break;
            case "214":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gx5b7b027h1jl7h3unkfiz";
                break;
            case "215":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gxc5e002f31jt3ubz01l2v";
                break;
            case "216":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gxkrnp02m51imzc3adnc7z";
                break;
            case "217":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gxlgss02ms1imz5wwqru5i";
                break;
            case "218":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gxwz9k02yv1io8ppacxg6t";
                break;
            case "219":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gxxmyq030p1imush79yptc";
                break;
            case "220":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkt9q81myk1ir3ba3uu7i4";
                break;
            case "221":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkwbov20er1iok02xne13e";
                break;
            case "222":
                styleZona = "mapbox://styles/dupreeazzorti/ck5efqot30mey1iqmuvv5c7hd";
                break;
            case "223":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gy5vwy035n1imzc6hen8r7";
                break;
            case "224":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gy6pmz037j1iqz34mcn0ma";
                break;
            case "225":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkxzfg236l1ilg6hdwqrub";
                break;
            case "226":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lkzpza1lmn1ipri4hwbzpr";
                break;
            case "227":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e3f9yj0yuc1ip9m83h1oz4";
                break;
            case "228":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e4pmew104g1ini3d41q4sv";
                break;
            case "229":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "230":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e6ok0u11xf1isig22nchvs";
                break;
            case "231":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gyf8si03gh1in5byku1kys";
                break;
            case "232":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gyg09103gl1iqlhkrv3d9u";
                break;
            case "233":
                styleZona = "mapbox://styles/dupreeazzorti/ck5e77vwn06rg1ipbvf4f19eh";
                break;
            case "234":
                styleZona = "mapbox://styles/dupreeazzorti/ck5ee2kcg0ttw1io7gbxx390l";
                break;
            case "235":
                styleZona = "mapbox://styles/dupreeazzorti/ck5eecdmv197o1in59oiyshb4";
                break;
            case "236":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gyidck03hl1io8m6zczjfv";
                break;
            case "237":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gymowb03p41iqyp4nvihsk";
                break;
            case "238":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gysh3803su1ir12317h6hg";
                break;
            case "239":
                styleZona = "mapbox://styles/dupreeazzorti/ck5eg4le0061n1il3gsbarqix";
                break;
            case "240":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gyvuoj03ts1imz9nvvd3k2";
                break;
            case "241":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gz018n03z51ilnyawtrxen";
                break;
            case "242":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gz6toa046a1in57q609dw2";
                break;
            case "243":
                styleZona = "mapbox://styles/dupreeazzorti/ck5eg7oln1ap01ilf0kflt6y8";
                break;
            case "244":
                styleZona = "mapbox://styles/dupreeazzorti/ck5efecys0ec21ipbeat78dln";
                break;
            case "245":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lifrve29hc1ip5orm1r2fl";
                break;
            case "250":
                styleZona = "mapbox://styles/dupreeazzorti/ck5lj2jn40r6w1ink71zntbui";
                break;
            case "300":
                styleZona = "mapbox://styles/dupreeazzorti/ck6gz7ial04701iqlqq31tywf";
                break;
            case "666":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "760":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "950":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "951":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
            case "999":
                styleZona = "mapbox://styles/dupreeazzorti/ck3kjau1l2uyn1cmz4brrwhoz";
                break;
        }

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

                String url = URL_EMPRESA+"georefere/visi?nume_iden="+nume_iden+"&codi_zona="+codi_zona+"&cx="+cx+"&cy="+cy+"&dire_terc="+dire_terc+"&dire_refe="+dire_refe+"&desc_orig="+desc_orig+"&acti_usua="+acti_usua+"&esta_visi="+esta_visi;
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onResponse(JSONArray response) {
                        pdp.dismiss();
                        try {
                            JSONObject visi = response.getJSONObject(0);
                            String mensaje =  visi.getString("mensaje");
                            new dialogo_personal(contexto,mensaje);
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
