package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones;


import androidx.databinding.ViewDataBinding;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncorpTodosBinding;
import com.dupreincaperu.dupree.mh_adapters.NuevasPagerAdapter;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Incorp_Nuevas_Fragment extends BaseFragment implements IncorporacionesVPages{
    private final String TAG = Incorp_Nuevas_Fragment.class.getName();

    private NuevasPagerAdapter adapterFragIncorp;
    private FragmentIncorpTodosBinding binding;

    public Incorp_Nuevas_Fragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    private int initPage;
    public void loadData(int initPage, Profile perfil){
        this.initPage=initPage;
        this.perfil=perfil;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incorp_todos;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentIncorpTodosBinding) view;

        binding.swipeIncorp.setOnRefreshListener(mOnRefreshListener);
        binding.swipeIncorp.setEnabled(false);

        binding.pagerIncorp.setPagingEnabled(false);// esto lo permite la libreria externa

        //adapterFragIncorp = new MH_PagerAdapter_Incorporacion(getFragmentManager(), perfil);
        adapterFragIncorp = new NuevasPagerAdapter(getChildFragmentManager(), perfil);
        binding.pagerIncorp.setAdapter(adapterFragIncorp);
        binding.pagerIncorp.addOnPageChangeListener(mOnPageChangeListener);

        binding.tabsIncorp.setupWithViewPager(binding.pagerIncorp);
        createTabIcons();

        binding.pagerIncorp.setCurrentItem(initPage);
    }

    @Override
    protected void onLoadedView() {

    }

    private int[] title = {R.string.posibles_asesoras, R.string.lista_de_posibles_asesoras};
    private int[] icon = {R.drawable.ic_person_outline_white_24dp, R.drawable.ic_people_white_24dp};

    private void createTabIcons() {

        for(int i=0; i<title.length; i++){
            TextView tab = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_item, null);
            tab.setText(title[i]);

            Drawable mDrawable = getResources().getDrawable(icon[i]);
            mDrawable.setColorFilter(new
                    PorterDuffColorFilter(getResources().getColor(R.color.azulDupree), PorterDuff.Mode.MULTIPLY));

            tab.setCompoundDrawablesWithIntrinsicBounds(null, mDrawable, null, null);
            binding.tabsIncorp.getTabAt(i).setCustomView(tab);
        }
    }

    /**
     * Eventos SwipeRefreshLayout
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener
            = new SwipeRefreshLayout.OnRefreshListener(){
        @Override
        public void onRefresh() {

        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position += 3; //se le suma 3
            Log.i(TAG,"onPageSelected Page: "+position);
            switch (position){
                case NuevasPagerAdapter.PAGE_POSI_ASES:
                    break;
                case NuevasPagerAdapter.PAGE_LIST_POSI_ASES:
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    boolean update=false;
    //MARK: IncorporacionesVPages
    @Override
    public void gotoPage(int pos) {
        update=true;
        binding.pagerIncorp.setCurrentItem(pos);
    }

}
