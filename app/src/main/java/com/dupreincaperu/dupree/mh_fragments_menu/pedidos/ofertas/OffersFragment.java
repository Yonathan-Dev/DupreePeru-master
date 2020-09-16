package com.dupreincaperu.dupree.mh_fragments_menu.pedidos.ofertas;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentProductsBinding;
import com.dupreincaperu.dupree.mh_adapters.OffersListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.BasePedido;
import com.dupreincaperu.dupree.mh_holders.OfertasHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends BaseFragment implements OfertasHolder.Events{
    private final String TAG = OffersFragment.class.getName();

    RealmResults<Oferta> querycat;
    private List<Oferta> listFilterOffers = new ArrayList<>();//, listSelected;
    private OffersListAdapter adapter_catalogo;
    private Realm realm;

    private boolean enable=false;
    public OffersFragment() {
        // Required empty public constructor
    }

    public static OffersFragment newInstance() {
        Bundle args = new Bundle();
        
        OffersFragment fragment = new OffersFragment();
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
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        binding.recycler.setHasFixedSize(true);


        //listPremios = new ArrayList<>();
        listFilterOffers = new ArrayList<>();
        adapter_catalogo = new OffersListAdapter(listFilterOffers, this);

        binding.recycler.setAdapter(adapter_catalogo);
    }

    //MARK: OfertasHolder.Events
    @Override
    public void onAddCartClick(Oferta dataRow, int row) {
        Log.e(TAG,"onAddCartClick: "+String.valueOf(row));
        testAddCart(row);
    }

    //MARK: OfertasHolder.Events
    @Override
    public void onIncreaseClick(Oferta dataRow, int row) {
        Log.e(TAG,"onIncreaseClick, pos:  "+row);
        increaseCart(row);
    }

    //MARK: OfertasHolder.Events
    @Override
    public void onDecreaseClick(Oferta dataRow, int row) {
        Log.e(TAG,"onDecreaseClick, pos:  "+row);
        int cantidad =listFilterOffers.get(row).getCantidad();
        if(cantidad>0) {
            if(cantidad==1){
                testRemoveCart(row);//pregunta si quiere eliminar
            } else {
                decreaseCart(row, cantidad);
            }
        }
    }

    //MARK: OfertasHolder.Events
    @Override
    public void onAddEditableClick(boolean isEditable) {
        Log.e(TAG,"onAddEditableClick, status:  "+isEditable);
        if(viewParent != null){
            viewParent.offersEditable(isEditable);
        }
    }

    @Override
    protected void onLoadedView() {
        //QUIZAS INICESARIO, PARA NO DEJAR LA PANTALLA VACIA
        filterOffersDB("");//mostrar todo el catalogo
    }

    //antes de enviar
    public boolean validate(){
        if(listFilterOffers.size()>0){
            for(int i=0;i<listFilterOffers.size(); i++){
                if (listFilterOffers.get(i).getCantidad() != listFilterOffers.get(i).getCantidadServer()) {
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

    private void increaseCart(int position){
        Log.i(TAG, "increaseCart(), pos: "+position);
        if(position>-1 && position<listFilterOffers.size()) {
            try {
                realm.beginTransaction();
                listFilterOffers.get(position).setCantidad(listFilterOffers.get(position).getCantidad()+1);
                adapter_catalogo.notifyItemChanged(position);
                realm.commitTransaction();

                updateCarrito();
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
        if(position>-1 && position<listFilterOffers.size()) {
            try {
                realm.beginTransaction();
                listFilterOffers.get(position).setCantidad(cantidad - 1);
                adapter_catalogo.notifyItemChanged(position);
                realm.commitTransaction();

                updateCarrito();
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
        if(position>-1 && position<listFilterOffers.size()) {
            try {
                realm.beginTransaction();
                listFilterOffers.get(position).setCantidad(1);// se predetermina cantidad en 1
                listFilterOffers.get(position).setTime(Calendar.getInstance().getTimeInMillis());//controla el ultimo item agregado a la lista (cambiar a un int y controlar incremento)
                adapter_catalogo.notifyItemChanged(position);
                realm.commitTransaction();

                updateCarrito();
            } catch (Throwable e) {
                Log.v(TAG, "addCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

//    private void updateTotal(){
//        Log.i(TAG, "updateTotal()");
//        if(viewParent != null){
//            viewParent.updateTotal();
//        }
//    }

    private void updateCarrito(){
        Log.i(TAG, "updateTotal()");
        if(viewParent != null){
            viewParent.updateCarrito();
        }
    }

    private void removeCart(int position){
        Log.i(TAG, "removeCart(), pos: "+position);
        if(position>-1 && position<listFilterOffers.size()) {
            try {
                realm.beginTransaction();
                listFilterOffers.get(position).setCantidad(0);// se predetermina cantidad en 1
                adapter_catalogo.notifyItemChanged(position);
                realm.commitTransaction();

                updateCarrito();
            } catch (Throwable e) {
                Log.v(TAG, "removeCart... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    public void sincOfertasDB(List<Oferta> listServer, boolean edit){
        Log.v(TAG,"sincOfertasDB... ---------------sincOfertasDB--------------");
        // si no hay ofertas, se agrega a la base de datos.....
        if(listServer.size()>0) {

            querycat = realm.where(Oferta.class).findAll();

            if (querycat.size() < 1) {
                //si no hay nada local, se sincroniza cantidad server y local iguales y se escribe toodo tal cual localmente
                for(int i=0; i<listServer.size();i++){
                    listServer.get(i).setCantidadServer(listServer.get(i).getCantidad());//se igualan las cantidades local y del server
                }

                writeOfertas(listServer);//si no hay ofertas, se agregan
            } else {
                updateOffers(listServer, edit);
                filterOffersDB("");
            }
        }
    }

    private void updateOffers(List<Oferta> listServer, boolean edit){
        // si hay tanto ofertas locales, como remotas...
        /// se da prioridad a las locales, eliminando las que ya no existan en la nueva lista consultada
        realm.beginTransaction();

        try {
            //listFilter.clear();
            for (int i = 0; i < listServer.size(); i++) {
                Oferta oferta = realm.where(Oferta.class).equalTo("id", listServer.get(i).getId()).findFirst();// se busca el id
                if(oferta==null){//si NO existe, se agrega
                    listServer.get(i).setCantidadServer(listServer.get(i).getCantidad());//se agrega pero sincronizada
                    realm.insertOrUpdate(listServer.get(i));
                } else {/// en caso de que exista
                    if(edit){//si esta en modo edicion, se prevalece la del server, de lo contrario queda la local
                        oferta.setCantidad(listServer.get(i).getCantidad());
                        oferta.setCantidadServer(listServer.get(i).getCantidad());
                    }
                }
            }

            for (int i = 0; i < listFilterOffers.size(); i++) {
                //se busca si hay algun elemento que dejo de estar disponible
                if(!listServer.contains(listFilterOffers.get(i))){///si no existe el elemento en la lista actual del server
                    listFilterOffers.get(i).deleteFromRealm();
                }
            }

            realm.commitTransaction();

        } catch (Throwable e) {
            Log.v(TAG, "updateOffers()... ---------------error--------------");
            if (realm.isInTransaction()) {
                realm.cancelTransaction();
            }
            throw e;
        }
    }

    public void writeOfertas(final List<Oferta> listServer){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.insert(listServer);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.v(TAG,"writeOfertas... ---------------ok--------------");
                Log.v(TAG, ": " + new Gson().toJson(listServer));
                filterOffersDB("");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e(TAG,"writeOfertas... ---------------error--------------");
                Log.e(TAG,"writeOfertas... "+error.getMessage());
                //realm.close();
            }
        });
    }

    public void filterOffersDB(final String textFilter){
        Log.e(TAG, "filterOffersDB(INIT), textFilter: "+textFilter);
        if(showOffers) {
            Log.v(TAG, "filterOffersDB... ---------------filterOffersDB--------------");
            realm.beginTransaction();
            try {
                listFilterOffers.clear();

                String []fieldNames={"cantidad","cantidadServer"};
                Sort sort[]={Sort.DESCENDING,Sort.DESCENDING};

                querycat = realm.where(Oferta.class).contains("id", textFilter)
                        .findAllSorted(fieldNames, sort);
                if (querycat.size() > 0)
                    listFilterOffers.addAll(querycat);

                adapter_catalogo.notifyDataSetChanged();

                realm.commitTransaction();

//                updateTotal();///VERIFICAR SI ES NECESARIO

                Log.e(TAG, "filterOffersDB(END)");
            } catch (Throwable e) {
                Log.v(TAG, "filterOffersDB... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    public void ofertaEndviadoexitosamente(){
        if(showOffers) {
            Log.v(TAG, "ofertaEndviadoexitosamente... ---------------ofertaEndviadoexitosamente--------------");
            realm.beginTransaction();
            try {
                listFilterOffers.clear();


                String []fieldNames={"cantidad","cantidadServer"};
                Sort sort[]={Sort.DESCENDING,Sort.DESCENDING};

                querycat = realm.where(Oferta.class).contains("id", "")
                        .findAllSorted(fieldNames, sort);

                //querycat = realm.where(Oferta.class).findAllSorted("cantidad",Sort.DESCENDING);
                if (querycat.size() > 0) {
                    listFilterOffers.addAll(querycat);
                    for(int i=0; i<listFilterOffers.size();i++){
                        listFilterOffers.get(i).setCantidadServer(listFilterOffers.get(i).getCantidad());
                    }
                }

                adapter_catalogo.notifyDataSetChanged();
//                updateTotal();
                realm.commitTransaction();
            } catch (Throwable e) {
                Log.v(TAG, "ofertaEndviadoexitosamente... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    public void deleteOferta(){
        Log.e(TAG, "deleteOferta2()");
        if(showOffers) {
            Log.v(TAG, "deleteOferta2... ---------------filterOdeleteOferta2ffersDB--------------");
            realm.beginTransaction();
            try {
                realm.delete(Oferta.class);
                realm.commitTransaction();

                filterOffersDB("");
            } catch (Throwable e) {
                Log.v(TAG, "deleteOferta2... ---------------error--------------");
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
                throw e;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private boolean showOffers=false;
    public void setShowOffers(boolean showOffers) {
        this.showOffers = showOffers;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        adapter_catalogo.setEnable(enable);
        adapter_catalogo.notifyDataSetChanged();
        updateCarrito();
    }

    public List<Oferta> getListFilterOffers() {
        return listFilterOffers;
    }

    public void clearEditable(){
        adapter_catalogo.clearEditable();
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
