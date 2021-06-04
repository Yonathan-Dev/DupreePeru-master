package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.incripcion;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ActivityBaseBinding;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.activity.BaseActivity;

public class InscripcionActivity extends BaseActivity {
    public static String TAG = InscripcionActivity.class.getName();
    private String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        setSupportActionBar(binding.toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        DataAsesora data;
        Intent intent = getIntent();
        if((intent != null) && (data = intent.getParcelableExtra(TAG)) != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DatosPersonalesFragment.TAG, data);

            Bundle extras = intent.getExtras();
            if(extras != null)
                estado = extras.getString("estado");
            bundle.putString("estado",estado);
            replaceFragmentWithBackStack(DatosPersonalesFragment.class, false, bundle);
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
