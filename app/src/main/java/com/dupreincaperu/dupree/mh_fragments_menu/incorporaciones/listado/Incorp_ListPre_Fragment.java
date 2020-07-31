package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado;


import android.app.Activity;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.controller.InscripcionController;
import com.dupreeinca.lib_api_rest.enums.EnumStatusPreInsc;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.ElimPreIns;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.IncorporacionesVPages;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.incripcion.InscripcionActivity;
import com.dupreincaperu.dupree.mh_holders.ListPreHolder;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentIncorpListPreBinding;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog3Button;
import com.dupreeinca.lib_api_rest.model.dto.request.ApprovePreIns;
import com.dupreeinca.lib_api_rest.model.dto.response.Preinscripcion;
import com.dupreeinca.lib_api_rest.model.dto.response.ListPreinscripcionDTO;
import com.dupreincaperu.dupree.mh_adapters.PreinscripListAdapter;
import com.dupreincaperu.dupree.mh_dialogs.SimpleDialog;
import com.dupreeinca.lib_api_rest.model.dto.request.ListPre;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Incorp_ListPre_Fragment extends BaseFragment implements ListPreHolder.Events{

    private final String TAG = Incorp_ListPre_Fragment.class.getName();
    private InscripcionController inscripcionController;

    private FragmentIncorpListPreBinding binding;
    private List<Preinscripcion> list;
    private ListPreinscripcionDTO responseListPreinscripcion;
    private PreinscripListAdapter adapter_listPre;

    private TextView[] dots;
    public Incorp_ListPre_Fragment() {
        // Required empty public constructor
    }

    private Profile perfil;
    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    public static Incorp_ListPre_Fragment newInstance() {
        Bundle args = new Bundle();

        Incorp_ListPre_Fragment fragment = new Incorp_ListPre_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    ListPre requiredListPre;
    public static String formato_direccion, nameSelected="", identySelected="";

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_incorp_list_pre;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        onAttachFragment(getParentFragment());

        binding = (FragmentIncorpListPreBinding) view;

        binding.pagination.tvButtonLeft.setOnClickListener(mClickPaginator);
        binding.pagination.tvButtonRight.setOnClickListener(mClickPaginator);
        enableButton(binding.pagination.tvButtonLeft, false);
        enableButton(binding.pagination.tvButtonRight, false);

        binding.swipe.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.swipe.recycler.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter_listPre = new PreinscripListAdapter(list, this);
        binding.swipe.recycler.setAdapter(adapter_listPre);
        binding.swipe.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                binding.swipe.refresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onLoadedView() {
        inscripcionController = new InscripcionController(getContext());

        refresh();
    }

    //MARK: ListPreHolder.Events
    @Override
    public void onClickRoot(Preinscripcion dataRow, int row) {
        if(row>-1 && row<list.size()) {
            if (dataRow.getEstado().equals(EnumStatusPreInsc.AUTORIZADO.getKey())) {
                nameSelected = dataRow.getNombre() + " " + dataRow.getApellido();
                identySelected = dataRow.getCedula();
                formato_direccion = dataRow.getFormato_direccion();

                testInscription(nameSelected,dataRow.getEstado());
                //si es gerente de zona, el etado es pendiente y fue realizada por una LIDER, la Z debe aprobar.
            } else if(dataRow.getEstado().equals(EnumStatusPreInsc.PENDIENTE.getKey())
                    && perfil.getPerfil().equals(Profile.GERENTE_ZONA) && dataRow.getUsuario().equals(Profile.LIDER)){
                identySelected = dataRow.getCedula();
                testPreInscription(dataRow.getNombre() + " " + dataRow.getApellido(), row);
                //Editar cuando esta RECHAZADO
            }/*else if(dataRow.getEstado().equals(EnumStatusPreInsc.RECHAZADO.getKey())) {
                /*nameSelected = dataRow.getNombre() + " " + dataRow.getApellido();
                identySelected = dataRow.getCedula();
                formato_direccion = dataRow.getFormato_direccion();

                testEditInscription(nameSelected,dataRow.getEstado());

                //Es para casos donde se desea eliminar una presinscripcion
            }*/else if(dataRow.getEstado().equals(EnumStatusPreInsc.RECHAZADO.getKey())) {
                identySelected = dataRow.getCedula();
                testElimInscription(dataRow.getNombre() + " " + dataRow.getApellido(), row);

                //Este es para los casos de revision que solo lo pueden realizar los GZ
            } else if(dataRow.getEstado().equals(EnumStatusPreInsc.REVISION.getKey())
                    && perfil.getPerfil().equals(Profile.GERENTE_ZONA) /*&& dataRow.getUsuario().equals(Profile.LIDER)*/){

                nameSelected = dataRow.getNombre() + " " + dataRow.getApellido();
                identySelected = dataRow.getCedula();
                formato_direccion = dataRow.getFormato_direccion();
                testReviInscription(nameSelected,dataRow.getEstado());
                //testEditInscription(nameSelected,dataRow.getEstado());
            }


        }
    }

    private  void refresh(){
        Profile typePerfil = new Gson().fromJson(mPreferences.getJSON_TypePerfil(getActivity()), Profile.class);

        if(typePerfil!=null) {
            requiredListPre = new ListPre(mPreferences.getTokenSesion(getActivity()), typePerfil.getPerfil(), typePerfil.getValor(), 0);
            checkListaPre();
        } else {
            msgToast("No hay datos de perfil... Vuelva a iniciar sesion");
        }
    }
    public void checkListaPre(){
        //if(listPre.size()<1){//OJO REFREZCAR CON PULL REFRESH
            searchListPre(0);
        //}
    }

    private void searchListPre(int page){
        showProgress();
        requiredListPre.setIndex_pages(page);

        inscripcionController.getListPre(requiredListPre, new TTResultListener<ListPreinscripcionDTO>() {

            @Override
            public void success(ListPreinscripcionDTO result) {
//                Log.e(TAG, new Gson().toJson(result));
                dismissProgress();
                addRetenidos(result);
                controllerPagination();
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
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

    public void testInscription(String to,String estado){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.inscripcion), getString(R.string.desea_inscribir)+" "+to+"?");
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    gotoInscripcion(false,estado);
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    public void testEditInscription(String to, String estado){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.inscripcion), getString(R.string.desea_editar_inscripcion)+" "+to+"?");
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    gotoInscripcion(true,estado);
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }

    public void testReviInscription(String to, String estado){
        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.revision), getString(R.string.desea_revisar_inscripcion)+" "+to+"?");
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if(status)
                    gotoInscripcion(true,estado);
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }



    public void testPreInscription(String to, int row){
        SimpleDialog3Button simpleDialog = new SimpleDialog3Button();
        simpleDialog.loadData(getString(R.string.approve_preinscription), getString(R.string.desea_aprobar_preinscription)+" "+to+"?",getString(R.string.aprobar), getString(R.string.rechazar));
        simpleDialog.setListener(new SimpleDialog3Button.ListenerResult() {
            @Override
            public void result(boolean status) {
                showProgress();
                inscripcionController.aprobarPreinscripcion(new ApprovePreIns(identySelected, status ? ApprovePreIns.APROBAR : ApprovePreIns.RECHAZAR), new TTResultListener<GenericDTO>() {
                    @Override
                    public void success(GenericDTO result) {
                        dismissProgress();

                        String msg = result.getResult();

                        if(msg != null){
                            msgToast(msg);
                            refreshList(msg, row);
                        }
                    }

                    @Override
                    public void error(TTError error) {
                        dismissProgress();
                        checkSession(error);
                    }
                });
            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");
    }


    public void testElimInscription(String to, int row){

        SimpleDialog simpleDialog = new SimpleDialog();
        simpleDialog.loadData(getString(R.string.eliminar_preinscription), getString(R.string.desea_eliminar_preinscription)+" "+to+"?");
        simpleDialog.setListener(new SimpleDialog.ListenerResult() {
            @Override
            public void result(boolean status) {
                if (status){
                    showProgress();
                    inscripcionController.eliminarPreinscripcion(new ElimPreIns(identySelected, ElimPreIns.ELIMINAR), new TTResultListener<GenericDTO>() {
                        @Override
                        public void success(GenericDTO result) {
                            dismissProgress();

                            String msg = result.getResult();

                            if(msg != null){
                                refresh();
                                msgToast(msg);
                            }
                        }

                        @Override
                        public void error(TTError error) {
                            dismissProgress();
                            checkSession(error);
                        }
                    });
                }

            }
        });
        simpleDialog.show(getChildFragmentManager(),"mDialog");

    }

    private void addRetenidos(ListPreinscripcionDTO result){
        responseListPreinscripcion = result;
        list.clear();
        if(result.getResult()!=null) {
            list.addAll(result.getResult());
        }

        adapter_listPre.notifyDataSetChanged();
    }

    private final int REQUEST_CODE_INCRPCION = 5;
    private void gotoInscripcion(boolean modeEdit, String estado){
        Intent i = new Intent(getActivity(), InscripcionActivity.class);
        i.putExtra(InscripcionActivity.TAG, new DataAsesora(formato_direccion, nameSelected, identySelected, modeEdit));
        i.putExtra("estado",estado);
        startActivityForResult(i, REQUEST_CODE_INCRPCION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult() -> requestCode: "+requestCode+", resultCode: "+resultCode);

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_INCRPCION) {
                updateList();
            }
        }

    }

    private void refreshList(String result, int row){
        if(row!=-1) {
            switch (result) {
                case "Rechazada":
                    list.get(row).setEstado(EnumStatusPreInsc.RECHAZADO.getKey());
                    break;
                case "Aprobada":
                    list.get(row).setEstado(EnumStatusPreInsc.AUTORIZADO.getKey());
                    break;
            }
            adapter_listPre.notifyItemChanged(row);
        }
    }

    private void addBottomDots(int currentPage, int total_pages) {
        try{
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
        }catch (Exception ex){

        }
    }

    View.OnClickListener mClickPaginator = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.tvButtonLeft:
                    searchListPre(responseListPreinscripcion.getPage_index()-1);
                    break;
                case R.id.tvButtonRight:
                    searchListPre(responseListPreinscripcion.getPage_index()+1);
                    break;
            }
        }
    };

    private void controllerPagination(){
        if(responseListPreinscripcion!=null){
            int page_index = responseListPreinscripcion.getPage_index();//pagina actial
            int page_results = responseListPreinscripcion.getPage_results();//total de items 3
            int total_pages = responseListPreinscripcion.getTotal_pages();//total de paginas 3
            int total_results = responseListPreinscripcion.getPage_results();//total de items


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
        searchListPre(0);
    }

    //    MenuListener menuListener;
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof MenuActivity) {
//            menuListener = (MenuActivity) context;
//        } else
//            throw new RuntimeException(context.toString().concat(" is not OnInteractionActivity"));
//
//    }

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

}
