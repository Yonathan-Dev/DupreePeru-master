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

import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevas;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncorpTodosBinding;
import com.dupreincaperu.dupree.mh_adapters.IncorporacionPagerAdapter;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Incorp_Todos_Fragment extends BaseFragment implements IncorporacionesVPages{
    private final String TAG = Incorp_Todos_Fragment.class.getName();

    private IncorporacionPagerAdapter adapterFragIncorp;
    private FragmentIncorpTodosBinding binding;

    public Incorp_Todos_Fragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    private int initPage;
    private PosiblesNuevas posiblesNuevas;

    public void loadData(int initPage, Profile perfil){
        this.initPage=initPage;
        this.perfil=perfil;
    }

    public void loadData(int initPage, Profile perfil, PosiblesNuevas posiblesNuevas){
        this.initPage=initPage;
        this.perfil=perfil;
        this.posiblesNuevas = posiblesNuevas;
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
        binding.pagerIncorp.setPagingEnabled(false);


        if(posiblesNuevas!=null)//con esto se identifica que viene de la forma de posibles nuevas.
        {
            adapterFragIncorp = new IncorporacionPagerAdapter(getChildFragmentManager(), perfil,posiblesNuevas);
        }
        else
        {
            adapterFragIncorp = new IncorporacionPagerAdapter(getChildFragmentManager(), perfil);
        }

        binding.pagerIncorp.setAdapter(adapterFragIncorp);
        binding.pagerIncorp.addOnPageChangeListener(mOnPageChangeListener);
        binding.tabsIncorp.setupWithViewPager(binding.pagerIncorp);
        createTabIcons();
        binding.pagerIncorp.setCurrentItem(initPage);

    }

    @Override
    protected void onLoadedView() {

    }

    private int[] title = {R.string.preinscripcion, R.string.listado, R.string.registradas};
    private int[] icon = {R.drawable.ic_person_outline_white_24dp, R.drawable.ic_people_white_24dp, R.drawable.baseline_assignment_white_24};


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
            Log.i(TAG,"onPageSelected Page: "+position);
            switch (position){
                case IncorporacionPagerAdapter.PAGE_PREINSCRIPCION:
                    //setSelectedItem(R.id.navigation_home);
                    break;
                case IncorporacionPagerAdapter.PAGE_LIST_PRE:
                    //setSelectedItem(R.id.navigation_asesora);
                    if(update){
                        update=false;
                        adapterFragIncorp.getIncorp_listPre_fragment().updateList();
                    }
                    break;
                case IncorporacionPagerAdapter.PAGE_LIST_REG:

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

//    public String nombre="", cedula="";
//    public boolean modeEdit;
//    @Override
//    public void gotoPageInscription(String nombre, String cedula, boolean modeEdit){
//        this.nombre = nombre;
//        this.cedula = cedula;
//        this.modeEdit = modeEdit;
//        binding.pagerIncorp.setCurrentItem(MH_PagerAdapter_Incorporacion.PAGE_INCRIPCION);
//    }
}
