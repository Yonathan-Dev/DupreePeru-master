package com.dupreeinca.lib_api_rest.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dupreeinca.lib_api_rest.util.preferences.DataStore;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by marwuinh@gmail.com on 26/8/17.
 */

/**
 * This Interceptor add all received Cookies to the app DefaultPreferences.
 * Your implementation on how to save the Cookies on the mPreferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private String TAG = ReceivedCookiesInterceptor.class.getName();
    private DataStore dataStore;

    public ReceivedCookiesInterceptor(Context ctx) {
        dataStore = DataStore.getInstance(ctx);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        Log.e(TAG, "intercept: try obtain cookies");

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            Set<String> cookies = new HashSet<>();
            String token;
            for (String header : originalResponse.headers("Set-Cookie")) {
                //filtrar solo el que me interesa
                if(header.substring(0,3).equals("SID")) {
                    //Trae toda la trama y me interesa solo el SID
                    String[] separatedCookie = header.split(";");
                    cookies.add(separatedCookie[0]);
                    dataStore.setCookiesAuth(cookies);

                    String[] separatedToken = separatedCookie[0].split("=");
                    token= separatedToken[1];
                    dataStore.setTokenSesion(token);
                    Log.e(TAG, "Extract Header TRUE: cookies = " + separatedCookie[0]);
                    Log.e(TAG, "Extract Header TRUE: token = " + token);
                    break;//solo toma un SID, y omite los repetidos
                } else {
                    Log.e(TAG, "Extract Header FALSE: cookies = " + header);
                }
            }

        }

        return originalResponse;
    }
}
