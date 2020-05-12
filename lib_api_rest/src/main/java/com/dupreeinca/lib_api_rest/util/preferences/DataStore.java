package com.dupreeinca.lib_api_rest.util.preferences;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.google.gson.Gson;

import java.util.Set;


/**
 * Created by marwuinh@gmail.com on 08/11/2017.
 */

public class DataStore {
    private static String TAG = DataStore.class.getSimpleName();

    private static DataStore singleton;
    private static SharedPreferencesUtil preferences;

    public static synchronized DataStore getInstance(Context context) {
        if (singleton == null) {
            singleton = new DataStore(context);
        }
        return singleton;
    }

    public DataStore(Context context) {
        preferences = SharedPreferencesUtil.getInstance(context);
        if (TextUtils.isEmpty(getDeviceID())) {
            setDeviceID(Guid.NewGuid().ToString());
        }
    }
    public void setCampaniaCierre(String value){
        preferences.setPreferences(BundlePreferences.CAMPANIA_CIERRE.getKeyPreference(), value);
    }

    public String getCampaniaCierre(){
        return preferences.getPreferencesString(BundlePreferences.CAMPANIA_CIERRE.getKeyPreference());
    }

    public String getDeviceID() {
        return preferences.getPreferencesString(BundlePreferences.DEVICE_ID.getKeyPreference());
    }


    public void setDeviceID(String value) {
        preferences.setPreferences(BundlePreferences.DEVICE_ID.getKeyPreference(), value);
    }

    public void setCookiesAuth(Set<String> cookies){
        preferences.setPreferences(BundlePreferences.COOKIES_AUTH.getKeyPreference(), cookies);
    }

    public Set<String> getCookiesAuth(){
        return preferences.getStringSet(BundlePreferences.COOKIES_AUTH.getKeyPreference());
    }

    public void setTokenSesion(String token){
        preferences.setPreferences(BundlePreferences.TOKEN_SESION.getKeyPreference(), token);
    }

    public String getTokenSesion(){
        return preferences.getPreferencesString(BundlePreferences.TOKEN_SESION.getKeyPreference());
    }

    public String getJSON_TypePerfil(){
        return preferences.getPreferencesString(BundlePreferences.JSON_PERFIL.getKeyPreference());
    }

    public Profile getTipoPerfil(){

        String jsonPerfil = preferences.getPreferencesString(BundlePreferences.JSON_PERFIL.getKeyPreference());
        Log.e(TAG, "jsonPerfil: "+jsonPerfil);
        if(jsonPerfil!=null) {
            return new Gson().fromJson(jsonPerfil, Profile.class);
        }
        return null;
    }

    public void setJSON_TypePerfil(String value) {
        preferences.setPreferences(BundlePreferences.JSON_PERFIL.getKeyPreference(), value);
    }

    public void setLoggedIn(boolean value){
        preferences.setPreferences(BundlePreferences.IS_LOGGED_IN.getKeyPreference(), value);
    }

    public boolean isLoggedIn(){
        return preferences.getPreferencesBoolean(BundlePreferences.IS_LOGGED_IN.getKeyPreference());
    }

    public void setPathFiles(String pathFiles){
        preferences.setPreferences(BundlePreferences.PATH_FILE_DOWNLOADED.getKeyPreference(), pathFiles);
    }

    //get images banner
    public String getPathFiles(){
        return preferences.getPreferencesString(BundlePreferences.PATH_FILE_DOWNLOADED.getKeyPreference());
    }

    //get images banner
    public String getJSON_UrlCatalodos(){
        return preferences.getPreferencesString(BundlePreferences.JSON_URL_CATALOGOS.getKeyPreference());
    }


    //get images banner
    public String getJSON_Campana(){
        return preferences.getPreferencesString(BundlePreferences.JSON_CAMPANA.getKeyPreference());
    }



    public String getCampanaActual(){
        return preferences.getPreferencesString(BundlePreferences.CAMPANA_ACTUAL.getKeyPreference());
    }

    public void setCampanaActual(String data){
        preferences.setPreferences(BundlePreferences.CAMPANA_ACTUAL.getKeyPreference(), data);
    }

    public String getCampanaActualPre(){
        return preferences.getPreferencesString(BundlePreferences.CAMPANA_ACTUALPRE.getKeyPreference());
    }

    public void setCampanaActualPre(String data){
        preferences.setPreferences(BundlePreferences.CAMPANA_ACTUALPRE.getKeyPreference(), data);
    }


    //Para controlar si cambio la campaña
    public void setChangeCampana(Boolean status){
        preferences.setPreferences(BundlePreferences.IS_CHANGE_CAMPANA.getKeyPreference(), status);
    }
    public static Boolean isChangeCampanaON(Context mycontext){
        return preferences.getPreferencesBoolean(BundlePreferences.IS_CHANGE_CAMPANA.getKeyPreference());
    }

    public void cerrarSesion(){
        Log.e(TAG, "cerrarSesion");
        setJSON_TypePerfil(null);
        setLoggedIn(false);
    }

}
