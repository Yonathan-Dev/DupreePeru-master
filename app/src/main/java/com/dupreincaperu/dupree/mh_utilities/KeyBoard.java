package com.dupreincaperu.dupree.mh_utilities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by marwuinh@gmail.com on 3/15/19.
 */

public class KeyBoard {
    public static  void hide(Activity ctx){
        InputMethodManager imm = (InputMethodManager)  ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm != null && ctx.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
