package com.dupreeinca.lib_api_rest.controller.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class TTGenericController {

    protected Context context;

    public TTGenericController(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public Boolean isNetworkingOnline(Context context){
        if (context == null){
            return true;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = cm.getActiveNetworkInfo();
        return (activeConnection != null) && activeConnection.isConnected();
    }
}
