package com.dupreeinca.lib_api_rest.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dupreeinca.lib_api_rest.util.preferences.DataStore;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cloudemotion on 26/8/17.
 */

/**
 * This interceptor put all the Cookies in mPreferences in the Request.
 * Your implementation on how to get the mPreferences MAY VARY.
 * <p>
 * Created by tsuharesu on 4/1/15.
 */
public class AddCookiesInterceptor implements Interceptor {
    private static String TAG = AddCookiesInterceptor.class.getName();
    private DataStore dataStore;

    public AddCookiesInterceptor(Context ctx) {
        dataStore = DataStore.getInstance(ctx);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Log.e(TAG, "intercept: try add cookies");
        Request.Builder builder = chain.request().newBuilder();//.addHeader("Content-Encoding","UTF-8");
        Set<String> preferences = dataStore.getCookiesAuth();
        String jsonPerfil = dataStore.getJSON_TypePerfil();
        if(jsonPerfil!=null){
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                Log.e(TAG, "Adding Header: Cookie = " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }

        return chain.proceed(builder.build());
    }
}
