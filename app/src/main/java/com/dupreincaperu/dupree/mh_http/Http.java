package com.dupreincaperu.dupree.mh_http;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.interceptors.AddCookiesInterceptor;
import com.dupreeinca.lib_api_rest.interceptors.ConnectivityInterceptor;
import com.dupreeinca.lib_api_rest.interceptors.NoConnectivityException;
import com.dupreeinca.lib_api_rest.interceptors.ReceivedCookiesInterceptor;
import com.dupreeinca.lib_api_rest.model.dto.request.LiquidarSend;
import com.dupreeinca.lib_api_rest.model.dto.response.LiquidarDTO;
import com.dupreincaperu.dupree.MainActivity;
import com.dupreincaperu.dupree.MenuActivity;
import com.dupreincaperu.dupree.R;

import com.dupreincaperu.dupree.mh_required_api.RequiredConfirmaPedido;
import com.dupreeinca.lib_api_rest.model.dto.request.Index;
import com.dupreincaperu.dupree.mh_required_api.RequiredRefreshToken;
import com.dupreincaperu.dupree.mh_required_api.RequiredRegister_NEW_2018;
import com.dupreeinca.lib_api_rest.model.dto.request.Register;
import com.dupreeinca.lib_api_rest.model.dto.request.IdMessages;
import com.dupreeinca.lib_api_rest.model.dto.response.CatalogoPremiosList;
import com.dupreeinca.lib_api_rest.model.dto.request.RedimirPremios;
import com.dupreeinca.lib_api_rest.model.dto.response.BandejaDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCampana;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.FaltantesDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PanelAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.PerfilDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ProductCatalogoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PuntosAsesoraDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RedimirDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ReferidosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.RetenidosDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.VersionDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.DataUser;
import com.dupreincaperu.dupree.mh_interface_api.iAuth;
import com.dupreincaperu.dupree.mh_interface_api.iBanners;
import com.dupreincaperu.dupree.mh_interface_api.iFile;
import com.dupreincaperu.dupree.mh_interface_api.iReport;
import com.dupreincaperu.dupree.mh_required_api.RequiredAuth;
import com.dupreeinca.lib_api_rest.model.dto.request.Campana;
import com.dupreincaperu.dupree.mh_required_api.RequiredCode;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.mh_required_api.RequiredNewPwd;
import com.dupreincaperu.dupree.mh_required_api.RequiredRegister;
import com.dupreincaperu.dupree.mh_required_api.RequiredTermins;
import com.dupreincaperu.dupree.mh_required_api.RequiredTerminsGerente;
import com.dupreeinca.lib_api_rest.model.dto.response.DataAuth;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.util.alert.DownloadFileAsyncTask;
import com.dupreincaperu.dupree.mh_utilities.MyDialoges;
import com.dupreeinca.lib_api_rest.util.alert.ProgressDialogHorizontal;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
  Created by cloudemotion on 25/8/17
 */

public class Http {
    private String TAG = Http.class.getName();
//    String baseURL="https://alcor.dupree.co/dupreeWS/";
    Context myContext;

    Retrofit retrofit;
    public Http(Context myContext) {
        this.myContext = myContext;

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(new AddCookiesInterceptor(myContext));
        client.addInterceptor(new ReceivedCookiesInterceptor(myContext));
        client.addInterceptor(new ConnectivityInterceptor(myContext));
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(myContext.getResources().getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();


    }

    private GenericListener genericListener;
    private boolean genericListenerON = false;
    public void setGenericListener(GenericListener genericListener) {
        this.genericListener = genericListener;
        genericListenerON = true;
    }
    public interface GenericListener {
        void onProcess(String message);
        void onFailed();
    }


//    public void getBanners()
//    {
//        final iBanners service = retrofit.create(iBanners.class);
//
//        Log.e(TAG, "getBanners()");
//
//        Call<ResponseBannerDTO> call = service.obtainBanner();
//
//        call.enqueue(new Callback<ResponseBannerDTO>() {
//            @Override
//            public void onResponse(Call<ResponseBannerDTO> call, Response<ResponseBannerDTO> response) {
//                Log.e(TAG+"onResponse", call.request().url().toString());
//                if (!response.isSuccessful()) {
//                    Log.e(TAG+"onResponse", " : " + new Gson().toJson(response.body()));
//                    ((FullscreenActivity) myContext).errorLoadInitData();
//                } else {
//                    //Toast.makeText(myContext, myContext.getResources().getString(R.string.operacion_exitosa), Toast.LENGTH_LONG).show();
//                    Log.e(TAG+"onResponse", "-> " + new Gson().toJson(response.body()));
//                    Log.e(TAG+"onResponse", "-> version: " + response.body().getVersion());
//
//                    ((FullscreenActivity) myContext).responseBanner(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBannerDTO> call, Throwable t) {
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//
//                }
//                ((FullscreenActivity) myContext).errorLoadInitData();
//            }
//        });
//    }

    public void getVersion()
    {
        showDialogWait();
        final iBanners service = retrofit.create(iBanners.class);
        Log.e(TAG, "getVersion()");
        Call<VersionDTO> call = service.obtainVersion();
        call.enqueue(new Callback<VersionDTO>() {
            @Override
            public void onResponse(Call<VersionDTO> call, Response<VersionDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());
                if (!response.isSuccessful()) {
                    //Log.e(TAG+"onResponse", " : " + new Gson().toJson(response.body()));
                    if(genericListenerON){
                        genericListener.onFailed();
                    }
                } else {
                    //Toast.makeText(myContext, myContext.getResources().getString(R.string.operacion_exitosa), Toast.LENGTH_LONG).show();
                    Log.e(TAG+"onResponse", "-> " + new Gson().toJson(response.body()));
                    if(genericListenerON){
                        genericListener.onProcess(response.body().getVersion());
                    }
                }
            }
            @Override
            public void onFailure(Call<VersionDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){

                }
                if(genericListenerON){
                    genericListener.onFailed();
                }
            }
        });
    }

    private BandejaListener bandejaListener;
    private boolean bandejaListenerON = false;
    public void setBandejaListener(BandejaListener bandejaListener) {
        this.bandejaListener = bandejaListener;
        bandejaListenerON = true;
    }
    public interface BandejaListener {
        void onProcess(BandejaDTO responseBandeja);
        void onFailed();
    }

    public void obtainBandejaEntrada()
    {
        final iReport service = retrofit.create(iReport.class);

        //showDialogWait();
        Log.e("JSON obtainBandeja", "Params: ");

        /*ENVIANDO TOKEN EN LA CABECERA*/
        //Map<String, String> map = new HashMap<>();
        //map.put("Authorization", "jwt ".concat(Preferences.getToken(myContext)));
        Call<BandejaDTO> call = service.obtainMessages();

        call.enqueue(new Callback<BandejaDTO>() {
            @Override
            public void onResponse(Call<BandejaDTO> call, Response<BandejaDTO> response) {
                String msgError=null;
                Log.e("onResponse", call.request().url().toString());
                //stopDialogoWait();
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            Log.e(TAG, "sendChat-> TRY");
                            if(resp.getRaise()!=null)
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            else
                                msgError = (myContext.getResources().getString(R.string.http_error_desconocido));//msgError = resp.getMessage();
                        } catch (IOException | JsonSyntaxException e) {
                            Log.e(TAG, "sendChat-> CATCH");
                            msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                        }
                    } else {
                        Log.e(TAG, "200: "+new Gson().toJson(response.body()));
                        if(bandejaListenerON)
                            bandejaListener.onProcess(response.body());
                    }
                } else {
                    Log.e(TAG, "addMyFavorite-> ELSE");
                    msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                    //toastMSG(myContext.getResources().getString(R.string.error_desconocido));
                }

                if(msgError!=null) {
                    toastMSG(msgError);
                    if(code==401){
                        gotoMain();
                    } else {
                        if(bandejaListenerON)
                            bandejaListener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<BandejaDTO> call, Throwable t) {

                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                }
                if(bandejaListenerON)
                    bandejaListener.onFailed();
            }
        });

    }

    public void readMessages(IdMessages requiredIdMessaage)
    {
        final iReport service = retrofit.create(iReport.class);

        //showDialogWait();
        Log.e("JSON readMessages", "Params: "+new Gson().toJson(requiredIdMessaage));

        /*ENVIANDO TOKEN EN LA CABECERA*/
        Map<String, String> map = new HashMap<>();
        //map.put("Authorization", "jwt ".concat(Preferences.getToken(myContext)));
        Call<GenericDTO> call = service.readMessages(
                new Gson().toJson(requiredIdMessaage)
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                String msgError=null;
                Log.e("onResponse", call.request().url().toString());
                //stopDialogoWait();
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            Log.e(TAG, "sendChat-> TRY");
                            if(resp.getRaise()!=null)
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            else
                                msgError = (myContext.getResources().getString(R.string.http_error_desconocido));//msgError = resp.getMessage();
                        } catch (IOException | JsonSyntaxException e) {
                            Log.e(TAG, "sendChat-> CATCH");
                            msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                        }
                    } else {
                        Log.e(TAG, "200: "+new Gson().toJson(response.body()));
                        if(bandejaListenerON)
                            bandejaListener.onProcess(null);
                    }
                } else {
                    Log.e(TAG, "addMyFavorite-> ELSE");
                    msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                    //toastMSG(myContext.getResources().getString(R.string.error_desconocido));
                }

                if(msgError!=null) {
                    toastMSG(msgError);
                    if(code==401){
                        gotoMain();
                    } else {
                        if(bandejaListenerON)
                            bandejaListener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                }
                if(bandejaListenerON)
                    bandejaListener.onFailed();
            }
        });

    }

    public void deleteMessages(IdMessages requiredIdMessaage)
    {
        final iReport service = retrofit.create(iReport.class);

        //showDialogWait();
        Log.e("JSON deleteMessages", "Params: "+new Gson().toJson(requiredIdMessaage));

        /*ENVIANDO TOKEN EN LA CABECERA*/
        Map<String, String> map = new HashMap<>();
        //map.put("Authorization", "jwt ".concat(Preferences.getToken(myContext)));
        Call<GenericDTO> call = service.deleteMessages(
                new Gson().toJson(requiredIdMessaage)
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                String msgError=null;
                Log.e("onResponse", call.request().url().toString());
                //stopDialogoWait();
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            Log.e(TAG, "sendChat-> TRY");
                            if(resp.getRaise()!=null)
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            else
                                msgError = (myContext.getResources().getString(R.string.http_error_desconocido));//msgError = resp.getMessage();
                        } catch (IOException | JsonSyntaxException e) {
                            Log.e(TAG, "sendChat-> CATCH");
                            msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                        }
                    } else {
                        Log.e(TAG, "200: "+new Gson().toJson(response.body()));
                        if(bandejaListenerON)
                            bandejaListener.onProcess(null);
                    }
                } else {
                    Log.e(TAG, "addMyFavorite-> ELSE");
                    msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                    //toastMSG(myContext.getResources().getString(R.string.error_desconocido));
                }

                if(msgError!=null) {
                    toastMSG(msgError);
                    if(code==401){
                        gotoMain();
                    } else {
                        if(bandejaListenerON)
                            bandejaListener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                }
                if(bandejaListenerON)
                    bandejaListener.onFailed();
            }
        });

    }

//    public void getUrlCatalogos(final boolean animation)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getUrlCatalogos()");
//
//        Call<UrlsCatalogosDTO> call = service.getUrlCatalogos();
//
//        if(animation)
//            showDialogWait();
//
//        call.enqueue(new Callback<UrlsCatalogosDTO>() {
//            @Override
//            public void onResponse(Call<UrlsCatalogosDTO> call, Response<UrlsCatalogosDTO> response) {
//                if(animation)
//                    stopDialogoWait();
//
//                Log.e(TAG+"onResponse", call.request().url().toString());
//                if (!response.isSuccessful()) {
//                    Log.e(TAG+"onResponse", " : " + new Gson().toJson(response.body()));
//                    ((FullscreenActivity) myContext).errorLoadInitData();
//                } else {
//                    //Toast.makeText(myContext, myContext.getResources().getString(R.string.operacion_exitosa), Toast.LENGTH_LONG).show();
//                    Log.e(TAG+"onResponse", "-> " + new Gson().toJson(response.body()));
//                    ((FullscreenActivity) myContext).responseFileCatalogos(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UrlsCatalogosDTO> call, Throwable t) {
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//
//                if(animation)
//                    stopDialogoWait();
//
//                ((FullscreenActivity) myContext).errorLoadInitData();
//
//
//            }
//        });
//    }

//    public void getBarrios(String id_ciudad, final String tagFragment, final String objectFragment)
//    {
//        final iBarrios service = retrofit.create(iBarrios.class);
//
//        Log.e(TAG, "getBarrios(), city = "+id_ciudad);
//
//        Call<ListBarrioDTO> call = service.obtainBarrios(id_ciudad);
//
//        showDialogWait();
//        call.enqueue(new Callback<ListBarrioDTO>() {
//            @Override
//            public void onResponse(Call<ListBarrioDTO> call, Response<ListBarrioDTO> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//                if (!response.isSuccessful()) {
//                    Log.e(TAG+"onResponse", " : " + new Gson().toJson(response.body()));
//                } else {
//                    Log.e(TAG+"onResponse", "-> " + new Gson().toJson(response.body()));
//                    publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getBarrios()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListBarrioDTO> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void Auth(final RequiredAuth requiredAuth)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON Auth", "Params: "+new Gson().toJson(requiredAuth));
        Call<DataAuth> call = service.auth(
                new Gson().toJson(requiredAuth)
        );

        showDialogWait();
        call.enqueue(new Callback<DataAuth>() {
            @Override
            public void onResponse(Call<DataAuth> call, Response<DataAuth> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            DataAuth resp = new Gson().fromJson(jsonInString, DataAuth.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"onResponse", "-> " + new Gson().toJson(response.body()));
                        ((MainActivity) myContext).successfulAuth(response.body());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null)
                    msgToast(msgError);
            }

            @Override
            public void onFailure(Call<DataAuth> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void notifyForgot(Identy requiredIdenty)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON Forgot", "Params: "+new Gson().toJson(requiredIdenty));
        Call<GenericDTO> call = service.notifyForgot(
                new Gson().toJson(requiredIdenty)
        );

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        ((MainActivity) myContext).successfulNotifyForgot(response.body());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null)
                    msgToast(msgError);
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void validateCode(final RequiredCode requiredCode)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON COde", "Params: "+new Gson().toJson(requiredCode));
        Call<GenericDTO> call = service.validateCode(
                new Gson().toJson(requiredCode)
        );

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());


                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        ((MainActivity) myContext).successfulValidateCode(response.body(), requiredCode.getCodigo());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void refreshTokenFCM(final RequiredRefreshToken requiredRefreshToken)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON tokenFCM", "Params: "+new Gson().toJson(requiredRefreshToken));
        Call<GenericDTO> call = service.refreshToken(
                new Gson().toJson(requiredRefreshToken)
        );

        //showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());


                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        //((MainActivity) myContext).successfulValidateCode(response.body(), requiredCode.getCodigo());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        //gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    //toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void validateNewPwd(RequiredNewPwd requiredNewPwd)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON Password", "Params: "+new Gson().toJson(requiredNewPwd));
        Call<GenericDTO> call = service.validateCode(
                new Gson().toJson(requiredNewPwd)
        );

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        ((MainActivity) myContext).successfulNewPwd(response.body());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public static  final String BROACAST_DATA="broacast_data";

    public void register_main(RequiredRegister_NEW_2018 requiredRegister)
    {
        final iAuth service = retrofit.create(iAuth.class);

        //showDialogWait();
        Log.e("JSON obtainBandeja", "Params: "+ new Gson().toJson(requiredRegister));

        Call<GenericDTO> call = service.vuelveteAsesora(
                new Gson().toJson(requiredRegister)
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                String msgError=null;
                Log.e("onResponse", call.request().url().toString());
                //stopDialogoWait();
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            Log.e(TAG, "sendChat-> TRY");
                            if(resp.getRaise()!=null)
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            else
                                msgError = (myContext.getResources().getString(R.string.http_error_desconocido));//msgError = resp.getMessage();
                        } catch (IOException | JsonSyntaxException e) {
                            Log.e(TAG, "sendChat-> CATCH");
                            msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                        }
                    } else {
                        Log.e(TAG, "200: "+new Gson().toJson(response.body()));
                        if(genericListenerON)
                            genericListener.onProcess(response.body().getResult());
                    }
                } else {
                    Log.e(TAG, "addMyFavorite-> ELSE");
                    msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                    //toastMSG(myContext.getResources().getString(R.string.error_desconocido));
                }

                if(msgError!=null) {
                    toastMSG(msgError);
                    if(code==401){
                        gotoMain();
                    } else {
                        if(genericListenerON)
                            genericListener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    //toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
                if(genericListenerON)
                    genericListener.onFailed();
            }
        });
    }

    public void getCatalogoPremios()
    {
        final iReport service = retrofit.create(iReport.class);

        //showDialogWait();
        Log.e("JSON getCatalogoPremios", "Params: "/*+ new Gson().toJson(requiredRegister)*/);

        Call<CatalogoPremiosList> call = service.obtainCatalogoPremios();

        call.enqueue(new Callback<CatalogoPremiosList>() {
            @Override
            public void onResponse(Call<CatalogoPremiosList> call, Response<CatalogoPremiosList> response) {
                String msgError=null;
                Log.e("onResponse", call.request().url().toString());
                //stopDialogoWait();
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e("LOG", "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            Log.e(TAG, "sendChat-> TRY");
                            if(resp.getRaise()!=null)
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            else
                                msgError = (myContext.getResources().getString(R.string.http_error_desconocido));//msgError = resp.getMessage();
                        } catch (IOException | JsonSyntaxException e) {
                            Log.e(TAG, "sendChat-> CATCH");
                            msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                        }
                    } else {
                        Log.e(TAG, "200: "+new Gson().toJson(response.body()));
                        if(genericListenerON)
                            genericListener.onProcess(new Gson().toJson(response.body()));
                    }
                } else {
                    Log.e(TAG, "addMyFavorite-> ELSE");
                    msgError = (myContext.getResources().getString(R.string.http_error_desconocido));
                    //toastMSG(myContext.getResources().getString(R.string.error_desconocido));
                }

                if(msgError!=null) {
                    toastMSG(msgError);
                    if(code==401){
                        gotoMain();
                    } else {
                        if(genericListenerON)
                            genericListener.onFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<CatalogoPremiosList> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    //toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
                if(genericListenerON)
                    genericListener.onFailed();
            }
        });
    }

    public void register(RequiredRegister requiredRegister, final String tagFragment, final String objectFragment, final String error)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON Password", "Params: "+new Gson().toJson(requiredRegister));
        Call<GenericDTO> call = service.preinscripcion(
                new Gson().toJson(requiredRegister)
        );
        //showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {

                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));
                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                        /*
                        if(mActivity==ACTIVITY_MAIN) {
                            ((MainActivity) myContext).successfulRegister(response.body());
                        } else if(mActivity==ACTIVITY_REGISTER) {
                            ((MenuActivity) myContext).successfulRegister(response.body());
                        }
                        */
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);

                }

                if(msgError!=null) {
                    msgToast(msgError);

                    publishError(tagFragment, error);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                //stopDialogoWait();
                publishError(tagFragment, error);
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void register_new(Register requiredRegister, final String tagFragment, final String objectFragment, final String error)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON Password", "Params: "+new Gson().toJson(requiredRegister));
        Call<GenericDTO> call = service.preinscripcion(
                new Gson().toJson(requiredRegister)
        );
        //showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {

                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));
                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                        /*
                        if(mActivity==ACTIVITY_MAIN) {
                            ((MainActivity) myContext).successfulRegister(response.body());
                        } else if(mActivity==ACTIVITY_REGISTER) {
                            ((MenuActivity) myContext).successfulRegister(response.body());
                        }
                        */
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);

                }

                if(msgError!=null) {
                    msgToast(msgError);

                    publishError(tagFragment, error);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                //stopDialogoWait();
                publishError(tagFragment, error);
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void termins(RequiredTermins requiredTermins, final String tagFragment, final String objectFragment, final String error)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON termins", "Params: "+new Gson().toJson(requiredTermins));
        Call<GenericDTO> call = service.termins(
                new Gson().toJson(requiredTermins)
        );

        //showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));
                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                        /*)
                        //((MainActivity) myContext).successfulTermins(response.body());
                        if(mActivity==ACTIVITY_MAIN) {
                            ((MainActivity) myContext).successfulTermins(response.body());
                        } else if(mActivity==ACTIVITY_REGISTER) {
                            ((MenuActivity) myContext).successfulTermins(response.body());
                        }
                        */
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    publishError(tagFragment, error);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                //stopDialogoWait();
                publishError(tagFragment, error);
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void uploadImage(String filePath, final String tagFragment, final String objectFragment)
    {
        final iFile service = retrofit.create(iFile.class);

        File file = new File(filePath);

        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(filePath));
        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getName()));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        Log.e("JSON", "name: "+name.toString());

        Call<GenericDTO> call = service.uploadImage(
                body/*,
                name*/
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        //((MenuActivity) myContext).successUploadImage(response.body());
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));

                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }
            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    private File convertFileFromBitmap(Bitmap bitmap){
        //create a file to write bitmap data
        try {
            File f = new File(myContext.getCacheDir(), Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            f.createNewFile();
            //Convert bitmap to byte array
            //Bitmap bitmap = bitmap;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return f;
        }catch (IOException e){
            return null;
        }

    }

    public Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


        int scaleFactor = 1;//potencias de 2 (1= mitad , 4 , carta parte, 8 octaba parte)

        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            //scaleFactor = Math.max(photoW/targetW, photoH/targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);

        return bitmap;
    }

    public void terminsGerente(RequiredTerminsGerente requiredTermins, final String tagFragment, final String objectFragment, final String error)
    {
        final iAuth service = retrofit.create(iAuth.class);

        Log.e(TAG+"JSON termins", "Params: "+new Gson().toJson(requiredTermins));
        Call<GenericDTO> call = service.terminsGerente(
                new Gson().toJson(requiredTermins)
        );

        //showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));
                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    publishError(tagFragment, error);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                //stopDialogoWait();
                publishError(tagFragment, error);
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getCampanas(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getCampanas()");

        Call<ListCampana> call = service.obtainCampanas();

        //showDialogWait();
        call.enqueue(new Callback<ListCampana>() {
            @Override
            public void onResponse(Call<ListCampana> call, Response<ListCampana> response) {
                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            ListCampana resp = new Gson().fromJson(jsonInString, ListCampana.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getCampanaList()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getCampanaList()));
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);

                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListCampana> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getPerfilUser(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getPerfilUser()");

        Call<PerfilDTO> call = service.getPerfilUser("");

        //showDialogWait();
        call.enqueue(new Callback<PerfilDTO>() {
            @Override
            public void onResponse(Call<PerfilDTO> call, Response<PerfilDTO> response) {
                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            PerfilDTO resp = new Gson().fromJson(jsonInString, PerfilDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getPerfilList()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getPerfilList().get(0)));
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);

                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<PerfilDTO> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void editarPerfil(DataUser mPerfil, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "editarPerfil()");
        Log.e(TAG+"JSON", ": "+new Gson().toJson(mPerfil));

        Call<DataAuth> call = service.EditPerfil(new Gson().toJson(mPerfil));

        showDialogWait();
        call.enqueue(new Callback<DataAuth>() {
            @Override
            public void onResponse(Call<DataAuth> call, Response<DataAuth> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            DataAuth resp = new Gson().fromJson(jsonInString, DataAuth.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);

                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataAuth> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getDetailCampanas(Campana campana, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getDetailCampanas()");

        Call<ListPanelGte> call = service.obtainPanelGrte(new Gson().toJson(campana));

        showDialogWait();
        call.enqueue(new Callback<ListPanelGte>() {
            @Override
            public void onResponse(Call<ListPanelGte> call, Response<ListPanelGte> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            ListPanelGte resp = new Gson().fromJson(jsonInString, ListPanelGte.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()/*.getListDetail().getPanelGteDetails()*/));
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPanelGte> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getPedidosRetenidos(Identy requiredIdenty, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getPedidosRetenidos()");

        Call<RetenidosDTO> call = service.obtainPedRetenidos(new Gson().toJson(requiredIdenty));

        showDialogWait();
        call.enqueue(new Callback<RetenidosDTO>() {
            @Override
            public void onResponse(Call<RetenidosDTO> call, Response<RetenidosDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            RetenidosDTO resp = new Gson().fromJson(jsonInString, RetenidosDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getListTitleRetenidos().getRetenidoList()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<RetenidosDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

//    public void getCupoSaldoCOnf(Identy requiredIdenty, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getCupoSaldoCOnf()");
//
//        Call<ListCupoSaldoConf> call = service.obtainCupoSaldoConf(new Gson().toJson(requiredIdenty));
//
//        showDialogWait();
//        call.enqueue(new Callback<ListCupoSaldoConf>() {
//            @Override
//            public void onResponse(Call<ListCupoSaldoConf> call, Response<ListCupoSaldoConf> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListCupoSaldoConf resp = new Gson().fromJson(jsonInString, ListCupoSaldoConf.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getCupoSaldoConfList().get(0)));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListCupoSaldoConf> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void getListaPre(ListPre requiredListPre, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getCupoSaldoCOnf()");
//
//        Call<ListPreinscripcionDTO> call = service.obtainListPre(
//                requiredListPre.getPerfil(),
//                requiredListPre.getIndex_pages(),
//                requiredListPre.getValor(),
//                requiredListPre.getToken());
//
////        showDialogWait();
//        call.enqueue(new Callback<ListPreinscripcionDTO>() {
//            @Override
//            public void onResponse(Call<ListPreinscripcionDTO> call, Response<ListPreinscripcionDTO> response) {
////                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListPreinscripcionDTO resp = new Gson().fromJson(jsonInString, ListPreinscripcionDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListPreinscripcionDTO> call, Throwable t) {
////                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void validateReferido(ValidateRef requiredValidateRef, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "validateReferido()");
//
//        Call<GenericDTO> call = service.validateReferido(new Gson().toJson(requiredValidateRef));
//
//        showDialogWait();
//        call.enqueue(new Callback<GenericDTO>() {
//            @Override
//            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, response.body().getResult());
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GenericDTO> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void aprobarPreinscriccion(ApprovePreIns requiredApprovePreIns, final String tagFragment, final String objectFragment, final String error)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "aprobarPreinscriccion() "+new Gson().toJson(requiredApprovePreIns));
//
//        Call<GenericDTO> call = service.aprobarPreinscripcion(new Gson().toJson(requiredApprovePreIns));
//
//        //showDialogWait();
//        call.enqueue(new Callback<GenericDTO>() {
//            @Override
//            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
//                //stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, response.body().getResult());
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    publishError(tagFragment, error);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GenericDTO> call, Throwable t) {
//                //stopDialogoWait();
//                publishError(tagFragment, error);
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void validateCentralRiesgo(Identy requiredIdenty, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "validateReferido()");

        Call<GenericDTO> call = service.validateCentralRiesgo(new Gson().toJson(requiredIdenty));

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

//    public void inscribir_NEW(InscriptionDTO requiredInscription, final String tagFragment, final String objectFragment, final String error)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "inscribir(): "+new Gson().toJson(requiredInscription));
//
//        Call<GenericDTO> call = service.inscribir(new Gson().toJson(requiredInscription));
//
//        //showDialogWait();
//        call.enqueue(new Callback<GenericDTO>() {
//            @Override
//            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
//                //stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    publishError(tagFragment, error);
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GenericDTO> call, Throwable t) {
//                //stopDialogoWait();
//                publishError(tagFragment, error);
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }
//
//
//    public void inscribir(RequiredInscription requiredInscription, final String tagFragment, final String objectFragment, final String error)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "inscribir(): "+new Gson().toJson(requiredInscription));
//
//        Call<GenericDTO> call = service.inscribir(new Gson().toJson(requiredInscription));
//
//        //showDialogWait();
//        call.enqueue(new Callback<GenericDTO>() {
//            @Override
//            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
//                //stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    publishError(tagFragment, error);
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GenericDTO> call, Throwable t) {
//                //stopDialogoWait();
//                publishError(tagFragment, error);
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void getDataEditInscripcion(Identy requiredIdenty, final String tagFragment, final String objectFragment, final String error)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getDataEditInscripcion(): "+new Gson().toJson(requiredIdenty));
//
//        Call<InscriptionDTO> call = service.editInscription(new Gson().toJson(requiredIdenty));
//
//        //showDialogWait();
//        call.enqueue(new Callback<InscriptionDTO>() {
//            @Override
//            public void onResponse(Call<InscriptionDTO> call, Response<InscriptionDTO> response) {
//                //stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ResponseGeneric resp = new Gson().fromJson(jsonInString, ResponseGeneric.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    publishError(tagFragment, error);
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InscriptionDTO> call, Throwable t) {
//                //stopDialogoWait();
//                publishError(tagFragment, error);
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    toastMSG(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void getPanelAsesora(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getPanelAsesora()");

        Call<PanelAsesoraDTO> call = service.obtainPanelAsesora();

        showDialogWait();
        call.enqueue(new Callback<PanelAsesoraDTO>() {
            @Override
            public void onResponse(Call<PanelAsesoraDTO> call, Response<PanelAsesoraDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            PanelAsesoraDTO resp = new Gson().fromJson(jsonInString, PanelAsesoraDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getPanelAsesora()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<PanelAsesoraDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }



    public void getFaltantes(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getFaltante()");

        Call<FaltantesDTO> call = service.obtainFaltantes();

        showDialogWait();
        call.enqueue(new Callback<FaltantesDTO>() {
            @Override
            public void onResponse(Call<FaltantesDTO> call, Response<FaltantesDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            FaltantesDTO resp = new Gson().fromJson(jsonInString, FaltantesDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<FaltantesDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getRedimirPremios(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getRedimirPremios()");

        Call<RedimirDTO> call = service.obtainRedimirIncentivos(
                new Gson().toJson(new Identy("")),
                new Gson().toJson(new Index("1"))
        );

        showDialogWait();
        call.enqueue(new Callback<RedimirDTO>() {
            @Override
            public void onResponse(Call<RedimirDTO> call, Response<RedimirDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            RedimirDTO resp = new Gson().fromJson(jsonInString, RedimirDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<RedimirDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void redimirPremios(RedimirPremios requiredRedimirPremios, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "redimirPremios(): "+new Gson().toJson(requiredRedimirPremios));

        Call<GenericDTO> call = service.redimirPremios(new Gson().toJson(requiredRedimirPremios));

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getPuntosAsesora(Identy requiredIdenty, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getPuntosAsesora(), requiredIdenty: "+new Gson().toJson(requiredIdenty));

        Call<PuntosAsesoraDTO> call = service.obtainPuntosAsesora(new Gson().toJson(requiredIdenty));

        showDialogWait();
        call.enqueue(new Callback<PuntosAsesoraDTO>() {
            @Override
            public void onResponse(Call<PuntosAsesoraDTO> call, Response<PuntosAsesoraDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            PuntosAsesoraDTO resp = new Gson().fromJson(jsonInString, PuntosAsesoraDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<PuntosAsesoraDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void getIncentivosReferidos(Identy requiredIdenty, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getIncentivosReferidos()");

        Call<ReferidosDTO> call = service.obtainIncentivosReferido(
                new Gson().toJson(requiredIdenty),
                new Gson().toJson(new Index("1")));

        showDialogWait();
        call.enqueue(new Callback<ReferidosDTO>() {
            @Override
            public void onResponse(Call<ReferidosDTO> call, Response<ReferidosDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            ReferidosDTO resp = new Gson().fromJson(jsonInString, ReferidosDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReferidosDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

//    public void getCDR(Identy requiredIdenty, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getIncentivosReferidos()");
//
//        Call<ListCDR> call = service.obtainCDR(
//                new Gson().toJson(requiredIdenty),
//                new Gson().toJson(new Index("1")));
//
//        showDialogWait();
//        call.enqueue(new Callback<ListCDR>() {
//            @Override
//            public void onResponse(Call<ListCDR> call, Response<ListCDR> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListCDR resp = new Gson().fromJson(jsonInString, ListCDR.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListCDR> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }


//    public void getPQR(Identy requiredIdenty, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getIncentivosReferidos()");
//
//        Call<ListPQR> call = service.obtainPQR(
//                new Gson().toJson(requiredIdenty),
//                new Gson().toJson(new Index("1")));
//
//        showDialogWait();
//        call.enqueue(new Callback<ListPQR>() {
//            @Override
//            public void onResponse(Call<ListPQR> call, Response<ListPQR> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListPQR resp = new Gson().fromJson(jsonInString, ListPQR.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListPQR> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void getFactura(Identy requiredIdenty, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getFactura()");
//
//        Call<ListFactura> call = service.obtainFacturas(
//                new Gson().toJson(requiredIdenty)
//        );
//
//        showDialogWait();
//        call.enqueue(new Callback<ListFactura>() {
//            @Override
//            public void onResponse(Call<ListFactura> call, Response<ListFactura> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListFactura resp = new Gson().fromJson(jsonInString, ListFactura.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListFactura> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

//    public void getPagosRealizados(Identy requiredIdenty, final String tagFragment, final String objectFragment)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getFactura()");
//
//        Call<ListPagosRealizados> call = service.obtainPagos(
//                new Gson().toJson(requiredIdenty)
//        );
//
//        showDialogWait();
//        call.enqueue(new Callback<ListPagosRealizados>() {
//            @Override
//            public void onResponse(Call<ListPagosRealizados> call, Response<ListPagosRealizados> response) {
//                stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ListPagosRealizados resp = new Gson().fromJson(jsonInString, ListPagosRealizados.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ListPagosRealizados> call, Throwable t) {
//                stopDialogoWait();
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void getEstadoPedidos(Identy requiredIdenty, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getEstadoPedidos()");

        Call<EstadoPedidoDTO> call = service.obtainEstadoPedido(
                new Gson().toJson(requiredIdenty)
        );

        showDialogWait();
        call.enqueue(new Callback<EstadoPedidoDTO>() {
            @Override
            public void onResponse(Call<EstadoPedidoDTO> call, Response<EstadoPedidoDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            EstadoPedidoDTO resp = new Gson().fromJson(jsonInString, EstadoPedidoDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {

                        Log.e(TAG + "JSON response", ": " + "111111111");

                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body().getResult().getProductos()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<EstadoPedidoDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void confirmarPedidoById(RequiredConfirmaPedido requiredConfirmaPedido, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "confirmarPedidoById()");

        Call<GenericDTO> call = service.confirmarPedido(
                new Gson().toJson(requiredConfirmaPedido)
        );

        showDialogWait();
        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public static final String CODE_DEBAJJO_MONTO="E001";
    public static String CODE_OK="S001";
    public void liquidarPedido(final LiquidarSend requiredLiquidar, final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "liquidarPedido(): "+new Gson().toJson(requiredLiquidar));

        Call<LiquidarDTO> call = service.liquidarPedido(
                new Gson().toJson(requiredLiquidar)
        );

        showDialogWait();
        call.enqueue(new Callback<LiquidarDTO>() {
            @Override
            public void onResponse(Call<LiquidarDTO> call, Response<LiquidarDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            LiquidarDTO resp = new Gson().fromJson(jsonInString, LiquidarDTO.class);

                            if(code==404 && resp.getCodigo()!=null) {
                                if(resp.getCodigo().equals(CODE_DEBAJJO_MONTO)){
                                    //msgError = resp.getMensaje();
                                    NumberFormat formatter = NumberFormat.getInstance(Locale.US);

//                                    ((MenuActivity) myContext).snackBar
//                                            (
//                                            "Total: "
//                                            .concat("$".concat(formatter.format(Float.parseFloat(resp.getTotal_pedido()))))
//                                            .concat(". ")
//                                            .concat(resp.getMensaje())
//                                            );
                                }
                            } else {
                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                            }

                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {

                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<LiquidarDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

//    public void getProductosCatalogo(final boolean animation)
//    {
//        final iReport service = retrofit.create(iReport.class);
//
//        Log.e(TAG, "getProductosCatalogo()");
//
//        Call<ProductCatalogoDTO> call = service.obtainProductos();
//
//        if(animation)
//            showDialogWait();
//        call.enqueue(new Callback<ProductCatalogoDTO>() {
//            @Override
//            public void onResponse(Call<ProductCatalogoDTO> call, Response<ProductCatalogoDTO> response) {
//                if(animation)
//                    stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//
//
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            //Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            ProductCatalogoDTO resp = new Gson().fromJson(jsonInString, ProductCatalogoDTO.class);
//
//                            if(code==404){
//                                ((FullscreenActivity) myContext).responseCatalogo(resp.getResult());
//                            }else {
//                                msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                            }
//
//                        } catch (IOException | JsonSyntaxException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
//                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
//                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
//                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
//                        } else {
//                            //publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
//                            ((FullscreenActivity) myContext).responseCatalogo(response.body().getResult());
//                        }
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    ((FullscreenActivity) myContext).errorLoadInitData();
//                    //msgToast(msgError);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ProductCatalogoDTO> call, Throwable t) {
//                if(animation)
//                    stopDialogoWait();
//
//                ((FullscreenActivity) myContext).errorLoadInitData();
//
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void getProductosCatalogo_broadcast(final String tagFragment, final String objectFragment)
    {
        final iReport service = retrofit.create(iReport.class);

        Log.e(TAG, "getProductosCatalogo()");

        Call<ProductCatalogoDTO> call = service.obtainProductos();

        showDialogWait();
        call.enqueue(new Callback<ProductCatalogoDTO>() {
            @Override
            public void onResponse(Call<ProductCatalogoDTO> call, Response<ProductCatalogoDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            ProductCatalogoDTO resp = new Gson().fromJson(jsonInString, ProductCatalogoDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        Log.e(TAG + "JSON response", ": " + new Gson().toJson(response.body()));
                        //((MenuActivity) myContext).responseHttpCampana(response.body().getCampanaList());
                        if (response.body().getCode() == 404) {//un detalle envia 200 con error 404
                            msgError = response.body().getRaise().get(0).getField().concat(". ").concat(response.body().getRaise().get(0).getError());
                        } else {
                            publishResultRegister(tagFragment, objectFragment, new Gson().toJson(response.body().getResult()));
                        }
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductCatalogoDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    public void downloadFile(final String tagFragment, final String objectFragment, final String error, String urlFile, final String fileName, final String fileDirectory){

        iFile service = retrofit.create(iFile.class);

        final ProgressDialogHorizontal dialogHorizontal = new ProgressDialogHorizontal(myContext);
        dialogHorizontal.showProgressDialog("Descargando archivo");

        service.getFile(urlFile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.message());
                if(!response.isSuccessful()){
                    Log.e(TAG, "Something's gone wrong");
                    return;
                }

                DownloadFileAsyncTask downloadFileAsyncTask = new DownloadFileAsyncTask(myContext, tagFragment, objectFragment, error, dialogHorizontal, fileName, fileDirectory);
                downloadFileAsyncTask.execute(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
                publishError(tagFragment, error);
                dialogHorizontal.dismissProgressDialog();
            }
        });
    }

    private void msgToast(String msg){
        Log.e("onError", msg);
        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show();
    }

    private void showDialogWait(){
        MyDialoges.showProgressDialog(myContext, myContext.getResources().getString(R.string.msg_espere));
    }

    public static void stopDialogoWait(){
        MyDialoges.dismissProgressDialog();
    }

    /**
     * BROADCAST
     * @param tagFragment
     * @param objectFragment
     * @param data
     */
    private void publishResultRegister(String tagFragment, String objectFragment, String data){
        Log.i(TAG,"publishResult: "+data);
        LocalBroadcastManager.getInstance(myContext).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, objectFragment)
                        .putExtra(BROACAST_DATA, data));
    }

    /////////////////// ENLACES ENTREA FRAGMENTOS Y ACTIVITIES /////////////////////
    public void enlaceLoadCamera(String tagFragment, String objectFragment){
        ((MenuActivity) myContext).takeImage(tagFragment, objectFragment).show();
    }
    /////////////////// ENLACES ENTREA FRAGMENTOS Y ACTIVITIES /////////////////////

    File file;
    public class fileFromBitmap extends AsyncTask<Void, Integer, String> {

        Context context;
        Bitmap bitmap;
        //String path_external = Environment.getExternalStorageDirectory() + File.separator + "dupree_temporary_file.jpg";

        public fileFromBitmap(Bitmap bitmap, Context context) {
            this.bitmap = bitmap;
            this.context= context;
            //Log.e("JSON", "11111111111: "+new Gson().toJson(filePath));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before executing doInBackground
            // update your UI
            // exp; make progressbar visible
        }

        @Override
        protected String doInBackground(Void... params) {

            //Log.e("JSON", "22222222: "+new Gson().toJson(filePath));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            //si la quieres en discofile = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            file = new File(myContext.getCacheDir() + "dupree_temporary_file.jpg");//en cache
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // back to main thread after finishing doInBackground
            // update your UI or take action after
            // exp; make progressbar gone

            //sendFile(file);
            uploadImage_file2(file);

        }
    }

    String filePath,  tagFragment, objectFragment;
    public void uploadImage_file(String filePath, final String tagFragment, final String objectFragment)
    {
        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(filePath));
        this.filePath=filePath;
        this.tagFragment=tagFragment;
        this.objectFragment=objectFragment;

        new fileFromBitmap(resizeBitmap(filePath, 960, 1200), myContext.getApplicationContext()).execute();
    }

    public void uploadImage_bitmap(Bitmap fileBitmap, final String tagFragment, final String objectFragment)
    {
        //Log.e("JSON", "uploadChatImage: "+new Gson().toJson(filePath));
        //this.filePath=filePath;
        this.tagFragment=tagFragment;
        this.objectFragment=objectFragment;

        new fileFromBitmap(fileBitmap, myContext.getApplicationContext()).execute();
    }

    public void uploadImage_file2(File file)
    {
        final iFile service = retrofit.create(iFile.class);
        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getPath()));
        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getName()));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
        //RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        //Log.e("JSON", "name: "+name.toString());

        Call<GenericDTO> call = service.uploadImage(
                body/*,
                name*/
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
                stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        //((MenuActivity) myContext).successUploadImage(response.body());
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));

                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    if(code==501){
                        gotoMain();
                    }
                }
            }
            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                stopDialogoWait();
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

//    public void uploadImage_File(File file, final String tagFragment, final String objectFragment, final String error)
//    {
//        //showDialogWait();//solo lo muestra en la primera imagen que se sube
//        final iFile service = retrofit.create(iFile.class);
//
//        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getPath()));
//        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getName()));
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
//        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
//
//        Log.e("JSON", "name: "+name.toString());
//
//        Call<GenericDTO> call = service.uploadImage(
//                body/*,
//                name*/
//        );
//
//        call.enqueue(new Callback<GenericDTO>() {
//            @Override
//            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {
//
//                //stopDialogoWait();
//                Log.e(TAG+"onResponse", call.request().url().toString());
//
//                String msgError=null;
//                int code=response.code();
//                Log.e("code", String.valueOf(code) );
//                if(code==200 || code==400 || code==401 || code==404 || code==501) {
//                    if (!response.isSuccessful()) {
//                        try {
//                            String jsonInString = response.errorBody().string();
//                            Log.e(TAG, "Retrofit Response : " + jsonInString);
//                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);
//
//                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
//                        } catch (IOException e) {
//                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                        }
//                    } else {
//                        //((MenuActivity) myContext).successUploadImage(response.body());
//                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));
//
//                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
//                    }
//                } else {
//                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
//                }
//
//                if(msgError!=null) {
//                    msgToast(msgError);
//                    publishError(tagFragment, error);
//                    if(code==501){
//                        gotoMain();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<GenericDTO> call, Throwable t) {
//                //stopDialogoWait();
//                publishError(tagFragment, error);
//                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
//                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
//                }
//            }
//        });
//
//    }

    public void uploadImage_Profile(File file, final String tagFragment, final String objectFragment, final String error)
    {
        //showDialogWait();//solo lo muestra en la primera imagen que se sube
        final iFile service = retrofit.create(iFile.class);

        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getPath()));
        Log.e("JSON", "uploadChatImage: "+new Gson().toJson(file.getName()));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imagen", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        Log.e("JSON", "name: "+name.toString());

        Call<GenericDTO> call = service.uploadImageProfile(
                body/*,
                name*/
        );

        call.enqueue(new Callback<GenericDTO>() {
            @Override
            public void onResponse(Call<GenericDTO> call, Response<GenericDTO> response) {

                //stopDialogoWait();
                Log.e(TAG+"onResponse", call.request().url().toString());

                String msgError=null;
                int code=response.code();
                Log.e("code", String.valueOf(code) );
                if(code==200 || code==400 || code==401 || code==404 || code==501) {
                    if (!response.isSuccessful()) {
                        try {
                            String jsonInString = response.errorBody().string();
                            Log.e(TAG, "Retrofit Response : " + jsonInString);
                            GenericDTO resp = new Gson().fromJson(jsonInString, GenericDTO.class);

                            msgError = resp.getRaise().get(0).getField().concat(". ").concat(resp.getRaise().get(0).getError());
                        } catch (IOException e) {
                            msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                        }
                    } else {
                        //((MenuActivity) myContext).successUploadImage(response.body());
                        Log.e(TAG+"JSON response", ": "+new Gson().toJson(response.body().getResult()));

                        publishResultRegister(tagFragment, objectFragment, response.body().getResult());
                    }
                } else {
                    msgError = myContext.getResources().getString(R.string.http_error_desconocido);
                }

                if(msgError!=null) {
                    msgToast(msgError);
                    publishError(tagFragment, error);
                    if(code==501){
                        gotoMain();
                    }
                }
            }
            @Override
            public void onFailure(Call<GenericDTO> call, Throwable t) {
                //stopDialogoWait();
                publishError(tagFragment, error);
                if(checkIdDataMovileAvailable(call.request().url().toString(), t)){
                    msgToast(myContext.getResources().getString(R.string.http_error_desconocido));
                }
            }
        });

    }

    private void publishError(String tagFragment, String error){
        Log.i(TAG,"publishResult: "+error);
        LocalBroadcastManager.getInstance(myContext).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, error)
                        .putExtra(BROACAST_DATA, error));
    }

    private void gotoMain(){
        mPreferences.cerrarSesion(myContext);
        myContext.startActivity(new Intent(myContext, MainActivity.class));
        if(((MenuActivity)myContext)!=null){
            ((MenuActivity)myContext).finish();
        }
    }

    private void toastMSG(String msg){
        Log.e(TAG, "toast, "+msg);
        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkIdDataMovileAvailable(String url, Throwable t){
        Log.e(TAG+"onFailure", url);
        Log.e(TAG+"onFailure", t.getMessage());

        boolean noDataAvailable = (t instanceof NoConnectivityException);
        if(noDataAvailable){
            msgToast(myContext.getResources().getString(R.string.http_datos_no_disponibles));
        }
        return !noDataAvailable;
    }
}
