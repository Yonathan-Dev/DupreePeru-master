package com.dupreincaperu.dupree.mh_fragments_menu;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.RetenidosDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentReporteRetenidosBinding;
import com.dupreincaperu.dupree.mh_holders.RetenidosHolder;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemRetenido;
import com.dupreincaperu.dupree.mh_adapters.RetenidosListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReporteRetenidosFragment extends BaseFragment implements RetenidosHolder.Events{

    private final String TAG = ReporteRetenidosFragment.class.getName();
    private FragmentReporteRetenidosBinding binding;

    private ReportesController reportesController;

    private static final int RC_CALL = 111;

    private List<ItemRetenido> list, listFilter;//, listSelected;
    private RetenidosListAdapter listAdapter;

    String phone;
    public ReporteRetenidosFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_reporte_retenidos;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentReporteRetenidosBinding) view;

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listFilter = new ArrayList<>();

        //listRetenidos = getRetenidos();
        listFilter.addAll(list);
        listAdapter = new RetenidosListAdapter(list, listFilter, this);
        binding.recycler.setAdapter(listAdapter);

    }

    @Override
    protected void onLoadedView() {
        reportesController = new ReportesController(getContext());

        checkRetenidos();
    }

    @Override
    public void onClickRoot(ItemRetenido dataRow, int row) {

    }

    @Override
    public void onClickCall(ItemRetenido dataRow, int row) {
        phone = dataRow.getCelular();
        if(phone!= null && !phone.isEmpty() && !phone.equals("0")) {

            testCall();
        }
    }

    public void checkRetenidos(){
        if(list.size()<1){//OJO REFREZCAR CON PULL REFRESH
            showProgress();
            reportesController.getPedRetenidos(new Identy(""), new TTResultListener<RetenidosDTO>() {
                @Override
                public void success(RetenidosDTO result) {
                    dismissProgress();
                    addRetenidos(result.getListTitleRetenidos().getRetenidoList());
                }

                @Override
                public void error(TTError error) {
                    dismissProgress();
                    checkSession(error);
                }
            });
        }
    }
    public void testCall(){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.llamar), getString(R.string.desea_lammar));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    callPhone();
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    @AfterPermissionGranted(RC_CALL)
    private void callPhone() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            makeCall();
        } else {
            EasyPermissions.requestPermissions(this, "Need some permissions", RC_CALL, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    private void addRetenidos(List<ItemRetenido> retenidoList){
        list.clear();
        listFilter.clear();

        if(retenidoList!=null) {
            list.addAll(retenidoList);
            listFilter.addAll(retenidoList);
            listAdapter.notifyDataSetChanged();
        }
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

    }

    public void searchViewTextChange(String newText) {
        Log.e("newText to: ", newText);
        listAdapter.getmFilter().filter(newText);
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
