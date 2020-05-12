package com.dupreincaperu.dupree.mh_fragments_menu;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPagosrealizados;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPagos;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPagosRealizados;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReportePagosRealizadosBinding;
import com.dupreincaperu.dupree.mh_adapters.PagosRealizadoListAdapter;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesActivity;
import com.dupreincaperu.dupree.mh_holders.PagosRealizHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportePagosRealizadosFragment extends BaseFragment implements PagosRealizHolder.Events{

    private final String TAG = ReportePagosRealizadosFragment.class.getName();

    private ReportesController reportesController;
    private PagosRealizadoListAdapter adapter_pagos;
    private List<ItemPagosrealizados> listPagos, listFilter;

    public ReportePagosRealizadosFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reporte_pagos_realizados;
    }

    private FragmentReportePagosRealizadosBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentReportePagosRealizadosBinding) view;

        reportesController = new ReportesController(getContext());

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");

        binding.rcvPagosRealizados.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.rcvPagosRealizados.setHasFixedSize(true);

        listPagos = new ArrayList<>();
        listFilter = new ArrayList<>();

        adapter_pagos = new PagosRealizadoListAdapter(listPagos, listFilter, this);
        binding.rcvPagosRealizados.setAdapter(adapter_pagos);

    }

    @Override
    protected void onLoadedView() {
        checkPagos();
    }

    private void checkPagos(){
        Bundle bundle;
        DataAsesora data;
        if((bundle = getArguments()) != null && (data = bundle.getParcelable(ReportesActivity.TAG)) != null){
            searchNewIdenty(data.getCedula());
        }
    }

    private void updateView(ListPagos listaPagos){
        listPagos.clear();
        listFilter.clear();
        listPagos.addAll(listaPagos.getPago());
        listFilter.addAll(listaPagos.getPago());

        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(listaPagos.getAsesora());

        adapter_pagos.notifyDataSetChanged();
    }

    public void searchNewIdenty(String cedula){
        showProgress();
        reportesController.getPagos(new Identy(cedula), new TTResultListener<ListPagosRealizados>() {
            @Override
            public void success(ListPagosRealizados result) {
                dismissProgress();
                updateView(result.getResult());
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    @Override
    public void onClickRoot(ItemPagosrealizados dataRow, int row) {

    }
}
