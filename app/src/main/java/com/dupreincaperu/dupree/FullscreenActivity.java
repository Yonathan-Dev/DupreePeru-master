package com.dupreincaperu.dupree;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.controller.BannerController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.BannerDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.ListProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import io.realm.Realm;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity  {
    public static final String TAG = "FullscreenActivity";
    String campanaActual;
    private final String BROACAST_CATALOGO_OBTAIN = "broadcast_catalogo_obtaint";
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private Realm realm;
    ImageView imgFlor;

    boolean updateOnlyBannerAndPDF=false;
    private BannerController bannerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        String campana = getIntent().getStringExtra("campanaActual");
        if (campana != null){
            campanaActual = campana;
         }

        //API rest
        bannerController =  new BannerController(FullscreenActivity.this);

        //ESTO SE HACE PARA ATRAPAR NOTIFICACIONES FIREBASE EN SEGUNDO PLANO O MUERTA (LLEGAN LOS DATOS EN EL INTENT)
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);


            }
        }
        //ESTO SE HACE PARA ATRAPAR NOTIFICACIONES FIREBASE EN SEGUNDO PLANO O MUERTA (LLEGAN LOS DATOS EN EL INTENT)
//ESTO SE HACE PARA ATRAPAR NOTIFICACIONES FIREBASE EN SEGUNDO PLANO O MUERTA (LLEGAN LOS DATOS EN EL INTENT)
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);


            }
        }
        //ESTO SE HACE PARA ATRAPAR NOTIFICACIONES FIREBASE EN SEGUNDO PLANO O MUERTA (LLEGAN LOS DATOS EN EL INTENT)

        //Realm.init(this);//esto debe iniciarse una vez en la app, debo colocarlo ene l main y menu para cuando pase directo. (creo)
        realm = Realm.getDefaultInstance();

        if(mPreferences.getIsNotNewAppOn(FullscreenActivity.this) && mPreferences.isChangeCampanaON(FullscreenActivity.this)) {
            loadAllData();
        } else if(mPreferences.getIsNotNewAppOn(FullscreenActivity.this) && mPreferences.isUpdateOnly_BannerAndPFD_ON(FullscreenActivity.this)) {
            gotoPanel();
            updateOnlyBannerAndPDF=true;
            loadAllData();
        } else if(mPreferences.getIsNotNewAppOn(FullscreenActivity.this) && mPreferences.isLoggedIn(FullscreenActivity.this)) {
            gotoPanel();
        } else if(mPreferences.getIsNotNewAppOn(FullscreenActivity.this)){
            gotoMain();
        } else {
           loadAllData();
        }
        //hasta tanto todos se hayan suscrito, el lugar ieal de hacerlo es la primera vez que se instala
        Log.e(TAG, "FirebaseMessaging.getInstance().subscribeToTopic(\"all_devices\")");
        FirebaseMessaging.getInstance().subscribeToTopic("all_devices");
       // irHacerPrePedido();
    }

    private void loadAllData(){
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        imgFlor = (ImageView) findViewById(R.id.image_flor);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        //if(!updateOnlyBannerAndPDF) {
            deleteCatalogo();
            deleteOferta();
        //}
        getBanner();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    /**
     * START ANIMATIOV
     */


    Animation rotation;
    public void startAnimation(){
        rotation = AnimationUtils.loadAnimation(FullscreenActivity.this, R.anim.rotate);
        rotation.setFillAfter(true);
        imgFlor.startAnimation(rotation);
    }

    public void stopAnimation(){
        rotation.cancel();
    }


    private void getBanner(){
        startAnimation();
        responseBanner = null;

        bannerController.getBanner(new TTResultListener<BannerDTO>() {
            @Override
            public void success(BannerDTO result) {
                Log.e(TAG, "getBanner() -> success(): " + new Gson().toJson(result));
                responseBanner(result);
            }

            @Override
            public void error(TTError error) {
                Log.e(TAG, "getBanner() -> error(): " + new Gson().toJson(error));
                gotoMain();
                //errorLoadInitData(); //Caso contrario muestra un error y no carga el app
            }
        });
    }




    BannerDTO responseBanner;
    public void responseBanner(BannerDTO responseBanner){
        if(responseBanner!=null) {
            this.responseBanner = responseBanner;
            getFilesCatalogos();
        }else{
            errorLoadInitData();
        }
    }

    private void getFilesCatalogos(){
        bannerController.getUrlCatalogos(new TTResultListener<UrlsCatalogosDTO>() {
            @Override
            public void success(UrlsCatalogosDTO result) {
                Log.e(TAG, "getFilesCatalogos() -> success(): " + new Gson().toJson(result));
                responseFileCatalogos(result);
            }

            @Override
            public void error(TTError error) {
                Log.e(TAG, "getFilesCatalogos() -> error(): " + new Gson().toJson(error));
                errorLoadInitData();
            }
        });
    }

    UrlsCatalogosDTO responseUrlCatalogos;
    public void responseFileCatalogos(UrlsCatalogosDTO responseUrlCatalogos){
        if(responseUrlCatalogos!=null) {
            this.responseUrlCatalogos = responseUrlCatalogos;
            //SE DECIDE SI SE DESCARGA LA LISTA DE PRODUCTOS O NO
            /*if(updateOnlyBannerAndPDF)
                terminatedProcess();
            else*/
            obtainCatalogo();
        }else{
            errorLoadInitData();
        }
    }



    private void obtainCatalogo(){

        bannerController.getProductos(campanaActual, new TTResultListener<ProductCatalogoDTO>() {
            @Override
            public void success(ProductCatalogoDTO result) {
                Log.e(TAG, "obtainCatalogo() -> success(): " + new Gson().toJson(result));
                //un detalle envia 200 con error 404
                if (result.getCode() == 404) {
                    errorLoadInitData();
                } else {
                    responseCatalogo(result.getResult());
                }
            }

            @Override
            public void error(TTError error) {
                Log.e(TAG, "obtainCatalogo() -> error(): " + new Gson().toJson(error));

                //error de Backend
                if (!error.getCodigo().equalsIgnoreCase("")){
                    if(error.getStatusCode() == 404) {
                        try {
                            ProductCatalogoDTO resp = new Gson().fromJson(error.getErrorBody(), ProductCatalogoDTO.class);
                            responseCatalogo(resp.getResult());
                        } catch (JsonSyntaxException e) {
                            terminatedProcess();
                            //Se comenta para que pueda ingresar al menu esto pasa cuando no carga los productos de catalogo
                            //errorLoadInitData();
                        }
                    } else {
                        terminatedProcess();
                        //errorLoadInitData();
                    }
                } else {
                    terminatedProcess();
                }


            }
        });


//        new Http(FullscreenActivity.this).getProductosCatalogo(false);
    }

    ListProductCatalogoDTO listaProd_catalogo;
    public void responseCatalogo(ListProductCatalogoDTO listaProd_catalogo){
        Log.e(TAG, "listaProd_catalogo.getOfertas(): "+new Gson().toJson(listaProd_catalogo.getOfertas()));
        Log.e(TAG, "listaProd_catalogo.getPaquetones(): "+new Gson().toJson(listaProd_catalogo.getPaquetones()));
        Log.e(TAG, "listaProd_catalogo.getProductos(): "+new Gson().toJson(listaProd_catalogo.getProductos()));
        if(listaProd_catalogo.getProductos()!=null && listaProd_catalogo.getProductos().size()>0) {
            this.listaProd_catalogo = listaProd_catalogo;
            if(listaProd_catalogo.getOfertas()!=null && listaProd_catalogo.getOfertas().size()>0) {
                writeOfertas();
            } else if(listaProd_catalogo.getPaquetones()!=null &&
                    listaProd_catalogo.getPaquetones().getLinea_1()!=null &&
                    listaProd_catalogo.getPaquetones().getLinea_2()!=null &&
                    listaProd_catalogo.getPaquetones().getLinea_3()!=null &&
                    listaProd_catalogo.getPaquetones().getLinea_4()!=null){
                writePaquetones();
            } else {
                writeCatalogo();
            }
        } else{
            errorLoadInitData();
        }
    }

    public void errorLoadInitData(){
        msgToast("No se pueden cargar datos iniciales");
        finish();
    }

    private void gotoMain(){
        //obtainCatalogo();
        startActivity(new Intent(FullscreenActivity.this, MainActivity.class));
        finish();
    }
    private void gotoPanel() {
        //obtainCatalogo();
        startActivity(new Intent(FullscreenActivity.this, MenuActivity.class));
        finish();
    }

    public void deleteCatalogo(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.delete(Catalogo.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"deleteCatalogo... ---------------ok--------------");
                //realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"deleteCatalogo... ---------------error--------------");
                Log.e(TAG,"deleteCatalogo... "+error.getMessage());
                //realm.close();
                errorLoadInitData();

            }
        });
        //realm.beginTransaction();

        //realm.commitTransaction();
    }

    public void deleteOferta(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.delete(Oferta.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"deleteOferta... ---------------ok--------------");
                //realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"deleteOferta... ---------------error--------------");
                Log.e(TAG,"deleteOferta... "+error.getMessage());
                //realm.close();
                errorLoadInitData();

            }
        });
        //realm.beginTransaction();

        //realm.commitTransaction();
    }


    public void writeOfertas(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.insert(listaProd_catalogo.getOfertas());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"writeOfertas... ---------------ok--------------");
                Log.v(TAG, ": " + new Gson().toJson(listaProd_catalogo.getOfertas()));
                if(listaProd_catalogo.getPaquetones()!=null &&
                        listaProd_catalogo.getPaquetones().getLinea_1()!=null  &&
                        listaProd_catalogo.getPaquetones().getLinea_2()!=null &&
                        listaProd_catalogo.getPaquetones().getLinea_3()!=null &&
                        listaProd_catalogo.getPaquetones().getLinea_4()!=null){
                    writePaquetones();
                } else {
                    writeCatalogo();
                }
                //realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"writeOfertas... ---------------error--------------");
                Log.e(TAG,"writeOfertas... "+error.getMessage());
                //realm.close();
                errorLoadInitData();
            }
        });
    }

    public void writePaquetones(){
        mPreferences.setJASON_Paquetones(FullscreenActivity.this, new Gson().toJson(listaProd_catalogo.getPaquetones()));
        Log.v(TAG,"writePaquetones... ---------------ok--------------");
        Log.v(TAG, ": " + new Gson().toJson(listaProd_catalogo.getPaquetones()));
        writeCatalogo();
    }

    public void writeCatalogo(){
        if(realm.isClosed())
            realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //RealmList<Catalogo> dm_list_catalogo = new RealmList<Catalogo>();
                //dm_list_catalogo.addAll(listaProd_catalogo.getProductos());
                //DM_List_Catalogo dm_list_catalogo = new DM_List_Catalogo(); // <-- create unmanaged
                //RealmList<Catalogo> _catalList = new RealmList<>();
                //_catalList.addAll(listaProd_catalogo.getProductos());
                //dm_list_catalogo.setCatalogoList(_catalList);
                bgRealm.insert(listaProd_catalogo.getProductos());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"writeCatalogo... ---------------ok--------------");
                Log.v(TAG, ": " + new Gson().toJson(listaProd_catalogo.getProductos()));

                terminatedProcess();
                //realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"writeCatalogo... ---------------error--------------");
                Log.e(TAG,"writeCatalogo... "+error.getMessage());
                //realm.close();
                errorLoadInitData();
            }
        });
    }

    private void terminatedProcess(){
        mPreferences.setDataInit(FullscreenActivity.this, responseBanner, responseUrlCatalogos);

        updateOnlyBannerAndPDF =false;

        mPreferences.setVersionCatalogo(FullscreenActivity.this, responseBanner.getVersion());
        Log.e(TAG, "ResponseVersion de catalogo: "+ responseBanner.getVersion());

        mPreferences.setChangeCampana(FullscreenActivity.this, false);
        mPreferences.setUpdateBannerAndPDF(FullscreenActivity.this, false);

        if(mPreferences.isLoggedIn(FullscreenActivity.this)){
            gotoPanel();//si esta ya logueado, es solo una actualizacion y regresa al panel
        } else {
            gotoMain();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void msgToast(String msg){
        Log.e("Toast", msg);
        Toast.makeText(FullscreenActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
