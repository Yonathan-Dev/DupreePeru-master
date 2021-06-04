package com.dupreincaperu.dupree.mh_fragments_menu.pedidos.historial.detalle_factura;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFacturaDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ActivityBaseBinding;
import com.dupreincaperu.dupree.view.activity.BaseActivity;

public class DetalleFacturaActivity extends BaseActivity {
    public static String TAG = DetalleFacturaActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        setSupportActionBar(binding.toolbar);
        final ActionBar actionBar = getSupportActionBar();

        ItemFacturaDTO data;
        Intent intent = getIntent();
        if(actionBar != null && (intent != null) && (data = intent.getParcelableExtra(TAG)) != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            Bundle bundle = new Bundle();
            bundle.putParcelable(DetalleFacturaFragment.TAG, data);

            actionBar.setTitle(R.string.detalle_factura);
            replaceFragmentWithBackStack(DetalleFacturaFragment.class, false, bundle);
        } else {
            finish();
        }
    }
    @Override
    protected int getFragmentLayout() {
        return R.id.fragment;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
