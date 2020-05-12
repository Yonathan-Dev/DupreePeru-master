package com.dupreincaperu.dupree.mh_fragments_menu.panel_asesoras.tabs;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentTrackingBinding;
import com.dupreincaperu.dupree.mh_adapters.TrackingListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.response.Tracking;
import com.dupreincaperu.dupree.mh_holders.TrackingHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackingFragment extends BaseFragment implements TrackingHolder.Events{
    private FragmentTrackingBinding binding;

    public TrackingFragment() {
        // Required empty public constructor
    }

    public static TrackingFragment newInstance() {
        Bundle args = new Bundle();

        TrackingFragment fragment = new TrackingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private List<Tracking> list;
    private TrackingListAdapter listAdapter;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_tracking;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentTrackingBinding) view;
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listAdapter = new TrackingListAdapter(list, this);
        binding.recycler.setAdapter(listAdapter);
    }

    @Override
    protected void onLoadedView() {

    }

    public void setData(List<Tracking> trackingList){
        this.list.clear();
        this.list.addAll(trackingList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickRoot(Tracking dataRow, int row) {

    }
}
