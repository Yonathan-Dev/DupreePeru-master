package com.dupreincaperu.dupree.mh_fragments_menu;


import android.content.Intent;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreincaperu.dupree.PDFActivity;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReporteFacturaBinding;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesActivity;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.mh_holders.FacturasPDFHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemFactura;
import com.dupreincaperu.dupree.mh_adapters.FacturaPDFListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreeinca.lib_api_rest.model.dto.response.ListFactura;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporteFacturaPDFFragment extends BaseFragment implements FacturasPDFHolder.Events{

    private final String TAG = ReporteFacturaPDFFragment.class.getName();
    private ReportesController reportesController;
    private FacturaPDFListAdapter listAdapter;
    private List<ItemFactura> list, listFilter;

    public ReporteFacturaPDFFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reporte_factura;
    }

    private FragmentReporteFacturaBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentReporteFacturaBinding) view;

        reportesController = new ReportesController(getContext());

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");

        binding.rcvFactura.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.rcvFactura.setHasFixedSize(true);


        list = new ArrayList<>();
        listFilter = new ArrayList<>();

        listAdapter = new FacturaPDFListAdapter(list, listFilter, this);
        binding.rcvFactura.setAdapter(listAdapter);

    }

    @Override
    protected void onLoadedView() {
        checkFactura();
    }

    public void viewFactura(String urlFile){
        Intent intent = new Intent(getActivity(), PDFActivity.class);
        intent.putExtra(PDFActivity.URL_FILE, urlFile);
        startActivity(intent);
    }

    private void checkFactura(){
        Bundle bundle;
        DataAsesora data;
        if((bundle = getArguments()) != null && (data = bundle.getParcelable(ReportesActivity.TAG)) != null){
            searchNewIdenty(data.getCedula());
        }
    }

    private void updateView(ListFactura responseFactura){
        list.clear();
        listFilter.clear();
        list.addAll(responseFactura.getResult());
        listFilter.addAll(responseFactura.getResult());

        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(responseFactura.getAsesora());

        listAdapter.notifyDataSetChanged();
    }

    public void testDescargar(ItemFactura dataRow, int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.descargar), getString(R.string.desea_descargar));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status){
                    if(row!=-1){
                        viewFactura(dataRow.getLink());
                    }else{
                        msgToast(getString(R.string.error_leyendo_archivo));
                    }
                }
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void searchNewIdenty(String cedula){
        showProgress();
        reportesController.getFacturasPDF(new Identy(cedula), new TTResultListener<ListFactura>() {
            @Override
            public void success(ListFactura result) {
                dismissProgress();
                updateView(result);
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });

    }

    @Override
    public void onClickRoot(ItemFactura dataRow, int row) {
        testDescargar(dataRow, row);
    }
}
