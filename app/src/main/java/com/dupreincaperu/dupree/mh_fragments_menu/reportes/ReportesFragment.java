package com.dupreincaperu.dupree.mh_fragments_menu.reportes;


import android.content.Context;
import android.content.Intent;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.SearchView;
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
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemCupoSaldoConf;
import com.dupreeinca.lib_api_rest.model.dto.response.ListCupoSaldoConf;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReportesBinding;
import com.dupreincaperu.dupree.mh_adapters.ReportesListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.InputDialog;
import com.dupreincaperu.dupree.mh_holders.ReportesHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportesFragment extends BaseFragment implements ReportesHolder.Events{
    private String TAG = ReportesFragment.class.getName();
    private ReportesController reportesController;

    private DataAsesora dataAsesora;
    public ReportesFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reportes;
    }

    private FragmentReportesBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        Log.e(TAG, "initViews()");
        binding = (FragmentReportesBinding) view;

        reportesController = new ReportesController(getContext());
        dataAsesora = new DataAsesora();

        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setClickable(true);
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(new ReportesListAdapter(getListReportes(), this));
    }

    @Override
    protected void onLoadedView() {
        Log.e(TAG, "onLoadedView()");
    }

    private List<ModelList> getListReportes(){
        List<ModelList> list = new ArrayList<>();
        list.add(new ModelList(R.drawable.ic_restore_white_24dp, getString(R.string.canjes_y_devoluciones_cdr)));
        list.add(new ModelList(R.drawable.ic_record_voice_over_white_24dp, getString(R.string.seguimiento_servicios)));
        list.add(new ModelList(R.drawable.ic_description_white_24dp, getString(R.string.detalle_factura_pdf)));
        list.add(new ModelList(R.drawable.ic_check_white_24dp, getString(R.string.pagos_realizados)));
        if(!dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA)) {
            list.add(new ModelList(R.drawable.ic_credit_card_white_24dp, getString(R.string.cupo_saldo_y_conferencia_asesora)));
        }

        return list;
    }

    //ReportesHolder.Events
    @Override
    public void onClickRoot(ModelList dataRow, int row) {
        if(!dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA) && dataAsesora.getCedula().isEmpty()){
            msgToast(getString(R.string.debe_buscar_asesora));
            return;
        }

        dataAsesora.setId(row);

        Intent intent = new Intent(getActivity(), ReportesActivity.class);
        intent.putExtra(ReportesActivity.TAG, dataAsesora);
        gotoActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.cedula_asesora));

        EditText txtSearch = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
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

        boolean isAsesora = dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA);
        searchItem.setVisible(!isAsesora);
        binding.cardViewBackGround.setVisibility(View.GONE);

        //llamamos a popup que pide la cedula.
        if(!dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA) && dataAsesora.getCedula().isEmpty()){
            obtainIdentyAsesora();
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void obtainIdentyAsesora(){
        //hideSearchView();
        InputDialog d = new InputDialog();
        d.loadData(getString(R.string.cedula_asesora), getString(R.string.cedula_asesora), new InputDialog.ResponseListener() {
            @Override
            public void result(String inputText) {
                searchMyQuery(inputText);
            }
        });
        d.show(getActivity().getSupportFragmentManager(),"mDialog");
    }


    private void searchMyQuery(String query){
        Log.e(TAG, "searchQuery() -> query: " + query);
        showProgress();
        reportesController.getCupoSaldoConf(new Identy(query), new TTResultListener<ListCupoSaldoConf>() {
            @Override
            public void success(ListCupoSaldoConf result) {
                dismissProgress();
                updateView(result.getCupoSaldoConfList().get(0), query);

            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    private void updateView(ItemCupoSaldoConf item, String cedula){
        binding.cardViewBackGround.setVisibility(View.VISIBLE);
        binding.tvNombreAsesora.setText(item.getAsesora());
        dataAsesora.setNombre(item.getAsesora());
        dataAsesora.setCedula(cedula);
        dataAsesora.setId(-1);
    }

    public void searchViewTextChange(String newText) {
        binding.cardViewBackGround.setVisibility(View.GONE);
        binding.tvNombreAsesora.setText("");
        dataAsesora.setNombre("");
        dataAsesora.setCedula("");
        dataAsesora.setId(-1);
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

