package com.dupreeinca.lib_api_rest.interceptors;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marwuinh@gmail.com on 2/13/18.
 */

public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;

    public ConnectivityInterceptor(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetworkUtil.isOnline(mContext)) {

            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();//.addHeader("Content-Encoding","UTF-8");
        return chain.proceed(builder.build());
    }

    private void msgToast(String msg){
        Log.e("onError", msg);
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

}