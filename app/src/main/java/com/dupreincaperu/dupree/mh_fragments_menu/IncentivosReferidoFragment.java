package com.dupreincaperu.dupree.mh_fragments_menu;


import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.ReferidosDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncentReferidoBinding;
import com.dupreincaperu.dupree.mh_holders.IncentivosRefHolder;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.IncentivoRef;
import com.dupreincaperu.dupree.mh_adapters.IncentivoRefListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.response.ListaReferidos;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncentivosReferidoFragment extends BaseFragment implements IncentivosRefHolder.Events{
    private final String TAG = IncentivosReferidoFragment.class.getName();
    private FragmentIncentReferidoBinding binding;
    private ReportesController reportesController;

    private List<IncentivoRef> list, listFilter;
    private IncentivoRefListAdapter adapter_incentivo_referido;
    public IncentivosReferidoFragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incent_referido;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentIncentReferidoBinding) view;

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listFilter = new ArrayList<>();
        //incentivoRefs = getResult();

        listFilter.addAll(list);

        adapter_incentivo_referido = new IncentivoRefListAdapter(list, listFilter, this);
        binding.recycler.setAdapter(adapter_incentivo_referido);

    }

    @Override
    protected void onLoadedView() {
        reportesController = new ReportesController(getContext());
        checkReferidos();
    }

    private void checkReferidos(){
        if(perfil != null){
            if(perfil.getPerfil().equals(Profile.ADESORA)){
                searchNewIdenty("");
            }
        }
    }

    private void updateView(ListaReferidos listaReferidos){
        list.clear();
        listFilter.clear();
        list.addAll(listaReferidos.getTable());
        listFilter.addAll(listaReferidos.getTable());

        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(listaReferidos.getAsesora());

        adapter_incentivo_referido.notifyDataSetChanged();
    }

    public void searchNewIdenty(String cedula){
        showProgress();
        reportesController.getIncentivosReferido(new Identy(cedula), new TTResultListener<ReferidosDTO>() {
            @Override
            public void success(ReferidosDTO result) {
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
    public void onClickRoot(IncentivoRef dataRow, int row) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.cedula_asesora));

        EditText txtSearch = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txtSearch.setInputType(InputType.TYPE_CLASS_NUMBER);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e(TAG, "onCreateOptionsMenu() -> onQueryTextSubmit() -> " + query);
                searchMyQuery(query);
                searchView.clearFocus();
                return false;//habilita el serach del teclado
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e(TAG, "onCreateOptionsMenu() -> onQueryTextChange() -> " + newText);
                searchViewTextChange(newText);
                return false;
            }
        });

        searchView.setIconified(true);//inicialmente oculto

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchMyQuery(String query){
        searchNewIdenty(query);
    }

    public void searchViewTextChange(String newText) {

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach()");
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()");
        setHasOptionsMenu(false);
    }
}
