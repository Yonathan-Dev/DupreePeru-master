package com.dupreeinca.lib_api_rest.util.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by marwuinh@gmail.com on 30/10/17.
 */

public class SharedPreferencesUtil {

    private static final String KEY_SHARED_PREFERENCES = "declare-it.app.technifiser.com";
    private SharedPreferences preferences;
    private static SharedPreferencesUtil instance = null;

    public static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null)
            instance = new SharedPreferencesUtil(context);

        return instance;
    }

    private SharedPreferencesUtil(Context context) {
        //public preferecnes
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);//context.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getPreferencesBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void setPreferences(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferencesString(String key) {
        return preferences.getString(key, "");
    }

    public void setPreferences(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getPreferencesLong(String key) {
        return preferences.getLong(key, 0);
    }

    public void setPreferences(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPreferencesInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void clearSharedPreferences() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

    /**
     * Guardar valores array
     * @param key
     * @param value
     */
    public void setPreferences(String key, Set<String> value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * Recuperar valores array
     * @param key
     * @return
     */
    public Set<String> getStringSet(String key){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(key, new HashSet<String>());
    }
}
