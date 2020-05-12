package com.dupreincaperu.dupree.mh_adapters.base.TabViewPager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.dupreincaperu.dupree.mh_fragments_menu.pedidos.Carrito.CarritoFragment;

/**
 * Created by marwuinh@gmail.com on 5/8/17.
 */

public class PrePedidosPagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG= PrePedidosPagerAdapter.class.getName();
    public final int numPages = 1;
    public static final int PAGE_CARRITO=0;


    //private ProductsFragment productsFragment;
    private CarritoFragment carritoFragment;
    private String fragmentPrepedidos;


    public PrePedidosPagerAdapter(FragmentManager fm) {
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
          default:
              return null;
        }
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public CarritoFragment getCarritoFragment() {
        return carritoFragment;
    }

}
