package com.dupreincaperu.dupree.mh_fragments_menu;


import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.CampanaController;
import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.util.preferences.DataStore;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemCampana;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.ListItemPanelGte;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPanelGte;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.MenuActivity;
import com.dupreincaperu.dupree.MenuListener;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentPanelGerenteBinding;
import com.dupreincaperu.dupree.mh_adapters.PanelGteListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SingleListDialog;
import com.dupreincaperu.dupree.mh_holders.PanelGteHolder;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PanelGerenteFragment extends BaseFragment implements PanelGteHolder.Events{
    private final String TAG = PanelGerenteFragment.class.getName();

    private ReportesController reportesController;
    private CampanaController campanaController;

    private List<ItemCampana> campHttp;
    private List<ItemPanelGte> panelGteDetails;


    private PanelGteListAdapter adapter_panelGte;
    protected DataStore dataStore;

    public PanelGerenteFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_panel_gerente;
    }

    private FragmentPanelGerenteBinding binding;
    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentPanelGerenteBinding) view;

        reportesController = new ReportesController(getContext());
        campanaController = new CampanaController(getContext());

        binding.rcvPanelGrnte.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.rcvPanelGrnte.setHasFixedSize(true);
        ((SimpleItemAnimator) binding.rcvPanelGrnte.getItemAnimator()).setSupportsChangeAnimations(false);

        binding.fabMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuListener != null) {
                    menuListener.gotoMenuPage(R.id.menu_lat_bandeja_entrada);
                }
            }
        });
        campHttp = new ArrayList<>();
        panelGteDetails =new ArrayList<>();

        adapter_panelGte = new PanelGteListAdapter(panelGteDetails, this);
        binding.rcvPanelGrnte.setAdapter(adapter_panelGte);

        //refresh pantalla
        binding.rcvPanelGrnte.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                obtainDetailCamp();
            }
        });

        dataStore = new DataStore(getActivity());

        binding.rcvPanelGrnte.setOnClickListener(mListenerClick);
    }

    @Override
    protected void onLoadedView() {
        obtainDetailCamp();
    }

    View.OnClickListener mListenerClick =
            new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.rcvPanelGrnte:
                            showList(getString(R.string.campana_two_points), getNameCampana(campHttp));
                            break;
                    }
                }
            };

    public void showList(String title, List<ModelList> data){
        SingleListDialog d = new SingleListDialog();
        d.loadData(title, data, "", new SingleListDialog.ListenerResponse() {
            @Override
            public void result(ModelList item) {
                obtainDetailCamp();
            }
        });
        d.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    private void addDetailCampana(ListItemPanelGte items){

        panelGteDetails.clear();
        if(items != null) {
            if(items.getFechaCierre() != null){
                dataStore.setCampaniaCierre(items.getFechaCierre());
            }else{
                dataStore.setCampaniaCierre(String.valueOf(items.getCampana()));
            }

            panelGteDetails.addAll(items.getPanelGteDetails());

            if(items.getCantidadMensajes()!=null)
                binding.fabMessages.setTitle(items.getCantidadMensajes()+" Mensajes");

            binding.txtCorte.setText(items.getFechaCorte());

            binding.txtAnimation.setVisibility(View.VISIBLE);
            binding.txtAnimation.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            binding.txtAnimation.setSelected(true);
            binding.txtAnimation.setSingleLine(true);
            binding.txtAnimation.setText(items.getFechaCierre());

            adapter_panelGte.notifyDataSetChanged();
        }
    }

    private void obtainDetailCamp(){
        showProgress();
        DataStore dataStore  ;
        dataStore =  DataStore.getInstance(getContext());
        String token = dataStore.getTokenSesion();

        reportesController.getPanelGrte(token, new TTResultListener<ListPanelGte>() {
            @Override
            public void success(ListPanelGte result) {
                dismissProgress();
                addDetailCampana(result.getListDetail());
                binding.refresh.setRefreshing(false);
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    public List<ModelList> getNameCampana(List<ItemCampana> campHttp){
        List<ModelList> respose = new ArrayList<>();

        for(int i=0; i<campHttp.size(); i++){
            respose.add(new ModelList(i, campHttp.get(i).getNombre()));
        }

        return respose;
    }

    MenuListener menuListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuActivity) {
            menuListener = (MenuActivity) context;
        } else {
            throw new RuntimeException(context.toString().concat(" is not OnInteractionActivity"));
        }

    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuListener = null;
    }

    @Override
    public void onClickRoot(ItemPanelGte dataRow, int row) {

    }
}
