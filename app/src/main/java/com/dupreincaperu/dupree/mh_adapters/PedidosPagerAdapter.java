package com.dupreincaperu.dupree.mh_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.Carrito.CarritoFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.historial.FacturasFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.ofertas.OffersFragment;

/**
 * Created by marwuinh@gmail.com on 5/8/17.
 */

public class PedidosPagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG=PedidosPagerAdapter.class.getName();
    public final int numPages = 2;
    public static final int PAGE_CARRITO=0;
    public static final int PAGE_OFFERS=1;
    //public static final int PAGE_HISTORICAL=2;


    //private ProductsFragment productsFragment;
    private CarritoFragment carritoFragment;
    private OffersFragment offersFragment;
    //private FacturasFragment historialFragment;
    private String fragmentPrepedidos;


    public PedidosPagerAdapter(FragmentManager fm) {
        super(fm);
        Log.e(TAG, "create -> PedidosPagerAdapter");
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_CARRITO:
                Log.e(TAG, "create -> PAGE_CART");
                carritoFragment = CarritoFragment.newInstance();
                return carritoFragment;
            case PAGE_OFFERS:
                Log.e(TAG, "create -> PAGE_OFFERS");
                offersFragment = OffersFragment.newInstance();
                return offersFragment;
            /*case PAGE_HISTORICAL:
                Log.e(TAG, "create -> PAGE_HISTORICAL");
                historialFragment = FacturasFragment.newInstance();
                return historialFragment;*/
        }
        return null;
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public CarritoFragment getCarritoFragment() {
        return carritoFragment;
    }

    public OffersFragment getOffersFragment() {
        return offersFragment;
    }

    /*public FacturasFragment getHistorialFragment() {
        return historialFragment;
    }*/
}
