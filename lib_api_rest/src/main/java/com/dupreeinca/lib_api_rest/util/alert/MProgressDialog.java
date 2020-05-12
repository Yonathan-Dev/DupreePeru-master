package com.dupreeinca.lib_api_rest.util.alert;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.R;
import com.github.ybq.android.spinkit.style.Circle;


/**
 * Created by marwuinh@gmail.com on 11/11/17.
 */

public class MProgressDialog extends DialogFragment {

    private static final String TAG = MProgressDialog.class.getSimpleName();
    private static MProgressDialog instance = new MProgressDialog();

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    private ProgressBar progressBar;
    private TextView loading;

    public static MProgressDialog getInstance(String message) {
        instance.setMessage(message);
        return instance;
    }

    public static MProgressDialog getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.e(TAG, "onCreateDialog");
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_dialog);
        loading = (TextView) view.findViewById(R.id.loading);
        progressBar.setIndeterminate(true);
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        this.setCancelable(false);
        //progressBar.setIndeterminateDrawable(getActivity().getResources().getDrawable(R.drawable.animation_list_loading));
        return view;
    }

    /**
     *Evita repetir en dialogo
     */
    private int show = 0;
    private int dismiss = 0;
    @Override
    public void show(final FragmentManager manager, final String tag) {
        try {
            this.show++;
            Log.d(TAG.concat(" -> show()"), tag);
            Log.d(TAG.concat(" -> show()"), Integer.toString(show));
            if (show == 1 && !instance.isAdded()) {
                super.show(manager, TAG.concat(String.valueOf(show)));
            }
        } catch (RuntimeException e) {
            this.show--;
            Log.e(TAG.concat(" -> show()"), e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            Log.d(TAG.concat(" -> dismiss()"), Integer.toString(show));
            if (show != 0 && dismiss == --show) {
                show = 0;
                super.dismiss();
            }
        } catch (RuntimeException e) {
            Log.e(TAG.concat(" -> dismiss()"), e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void dismissAllowingStateLoss() {
        try {
            Log.d(TAG.concat(" -> dismiss()"), Integer.toString(show));
            if (show != 0 && dismiss == --show) {
                show = 0;
                super.dismissAllowingStateLoss();
            }
        } catch (RuntimeException e) {
            Log.e(TAG.concat(" -> dismiss()"), e.getMessage());
            e.printStackTrace();
        }

    }
}
