package com.dupreincaperu.dupree.mh_fragments_menu;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReporteSegPetQueRecBinding;
import com.dupreincaperu.dupree.mh_fragments_menu.reportes.ReportesActivity;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.mh_holders.PQRHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPQR;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPQR;
import com.dupreincaperu.dupree.mh_adapters.SeguimientoPQRListAdapter;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporteSegPetQueRecFragment extends BaseFragment implements PQRHolder.Events{

    private ReportesController reportesController;
    private SeguimientoPQRListAdapter listAdapter;
    private List<ItemPQR> listPQR, listFilter;

    public ReporteSegPetQueRecFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reporte_seg_pet_que_rec;
    }

    private FragmentReporteSegPetQueRecBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentReporteSegPetQueRecBinding) view;

        reportesController = new ReportesController(getContext());

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");

        binding.rcvSeguimientoPQR.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.rcvSeguimientoPQR.setHasFixedSize(true);

        //faltantesHttp = getFaltantes();

        listPQR = new ArrayList<>();
        listFilter = new ArrayList<>();

        //listPQR = getResult();
        listFilter.addAll(listPQR);


        listAdapter = new SeguimientoPQRListAdapter(listPQR, listFilter, this);
        binding.rcvSeguimientoPQR.setAdapter(listAdapter);

    }

    @Override
    protected void onLoadedView() {
        checkPQR();
    }

    private void checkPQR(){
        Bundle bundle;
        DataAsesora data;
        if((bundle = getArguments()) != null && (data = bundle.getParcelable(ReportesActivity.TAG)) != null){
            searchNewIdenty(data.getCedula());
        }
    }

    private void updateView(ListPQR listaPQR){
        listPQR.clear();
        listFilter.clear();
        listPQR.addAll(listaPQR.getResult());
        listFilter.addAll(listaPQR.getResult());

        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(listaPQR.getAsesora());

        listAdapter.notifyDataSetChanged();
    }

    public void searchNewIdenty(String cedula){
        showProgress();
        reportesController.getPQR(new Identy(cedula), new TTResultListener<ListPQR>() {
            @Override
            public void success(ListPQR result) {
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
    public void onClickRoot(ItemPQR dataRow, int row) {

    }
}
