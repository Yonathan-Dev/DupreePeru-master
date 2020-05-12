package com.dupreincaperu.dupree.mh_fragments_menu.pedidos.Carrito;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.dupreeinca.lib_api_rest.model.dto.response.DetalleProductos;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.ItemCarrito;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentProductsBinding;
import com.dupreincaperu.dupree.mh_adapters.CarritoListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.BasePedido;
import com.dupreincaperu.dupree.mh_holders.CarritoHolder;
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
public class CarritoFragment extends BaseFragment implements CarritoHolder.Events{

    private final String TAG = CarritoFragment.class.getName();
    public static  final String BROACAST_DATA="broacast_data";

    private List<ItemCarrito> listFilterCart = new ArrayList<>();//, listSelected;
    private CarritoListAdapter listAdapter;

    private Realm realm;
    private boolean enable=true;

    public CarritoFragment() {
        // Required empty public constructor
    }

    public static CarritoFragment newInstance() {
        Bundle args = new Bundle();

        CarritoFragment fragment = new CarritoFragment();
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
        listAdapter = new CarritoListAdapter(listFilterCart, this);

        binding.recycler.setAdapter(listAdapter);

        //si es gerente limpia lo seleccionado, luego filtra

        if(!dataStore.getTipoPerfil().getPerfil().equals(Profile.ADESORA)){
            clearCartDB();
        } else {
            updateCarrito();//mostrar todo el catalogo
        }
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onAddCartClick(ItemCarrito dataRow, int row) {
        Log.e(TAG,"onAddCartClick: "+String.valueOf(row));
        testAddCart(row);
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onIncreaseClick(ItemCarrito dataRow, int row) {
        Log.e(TAG,"onIncreaseClick, pos:  "+row);
        int cantidad = dataRow.getCantidad();
        increaseCart(row, cantidad);
    }

    //MARK: CatalogoHolder.Events
    @Override
    public void onDecreaseClick(ItemCarrito dataRow, int row) {
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
                    RealmResults<Catalogo> query = realm.where(Catalogo.class).findAll();
                    for(int i=0; i<query.size(); i++){
                        query.get(i).setCantidadServer(query.get(i).getCantidad());
                    }
                realm.commitTransaction();
                Log.v(TAG, "pedidoEndviadoexitosamente... --------------  OK  --------------");
                updateCarrito();
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

    private void increaseCart(int i, int cantidad){
        Log.i(TAG, "increaseCart(), pos: "+i);
        if(i>-1 && i<listFilterCart.size()) {
            try {
                realm.beginTransaction();
                switch (listFilterCart.get(i).getType()){
                    case ItemCarrito.TYPE_CATALOGO:
                        Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(catalogo!=null) {
                            catalogo.setCantidad(cantidad + 1);
                        }
                        break;
                    case ItemCarrito.TYPE_OFFERTS:
                        Oferta oferta = realm.where(Oferta.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(oferta!=null) {
                            oferta.setCantidad(cantidad + 1);
                        }
                        break;
                }
                realm.commitTransaction();

                listFilterCart.get(i).setCantidad(cantidad + 1);
                listAdapter.notifyItemChanged(i);

                updateTotal();
            } catch (Throwable e) {
                Log.v(TAG, "increaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void decreaseCart(int i, int cantidad){
        Log.i(TAG, "decreaseCart(), pos: "+i);
        if(i>-1 && i<listFilterCart.size()) {
            try {
                realm.beginTransaction();
                switch (listFilterCart.get(i).getType()){
                    case ItemCarrito.TYPE_CATALOGO:
                        Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(catalogo!=null) {
                            catalogo.setCantidad(cantidad - 1);
                        }
                        break;
                    case ItemCarrito.TYPE_OFFERTS:
                        Oferta oferta = realm.where(Oferta.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(oferta!=null) {
                            oferta.setCantidad(cantidad - 1);
                        }
                        break;
                }
                realm.commitTransaction();

                listFilterCart.get(i).setCantidad(cantidad - 1);
                listAdapter.notifyItemChanged(i);

                updateTotal();
            } catch (Throwable e) {
                Log.v(TAG, "decreaseCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void addCart(int i){
        Log.i(TAG, "addCart(), pos: "+i);
        if(i>-1 && i<listFilterCart.size()) {
            try {
                realm.beginTransaction();
                switch (listFilterCart.get(i).getType()){
                    case ItemCarrito.TYPE_CATALOGO:
                        Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(catalogo!=null) {
                            catalogo.setCantidad(1);
                        }
                        break;
                    case ItemCarrito.TYPE_OFFERTS:
                        Oferta oferta = realm.where(Oferta.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(oferta!=null) {
                            oferta.setCantidad(1);
                        }
                        break;
                }
                realm.commitTransaction();

                listFilterCart.get(i).setCantidad(1);// se predetermina cantidad en 1
                listAdapter.notifyItemChanged(i);

                updateTotal();
            } catch (Throwable e) {
                Log.v(TAG, "addCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    private void removeCart(int i){
        Log.i(TAG, "removeCart(), pos: "+i);
        if(i>-1 && i<listFilterCart.size()) {
            try {
                realm.beginTransaction();
                switch (listFilterCart.get(i).getType()){
                    case ItemCarrito.TYPE_CATALOGO:
                        Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(catalogo!=null) {
                            catalogo.setCantidad(0);
                        }
                        break;
                    case ItemCarrito.TYPE_OFFERTS:
                        Oferta oferta = realm.where(Oferta.class).equalTo("id", listFilterCart.get(i).getId()).findFirst();
                        if(oferta!=null) {
                            oferta.setCantidad(0);
                        }
                        break;
                }
                realm.commitTransaction();

                listFilterCart.get(i).setCantidad(0);// se predetermina cantidad en 1
                listAdapter.notifyItemChanged(i);

//                updateTotal();

                updateCarrito();//esto se hace para que actualize la lista y quite el elemento
            } catch (Throwable e) {
                Log.v(TAG, "removeCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    public List<ItemCarrito> filterCatalogoDB(final String textFilter){
        List<ItemCarrito> listCarritos = new ArrayList<>();
        Log.v(TAG,"filterCatalogoDB... ---------------filterCatalogoDB--------------");
        try {
            realm.beginTransaction();
            RealmResults<Catalogo> query = realm.where(Catalogo.class)
                    .beginGroup()
                        .greaterThan("cantidad",0)
                        .or()
                        .greaterThan("cantidadServer",0)
                    .endGroup()
                    .contains("id",textFilter)
                    .findAllSorted("time").sort("time", Sort.DESCENDING);
            realm.commitTransaction();
            for (Catalogo item : query) {
                listCarritos.add(new ItemCarrito(item));
            }

        } catch(Throwable e) {
            Log.v(TAG,"filterCatalogoDB... ---------------error--------------");
            if(realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }

        return listCarritos;
    }

    public List<ItemCarrito> filterOffersDB(final String textFilter){
        List<ItemCarrito> listCarritos = new ArrayList<>();
        Log.e(TAG, "filterOffersDB(INIT), textFilter: "+textFilter);
//        if(showOffers) {
            Log.v(TAG, "filterOffersDB... ---------------filterOffersDB--------------");
            try {
                realm.beginTransaction();
                RealmResults<Oferta> query = realm.where(Oferta.class)
                        .beginGroup()
                            .greaterThan("cantidad",0)
                            .or()
                            .greaterThan("cantidadServer",0)
                        .endGroup()
                        .contains("id", textFilter)
                        .findAllSorted("time").sort("time", Sort.DESCENDING);
                realm.commitTransaction();

                for (Oferta item : query) {
                    listCarritos.add(new ItemCarrito(item));
                }

                Log.e(TAG, "filterOffersDB(END)");
            } catch (Throwable e) {
                Log.v(TAG, "filterOffersDB... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
//        }

        return listCarritos;
    }

    public void updateCarrito(){
        try{
            listFilterCart.clear();
            listFilterCart.addAll(filterCatalogoDB(""));
            listFilterCart.addAll(filterOffersDB(""));

            listAdapter.notifyDataSetChanged();

            updateTotal();
        }catch (Exception ex){

        }
    }

    public void sincCatalogoDB(List<DetalleProductos> listServer, boolean edit){//en modeo edicion, no se toma en cuenta si hay o no pedidos locales
        //en modo nuevo pedido, si hay algo local se deja tal cual
        Log.v(TAG,"sincCatalogoDB... ---------------sincCatalogoDB--------------");
        if( (listFilterCart.size()<1 || edit) && listServer.size()>0) {
            try {
                realm.beginTransaction();
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
                for (ItemCarrito item : listFilterCart) {
                    if(item.getType() == ItemCarrito.TYPE_CATALOGO) {//solo se analizan los prosutos, NO ofertas
                        //se busca si hay algun elemento que dejo de estar disponible
                        for (int j = 0; j < listServer.size(); j++) {
                            if (listServer.get(j).getId().equals(item.getId())) {///si no existe el elemento en la lista actual del server
                                break;//si encuentra el elemento, busca el siguiente
                            }
                            if (j >= listServer.size() - 1) {//llego al final de for y no encontro el elemento
                                Catalogo catalogo = realm.where(Catalogo.class).equalTo("id", item.getId()).findFirst();
                                if(catalogo!=null) {
                                    catalogo.deleteFromRealm();
                                }
                            }
                        }
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

        try {
            realm.beginTransaction();
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
        listAdapter.setEnable(enable);
        listAdapter.notifyDataSetChanged();
        updateTotal();
    }

    public List<ItemCarrito> getListFilterCart() {
        return listFilterCart;
    }

    public void clearEditable(){
        listAdapter.clearEditable();
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
