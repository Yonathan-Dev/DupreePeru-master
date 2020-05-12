package com.dupreincaperu.dupree.view.fragment;

import android.databinding.ViewDataBinding;
import android.util.Log;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.R;
import com.dupreeinca.lib_api_rest.controller.UserController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.UserDTO;


public class IntegrationFragment extends BaseFragment {

    private static final String TAG = IntegrationFragment.class.getSimpleName();
    private TextView titleTV;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_integration;
    }

    @Override
    protected void initViews(ViewDataBinding view) {

    }

    @Override
    protected void onLoadedView() {
        UserController controller = new UserController(getContext());
        controller.retrieveUser(new TTResultListener<UserDTO>() {
            @Override
            public void success(UserDTO result) {
                if(result.getData() != null){
                    titleTV.setText("Response from service: " + result.getData().toString());
                }
                Log.i(TAG,"SUCCESS");
            }

            @Override
            public void error(TTError error) {
                showError(error);
            }
        });
    }
}
