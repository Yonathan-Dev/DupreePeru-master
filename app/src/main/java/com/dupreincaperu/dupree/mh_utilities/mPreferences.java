package com.dupreincaperu.dupree.mh_utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dupreeinca.lib_api_rest.model.dto.response.BannerDTO;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreeinca.lib_api_rest.model.dto.response.UrlsCatalogosDTO;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

/*
 * Created by Marwuin on 22/3/2017.
 */

public class mPreferences {

    private static String TAG = "Preferences-->";
    private static final String IS_NOT_NEW_APP_ON = "isNewApp";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String IS_CHANGE_CAMPANA = "isChangeCampana";
    private static final String IS_UPDATE_BANNER_AND_PDF = "isUpdateBannerAndPDF";

    private static final String JSON_URL_CATALOGOS = "json_url_catalogos";

    private static final String JSON_IMG_BANNER = "json_banner";
    private static final String JSON_TYPO_VIA = "json_tipo_via";
    private static final String JSON_DPTO = "json_dpto";
    private static final String IMG_CATALOGO = "img_catalogo";

    private static final String COOKIES_AUTH = "cookies_auth";

    private static final String JSON_PERFIL = "perfil";
    private static final String CODE_PWD = "code_pwd";

    private static final String JSON_CAMPANA = "json_campana";
    private static final String JSON_DETAIL_CAMPANA = "json_detail_campana";

    private static final String JSON_PERFIL_USER = "json_perfil_USER";

    private static final String TOKEN_SESION = "token_sesion";

    private static final String CAMPANA_ACTUAL = "campana_actual";

    private static final String JSON_PAQUETONES = "json_paquetones";

    private static final String PATH_FILE_DOWNLOADED = "path_files_downloaded";

    private static final String URL_EMBEDDED_CHAT = "url_embedded_chat";

    private static final String TOKEN_FIREBASE = "token_firebase";

    private static final String VERSION_CATALOGO = "version_catalogo";


    /**
     * Guardar valores array
     * @param key
     * @param value
     * @param context
     */
    private static void setStringSet(String key, Set<String> value, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * Recuperar valores array
     * @param key
     * @param context
     * @return
     */
    private static Set<String> getStringSet(String key, Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(key, new HashSet<String>());
    }

    /**
     * Asignar String
     * @param key
     * @param value
     * @param context
     */
    private static void setString(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Asignat booleano
     * @param key
     * @param value
     * @param context
     */
    private static void setBoolean(String key, boolean value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Asignar entero
     * @param key
     * @param value
     * @param context
     */
    private static void setInteger(String key, int value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Obtener booleano
     * @param key
     * @param context
     * @return
     */
    private static Boolean getBoolean(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    /**
     * Obtener String
     * @param key
     * @param context
     * @return
     */
    private static String getString(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    /**
     * Obtener entero
     * @param key
     * @param context
     * @return
     */
    private static int getInteger(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public static Boolean getIsNotNewAppOn(Context mycontext){
        return getBoolean(IS_NOT_NEW_APP_ON,mycontext);
    }

    //Para controlar si hay un usuario logueado
    public static void setLoggedIn(Context mycontext, Boolean status){
        setBoolean(IS_LOGGED_IN,status,mycontext);
    }
    public static Boolean isLoggedIn(Context mycontext){
        return getBoolean(IS_LOGGED_IN,mycontext);
    }

    //Para controlar si hay un usuario logueado
    public static void setTokenFirebase(Context mycontext, String token){
        setString(TOKEN_FIREBASE,token,mycontext);
    }
    public static String getTokenFirebase(Context mycontext){
        return getString(TOKEN_FIREBASE,mycontext);
    }

    //Para controlar la version del catalogo
    public static void setVersionCatalogo(Context mycontext, String version){
        setString(VERSION_CATALOGO,version,mycontext);
    }
    public static String getVersionCatalogo(Context mycontext){
        return getString(VERSION_CATALOGO,mycontext);
    }

    //Para controlar si cambio la campaña
    public static void setChangeCampana(Context mycontext, Boolean status){
        setBoolean(IS_CHANGE_CAMPANA,status,mycontext);
    }
    public static Boolean isChangeCampanaON(Context mycontext){
        return getBoolean(IS_CHANGE_CAMPANA,mycontext);
    }

    public static void setUpdateBannerAndPDF(Context mycontext, Boolean status){
        setBoolean(IS_UPDATE_BANNER_AND_PDF,status,mycontext);
    }
    public static Boolean isUpdateOnly_BannerAndPFD_ON(Context mycontext){
        return getBoolean(IS_UPDATE_BANNER_AND_PDF,mycontext);
    }

    //Guardar toda la data inicial
    public static void setDataInit(Context ctxt, BannerDTO responseBanner, UrlsCatalogosDTO responseUrlCatalogos){
        Log.e(TAG, "setDataInit");
        setBoolean(IS_NOT_NEW_APP_ON,true,ctxt);
        setString(JSON_IMG_BANNER, new Gson().toJson(responseBanner.getBanner().get(0).getResoluction2()),ctxt);
        setString(JSON_DPTO, new Gson().toJson(responseBanner.getDepartamentos()),ctxt);
        setString(JSON_TYPO_VIA, new Gson().toJson(responseBanner.getTipo_via()),ctxt);
        setString(IMG_CATALOGO, responseBanner.getImg_catalogo(),ctxt);
        setString(URL_EMBEDDED_CHAT, responseBanner.getUrl_chat(), ctxt);
        setString(JSON_URL_CATALOGOS, new Gson().toJson(responseUrlCatalogos),ctxt);
    }

    //get urlchat
    public static String getUrlEmbeddedChat(Context ctx){
        return getString(URL_EMBEDDED_CHAT,ctx);
    }
    //get images banner
    public static String getJSONImageBanner(Context mycontext){
        return getString(JSON_IMG_BANNER,mycontext);
    }

    //get images banner
    public static String getJSON_UrlCatalodos(Context mycontext){
        return getString(JSON_URL_CATALOGOS,mycontext);
    }

    //get images catalogo
    public static String getImageCatalogo(Context mycontext){
        Log.e(TAG, "getImageCatalogo: "+getString(IMG_CATALOGO,mycontext));
        return getString(IMG_CATALOGO,mycontext);
    }

    //get departamento
    public static String getDpto(Context mycontext){
        return getString(JSON_DPTO,mycontext);
    }

    public static Set<String> getCookiesAuth(Context mycontext){
        return  getStringSet(COOKIES_AUTH, mycontext);
    }

    public static void setCookiesAuth(Set<String> value, Context mycontext){
        setStringSet(COOKIES_AUTH, value, mycontext);
    }

    //Guardar toda la data inicial
    public static void setJSON_TypePerfil(Context ctxt, Profile perfil){
        Log.e(TAG, "setPerfil");
        setString(JSON_PERFIL, new Gson().toJson(perfil),ctxt);
    }

    //get images banner
    public static String getJSON_TypePerfil(Context mycontext){
        return getString(JSON_PERFIL,mycontext);
    }

    //Guardar code sms
    public static void setCodeSMS(Context ctxt, String codeSMS){
        Log.e(TAG, "setCodeSMS");
        setString(CODE_PWD, codeSMS,ctxt);
    }

    //get images banner
    public static String getCodeSMS(Context mycontext){
        return getString(CODE_PWD,mycontext);
    }

    public static void setPathFiles(Context ctxt, String pathFiles){
        Log.e(TAG, "setCodeSMS");
        setString(PATH_FILE_DOWNLOADED, pathFiles,ctxt);
    }

    //get images banner
    public static String getPathFiles(Context mycontext){
        return getString(PATH_FILE_DOWNLOADED,mycontext);
    }

    //get images banner
    public static String getJSON_Campana(Context mycontext){
        return getString(JSON_CAMPANA,mycontext);
    }

    //Guardar code sms
    public static void setJASON_Paquetones(Context ctxt, String jsonPaquetones){
        Log.e(TAG, "setJASON_Paquetones");
        setString(JSON_PAQUETONES, jsonPaquetones,ctxt);
    }

    //get images banner
    public static String getJSON_DetailCampana(Context mycontext){
        return getString(JSON_DETAIL_CAMPANA,mycontext);
    }

    //get images banner
    public static String getJSON_PerfilUser(Context mycontext){
        return getString(JSON_PERFIL_USER,mycontext);
    }

    //Guardar code sms
    public static void setTokenSesion(String jsonCampana, Context ctxt){
        Log.e(TAG, "TOKEN_SESION");
        setString(TOKEN_SESION, jsonCampana,ctxt);
    }

    //get images banner
    public static String getTokenSesion(Context mycontext){
        return getString(TOKEN_SESION,mycontext);
    }

    public static void setCampanaActual(String jsonCampana, Context ctxt){
        Log.e(TAG, "CAMPANA_ACTUAL");
        setString(CAMPANA_ACTUAL, jsonCampana,ctxt);
    }

    //get images banner
    public static String getCampanaActual(Context mycontext){
        return getString(CAMPANA_ACTUAL,mycontext);
    }

    public static void cerrarSesion(Context ctxt){
        Log.e(TAG, "cerrarSesion");
        setString(JSON_PERFIL, null, ctxt);
        setLoggedIn(ctxt, false);
    }

}
