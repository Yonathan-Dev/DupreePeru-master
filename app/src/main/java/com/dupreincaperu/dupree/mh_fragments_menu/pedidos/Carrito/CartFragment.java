package com.dupreincaperu.dupree.mh_fragments_menu.pedidos.Carrito;


import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.util.Log;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentProductsBinding;
import com.dupreincaperu.dupree.mh_adapters.CatalogoListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.DetalleProductos;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.BasePedido;
import com.dupreincaperu.dupree.mh_holders.CatalogoHolder;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseFragment implements CatalogoHolder.Events{

    private final String TAG = CartFragment.class.getName();
    public static  final String BROACAST_DATA="broacast_data";

    RealmResults<Catalogo> querycat;

    private List<Catalogo> listFilterCart = new ArrayList<>();//, listSelected;
    private CatalogoListAdapter adapter_cart;

    private Realm realm;
    private boolean enable=true;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        Bundle args = new Bundle();

        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_products;
    }

    private FragmentProductsBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        onAttachFragment(getParentFragment());

        binding = (FragmentProductsBinding) view;
        realm = Realm.getDefaultInstance();

        binding.refresh.setEnabled(false);
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        listFilterCart = new ArrayList<>();
        adapter_cart = new CatalogoListAdapter(listFilterCart, this);

        binding.recycler.setAdapter(adapter_cart);

        //si es gerente limpia lo seleccionado, luego filtra
        if(!getTypePerfil().getPerfil().equals(Profile.ADESORA)){
            clearCartDB();
        } else {
            filterCartDB("");//mostrar todo el catalogo
        }
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onAddCartClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onAddCartClick: "+String.valueOf(row));
        testAddCart(row);
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onIncreaseClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onIncreaseClick, pos:  "+row);
        increaseCart(row);
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onDecreaseClick(Catalogo dataRow, int row) {
        Log.e(TAG,"onDecreaseClick, pos:  "+row);
        int cantidad = dataRow.getCantidad();
        if(cantidad>0) {
            if(cantidad==1){
                testRemoveCart(row);//pregunta si quiere eliminar
            } else {
                decreaseCart(row, cantidad);
            }
        }
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onAddEditableClick(boolean isEditable) {
        Log.e(TAG,"onAddEditableClick, status:  "+isEditable);
        if(viewParent != null){
            viewParent.productsEditable(isEditable);
        }
    }


    @Override
    protected void onLoadedView() {

    }

    private Profile getTypePerfil(){
        String jsonPerfil = mPreferences.getJSON_TypePerfil(getActivity());
        if(jsonPerfil != null){
            return new Gson().fromJson(jsonPerfil, Profile.class);
        }
        msgToast("Hubo un problema desconocido, se recomienda cerrar sesión e iniciar nuevamente");
        return null;
    }

    //antes de enviar
    public boolean validate(){
        if(listFilterCart.size()>0){
            for(int i=0;i<listFilterCart.size(); i++){
                if (listFilterCart.get(i).getCantidad() != listFilterCart.get(i).getCantidadServer())
                {
                    return true;
                }
            }
        }

        return false;
    }


    public void testAddCart(int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.agregar), getString(R.string.desea_agregar_pedido));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    addCart(row);
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }


    public void testRemoveCart(int row){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.remover), getString(R.string.desea_remover_pedido));
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    removeCart(row);
            }
        });
        simpleDialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    String textFilter="";
    public void filterCart(String textFilter){
        this.textFilter = textFilter;
        filterCartDB(textFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume()");
        //setSelectedItem(oldItem);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause()");
    }

    public void pedidoEndviadoexitosamente(){
        Log.v(TAG, "pedidoEndviadoexitosamente... -----------------------------------");
        if(listFilterCart.size()>0) {
            try {
                realm.beginTransaction();
                for(int position=0; position<listFilterCart.size(); position++) {
                        //se edita para que la cantidad actual sea la enviada

                        listFilterCart.get(position).setCantidadServer(listFilterCart.get(position).getCantidad());
                }
                realm.commitTransaction();
                Log.v(TAG, "pedidoEndviadoexitosamente... --------------  OK  --------------");


                //adapter_cart.notifyDataSetChanged();// esto no actualiza a los que se eliminaron, se procedio a filtar nuevamente
                filterCart(textFilter);
            } catch (Throwable e) {
                Log.v(TAG, "pedidoEndviadoexitosamente... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                //break;
                throw e;
            }
        }
    }

    private void increaseCart(int position){
        Log.i(TAG, "increaseCart(), pos: "+position);
        if(position>-1 && position<listFilterCart.size()) {
            realm.beginTransaction();
            try {
                listFilterCart.get(position).setCantidad(listFilterCart.get(position).getCantidad()+1);
                adapter_cart.notifyItemChanged(position);
                updateTotal();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "increaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void decreaseCart(int position, int cantidad){
        Log.i(TAG, "decreaseCart(), pos: "+position);
        if(position>-1 && position<listFilterCart.size()) {
            realm.beginTransaction();
            try {
                listFilterCart.get(position).setCantidad(cantidad - 1);
                adapter_cart.notifyItemChanged(position);
                updateTotal();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "decreaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void addCart(int position){
        Log.i(TAG, "addCart(), pos: "+position);
        if(position>-1 && position<listFilterCart.size()) {
            realm.beginTransaction();
            try {
                listFilterCart.get(position).setCantidad(1);// se predetermina cantidad en 1
                adapter_cart.notifyItemChanged(position);
                updateTotal();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "addCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void removeCart(int position){
        Log.i(TAG, "removeCart(), pos: "+position);
        if(position>-1 && position<listFilterCart.size()) {
            realm.beginTransaction();
            try {

                listFilterCart.get(position).setCantidad(0);// se predetermina cantidad en 1
                realm.commitTransaction();

                adapter_cart.notifyItemChanged(position);
                updateTotal();

                filterCartDB(textFilter);//esto se hace para que actualize la lista y quite el elemento
            } catch (Throwable e) {
                Log.v(TAG, "removeCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }


    public void filterCartDB(final String textFilter){
        Log.v(TAG,"filterCatalogoDB... ---------------filterCatalogoDB--------------");
        realm.beginTransaction();
        try {
            listFilterCart.clear();
            querycat = realm.where(Catalogo.class)
                    .beginGroup()
                        .greaterThan("cantidad",0)
                        .or()
                        .greaterThan("cantidadServer",0)
                    .endGroup()
                    .contains("id",textFilter)

                    .findAllSorted("time").sort("time", Sort.DESCENDING);
                    //.findAll();

            listFilterCart.addAll(querycat);

            adapter_cart.notifyDataSetChanged();
            updateTotal();

            realm.commitTransaction();
        } catch(Throwable e) {
            Log.v(TAG,"filterCatalogoDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }


    public void sincCatalogoDB(List<DetalleProductos> listServer, boolean edit){//en modeo edicion, no se toma en cuenta si hay o no pedidos locales
        //en modo nuevo pedido, si hay algo local se deja tal cual
        Log.v(TAG,"sincCatalogoDB... ---------------sincCatalogoDB--------------");
        if( (listFilterCart.size()<1 || edit) && listServer.size()>0) {
            realm.beginTransaction();
            try {
                //listFilter.clear();
                for (int i = 0; i < listServer.size(); i++) {
                    Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", listServer.get(i).getId()).findFirst();// se busca el id
                    if (catalogo != null && catalogo.getId().equals(listServer.get(i).getId())) {//se actualiza localmente lo que trae el API
                        catalogo.setCantidad(listServer.get(i).getCantidad());
                        catalogo.setCantidadServer(listServer.get(i).getCantidad());
                    } else if(catalogo==null){//si no existe, es xq hay que eliminarlo
                        //este caso supone que se modifico el catalogo
                    }
                }

                //debe verificar cuales no llegaron xq fieron eliminados
                for (int i = 0; i < listFilterCart.size(); i++) {
                    //se busca si hay algun elemento que dejo de estar disponible
                    for(int j=0; j<listServer.size();j++){
                        if(listServer.get(j).getId().equals(listFilterCart.get(i).getId())){///si no existe el elemento en la lista actual del server
                            break;//si encuentra el elemento, busca el siguiente
                        }
                        if(j>=listServer.size()-1)//llego al final de for y no encontro el elemento
                            listFilterCart.get(i).deleteFromRealm();
                    }

                }

                //listFilter.addAll(querycat);
                //adapter_cart.notifyDataSetChanged();
                realm.commitTransaction();
                Log.v(TAG, "sincCatalogoDB... ---------------  OK  --------------");
            } catch (Throwable e) {
                Log.v(TAG, "sincCatalogoDB... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }


    public void clearCartDB(){
        RealmResults<Catalogo> querycat;
        List<Catalogo> catalogoList = new ArrayList<>();
        Log.v(TAG,"clearCartDB... ---------------clearCartDB--------------");
        realm.beginTransaction();
        try {
            catalogoList.clear();
            querycat = realm.where(Catalogo.class)
                    .beginGroup()
                    .greaterThan("cantidad",0)
                    .or()
                    .greaterThan("cantidadServer",0)
                    .endGroup()
                    .findAllSorted("time").sort("time", Sort.DESCENDING);
            //.findAll();
            catalogoList.addAll(querycat);

            for (int i = 0; i < catalogoList.size(); i++) {
                catalogoList.get(i).setCantidad(0);
                catalogoList.get(i).setCantidadServer(0);
            }

            realm.commitTransaction();
        } catch(Throwable e) {
            Log.v(TAG,"clearCartDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }

    private void updateTotal(){
        if(viewParent != null){
            viewParent.updateTotal();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        adapter_cart.setEnable(enable);
        adapter_cart.notifyDataSetChanged();
        updateTotal();
    }

    public List<Catalogo> getListFilterCart() {
        return listFilterCart;
    }

    public void clearEditable(){
        adapter_cart.clearEditable();
    }

    //Parent Fragment
    private BasePedido viewParent;
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof BasePedido) {
            viewParent = (BasePedido) childFragment;
        } else {
            Log.e(TAG, "is not OnInteractionActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewParent = null;
    }

}
