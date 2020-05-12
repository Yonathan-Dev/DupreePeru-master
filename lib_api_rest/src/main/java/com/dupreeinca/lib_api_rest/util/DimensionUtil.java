package com.dupreeinca.lib_api_rest.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DimensionUtil {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getWidth(Integer dpPadding, Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int px = DimensionUtil.dpToPx(dpPadding);
        Integer result = width - px*2;
        return result;
    }

    public static int getWidth(Double percent, Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        double result = width*percent;
        return (int) result;
    }


    public static int getHeight(Double percent, Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.y;
        double result = width*percent;
        return (int) result;
    }

    public static int getHeight(Integer width, String aspectRatio){
        String[] values = aspectRatio.split("/");
        Integer w = Integer.parseInt(values[0]);
        Integer h = Integer.parseInt(values[1]);
//        w     width
//        h     x=
        return h*width/w;
    }

    public static double aspectRatio(String aspectRatio){
        String[] values = aspectRatio.split("/");
        Integer w = Integer.parseInt(values[0]);
        Integer h = Integer.parseInt(values[1]);
        return w/h;
    }
}
