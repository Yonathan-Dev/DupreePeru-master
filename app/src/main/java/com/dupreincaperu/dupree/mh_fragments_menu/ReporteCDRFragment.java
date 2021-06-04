package com.dupreincaperu.dupree.mh_fragments_menu;


import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCDR;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReporteCdrBinding;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesActivity;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.mh_holders.CDRHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemCDR;
import com.dupreincaperu.dupree.mh_adapters.CDRListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.response.TitlesCDR;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporteCDRFragment extends BaseFragment implements CDRHolder.Events{

    private final String TAG = ReporteCDRFragment.class.getName();
    private ReportesController reportesController;
    private CDRListAdapter adapter_cdr;
    private List<ItemCDR> listCDR, listFilter;

    public ReporteCDRFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reporte_cdr;
    }

    private FragmentReporteCdrBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentReporteCdrBinding) view;
        reportesController = new ReportesController(getContext());

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");

        binding.rcvCDR.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.rcvCDR.setHasFixedSize(true);


        listCDR = new ArrayList<>();
        listFilter = new ArrayList<>();

        adapter_cdr = new CDRListAdapter(listCDR, listFilter, this);
        binding.rcvCDR.setAdapter(adapter_cdr);
    }

    @Override
    protected void onLoadedView() {
        checkCDR();
    }

    private void checkCDR(){
        Bundle bundle;
        DataAsesora data;
        if((bundle = getArguments()) != null && (data = bundle.getParcelable(ReportesActivity.TAG)) != null){
            searchNewIdenty(data.getCedula());
        }
    }

    private void updateView(TitlesCDR listaCDR){
        listCDR.clear();
        listFilter.clear();
        listCDR.addAll(listaCDR.getTable());
        listFilter.addAll(listaCDR.getTable());

        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(listaCDR.getAsesora());

        adapter_cdr.notifyDataSetChanged();
    }

    public void searchNewIdenty(String cedula){
        showProgress();
        reportesController.getCDR(new Identy(cedula), new TTResultListener<ListCDR>() {
            @Override
            public void success(ListCDR result) {
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
    public void onClickRoot(ItemCDR dataRow, int row) {

    }
}
