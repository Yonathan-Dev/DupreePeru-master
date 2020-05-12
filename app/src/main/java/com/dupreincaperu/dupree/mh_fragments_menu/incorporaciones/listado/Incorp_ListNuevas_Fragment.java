package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.controller.InscripcionController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.ListPre;
import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevas;
import com.dupreeinca.lib_api_rest.model.dto.request.PosiblesNuevasSend;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Posibles_Nuevas;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncorpListNuevasBinding;
import com.dupreincaperu.dupree.mh_CRUD.CRUDPosibles_Nuevas;
import com.dupreincaperu.dupree.mh_CRUD.ListPosiblesNuevasDTO;
import com.dupreincaperu.dupree.mh_adapters.IncorporacionPagerAdapter;
import com.dupreincaperu.dupree.mh_adapters.NuevasListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.Incorp_Todos_Fragment;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.IncorporacionesVPages;
import com.dupreincaperu.dupree.mh_holders.ListNuevasHolder;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Incorp_ListNuevas_Fragment extends BaseFragment implements ListNuevasHolder.Events{

    private final String TAG = Incorp_ListNuevas_Fragment.class.getName();
    private InscripcionController inscripcionController;

    private FragmentIncorpListNuevasBinding binding;
    private List<Posibles_Nuevas> list;
    private ListPosiblesNuevasDTO responseListPosiblesNuevas;
    private NuevasListAdapter adapter_listNuevas;

    private TextView[] dots;
    public Incorp_ListNuevas_Fragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    public static Incorp_ListNuevas_Fragment newInstance() {
        Bundle args = new Bundle();

        Incorp_ListNuevas_Fragment fragment = new Incorp_ListNuevas_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    ListPre requiredListNuevas;

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incorp_list_nuevas;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        onAttachFragment(getParentFragment());
        binding = (FragmentIncorpListNuevasBinding) view;

        binding.pagination.tvButtonLeft.setOnClickListener(mClickPaginator);
        binding.pagination.tvButtonRight.setOnClickListener(mClickPaginator);
        enableButton(binding.pagination.tvButtonLeft, false);
        enableButton(binding.pagination.tvButtonRight, false);

        binding.swipe.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.swipe.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter_listNuevas = new NuevasListAdapter(list,this);
        binding.swipe.recycler.setAdapter(adapter_listNuevas);
        binding.swipe.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                binding.swipe.refresh.setRefreshing(false);
            }
        });

        binding.btnEnviarTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataPosiblesNuevas();
                updateList();
            }
        });

        CRUDPosibles_Nuevas.asignePagina();//asignamos numero de pagina para cada registro.
    }

    @Override
    protected void onLoadedView() {
        inscripcionController = new InscripcionController(getContext());
        refresh();
    }

    @Override
    public void onClickRoot(Posibles_Nuevas dataRow, int row) {
        if(row>-1 && row<list.size()) {
            int index = 0;
            index = (dataRow.getTipo_docu() == "E") ? 1:index;
            index = (dataRow.getTipo_docu() == "N") ? 2:index;
            List<String> tipo_docu = Arrays.asList(getResources().getStringArray(R.array.typeIdenty));
            String tipo_docu_desc = tipo_docu.get(index);
            PosiblesNuevas posiblesNuevas = new PosiblesNuevas(tipo_docu_desc,dataRow.getCedula(),dataRow.getNombre(),dataRow.getApellido(),dataRow.getMovil1(),dataRow.getMovil2(),"ACT",dataRow.getId(),dataRow.getDireccion(),dataRow.getBarrio());
            testInscription(posiblesNuevas);
        }
    }

    //@Override
    //public void onBorrarLinea(String cedula) {
    //    CRUDPosibles_Nuevas.borrarNueva(cedula);
     //   updateList();
    //}

    @Override
    public void onDeleteRegistroId(int id) {
        CRUDPosibles_Nuevas.borrariD(id);
        updateList();
    }

     private  void refresh(){
        Profile typePerfil = new Gson().fromJson(mPreferences.getJSON_TypePerfil(getActivity()), Profile.class);

        if(typePerfil!=null) {
            requiredListNuevas = new ListPre(mPreferences.getTokenSesion(getActivity()), typePerfil.getPerfil(), typePerfil.getValor(), 0);
            checkListaNuevas();
        } else {
            msgToast("No hay datos de perfil... Vuelva a iniciar sesion");
        }
        sendDataPosiblesNuevas();
    }
    public void checkListaNuevas(){
        //if(listPre.size()<1){//OJO REFREZCAR CON PULL REFRESH
            searchListNuevas(1);
        //}
    }

    private void searchListNuevas(int page){
        showProgress();
        Log.e("NUME_PAGINA_POSI", ""+page);
        requiredListNuevas.setIndex_pages(page);

        List<Posibles_Nuevas> result0 = CRUDPosibles_Nuevas.traeTodas();//todas para el total
        List<Posibles_Nuevas> result1 = CRUDPosibles_Nuevas.traeTodas(page);
        dismissProgress();
        addRetenidos(result1,result0.size(),page);
        controllerPagination();
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
        //unregisterBroadcat();//CREO QUE SE SALE AL IR A INSCRIPCION
        Log.i(TAG,"onPause()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }

    public void testInscription(PosiblesNuevas posiblesNuevas){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.inscripcion), getString(R.string.desea_pre_inscribir)+" "+ posiblesNuevas.getNomb_terc() + " " + posiblesNuevas.getApel_terc()+"?");
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status) {
                    gotoPreInscripcion( posiblesNuevas);
                }
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    private void addRetenidos(List<Posibles_Nuevas> result,int tota_result,int page){
        page = (page<=0) ? 1:page;
        int page_result = (int)Math.ceil(tota_result/4.0);
        responseListPosiblesNuevas = new ListPosiblesNuevasDTO("Listado",true,result,page_result,page,page_result,tota_result,200);
        list.clear();
        if(result!=null) {
            list.addAll(result);
        }

        adapter_listNuevas.notifyDataSetChanged();
    }

    private void gotoPreInscripcion(PosiblesNuevas posiblesNuevas){
        Fragment fragmentoGenerico = new Incorp_Todos_Fragment();
        ((Incorp_Todos_Fragment)fragmentoGenerico).loadData(IncorporacionPagerAdapter.PAGE_PREINSCRIPCION, perfil,posiblesNuevas);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragmentoGenerico,"Realizar preinscripción")
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult() -> requestCode: "+requestCode+", resultCode: "+resultCode);
    }

    private void addBottomDots(int currentPage, int total_pages) {
        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dots = new TextView[total_pages];
        binding.pagination.layoutDots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {

            //addBottomDots(5);
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            dots[i].setTextSize(40);
            dots[i].setTextColor(colorsInactive[0]);
            binding.pagination.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[0]);
    }

    View.OnClickListener mClickPaginator = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.e("INDEX_PAGINADO_POSI", ""+responseListPosiblesNuevas.getPage_index());
            switch(view.getId()){
                case R.id.tvButtonLeft:
                    searchListNuevas(responseListPosiblesNuevas.getPage_index()-1);
                    break;
                case R.id.tvButtonRight:
                    searchListNuevas(responseListPosiblesNuevas.getPage_index()+1);
                    break;
            }
        }
    };

    private void controllerPagination(){
        if(responseListPosiblesNuevas !=null){
            int page_index = responseListPosiblesNuevas.getPage_index();//pagina actial
            int page_results = responseListPosiblesNuevas.getPage_results();//total de items 3
            int total_pages = responseListPosiblesNuevas.getTotal_pages();//total de paginas 3
            int total_results = responseListPosiblesNuevas.getPage_results();//total de items


            if((page_index==1 && total_pages>1) || (page_index==total_pages-1)){
                enableButton(binding.pagination.tvButtonRight, true);
            } else if(page_index==total_pages){
                enableButton(binding.pagination.tvButtonRight, false);
            }


            if(page_index==1){
                enableButton(binding.pagination.tvButtonLeft, false);
            } else if(page_index==2){
                enableButton(binding.pagination.tvButtonLeft, true);
            }

            addBottomDots(page_index-1, total_pages);//inicia en 0
        }
    }

    private void enableButton(TextView tvButtton, Boolean isEnable){
        tvButtton.setEnabled(isEnable);
    }

    public void updateList(){
        searchListNuevas(1);
    }

    //Parent Fragment
    private IncorporacionesVPages viewParent;
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof IncorporacionesVPages) {
            viewParent = (IncorporacionesVPages) childFragment;
        } else {
            //throw new RuntimeException(childFragment.toString().concat(" is not OnInteractionActivity"));
            Log.e(TAG, "is not OnInteractionActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewParent = null;
//        menuListener = null;
    }

    private void sendDataPosiblesNuevas(){
        showProgress();
        inscripcionController.postPosiblesNuevas(obtainData(), new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                dismissProgress();

                CRUDPosibles_Nuevas.actualizarEstado();
                String data = result.getResult();

                if(data != null)
                    msgToast(data);
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    public PosiblesNuevasSend obtainData()
    {
        List<Posibles_Nuevas> result = CRUDPosibles_Nuevas.traeTodas(TRUE);

        if((result == null || result.size() == 0))
        {
            msgToast("No hay datos pendientes por enviar");
        }

        List<PosiblesNuevas> posiblesNuevasDetalle = new ArrayList<PosiblesNuevas>();

        for(Posibles_Nuevas fila : result)
        {
            PosiblesNuevas itemLista = new PosiblesNuevas(fila.getTipo_docu(),fila.getCedula(),fila.getNombre(),fila.getApellido(), fila.getMovil1(), fila.getMovil2(), "ACT",fila.getId(),fila.getDireccion(),fila.getBarrio());
            posiblesNuevasDetalle.add(itemLista);
        }
        PosiblesNuevasSend posiblesNuevasCabecera = new PosiblesNuevasSend(perfil.getPerfil(),perfil.getValor(),posiblesNuevasDetalle);
        return posiblesNuevasCabecera;
    }

}
