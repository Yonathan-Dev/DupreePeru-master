package com.dupreincaperu.dupree.mh_fragments_menu.panel_asesoras.tabs;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentFaltantesAsesoraBinding;
import com.dupreincaperu.dupree.mh_adapters.FaltantesListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.response.Faltante;
import com.dupreincaperu.dupree.mh_holders.FaltantesHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaltantesAsesoraFragment extends BaseFragment implements FaltantesHolder.Events{
    private FragmentFaltantesAsesoraBinding binding;

    public FaltantesAsesoraFragment() {
        // Required empty public constructor
    }

    public static FaltantesAsesoraFragment newInstance() {

        Bundle args = new Bundle();

        FaltantesAsesoraFragment fragment = new FaltantesAsesoraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<Faltante> list;
    private FaltantesListAdapter listAdapter;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_faltantes_asesora;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentFaltantesAsesoraBinding) view;

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listAdapter = new FaltantesListAdapter(list, list, this);
        binding.recycler.setAdapter(listAdapter);
    }

    @Override
    protected void onLoadedView() {

    }

    public void setData(List<Faltante> faltanteList){
        this.list.clear();
        this.list.addAll(faltanteList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickRoot(Faltante dataRow, int row) {

    }
}
