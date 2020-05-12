package com.dupreincaperu.dupree.view.activity;

import android.os.Bundle;

import com.dupreeinca.lib_api_rest.R;
import com.dupreincaperu.dupree.view.fragment.IntegrationFragment;

public class IntegrationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integration);

        addFragmentWithBackStack(IntegrationFragment.class,false);
    }

    @Override
    protected int getFragmentLayout() {
        return R.id.fragment;
    }
}
