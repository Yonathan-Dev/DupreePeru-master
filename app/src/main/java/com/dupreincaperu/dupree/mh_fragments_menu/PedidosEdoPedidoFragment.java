package com.dupreincaperu.dupree.mh_fragments_menu;


import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.PedidosController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.response.EstadoPedidoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ResultEdoPedido;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentPedidosEdoPedidoBinding;
import com.dupreincaperu.dupree.mh_adapters.CDRListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.ListaProductos;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosEdoPedidoFragment extends BaseFragment {
    private final String TAG = PedidosEdoPedidoFragment.class.getName();
    private FragmentPedidosEdoPedidoBinding binding;
    private PedidosController pedidosController;

    private CDRListAdapter adapter_cdr;
    private ListaProductos listProducts, listPaquetones, listOfertas, listFilter;

    public PedidosEdoPedidoFragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_pedidos_edo_pedido;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentPedidosEdoPedidoBinding) view;

        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
        binding.tvNombreAsesora.setText("");

        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);


        listProducts = new ListaProductos();
        listPaquetones = new ListaProductos();
        listOfertas = new ListaProductos();
        listFilter = new ListaProductos();

        binding.recycler.setAdapter(adapter_cdr);

    }

    @Override
    protected void onLoadedView() {
        pedidosController =  new PedidosController(getContext());
        checkEdoPedido();
    }

    private void checkEdoPedido(){
        if(perfil != null){
            if(perfil.getPerfil().equals(Profile.ADESORA)){
                searchIdenty("");
            }
        }
    }

    private void updateView(ResultEdoPedido resultEdoPedido){
        binding.cardViewBackGround.setVisibility(View.INVISIBLE);
    }

    public void searchIdenty(String cedula){
        showProgress();
        pedidosController.getEstadoPedido(new Identy(cedula), new TTResultListener<EstadoPedidoDTO>() {
            @Override
            public void success(EstadoPedidoDTO result) {
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

}
