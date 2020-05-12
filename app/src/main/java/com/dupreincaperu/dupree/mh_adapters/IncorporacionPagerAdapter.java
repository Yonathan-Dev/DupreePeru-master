package com.dupreincaperu.dupree.mh_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevas;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Preinscripcion.PreinsciptionFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.registradas.IncorpListaRegistFragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.Incorp_ListPre_Fragment;
import com.dupreeinca.lib_api_rest.model.view.Profile;

/**
 * Created by cloudemotion on 5/8/17.
 */

public class IncorporacionPagerAdapter extends FragmentStatePagerAdapter {
    private final String TAG=IncorporacionPagerAdapter.class.getName();
    public final int numPages=3;
    public static final int PAGE_PREINSCRIPCION=0;
    public static final int PAGE_LIST_PRE=1;
    public static final int PAGE_LIST_REG=2;

    private PreinsciptionFragment newPreinsciptionFragment;
    private Incorp_ListPre_Fragment incorp_listPre_fragment;
    private IncorpListaRegistFragment registradasFragment;


    private Profile perfil;
    private PosiblesNuevas posiblesNuevas;

    public IncorporacionPagerAdapter(FragmentManager fm, Profile perfil) {
        super(fm);
        this.perfil=perfil;
    }

    public IncorporacionPagerAdapter(FragmentManager fm, Profile perfil, PosiblesNuevas posiblesNuevas) {
        super(fm);
        this.perfil=perfil;
        this.posiblesNuevas = posiblesNuevas;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case PAGE_PREINSCRIPCION:
                newPreinsciptionFragment = PreinsciptionFragment.newInstance();
                if(posiblesNuevas!=null)
                    newPreinsciptionFragment.loadData(posiblesNuevas);
                return newPreinsciptionFragment;
            case PAGE_LIST_PRE:
                incorp_listPre_fragment = Incorp_ListPre_Fragment.newInstance();
                incorp_listPre_fragment.loadData(perfil);
                return incorp_listPre_fragment;
            case PAGE_LIST_REG:
                registradasFragment = IncorpListaRegistFragment.newInstance();
                return registradasFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return numPages;
    }

    public PreinsciptionFragment getNewPreinsciptionFragment() {
        return newPreinsciptionFragment;
    }

    public Incorp_ListPre_Fragment getIncorp_listPre_fragment() {
        return incorp_listPre_fragment;
    }

    public IncorpListaRegistFragment getRegistradasFragment() {
        return registradasFragment;
    }
}
