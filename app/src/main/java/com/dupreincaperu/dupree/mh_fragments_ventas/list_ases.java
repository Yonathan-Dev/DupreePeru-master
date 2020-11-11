package com.dupreincaperu.dupree.mh_fragments_ventas;

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
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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
import com.dupreincaperu.dupree.mh_dial_peru.dialogo_personal;
import com.dupreincaperu.dupree.mh_pasa_prod.dato_gene;
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

    String URL_EMPRESA="";
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

    String codi_zona="", codi_camp="", codi_sect="", codi_usua="";
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

        dato_gene URL = new dato_gene();
        dato_gene SSL = new dato_gene();
        URL_EMPRESA = URL.getURL_EMPRESA();
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

        //Estilo de mapa
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
                //Punto del GPS (ubicacion actual) se puede comentar para hacer pruebas
                //enableLocationComponent(style);

                if (!codi_camp.equalsIgnoreCase("")){
                    listado_asesora_sami(codi_camp, codi_zona, codi_sect, codi_usua, tipo_clie_cons, tipo_clie_inco, tipo_clie_peg21,tipo_clie_peg42, tipo_clie_peg63, tipo_clie_posi_reincor, tipo_clie_posi_reingre, tipo_clie_reinco, tipo_clie_reingr, tipo_clie_ret_peg21, tipo_clie_ret_peg42, tipo_clie_ret_peg63, tipo_clie_sin_pedi);
                } else {
                    mapboxMap.addOnMapClickListener(com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this);
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
                        NavigationLauncher.startNavigation(com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this, options);

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

        String url = URL_EMPRESA+"gerentes/listsami?codi_camp="+codi_camp+"&codi_zona="+codi_zona+"&codi_sect="+codi_sect+"&codi_usua="+codi_usua+"&tipo_clie_cons="+tipo_clie_cons+"&tipo_clie_inco="+tipo_clie_inco+"&tipo_clie_peg21="+tipo_clie_peg21+"&tipo_clie_peg42="+tipo_clie_peg42+"&tipo_clie_peg63="+tipo_clie_peg63+"&tipo_clie_posi_reincor="+tipo_clie_posi_reincor+"&tipo_clie_posi_reingre="+tipo_clie_posi_reingre+"&tipo_clie_reinco="+tipo_clie_reinco+"&tipo_clie_reingr="+tipo_clie_reingr+"&tipo_clie_ret_peg21="+tipo_clie_ret_peg21+"&tipo_clie_ret_peg42="+tipo_clie_ret_peg42+"&tipo_clie_ret_peg63="+tipo_clie_ret_peg63+"&tipo_clie_sin_pedi="+tipo_clie_sin_pedi;
        jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject mensaje = response.getJSONObject(0);
                    if(mensaje.getString("mensaje").trim().equalsIgnoreCase("ERROR")){
                        guardarpreferenciaerrores("LISTA VACIA");
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
            new dialogo_personal(contexto,"Se ubicarón a "+fila+" asesoras");
        } else {
            new dialogo_personal(contexto,"Se ubicó a "+fila+" asesora");
        }

        //Camara del mapa en referencia a un punto y el zoom
        MapboxMapOptions options = MapboxMapOptions.createFromAttributes(this, null).
                camera(new CameraPosition.Builder().target(new LatLng(latitud,longitud)).
                        zoom(14).build());

        //Punto agregado para la ubicacion del dispositivo.
        com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this.mapboxMap = mapboxMap;

        //Punto agregado para un clic en el mapa y obtener la ruta
        mapboxMap.addOnMapClickListener(com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this);

        //Agregar ubicaciones con titulo y descripcion de las asesoras
        IconFactory iconFactory = IconFactory.getInstance(com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this);
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
                    mapSnapshotter = new MapSnapshotter(com.dupreincaperu.dupree.mh_fragments_ventas.list_ases.this, options);
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