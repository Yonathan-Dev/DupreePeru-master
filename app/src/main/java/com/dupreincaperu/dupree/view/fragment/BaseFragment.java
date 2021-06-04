package com.dupreincaperu.dupree.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.util.alert.MProgressDialog;
import com.dupreeinca.lib_api_rest.util.preferences.DataStore;
import com.dupreincaperu.dupree.MainActivity;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by marwuinh@gmail.com on 01/12/18.
 */

public abstract class BaseFragment extends Fragment {
    private String TAG = BaseFragment.class.getName();
    protected DataStore dataStore;
//    protected ProgressMngr progress;
//    protected ProgressBar progressBar;

//    private ProgressDialog pDialog;
//    protected void createProgress(){
//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setCancelable(true);
//        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//K0LM6F
//    }

    protected void showProgress(){
//        if(pDialog != null) {
//            pDialog.setMessage(getString(R.string.espere));
//            pDialog.show();
//        }
        MProgressDialog.getInstance().show(getFragmentManager(), TAG);
    }

    protected void dismissProgress(){
        MProgressDialog.getInstance().dismiss();
//        if(pDialog!=null)
//            pDialog.dismiss();
    }

    public Boolean isRestoredFromBackStack() {
        return isRestoredFromBackStack;
    }

    private Boolean isRestoredFromBackStack;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isRestoredFromBackStack = true;
    }

    protected boolean isOnCreate = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRestoredFromBackStack = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding view = DataBindingUtil.inflate(inflater, getMainLayout(), container, false);

//        this.createProgress();

        //progress
//        progress = ProgressMngr.getInstance(getActivity());

        dataStore = new DataStore(getActivity());
        this.initViews(view);





        return view.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.onLoadedView();
        //       this.progressBar = (ProgressBar) view.findViewById(getProgressBarID());
        //       if (progressBar != null) {
        //           progressBar.setVisibility(View.GONE);
        //       }

    }

    protected abstract int getMainLayout();

    protected abstract void initViews(ViewDataBinding view);

    protected abstract void onLoadedView();

    public Map<String,String> getAnalyticsParameters(){
        return new HashMap<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isRestoredFromBackStack) {
            // The fragment restored from backstack, do some work here!
        }
    }

    public void addFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack) {
        Fragment myNewFragment = Fragment.instantiate(this.getActivity().getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        t.add(R.id.fragment, myNewFragment, newFragment);
        t.commit();
    }

    public Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack) {
        return this.replaceFragmentWithBackStack(myNewFragmentClass, withBackstack, null);
    }

    public Fragment replaceFragmentWithBackStack(Class myNewFragmentClass, Boolean withBackstack, Bundle bundle) {
        Fragment myNewFragment = Fragment.instantiate(this.getActivity().getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        if (withBackstack){
            t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
            t.addToBackStack(newFragment);
        }
        myNewFragment.setArguments(bundle);
        t.replace(R.id.fragment, myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public Fragment replaceFragmentWithBackStackAnimated(Class myNewFragmentClass, Boolean withBackstack, Bundle bundle) {
        Fragment myNewFragment = Fragment.instantiate(this.getActivity().getApplicationContext(), myNewFragmentClass.getName());
        String newFragment = myNewFragment.getClass().getName();
        FragmentTransaction t = getChildFragmentManager().beginTransaction();
        t.setCustomAnimations(R.anim.pull_in_right,R.anim.push_out_left,R.anim.pull_in_left, R.anim.push_out_right);
        if (withBackstack){
            t.addToBackStack(newFragment);
        }
        myNewFragment.setArguments(bundle);
        t.replace(R.id.fragment, myNewFragment, newFragment);
        t.commit();
        return myNewFragment;
    }

    public void gotoActivity(Intent i){
        Activity a = getActivity();
        if(a != null){
            a.startActivity(i);
        }
    }


    public void showError(TTError error) {
        showErrorDialog(error);
    }

    public void showErrorDialog(TTError error) {
        new AlertDialog.Builder(getActivity())
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


    public void onBack() {

    }

    public String getTitle() {
        return null;
    }

    public void setEnabled(boolean b) {

    }

    protected void msgToast(String msg){
        try{
            Toast.makeText(this.getContext(), msg, Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Log.e("onError", msg);
        }
    }

    protected void checkSession(TTError error){
        if(error != null) {
            if (error.getStatusCode() != null && (error.getStatusCode() == 401 || error.getStatusCode() == 501)) {
                gotoMain();
            }
            if(error.getMessage() != null) {
                msgToast(error.getMessage());
            }
        }
    }

    protected void gotoMain(){
        dataStore.cerrarSesion();

        Activity a = getActivity();
        if(a != null) {
            Intent intent = new Intent(a, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            a.startActivity(intent);
        }
    }

//    protected void showSnackBar(int msg){
//        Activity a = getActivity();
//        if(a != null) {
//            Snackbar snackbar = Snackbar.make(a.getWindow().getDecorView().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
//            View sbView = snackbar.getView();
//            sbView.setBackgroundColor(ContextCompat.getColor(a, R.color.colorPrimary));
//            snackbar.show();
//        }
//    }

    protected void showSnackBarDuration(int msg, int duration){
        Activity a = getActivity();
        if(a != null) {
            Snackbar.make(a.getWindow().getDecorView().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).setDuration(duration).show();
        }
    }

    protected void showSnackBarDuration(String msg, int duration){
        Activity a = getActivity();
        if(a != null) {
            Snackbar.make(a.getWindow().getDecorView().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).setDuration(duration).show();
        }
    }

}
