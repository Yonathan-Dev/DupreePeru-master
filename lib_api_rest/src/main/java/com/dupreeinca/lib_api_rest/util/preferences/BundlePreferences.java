package com.dupreeinca.lib_api_rest.util.preferences;

/**
 * Created by Technifiser2 on 07/11/2017.
 */

public enum BundlePreferences {
    COOKIES_AUTH("cookies_auth"),
    TOKEN_SESION("token_sesion"),
    JSON_PERFIL("perfil"),
    DEVICE_ID("device.id"),
    IS_LOGGED_IN("isLoggedIn"),
    PATH_FILE_DOWNLOADED("path_files_downloaded"),
    JSON_CAMPANA("json_campana"),
    CAMPANA_ACTUAL("campana_actual"),
    CAMPANA_ACTUALPRE("campana_actual_pre"),
    IS_CHANGE_CAMPANA("isChangeCampana"),
    JSON_URL_CATALOGOS("json_url_catalogos"),
    CAMPANIA_CIERRE("campana_cierre");

    private String keyPreference;

    BundlePreferences(String keyPreference) {
        this.keyPreference = keyPreference;
    }

    public String getKeyPreference() {
        return keyPreference;
    }
}
