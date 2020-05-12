package com.dupreincaperu.dupree.mh_utilities;

import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Marwuin on 19/4/2017.
 */

public class Validate {
    public void setLoginError(String error, TextView txtView) {
        txtView.setError(error);
        txtView.requestFocus();
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return !pattern.matcher(email).matches();
    }


    public boolean isValidPwd(String pwd) {
        Matcher matcher;
        final Pattern USER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9@.#$%^&*_&?$()\\\\]+$");

        matcher = USER_NAME_PATTERN.matcher(pwd);

        return !matcher.matches() || pwd.length()<2;
    }

    public boolean isValidNameFolder(String nameFolder) {
        Matcher matcher;
        final Pattern USER_NAME_PATTERN = Pattern.compile("[a-zA-Z 0-9]+");

        matcher = USER_NAME_PATTERN.matcher(nameFolder);

        return !matcher.matches();
    }

    public boolean isValidInteger(String nameFolder) {
        Matcher matcher;
        final Pattern USER_NAME_PATTERN = Pattern.compile("[0-9]+");

        matcher = USER_NAME_PATTERN.matcher(nameFolder);

        return !matcher.matches();
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Float.parseFloat(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static long stringToLong(String str)
    {
        long d;
        try
        {
            d = Long.parseLong(str);
        }
        catch(NumberFormatException nfe)
        {
            return 0;
        }
        return d;
    }

}
