/*
 * THIS IS AN UNPUBLISHED WORK CONTAINING PARTS TOWN, LLC. CONFIDENTIAL
 * AND PROPRIETARY INFORMATION.  IF PUBLICATION OCCURS, THE FOLLOWING NOTICE
 * APPLIES: "COPYRIGHT 2016 PARTS TOWN, LLC. ALL RIGHTS RESERVED"
 *
 * $partstown$ $Rev: $ $Date: $
 *
 */
package com.dupreeinca.lib_api_rest.util.helpers;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created by steveparrish on 4/5/16.
 */
public class WindowHelpers {
    public static int GetDP (Resources resources, int pixels) {
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int)Math.ceil(metrics.density * pixels);
    }

    public static int GetPixel(Resources resources, float dp){
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    public static Currency getCurrency(String iso4217) {
        if (!TextUtils.isEmpty(iso4217) && iso4217.equals("CAD")) {
            return Currency.getInstance(iso4217);
        }
        return Currency.getInstance("USD");
    }

    private static String postFormat(final String value) {
        if (value.startsWith("CA")) {
            return value.replace("CA", "C");
        }
        return value;
    }

    public static String formatCurrency(final float price, String iso4217) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Currency c = getCurrency(iso4217);
        formatter.setCurrency(c);
        return postFormat(formatter.format(price));
    }

    public static String formatCurrency(String price, String iso4217) {
        float value = 0;
        try {
            value = Float.parseFloat(price);
        }
        catch (NumberFormatException e) {
            // Do nothing
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        Currency c = getCurrency(iso4217);

        formatter.setCurrency(c);
        return postFormat(formatter.format(value));
    }

    public static void lockCurrentOrientation(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        }
        else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        }
    }

    public static void unlockCurrentOrientation(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
