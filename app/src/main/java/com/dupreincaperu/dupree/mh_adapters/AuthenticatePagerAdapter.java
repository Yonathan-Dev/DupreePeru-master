package com.dupreincaperu.dupree.mh_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dupreincaperu.dupree.mh_fragments_login.AuthFragment;
import com.dupreincaperu.dupree.mh_fragments_login.CodeFragment;
import com.dupreincaperu.dupree.mh_fragments_login.ForgotFragment;
import com.dupreincaperu.dupree.mh_fragments_login.PasswordFragment;

/**
 * Created by marwuinh@gmail.com on 5/8/17.
 */

public class AuthenticatePagerAdapter extends FragmentPagerAdapter {
    private final String TAG=AuthenticatePagerAdapter.class.getName();
    public final int numPages=4;
    public static final int PAGE_LOGIN=0;
    public static final int PAGE_FORGOT=1;
    public static final int PAGE_CODE=2;
    public static final int PAGE_PASSWORD=3;

    private AuthFragment authFragment;
    private CodeFragment codeFragment;
    private ForgotFragment forgotFragment;
    private PasswordFragment passwordFragment;

    public AuthenticatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_LOGIN:
                authFragment = AuthFragment.newInstance();
                return authFragment;
            case PAGE_FORGOT:
                forgotFragment = ForgotFragment.newInstance();
                return forgotFragment;
            case PAGE_CODE:
                codeFragment =  CodeFragment.newInstance();
                return codeFragment;
            case PAGE_PASSWORD:
                passwordFragment = PasswordFragment.newInstance();
                return passwordFragment;
            /*case PAGE_LOGIN:
                return MainFragment.newInstance();*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public AuthFragment getAuthFragment() {
        return authFragment;
    }

    public CodeFragment getCodeFragment() {
        return codeFragment;
    }

    public ForgotFragment getForgotFragment() {
        return forgotFragment;
    }

    public PasswordFragment getPasswordFragment() {
        return passwordFragment;
    }
}
