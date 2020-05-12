package com.dupreeinca.lib_api_rest.util;

public class TTApp {

    private Boolean backEnabled;

    private static TTApp ourInstance = new TTApp();

    public static TTApp getInstance() {
        return ourInstance;
    }

    private TTApp() {
        backEnabled = true;
    }

    public Boolean isBackEnabled() {
        return backEnabled;
    }

    public void setBackEnabled(Boolean backEnabled) {
        this.backEnabled = backEnabled;
    }

}
