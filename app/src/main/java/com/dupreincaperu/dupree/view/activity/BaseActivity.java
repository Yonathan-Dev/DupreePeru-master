package com.dupreincaperu.dupree.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.util.TTApp;

import io.realm.Realm;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
//import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class BaseActivity extends AppCompatActivity implements BaseActivityListener{
    protected Realm realm;
    //@Override
    /*protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }

    @Override
    public void startActivity(Intent intent){
        super.startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (!TTApp.getInstance().isBackEnabled()){
            return;
        }
        super.onBackPressed();
        this.overrideBackAnimation();
    }

    protected void overrideBackAnimation(){
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public Fragment addFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.add(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack) {
        return this.replaceFragmentWithBackStack(myNewFragmentClass, withBackstack, null);
    }

    public Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack, Bundle bundle) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        myNewFragment.setArguments(bundle);
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStack(Fragment myNewFragment, Boolean withBackstack, Bundle bundle) {
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (bundle != null){
            myNewFragment.setArguments(bundle);
        }
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStackAnimate(Fragment myNewFragment, Boolean withBackstack, Bundle bundle) {
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        myNewFragment.setArguments(bundle);
        t.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in, R.anim.fade_out);
        if (withBackstack){
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStackAnimation(Class myNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in, R.anim.fade_out);
//        t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
        if (withBackstack){
            t.addToBackStack(newFragment);
        }
        t.replace(getFragmentLayout(), myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    protected int getFragmentLayout() {
        return R.id.fragment;
    }

    public void showError(TTError error) {
        showErrorDialog(error);
    }

    public void showErrorDialog(TTError error) {
        new AlertDialog.Builder(this)
                .setTitle(error.getTitle())
                .setMessage(error.getMessage())
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

}

