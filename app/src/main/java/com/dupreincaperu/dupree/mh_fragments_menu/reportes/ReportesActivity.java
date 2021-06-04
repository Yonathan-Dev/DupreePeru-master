package com.dupreincaperu.dupree.mh_fragments_menu.reportes;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ActivityBaseBinding;
import com.dupreeinca.lib_api_rest.enums.EnumReportes;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteCDRFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteCupoSaldoConfFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteFacturaPDFFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReportePagosRealizadosFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.ReporteSegPetQueRecFragment;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.activity.BaseActivity;

public class ReportesActivity extends BaseActivity {
    public static String TAG = ReportesActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBaseBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_base);

        setSupportActionBar(binding.toolbar);
        final ActionBar actionBar = getSupportActionBar();

        DataAsesora data;
        Intent intent = getIntent();
        if(actionBar != null && (intent != null) && (data = intent.getParcelableExtra(TAG)) != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_white_24);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            Bundle bundle = new Bundle();
            bundle.putParcelable(TAG, data);
            if(data.getId() == EnumReportes.REPORTE_CDR.getKey()){
                actionBar.setTitle(getString(R.string.canjes_y_devoluciones_cdr));
                replaceFragmentWithBackStack(ReporteCDRFragment.class, false, bundle);
            } else if(data.getId() == EnumReportes.REPORTE_SEGUIMIENTO.getKey()){
                actionBar.setTitle(getString(R.string.seguimiento_servicios));
                replaceFragmentWithBackStack(ReporteSegPetQueRecFragment.class, false, bundle);
            } else if(data.getId() == EnumReportes.REPORTE_FACTURAS.getKey()){
                actionBar.setTitle(getString(R.string.detalle_factura_pdf));
                replaceFragmentWithBackStack(ReporteFacturaPDFFragment.class, false, bundle);
            } else if(data.getId() == EnumReportes.REPORTE_PAGOS.getKey()){
                actionBar.setTitle(getString(R.string.pagos_realizados));
                replaceFragmentWithBackStack(ReportePagosRealizadosFragment.class, false, bundle);
            } else if(data.getId() == EnumReportes.REPORTE_CONFERENCIA.getKey()){
                actionBar.setTitle(getString(R.string.cupo_saldo_y_conferencia_asesora));
                replaceFragmentWithBackStack(ReporteCupoSaldoConfFragment.class, false, bundle);
            } else {
                finish();
            }

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
