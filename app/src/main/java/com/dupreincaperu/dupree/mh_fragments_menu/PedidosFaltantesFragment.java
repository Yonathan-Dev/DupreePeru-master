package com.dupreincaperu.dupree.mh_fragments_menu;


import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.PedidosController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.Faltante;
import com.dupreeinca.lib_api_rest.model.dto.response.FaltantesDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentPedidosFaltantesBinding;
import com.dupreincaperu.dupree.mh_adapters.FaltantesListAdapter;
import com.dupreincaperu.dupree.mh_holders.FaltantesHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFaltantesFragment extends BaseFragment implements FaltantesHolder.Events{
    private final String TAG = PedidosFaltantesFragment.class.getName();
    private FragmentPedidosFaltantesBinding binding;
    private PedidosController pedidosController;

    private FaltantesListAdapter listAdapter;
    private List<Faltante> list, listFilter;

    public PedidosFaltantesFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_pedidos_faltantes;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentPedidosFaltantesBinding) view;

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        listFilter = new ArrayList<>();

        //listFaltante = faltantesHttp.getResult();
        listFilter.addAll(list);


        listAdapter = new FaltantesListAdapter(list, listFilter, this);
        binding.recycler.setAdapter(listAdapter);

        setData(false, null);
    }

    @Override
    protected void onLoadedView() {
        pedidosController = new PedidosController(getContext());

        checkFaltantes();
    }

    private void checkFaltantes(){
        showProgress();
        pedidosController.getFaltantes(new TTResultListener<FaltantesDTO>() {
            @Override
            public void success(FaltantesDTO result) {
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

    private void setData(boolean isVisible, FaltantesDTO data){
        binding.cardViewBackGround.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        binding.tvCamp.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);

        binding.tvCamp.setText(data!=null ? getString(R.string.concat_campana, data.getCampana()) : "");
    }

    private void updateView(FaltantesDTO data){
        list.clear();
        listFilter.clear();
        list.addAll(data.getResult());
        listFilter.addAll(data.getResult());
        listAdapter.notifyDataSetChanged();

        setData(true, data);
    }

    public void filterFaltantes(String textFilter){
        //adapter_catalogo.getmFilter().filter(textFilter);
        Log.e("newText to: ", textFilter);
        listAdapter.getmFilter().filter(textFilter);
    }

    @Override
    public void onClickRoot(Faltante dataRow, int row) {

    }
}
