package com.dupreincaperu.dupree.mh_utilities;

/*
 * Created by marwuinh@gmail.com on 15/12/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class MyDialoges {
    private static ProgressDialog pDialog;

    public static void showProgressDialog(Context myContext, String msg){
        if(pDialog!=null && pDialog.isShowing())
            return;

            pDialog = new ProgressDialog(myContext);
            pDialog.setMessage(msg);
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//K0LM6F
            pDialog.show();
    }

    public static void dismissProgressDialog(){
        if(pDialog!=null)
            pDialog.dismiss();

        Log.e("detenr ","mando a detenr");
    }



}
