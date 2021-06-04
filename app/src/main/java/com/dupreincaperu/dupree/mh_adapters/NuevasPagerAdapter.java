package com.dupreincaperu.dupree.mh_adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.Incorp_ListNuevas_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.posibles.PosiblesAsesorasFragment;

/**
 * Created by cloudemotion on 5/8/17.
 */

public class NuevasPagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG= NuevasPagerAdapter.class.getName();
    public final int numPages=2;
    public static final int PAGE_POSI_ASES=0;
    public static final int PAGE_LIST_POSI_ASES=1;

    private PosiblesAsesorasFragment posiblesAsesorasFragment;
    private Incorp_ListNuevas_Fragment incorp_listNuevas_fragment;


    private Profile perfil;
    public NuevasPagerAdapter(FragmentManager fm, Profile perfil) {
        super(fm);
        this.perfil=perfil;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_POSI_ASES:
                posiblesAsesorasFragment = PosiblesAsesorasFragment.newInstance();
                posiblesAsesorasFragment.loadData(perfil);
                return posiblesAsesorasFragment;
            case PAGE_LIST_POSI_ASES:
                incorp_listNuevas_fragment = Incorp_ListNuevas_Fragment.newInstance();
                incorp_listNuevas_fragment.loadData(perfil);
                return incorp_listNuevas_fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public PosiblesAsesorasFragment getPosiblesAsesorasFragment() { return posiblesAsesorasFragment; }

    public Incorp_ListNuevas_Fragment getIncorp_listNuevas_fragment() {
        return incorp_listNuevas_fragment;
    }

}
