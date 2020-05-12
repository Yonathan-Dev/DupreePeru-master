package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.registradas;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;

import com.dupreeinca.lib_api_rest.controller.InscripcionController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.IdentyName;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPreinscripcionDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.Preinscripcion;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncorpListaRegistradasBinding;
import com.dupreincaperu.dupree.mh_adapters.PreinscripListAdapter;
import com.dupreincaperu.dupree.mh_holders.ListPreHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncorpListaRegistFragment extends BaseFragment  implements ListPreHolder.Events{
    private String TAG = IncorpListaRegistFragment.class.getName();
    private FragmentIncorpListaRegistradasBinding binding;
    private InscripcionController inscripcionController;
    private List<Preinscripcion> list;
    private PreinscripListAdapter adapter;

    public static IncorpListaRegistFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IncorpListaRegistFragment fragment = new IncorpListaRegistFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public IncorpListaRegistFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incorp_lista_registradas;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentIncorpListaRegistradasBinding) view;

        binding.swipe.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.swipe.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter = new PreinscripListAdapter(list, this);
        binding.swipe.recycler.setAdapter(adapter);
        binding.swipe.refresh.setEnabled(false);
        binding.swipe.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                typeSearch(binding.searchView.getQuery().toString());
                binding.swipe.refresh.setRefreshing(false);
            }
        });

        binding.searchView.setIconified(false);
//        binding.searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                typeSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                addList(null);
                return false;
            }
        });
    }

    @Override
    protected void onLoadedView() {
        inscripcionController = new InscripcionController(getContext());

//        refresh("");
    }

    private void typeSearch(String query){
        boolean isNumeric = Pattern.matches("[0-9]+", query);
        boolean isChar = Pattern.matches("[a-zA-Z]+", query);
        if(isNumeric){
            refresh(new IdentyName(query, ""));
        } else if(isChar){
            refresh(new IdentyName("", query));
        } else {
            msgToast(getString(R.string.debe_ser_cedula_nombre));
        }
    }

    private void refresh(IdentyName data){
        showProgress();
        inscripcionController.getListaRegistradas(data, new TTResultListener<ListPreinscripcionDTO>() {
            @Override
            public void success(ListPreinscripcionDTO result) {
//                Log.e(TAG, new Gson().toJson(result));
                dismissProgress();
                addList(result);
//                controllerPagination();
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    private void addList(ListPreinscripcionDTO result){
        list.clear();
        if(result != null && result.getResult()!=null) {
            list.addAll(result.getResult());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickRoot(Preinscripcion dataRow, int row) {

    }
}
