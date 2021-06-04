package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.incripcion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.View;

import com.dupreeinca.lib_api_rest.controller.InscripcionController;
import com.dupreeinca.lib_api_rest.controller.UploadFileController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.FormatoDireccion;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.Referencia;
import com.dupreeinca.lib_api_rest.model.dto.request.ValidateRef;
import com.dupreeinca.lib_api_rest.model.dto.response.BarrioDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.CiudadDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.DepartamentoDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.ListBarrioDTO;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.Incorp_ListPre_Fragment;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentInscripcionNewBinding;
import com.dupreincaperu.dupree.files.LoadJsonFile;
import com.dupreincaperu.dupree.files.ManagerFiles;
import com.dupreincaperu.dupree.mh_dialogs.DateDialog;
import com.dupreincaperu.dupree.mh_dialogs.ListCity;
import com.dupreincaperu.dupree.mh_dialogs.ListDpto;
import com.dupreincaperu.dupree.mh_dialogs.ListString;
import com.dupreincaperu.dupree.mh_dialogs.MH_Dialogs_Barrio;
import com.dupreincaperu.dupree.mh_dialogs.SingleListDialog;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.vansuita.pickimage.bean.PickResult;
//import com.vansuita.pickimage.bundle.PickSetup;
//import com.vansuita.pickimage.dialog.PickImageDialog;
//import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 */
public class InscripcionFragment_borrar extends BaseFragment {
    public static final String TAG = InscripcionFragment_borrar.class.getName();
    private FragmentInscripcionNewBinding binding;
    private InscripcionController inscripController;
    private UploadFileController uploadFileController;
    private LoadJsonFile jsonFile;
    private List<ModelList> listBis, listDirSend, listLetra, listParentezco, listPCardinal, listTipoVia;

    public static final String BROACAST_INSCRIP_TYPE_IMG_CED_FRONT_URL="inscrip_type_img_ced_front_url";
    public static final String BROACAST_INSCRIP_TYPE_IMG_CED_ADVER_URL="inscrip_type_img_ced_adver_url";
    public static final String BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL="inscrip_type_img_pag_front_url";
    public static final String BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL="inscrip_type_img_pag_adver_url";
    public static final String BROACAST_REG_TYPE_BARRIOS="reg_type_barriosB";
    public static final String BROACAST_REG_TYPE_BARRIOS_2="reg_type_barrios_2B";

    private final int IMG_CED_FRT=0, IMG_CED_ADV=1, IMG_PAG_FRT=2, IMG_PAG_ADV=3;

    public InscripcionFragment_borrar() {
        // Required empty public constructor
    }

    public static InscripcionFragment_borrar newInstance() {
        Bundle args = new Bundle();
        InscripcionFragment_borrar fragment = new InscripcionFragment_borrar();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_inscripcion_new;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
//        onAttachFragment(getParentFragment());
        binding = (FragmentInscripcionNewBinding) view;

        model = new InscriptionDTO();
        modelRefPersonal = new Referencia();
        modelRefFamiliar = new Referencia();

        inscripController = new InscripcionController(getContext());
        uploadFileController = new UploadFileController(getContext());
        jsonFile = new LoadJsonFile(getContext());

        binding.setModel(model);
        binding.setModelRefPersonal(modelRefPersonal);
        binding.setModelRefFamiliar(modelRefFamiliar);

        //Contacto
        binding.txtDateBird.setOnClickListener(mListenerClick);
//        binding.txtSpnTypeVia.setOnClickListener(mListenerClick);
//        binding.txtSpnChar1.setOnClickListener(mListenerClick);
//        binding.txtSpnBis.setOnClickListener(mListenerClick);
//        binding.txtSpnSur1.setOnClickListener(mListenerClick);
//        binding.txtSpnChar2.setOnClickListener(mListenerClick);
//        binding.txtSpnSur2.setOnClickListener(mListenerClick);
//        binding.txtSpnDpto.setOnClickListener(mListenerClick);
//        binding.txtSpnCity.setOnClickListener(mListenerClick);
        binding.txtSpnBarrio.setOnClickListener(mListenerClick);

        //events direccion envio
        binding.txtSpnDirSend.setOnClickListener(mListenerClick);
//        binding.txtSpnTypeVia2.setOnClickListener(mListenerClick);
//        binding.txtNum12.setOnClickListener(mListenerClick);
//        binding.txtSpnChar12.setOnClickListener(mListenerClick);
//        binding.txtSpnBis2.setOnClickListener(mListenerClick);
//        binding.txtSpnSur21.setOnClickListener(mListenerClick);
//        binding.txtNum22.setOnClickListener(mListenerClick);
//        binding.txtSpnChar22.setOnClickListener(mListenerClick);
//        binding.txtNum32.setOnClickListener(mListenerClick);
//        binding.txtSpnSur22.setOnClickListener(mListenerClick);
//        binding.txtInfo2.setOnClickListener(mListenerClick);
//        binding.txtSpnDpto2.setOnClickListener(mListenerClick);
//        binding.txtSpnCity2.setOnClickListener(mListenerClick);
//        binding.txtSpnBarrio2.setOnClickListener(mListenerClick);

        // --------- DE CAMBIOS SOLICITADOS
        binding.imgBSearchref.setOnClickListener(mListenerClick);
        binding.imgBCleanRef.setOnClickListener(mListenerClick);

        binding.imgCedFrontal.setOnClickListener(mListenerClick);
        binding.imgCedAdverso.setOnClickListener(mListenerClick);
        binding.imgPagFrontal.setOnClickListener(mListenerClick);
        binding.imgPagAdverso.setOnClickListener(mListenerClick);


        binding.txtSpnParentesco1.setOnClickListener(mListenerClick);
        binding.txtSpnParentesco2.setOnClickListener(mListenerClick);

        binding.btnRegister.setOnClickListener(mListenerClick);

    }

    @Override
    protected void onLoadedView() {
        listBis = jsonFile.getParentezcos(ManagerFiles.BIS.getKey());
        listDirSend = jsonFile.getParentezcos(ManagerFiles.DIR_SEND.getKey());
        listLetra = jsonFile.getParentezcos(ManagerFiles.LETRA.getKey());
        listParentezco = jsonFile.getParentezcos(ManagerFiles.PARENTEZCOS.getKey());
        listPCardinal = jsonFile.getParentezcos(ManagerFiles.PCARDINAL.getKey());
        listTipoVia = jsonFile.getParentezcos(ManagerFiles.TIPO_VIA.getKey());
        listDpto = getListDpto(mPreferences.getDpto(getActivity()));

        //Direccion de envio
        model.setSpnDirEnvio(listDirSend.get(0).getName());

        Bundle bundle;
        DataAsesora data;
        if((bundle = getArguments()) != null && (data = bundle.getParcelable(TAG)) != null){
            loadData(data.getNombre(), data.getCedula(), data.isModeEdit());
        }

    }


    boolean modeEdit = false;
    public void loadData(String name, String cedula, boolean modeEdit){
        clearData();

        this.modeEdit = modeEdit;
        model.setCedula(cedula.isEmpty() ? Incorp_ListPre_Fragment.identySelected : cedula);
        model.setNombre(name.isEmpty() ? Incorp_ListPre_Fragment.nameSelected : name);//se redunda xq a veces no llega

        if(modeEdit){
            showProgress();
            inscripController.getInscripcion(new Identy(cedula), new TTResultListener<InscriptionDTO>() {
                @Override
                public void success(InscriptionDTO result) {
                    dismissProgress();
                    loadDataInit(result);
                }

                @Override
                public void error(TTError error) {
                    dismissProgress();
                    checkSession(error);
                }
            });
        }
    }

    private File file0, file1, file2, file3;
    private InscriptionDTO model;
    private FormatoDireccion modelDirRes;
    private FormatoDireccion modelDirEnv;
    private Referencia modelRefPersonal;
    private Referencia modelRefFamiliar;

    public Profile getPerfil(){
        String jsonPerfil = mPreferences.getJSON_TypePerfil(getActivity());
        if(jsonPerfil!=null)
            return new Gson().fromJson(jsonPerfil, Profile.class);

        return null;
    }

    List<DepartamentoDTO> listDpto=null;
    DepartamentoDTO dptoSelected;
    List<CiudadDTO> listCiudad=null, listCiudad_2=null;
    CiudadDTO ciudad=null, ciudad_2=null;
    List<BarrioDTO> listBarrio=null, listBarrio_2=null;
    BarrioDTO barrio=null, barrio_2=null;
    public List<DepartamentoDTO> getListDpto(String data){
        Type listType = new TypeToken<ArrayList<DepartamentoDTO>>(){}.getType();
        return new Gson().fromJson(data, listType);
    }

    View.OnClickListener mListenerClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.txtSpnParentesco1://
                    showList(ListString.BROACAST_INSCRIP_TYPE_PARENTESCO, getString(R.string.parentesco), listParentezco, model.getReferencia().get(0).getName_parentesco());
                    break;
                case R.id.txtSpnParentesco2://
                    showList(ListString.BROACAST_INSCRIP_TYPE_PARENTESCO_2, getString(R.string.parentesco), listParentezco, model.getReferencia().get(1).getName_parentesco());
                    break;
                case R.id.btnRegister:

                    if(validateRegister()){
                        Log.e(TAG,"validateRegister() -> finish(true)");
                        if(validateIncrip()) {
                            Log.e(TAG,"validateIncrip() -> finish(true)");
                            showProgress();
                            if(modeEdit) {
                                if(file0 != null){
                                    sendImageMultiPart(file0, TAG, BROACAST_INSCRIP_TYPE_IMG_CED_FRONT_URL);
                                } else if(file1 != null){
                                    sendImageMultiPart(file1, TAG, BROACAST_INSCRIP_TYPE_IMG_CED_ADVER_URL);
                                } else if(file2 != null){
                                    sendImageMultiPart(file2, TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL);
                                } else if(file3 != null){
                                    sendImageMultiPart(file3, TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL);
                                } else {
                                    postInscipcion(obtainData());
                                }
                            } else {
                                sendImageMultiPart(file0, TAG, BROACAST_INSCRIP_TYPE_IMG_CED_FRONT_URL);
                            }
                        }
                    }
                    break;
                case R.id.imgB_Searchref://
                    if(!model.getReferenciado_por().isEmpty()) {
                        showProgress();

                        inscripController.validateReferido(new ValidateRef(
                                model.getReferenciado_por(),
                                mPreferences.getTokenSesion(getActivity())), new TTResultListener<GenericDTO>() {
                            @Override
                            public void success(GenericDTO result) {
                                dismissProgress();
                                model.setReferenciado_hint(result.getResult());
                                setRefValidated(true);
                            }

                            @Override
                            public void error(TTError error) {
                                dismissProgress();
                                checkSession(error);
                            }
                        });
                    }
                    break;
                case R.id.imgB_CleanRef://
                    setRefValidated(false);
                    break;
                case R.id.imgCedFrontal:
                    imageSelected = IMG_CED_FRT;
                    permissionImage();
                    break;
                case R.id.imgCedAdverso:
                    imageSelected = IMG_CED_ADV;
                    permissionImage();
                    break;
                case R.id.imgPagFrontal:
                    imageSelected = IMG_PAG_FRT;
                    permissionImage();
                    break;
                case R.id.imgPagAdverso:
                    imageSelected = IMG_PAG_ADV;
                    permissionImage();
                    break;
                //////////// DE NUEVOS CAMBIOS
                case R.id.txtDateBird://
                    showDate();
                    break;
                /*case R.id.txt_spn_tipo_via1://
                    showList(ListString.BROACAST_REG_TYPE_VIA, getString(R.string.tipo_via), listTipoVia, model.getTipo_via1());
                    break;
                case R.id.txt_spn_letra1://
                    showList(ListString.BROACAST_REG_TYPE_LETRA1, getString(R.string.letra), listLetra, model.getLetra1());
                    break;
                case R.id.txt_spn_bis1://
                    showList(ListString.BROACAST_REG_TYPE_BIS, getString(R.string.bis), listBis, model.getBis1());
                    break;
                case R.id.txt_spn_pcardinal1://
                    showList(ListString.BROACAST_REG_TYPE_SUR1, getString(R.string.sur_este), listPCardinal, model.getPcardinal1());
                    break;
                case R.id.txt_spn_letra2://
                    showList(ListString.BROACAST_REG_TYPE_LETRA2, getString(R.string.letra), listLetra, model.getLetra2());
                    break;
                case R.id.txt_spn_pcardinal2://
                    showList(ListString.BROACAST_REG_TYPE_SUR2, getString(R.string.sur_este), listPCardinal, model.getPcardinal2());
                    break;*/
                case R.id.txt_spn_departamento://
                    if(listDpto!=null)
                        showDpto(ListDpto.BROACAST_REG_TYPE_DPTO, getString(R.string.departamento), listDpto, model.getDepartamento());
                    break;
                case R.id.txt_spn_name_ciudad://
                    if(listCiudad!=null)
                        showCity(ListCity.BROACAST_REG_TYPE_CITY, getString(R.string.ciudad), listCiudad, model.getName_ciudad());
                    break;
                case R.id.txt_spn_barrio://
                    if(listBarrio!=null) {
                        List<BarrioDTO> barrioList = new ArrayList<>();
                        List<BarrioDTO> filterBarrio = new ArrayList<>();

                        barrioList.addAll(listBarrio);
                        filterBarrio.addAll(listBarrio);

                        showBarrio(MH_Dialogs_Barrio.BROACAST_REG_TYPE_BARRIO, getString(R.string.barrio), filterBarrio, barrioList);
                    }
                    break;
                case R.id.txtSpnDirSend://
                    showList(ListString.BROACAST_REG_TYPE_DIR_SEND, getString(R.string.direccion_de_envio_pedido), listDirSend, model.getSpnDirEnvio());
                    break;

                case R.id.txt_spn_tipo_via_env_1://
                    showList(ListString.BROACAST_REG_TYPE_VIA_2, getString(R.string.tipo_via), listTipoVia, model.getTipo_via_env_1());
                    break;
                case R.id.txt_spn_letra_env_1://
                    showList(ListString.BROACAST_REG_TYPE_LETRA1_2, getString(R.string.letra), listLetra, model.getLetra_env_1());
                    break;
                case R.id.txt_spn_bis_env_1://
                    showList(ListString.BROACAST_REG_TYPE_BIS_2, getString(R.string.bis), listBis, model.getBis_env_1());
                    break;
                case R.id.txt_spn_letra_env_2://
                    showList(ListString.BROACAST_REG_TYPE_SUR_21, getString(R.string.sur_este), listPCardinal, model.getPcardinal_env_2());
                    break;
                case R.id.txt_spn_pcardinal_env_1://
                    showList(ListString.BROACAST_REG_TYPE_LETRA2_2, getString(R.string.letra), listLetra, model.getLetra_env_2());
                    break;
                case R.id.txt_spn_letra_env_3://
                    showList(ListString.BROACAST_REG_TYPE_SUR_22, getString(R.string.sur_este), listPCardinal, model.getPcardinal_env_1());
                    break;
                case R.id.txt_spn_pcardinal_env_2://
                    showList(ListString.BROACAST_REG_TYPE_SUR2, getString(R.string.sur_este), listPCardinal, model.getPcardinal2());
                    break;
                case R.id.txt_spn_departamento_env://
                    if(listDpto!=null)
                        showDpto(ListDpto.BROACAST_REG_TYPE_DPTO_2, getString(R.string.departamento), listDpto, model.getDepartamento_env());
                    break;
                case R.id.txt_spn_name_ciudad_env://
                    if(listCiudad_2!=null)
                        showCity(ListCity.BROACAST_REG_TYPE_CITY_2, getString(R.string.ciudad), listCiudad_2, model.getName_ciudad_env());
                    break;
                case R.id.txt_spn_barrio_env://
                    if(listBarrio_2!=null) {
                        List<BarrioDTO>  barrioList = new ArrayList<>();
                        List<BarrioDTO>  filterBarrio = new ArrayList<>();
                        barrioList.addAll(listBarrio_2);
                        filterBarrio.addAll(listBarrio_2);
                        showBarrio(MH_Dialogs_Barrio.BROACAST_REG_TYPE_BARRIO_2, getString(R.string.barrio), filterBarrio, barrioList);
                    }
                    break;
            }
        }
    };

    private void postInscipcion(InscriptionDTO data){
        showProgress();
        inscripController.postIncripcion(data, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                dismissProgress();
                msgToast(result.getResult());
                clearData();
                updateList();
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }



    private void updateList(){
//        viewParent.gotoPage(MH_PagerAdapter_Incorporacion.PAGE_LIST_PRE);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "hola");
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    public void showList(String tag, String title, List<ModelList> data, String itemSelected){
        SingleListDialog dialog = new SingleListDialog();
        dialog.loadData(title, data, itemSelected, new SingleListDialog.ListenerResponse() {
            @Override
            public void result(ModelList item) {
                Log.i(TAG, tag);
                switch(tag){
                    //Direccion residencia
//                    case ListString.BROACAST_REG_TYPE_VIA:
//                        binding.txtSpnTypeVia.setError(null);
//                        model.setTipo_via1(item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_LETRA1:
//                        binding.txtSpnChar1.setError(null);
//                        model.setLetra1(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_BIS:
//                        binding.txtSpnBis.setError(null);
//                        model.setBis1(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_SUR1:
//                        binding.txtSpnSur1.setError(null);
//                        model.setPcardinal1(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_LETRA2:
//                        binding.txtSpnChar2.setError(null);
//                        model.setLetra2(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_SUR2:
//                        binding.txtSpnSur2.setError(null);
//                        model.setPcardinal2(item.getId() == -1 ? "" : item.getName());
//                        break;
                    //Direccion envios
                    case ListString.BROACAST_REG_TYPE_DIR_SEND:
                        binding.txtSpnDirSend.setError(null);
                        model.setSpnDirEnvio(item.getName());
                        model.setShowDirEnvio(item.getId() > 0);
                        break;

//                    case ListString.BROACAST_REG_TYPE_VIA_2:
//                        binding.txtSpnTypeVia2.setError(null);
//                        model.setTipo_via_env_1(item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_LETRA1_2:
//                        binding.txtSpnChar12.setError(null);
//                        model.setLetra_env_1(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_BIS_2:
//                        binding.txtSpnBis2.setError(null);
//                        model.setBis_env_1(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_SUR_21:
//                        binding.txtSpnSur21.setError(null);
//                        model.setPcardinal_env_2(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_LETRA2_2:
//                        binding.txtSpnChar22.setError(null);
//                        model.setLetra_env_2(item.getId() == -1 ? "" : item.getName());
//                        break;
//                    case ListString.BROACAST_REG_TYPE_SUR_22:
//                        binding.txtSpnSur22.setError(null);
//                        model.setPcardinal_env_1(item.getId() == -1 ? "" : item.getName());
//                        break;
                    //Parentesco
                    case ListString.BROACAST_INSCRIP_TYPE_PARENTESCO:
                        binding.txtSpnParentesco1.setError(null);
                        modelRefPersonal.setParentesco(String.valueOf(item.getId()));
                        modelRefPersonal.setName_parentesco(item.getName());
                        break;
                    case ListString.BROACAST_INSCRIP_TYPE_PARENTESCO_2:
                        binding.txtSpnParentesco2.setError(null);
                        modelRefFamiliar.setParentesco(String.valueOf(item.getId()));
                        modelRefFamiliar.setName_parentesco(item.getName());
                        break;
                }
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void showDpto(String tag, String title, List<DepartamentoDTO> data, String itemSelected){
        ListDpto dialogDpto = new ListDpto();
        dialogDpto.loadData(title, data, itemSelected, new ListDpto.ListenerResponse() {
            @Override
            public void result(DepartamentoDTO item) {
                Log.i(TAG, tag);
                switch(tag) {
                    case ListDpto.BROACAST_REG_TYPE_DPTO:
                        clearCity();
                        dptoSelected = item;
//                        binding.txtSpnDpto.setError(null);
                        model.setDepartamento(dptoSelected.getName_dpto());
//                        binding.txtSpnDpto.setTag(dptoSelected.getId_dpto());
                        listCiudad = dptoSelected.getCiudades();
                        break;
                    case ListDpto.BROACAST_REG_TYPE_DPTO_2:
                        clearCity_2();
                        dptoSelected = item;
//                        binding.txtSpnDpto2.setError(null);
                        model.setId_departamento_env(dptoSelected.getId_dpto());
                        model.setDepartamento_env(dptoSelected.getName_dpto());
//                        binding.txtSpnDpto2.setTag(dptoSelected.getId_dpto());
                        listCiudad_2 = dptoSelected.getCiudades();
                        break;
                }
            }
        });
        dialogDpto.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void showCity(String tag, String title, List<CiudadDTO> data, String itemSelected){
        ListCity dialogCity = new ListCity();
        dialogCity.loadData(title, data, itemSelected, new ListCity.ListenerResponse() {
            @Override
            public void result(CiudadDTO item) {
                Log.i(TAG, tag);
                switch(tag) {
                    case ListCity.BROACAST_REG_TYPE_CITY:
                        clearBarrio();
                        ciudad = item;
//                        binding.txtSpnCity.setError(null);
                        model.setName_ciudad(ciudad.getName_ciudad());
                        model.setId_ciudad(ciudad.getId_ciudad());

                        //Buscar barrios
                        getBarrios(BROACAST_REG_TYPE_BARRIOS, ciudad.getId_ciudad());
                        break;
                    case ListCity.BROACAST_REG_TYPE_CITY_2:
                        //limpiar
                        clearBarrio_2();

                        ciudad_2 = item;
//                        binding.txtSpnCity2.setError(null);
                        model.setName_ciudad_env(ciudad_2.getName_ciudad());
                        model.setId_ciudad_env(ciudad_2.getId_ciudad());

                        //Buscar barrios
                        getBarrios(BROACAST_REG_TYPE_BARRIOS_2, ciudad_2.getId_ciudad());
                        break;
                }
            }
        });
        dialogCity.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void getBarrios(String tag, String id_ciudad){
        showProgress();
        inscripController.getBarrios(id_ciudad, new TTResultListener<ListBarrioDTO>() {
            @Override
            public void success(ListBarrioDTO result) {
                dismissProgress();
                switch (tag){
                    case BROACAST_REG_TYPE_BARRIOS:
                        responseHttpBarrio(result.getBarrios());
                        break;
                    case BROACAST_REG_TYPE_BARRIOS_2:
                        responseHttpBarrio_2(result.getBarrios());
                        break;
                }
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    public void responseHttpBarrio(List<BarrioDTO> barrioList){
        listBarrio = new ArrayList<>();
        listBarrio.addAll(barrioList);
    }

    public void responseHttpBarrio_2(List<BarrioDTO> barrioList){
        listBarrio_2 = new ArrayList<>();
        listBarrio_2.addAll(barrioList);
    }

    public void showBarrio(String tag, String title, List<BarrioDTO> datafilter, List<BarrioDTO> data){
        MH_Dialogs_Barrio dialogBarrio = new MH_Dialogs_Barrio();
        dialogBarrio.loadData(datafilter, data, new MH_Dialogs_Barrio.ListenerResponse() {
            @Override
            public void result(BarrioDTO item) {
                Log.i(TAG, tag);
                switch (tag){
                    case MH_Dialogs_Barrio.BROACAST_REG_TYPE_BARRIO:
                        barrio = item;
                        binding.txtSpnBarrio.setError(null);
                        model.setBarrio(barrio.getName_barrio());
                        model.setId_barrio(barrio.getId_barrio());
                        break;
                    case MH_Dialogs_Barrio.BROACAST_REG_TYPE_BARRIO_2:
                        barrio_2 = item;
//                        binding.txtSpnBarrio2.setError(null);
                        model.setBarrio_env(barrio_2.getName_barrio());
                        model.setId_barrio_env(barrio_2.getId_barrio());
                        break;
                }
            }
        });
        dialogBarrio.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void showDate(){
        DateDialog newFragment = new DateDialog();
        newFragment.setData(new DateDialog.ListenerResponse() {
            @Override
            public void result(String date) {
                binding.txtDateBird.setError(null);
                model.setNacimiento(date);
            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private boolean validateIncrip(){
        Validate validate = new Validate();
        if(file0==null && !modeEdit){
            msgToast("Cédula frontal... Verifique");
            return false;
        }
        else if(file1==null && !modeEdit){
            msgToast("Cédula adverso... Verifique");
            return false;
        }
        else if(file2==null && !modeEdit){
            msgToast("pagaré frontal... Verifique");
            return false;
        }
        else if(file3==null && !modeEdit){
            msgToast("pagaré adverso... Verifique");
            return false;
        }
        else if(modelRefPersonal.getNombre().isEmpty()){
            msgToast("Nombre de ref. personal... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtName1);
            return false;
        }
        else if(modelRefPersonal.getApellido().isEmpty()){
            msgToast("Apellido de ref. personal... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtLastName1);
            return false;
        }
        else if(modelRefPersonal.getCelular().isEmpty()){
            msgToast("Celular de ref. personal... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtCellPhone1);
            return false;
        }
        else if(modelRefPersonal.getName_parentesco().isEmpty()){
            msgToast("Parentesco de ref. personal... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtSpnParentesco1);
            return false;
        }
        else if(modelRefFamiliar.getNombre().isEmpty()){
            msgToast("Nombre de ref. familiar... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtName2);
            return false;
        }
        else if(modelRefFamiliar.getApellido().isEmpty()){
            msgToast("Apellido de ref. familiar... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtLastName2);
            return false;
        }
        else if(modelRefFamiliar.getCelular().isEmpty()){
            msgToast("Celular de ref. familiar... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtCellPhone2);
            return false;
        }
        else if(modelRefFamiliar.getName_parentesco().isEmpty()){
            msgToast("Parentesco de ref. familiar... Verifique");
            validate.setLoginError(getString(R.string.campo_requerido),binding.txtSpnParentesco2);
            return false;
        }

        return true;
    }

    public void setRefValidated(boolean refValidated) {
        binding.txtIdentyCardRef.setError(null);

        model.setReferenciado_por(refValidated ? model.getReferenciado_por() : "");
        model.setReferenciado_hint(refValidated ? model.getReferenciado_hint() : getString(R.string.cedula));
        model.setNacimiento("");
        model.setZona_seccion("");

        model.setRefValidated(refValidated);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregisterBroadcat();//GENERALMENTE VA AQUI, PERO SE CAMBIO, PARA QUE SIGA RECIBIENDO CUANDO ABRE LA GALERIA DE IMAGENES
        Log.i(TAG,"onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
    }


    private void clearCity(){
        listCiudad=null;
        ciudad=null;
        model.setId_ciudad("");
        model.setName_ciudad("");

        clearBarrio();
    }

    private void clearBarrio(){
        listBarrio=null;
        barrio=null;
        model.setId_barrio("");
        model.setBarrio("");
    }

    private void clearCity_2(){
        listCiudad_2=null;
        ciudad_2=null;
        model.setId_ciudad_env("");
        model.setName_ciudad_env("");

        clearBarrio_2();
    }

    private void clearBarrio_2(){
        listBarrio_2=null;
        barrio_2=null;
        model.setId_barrio_env("");
        model.setBarrio_env("");
    }

    public boolean validateRegister() {
        Log.e(TAG,"validateRegister() -> init()");
        Validate valid=new Validate();
        //datos personales

        if(model.getNombre().isEmpty()){
            msgToast("Nombre de asesora... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtNameIncrip);
            return false;
        }else if(model.getCedula().isEmpty()){
            msgToast("Cêdula de asesora... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtIdentyCard);
            return false;
        }

       /* else if(model.getReferenciado_por().isEmpty()){
            msgToast("Cêdula de referido... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtIdentyCardRef);
            return false;
        }else if(!model.isRefValidated()){
            msgToast("Debe validar la cédula del referido... Verifique");
            valid.setLoginError(getString(R.string.deba_validar),binding.txtIdentyCardRef);
            return false;
        } */
        else if (model.getNacimiento().isEmpty())
        {
            msgToast("Seleccione fecha de nacimiento...");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtDateBird);
            return false;
        }
        else if (model.getZona_seccion().isEmpty())
        {
            msgToast("Sector... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtZone);
            return false;
        }
        else if (model.getZona_seccion().length() != 3 && model.getZona_seccion().length() != 6)
        {
            msgToast("Sector... Verifique");
            valid.setLoginError("Debe ser un valor de 3 o 6 números", binding.txtZone);
            return false;
        }

//        //REDIDENCIA
//        else if (model.getTipo_via1().isEmpty())
//        {
//            msgToast("Dir. Res. > Tipo de vía... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnTypeVia);
//            return false;
//        }
//        else if (!model.getTipo_via1().toUpperCase().equals("Otro".toUpperCase()) && model.getNumero1().isEmpty())
//        {
//            msgToast("Dir. Res. > Número 1... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum1);
//            return false;
//        }
//        else if (!model.getTipo_via1().toUpperCase().equals("Otro".toUpperCase()) && model.getNumero2().isEmpty())
//        {
//            msgToast("Dir. Res. > Número 2... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum2);
//            return false;
//        }
//        else if (!model.getTipo_via1().toUpperCase().equals("Otro".toUpperCase()) && model.getNumero3().isEmpty())
//        {
//            msgToast("Dir. Res. > Número 3... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum3);
//            return false;
//        }
//        else if (model.getTipo_via1().toUpperCase().equals("Otro".toUpperCase())  && model.getComplemento().isEmpty())
//        {
//            msgToast("Dir. Res. > Datos adicionales...");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtInfo);
//            return false;
//        }

        //departamento
        else if (model.getDepartamento().isEmpty())
        {
            msgToast("Dir. Res. > Dpto... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnDpto);
            return false;
        }
        else if (model.getName_ciudad().isEmpty())
        {
            msgToast("Dir. Res. > Ciudad... Verifique");
//            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnCity);
            return false;
        }
        else if (model.getBarrio().isEmpty())
        {
            msgToast("Dir. Res. > Barrio... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnBarrio);
            return false;
        }

        //DIRECCION DE ENVIO DE PEDIDOS
        else if (model.isShowDirEnvio()) {
//            if (model.getTipo_via_env_1().isEmpty())
//            {
//                msgToast("Dir. Envío. > Tipo de vía... Verifique");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnTypeVia2);
//                return false;
//            }
//            else if (!model.getTipo_via_env_1().toUpperCase().equals("Otro".toUpperCase()) && model.getNumero_env_1().isEmpty())
//            {
//                msgToast("Dir. Envío. > Número 1... Verifiqu");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum12);
//                return false;
//            }
//            else if (!model.getTipo_via_env_1().toUpperCase().equals("Otro".toUpperCase())  && model.getNumero_env_2().isEmpty())
//            {
//                msgToast("Dir. Envío. > Número 2... Verifiqu");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum22);
//                return false;
//            }
//            else if (!model.getTipo_via_env_1().toUpperCase().equals("Otro".toUpperCase())  && model.getNumero_env_3().isEmpty())
//            {
//                msgToast("Dir. Envío. > Número 3... Verifiqu");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtNum32);
//                return false;
//            }
//            else if (model.getTipo_via_env_1().toUpperCase().equals("Otro".toUpperCase())  && model.getComplemento_env().isEmpty())
//            {
//                msgToast("Dir. Envío. > Datos adicionales...");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtInfo2);
//                return false;
//            }
//
//            //departamento
//            else
                if (model.getDepartamento_env().isEmpty())
            {
                msgToast("Dir. Envío. > Dpto... Verifique");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnDpto2);
                return false;
            }
            else if (model.getName_ciudad_env().isEmpty())
            {
                msgToast("Dir. Envío. > Ciudad... Verifique");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnCity2);
                return false;
            }
            else if (model.getBarrio_env().isEmpty())
            {
                msgToast("Dir. Envío. > Barrio... Verifique");
//                valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtSpnBarrio2);
                return false;
            }
            //contacto
            else if (!validatenew())
            {
                return false;
            }
        }
        //contacto
        else if (!validatenew())
        {
            return false;
        }

        return true;

    }

    private boolean validatenew(){
        Validate valid=new Validate();
        //contacto
        if (!model.getCelular().isEmpty())
        {
            msgToast("Teléfono movil es requerido... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtCellphone);
            return false;
        }
        else if (!model.getTelefono().isEmpty() && !model.getCelular().isEmpty() && model.getTelefono().equals(model.getCelular()))
        {
            msgToast("Teléfonos deben ser diferentes... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtCellphone);
            return false;
        }
        return true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG,"new setUserVisibleHint(): "+String.valueOf(isVisibleToUser));
    }

    public void error(){
        dismissProgress();
    }

    private void clearData(){
        model.clearData();
        model.setReferenciado_hint(getResources().getString(R.string.cedula));
        model.setSpnDirEnvio(listDirSend.get(0).getName());

        modelRefPersonal.clearData();
        modelRefFamiliar.clearData();

        file0=null;
        file1=null;
        file2=null;
        file3=null;

        setRefValidated(false);
    }

    private void loadDataInit(InscriptionDTO data){
        binding.txtSpnDirSend.setError(null);

        List<String> listP = Arrays.asList(getResources().getStringArray(R.array.parentescoOptions));
        modelRefPersonal.loadDataInit(data.getReferencia().get(0), listP);
        modelRefFamiliar.loadDataInit(data.getReferencia().get(1), listP);

//        model.loadDataInit(data, listDirSend);
        model.setReferenciado_hint(data.getReferenciado_nombre());

        ciudad = new CiudadDTO(String.valueOf(data.getId_ciudad()), data.getName_ciudad());
        barrio = new BarrioDTO(String.valueOf(data.getId_barrio()), data.getBarrio());

        //esta infor viene codificada
        barrio_2 = new BarrioDTO(model.getId_barrio_env(), model.getBarrio_env());
        ciudad_2 = new CiudadDTO(model.getId_ciudad_env(), model.getName_ciudad_env());
        //esta infor viene codificada

        model.getReferencia().set(0, modelRefPersonal);
        model.getReferencia().set(1, modelRefFamiliar);
    }

    private class fileFromBitmap extends AsyncTask<Void, Integer, String> {
        File file;
        Context context;
        Bitmap bitmap;
        int indexFile=0;
        String name;
        public fileFromBitmap(Bitmap bitmap, String name, int indexFile, Context context) {
            this.bitmap = bitmap;
            this.name= name;
            this.indexFile= indexFile;
            this.context= context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            //si la quieres en discofile = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            file = new File(getActivity().getCacheDir() + "dupree_"+name+"_temporary_file"+String.valueOf(indexFile)+".jpg");//en cache
            try {
                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.flush();
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            saveFile(file, indexFile);
        }

    }

    private void saveFile(File file, int indexFile){
        Log.e(TAG,"File: "+String.valueOf(indexFile)+", "+file.getAbsolutePath());
        switch (indexFile){
            case IMG_CED_FRT:
                this.file0=file;
                model.setCedula_frontal(file.getAbsolutePath());
                break;
            case IMG_CED_ADV:
                this.file1=file;
                model.setCedula_adverso(file.getAbsolutePath());
                break;
            case IMG_PAG_FRT:
                this.file2=file;
                model.setPagare_frontal(file.getAbsolutePath());
                break;
            case IMG_PAG_ADV:
                this.file3=file;
                model.setPagare_adverso(file.getAbsolutePath());
                break;
        }
    }

    private void sendImageMultiPart(File file, String tagFragment, String objectFragment){
        Log.e(TAG,"sendImageMultiPart() -> " + objectFragment);
        showProgress();
        uploadFileController.uploadImage(file, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                dismissProgress();
                switch (objectFragment){
                    case BROACAST_INSCRIP_TYPE_IMG_CED_FRONT_URL:
                        Log.i(TAG, "BROACAST_INSCRIP_TYPE_IMG_CED_FRONT_URL");
                        model.setCedula_frontal(result.getResult());

                        if(!modeEdit || file1 != null){//en modo edicion si file == null, no se ha modificado y se llama al sigueinet caso
                            sendImageMultiPart(file1, TAG, BROACAST_INSCRIP_TYPE_IMG_CED_ADVER_URL);
                        } else {
                            if(file2 != null){
                                sendImageMultiPart(file2, TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL);
                            } else if(file3 != null){
                                sendImageMultiPart(file3, TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL);
                            } else {
                                postInscipcion(obtainData());
                            }
                        }
                        break;
                    case BROACAST_INSCRIP_TYPE_IMG_CED_ADVER_URL:
                        Log.i(TAG, "BROACAST_INSCRIP_TYPE_IMG_CED_ADVER_URL");
                        model.setCedula_adverso(result.getResult());

                        if(!modeEdit || file2 != null){//en modo edicion si file == null, no se ha modificado y se llama al sigueinet caso
                            sendImageMultiPart(file2,TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL);
                        } else if(file3 != null){
                            sendImageMultiPart(file3, TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL);
                        } else {
                            postInscipcion(obtainData());
                        }
                        break;
                    case BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL:
                        Log.i(TAG, "BROACAST_INSCRIP_TYPE_IMG_PAG_FRONT_URL");
                        model.setPagare_frontal(result.getResult());

                        if(!modeEdit || file3 != null){//en modo edicion si file == null, no se ha modificado y se llama al sigueinet caso
                            sendImageMultiPart(file3,TAG, BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL);
                        } else {
                            postInscipcion(obtainData());
                        }
                        break;
                    case BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL:
                        Log.i(TAG, "BROACAST_INSCRIP_TYPE_IMG_PAG_ADVER_URL");
                        model.setPagare_adverso(result.getResult());

                        postInscipcion(obtainData());
                        break;
                }
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
//        new Http(getActivity()).(
//                file, tagFragment, objectFragment, BROACAST_INSCRIP_ERROR
//        );
    }

    public static final int CONST_PERMISSION_IMAGE=123;
    @AfterPermissionGranted(CONST_PERMISSION_IMAGE)
    private void permissionImage() {
        //private void connect(int mode, String roomId, String nameCall, String numberCall, String idDevice) {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {
            //hacer
            Log.e(TAG,"----------------------------------");
            takeImage(imageSelected);
        } else {
            EasyPermissions.requestPermissions(this, "Necesita habilitar permisos", CONST_PERMISSION_IMAGE, perms);
        }
    }

    /**
     * Respuestas a permisos
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    int imageSelected=-1;
    String pathImage=null;
    private void takeImage(final int posImage){
//        PickSetup pickSetup = new PickSetup();
//        pickSetup.setHeight(1200);
//        pickSetup.setWidth(960);
//        PickImageDialog.build(pickSetup)
//                .setOnPickResult(new IPickResult() {
//                    @Override
//                    public void onPickResult(PickResult r) {
//                        if(r.getBitmap()!=null) {
//                            Log.e(TAG, "pathImage: "+r.getPath());
//                            pathImage = r.getPath();
//                            new fileFromBitmap(r.getBitmap(), "documents", posImage, getActivity().getApplicationContext()).execute();
//                        } else {
//                            msgToast("No se logro cargar la imagen");
//                        }
//                    }
//                }).show(getChildFragmentManager());
    }

    private InscriptionDTO obtainData(){
        Log.e(TAG,"obtainData() -> init()");

//        model.setDireccion_envio(model.concatenateDir_2_Protocol());
//        model.setId_barrio_envio(model.getId_barrio_env());

        List<Referencia> referenciaList = new ArrayList<>();
        referenciaList.add(modelRefPersonal);
        referenciaList.add(modelRefFamiliar);

        model.setReferencia(referenciaList);

        List<String> listCedula = new ArrayList<>();
        listCedula.add(model.getCedula_frontal());
        listCedula.add(model.getCedula_adverso());

        List<String> listPagare = new ArrayList<>();
        listPagare.add(model.getPagare_frontal());
        listPagare.add(model.getPagare_adverso());

        model.setImg_cedula(listCedula);
        model.setPagare(listPagare);

        Log.e(TAG, "obtainData() -> model: "+new Gson().toJson(model));
        return model;
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
//    IncorporacionesVPages viewParent;
//    @Override
//    public void onAttachFragment(Fragment childFragment) {
//        super.onAttachFragment(childFragment);
//        if (childFragment instanceof IncorporacionesVPages) {
//            viewParent = (IncorporacionesVPages) childFragment;
//        } else {
//            //throw new RuntimeException(childFragment.toString().concat(" is not OnInteractionActivity"));
//            Log.e(TAG, "is not OnInteractionActivity");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        viewParent = null;
////        menuListener = null;
//    }
}
