package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.incripcion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.image.lib_image.util.PermissionCamera;
import com.dupreeinca.lib_api_rest.controller.InscripcionController;
import com.dupreeinca.lib_api_rest.controller.UploadFileController;
import com.dupreeinca.lib_api_rest.enums.EnumFormatDireccion;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.model.dto.request.ValidateRef;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentDatosPersonalesBinding;
import com.dupreincaperu.dupree.files.LoadJsonFile;
import com.dupreincaperu.dupree.files.ManagerFiles;
import com.dupreincaperu.dupree.mh_dialogs.DateDialog;
import com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.Incorp_ListPre_Fragment;
import com.dupreincaperu.dupree.mh_utilities.PinchZoomImageView;
import com.dupreincaperu.dupree.mh_utilities.dialogs.DialogListOption;
import com.dupreincaperu.dupree.model_view.DataAsesora;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.view.activity.BaseActivityListener;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatosPersonalesFragment extends BaseFragment implements View.OnClickListener, PermissionCamera.Events {

    public static String TAG = DatosPersonalesFragment.class.getName();
    private FragmentDatosPersonalesBinding binding;
    private InscriptionDTO model;
    private InscripcionController inscripController;

    private LoadJsonFile jsonFile;
    private List<ModelList> listDirSend;
    List<String> listP;
    private DataAsesora data;
    private PermissionCamera camera;
    private UploadFileController uploadFileController;
    //String urlImage="";
    boolean imge_sended=false;
    private static final int PDF417_REQUEST_CODE = 1;
    private EditText txtNameIncrip;
    private View view;
    private String estado;
    int band_refe = 0;

    public DatosPersonalesFragment() {

    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_datos_personales;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new InscriptionDTO();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            estado = bundle.getString("estado", estado);
            data = bundle.getParcelable(TAG);
            bundle.putParcelable(DatosPersonalesFragment.TAG, data);
            isOnCreate = true;
        }else{
            onBack();
        }

    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentDatosPersonalesBinding) view;
        binding.setCallback(this);
        binding.setModel(model);
        controlImage(true);
        if(estado.equalsIgnoreCase("RECHAZADO")){
            binding.txtNameIncrip.setEnabled(true);
            binding.txtNameIncrip.setFocusableInTouchMode(true);
            binding.imgVolante.setClickable(false);
            binding.imgVolante.setEnabled(false);
            binding.imgVolante.setOnClickListener(null);
            binding.imgVolante.setVisibility(View.GONE);
            binding.txtZone.setEnabled(true);
            binding.linearImgVolante.setVisibility(View.GONE);
            binding.layoutTxtNamePre.setVisibility(View.VISIBLE);
            binding.layoutTxtLastnamePre.setVisibility(View.VISIBLE);
            binding.layoutTxtNameIncrip.setVisibility(View.GONE);

        }else{
            binding.imgVolante.setClickable(false);
            binding.imgVolante.setEnabled(false);
            binding.imgVolante.setOnClickListener(null);
            binding.imgVolante.setVisibility(View.GONE);
            binding.txtZone.setEnabled(true);
            binding.linearImgVolante.setVisibility(View.GONE);
            binding.layoutTxtNamePre.setVisibility(View.GONE);
            binding.layoutTxtLastnamePre.setVisibility(View.GONE);
            binding.layoutTxtNameIncrip.setVisibility(View.VISIBLE);
        }
        uploadFileController = new UploadFileController(getContext());

      /*  binding.imgVolante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelected = IMG_CED_FRT;
                showMenuImage();
//                permissionImage();
            }
        });*/
    }

    ////IMAGE
    private void showMenuImage(){
        DialogListOption dialogListOption = new DialogListOption(getActivity());
        List<ModelList> list = new ArrayList<>();
        list.add(new ModelList(0, getString(R.string.camara)));
        list.add(new ModelList(1, getString(R.string.galeria)));

        dialogListOption.showDialog(getString(R.string.seleccionar), list);
        dialogListOption.setDialogSelectModel(new DialogListOption.DialogSelectModel() {
            @Override
            public void onModelSelected(ModelList item) {
                switch (item.getId()) {
                    case 0:

                        camera.checkPermissionCamera();
                        break;
                    case 1:
                        camera.checkPermissionGalery();
                        break;
                }
            }
        });
    }

    @Override
    protected void onLoadedView() {
        camera = new PermissionCamera(this, this);
        inscripController = new InscripcionController(getContext());
        jsonFile = new LoadJsonFile(getContext());

        listDirSend = jsonFile.getParentezcos(ManagerFiles.DIR_SEND.getKey());
        listP = Arrays.asList(getResources().getStringArray(R.array.parentescoOptions));

        if(isOnCreate){
            isOnCreate = false;
            loadData(data);
        }
    }

    public void loadData(DataAsesora data){
        clearData();
        model.setReferenciado_hint(getResources().getString(R.string.referido));
        model.setCedula(data.getCedula().isEmpty() ? Incorp_ListPre_Fragment.identySelected : data.getCedula());
        model.setNombre(data.getNombre().isEmpty() ? Incorp_ListPre_Fragment.nameSelected : data.getNombre());//se redunda xq a veces no llega


        model.setFormato_direccion(data.getFormato_direccion());
        model.setModeEdit(data.isModeEdit());

        if(data.isModeEdit()){
            showProgress();
            inscripController.getInscripcion(new Identy(data.getCedula()), new TTResultListener<InscriptionDTO>() {
                @Override
                public void success(InscriptionDTO result) {
//                    Log.e(TAG, new Gson().toJson(result));
                    dismissProgress();
                    model.loadDataInit(result, listDirSend, listP);
                    model.setRefValidated(true);
                    if (estado.equalsIgnoreCase("REVISION")){
                        band_refe = 1;
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

    private void clearData(){
        model.clearData();
        model.setReferenciado_hint(getResources().getString(R.string.referido));
        setRefValidated(false);
    }
    @Override
    //Boton Lupa
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgB_Searchref://
                if(!model.getReferenciado_por().isEmpty()) {
                    showProgress();
                    inscripController.validateReferido(new ValidateRef(
                            model.getReferenciado_por(),
                            dataStore.getTokenSesion()), new TTResultListener<GenericDTO>() {
                        @Override
                        public void success(GenericDTO result) {
                            dismissProgress();
                            model.setReferenciado_hint(result.getResult());
                            setRefValidated(true);
                            band_refe = 1;
                        }

                        @Override
                        public void error(TTError error) {
                            dismissProgress();
                            checkSession(error);
                            band_refe = 0;
                            binding.txtIdentyCardRef.setText("");
                        }
                    });
                }
                break;
            case R.id.imgB_CleanRef://
                setRefValidated(false);
                break;
            case R.id.txtDateBird://
                showDate();
                break;
            case R.id.btnContinuar:
                if(validate()) {
                    if (band_refe==0){
                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                        dialogo1.setTitle("Atención");
                        dialogo1.setMessage("¿ Esta seguro de seguir sin referido ?");
                        dialogo1.setCancelable(false);
                        dialogo1.setPositiveButton("Aceptar",   new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                nextPage();
                            }
                        });
                        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                dialogo1.dismiss();
                            }
                        });
                        dialogo1.show();
                    } else {
                        nextPage();
                    }
                }
                break;
            case R.id.imgVolante:
                //imageSelected = IMG_CED_FRT;
                showMenuImage();
                break;
        }
    }

    public boolean validate() {
        Log.e(TAG,"validateRegister() -> init()");
        Validate valid = new Validate();
        //datos personales
        if (band_refe==0 && binding.txtIdentyCardRef.length()>0){
            valid.setLoginError("La identificación debe ser validado",binding.txtIdentyCardRef);
            return false;
        } else if(model.getNombre().isEmpty()){
            msgToast("Nombre de asesora... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtNameIncrip);
            return false;
        } else if(model.getCedula().isEmpty()){
            msgToast("Cêdula de asesora... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtIdentyCard);
            return false;
        }
        /*else if(model.getReferenciado_por().isEmpty()){
            msgToast("Cêdula de referido... Verifique");
            valid.setLoginError(getString(R.string.campo_requerido),binding.txtIdentyCardRef);
            return false;
        } else if(!model.isRefValidated()){
            msgToast("Debe validar la cédula del referido... Verifique");
            valid.setLoginError(getString(R.string.deba_validar),binding.txtIdentyCardRef);
            return false;
        }*/
        else if (model.getNacimiento().isEmpty()) {
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtDateBird);
            return false;
        } else if (!validarFecha()) {
            valid.setLoginError(getResources().getString(R.string.formato_fecha), binding.txtDateBird);
            return false;
        } else if (!validaEdad()){
            valid.setLoginError(getResources().getString(R.string.mayor_edad), binding.txtDateBird);
            return false;
        }

        else if (model.getZona_seccion().isEmpty()) {
            //msgToast("Campo sector... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtZone);
            return false;
        } else if (model.getZona_seccion().length() != 3) {
            //msgToast("Campo sector... Verifique");
            valid.setLoginError("Debe ser de 3 digitos", binding.txtZone);
            return false;
        } else if(band_refe==1 && binding.txtIdentyCardRef.length()==0){
            band_refe = 0;
        }
        return true;
    }

    public void nextPage(){
        Bundle bundle = new Bundle();

        if(data.getFormato_direccion().equals(EnumFormatDireccion.FORMATO_1.getKey())) {
            bundle.putParcelable(DirFormato1Fragment.TAG, model);
            baseActivityListener.replaceFragmentWithBackStack(DirFormato1Fragment.class, true, bundle);
        } else if (data.getFormato_direccion().equals(EnumFormatDireccion.FORMATO_2.getKey())){
            bundle.putParcelable(DirFormato2Fragment.TAG, model);
            baseActivityListener.replaceFragmentWithBackStack(DirFormato2Fragment.class, true, bundle);
        } else if (data.getFormato_direccion().equals(EnumFormatDireccion.FORMATO_3.getKey())){
            bundle.putParcelable(DirFormato3Fragment.TAG, model);
            baseActivityListener.replaceFragmentWithBackStack(DirFormato3Fragment.class, true, bundle);
        }  else if (data.getFormato_direccion().equals(EnumFormatDireccion.FORMATO_4.getKey())){
            bundle.putParcelable(DirFormato4Fragment.TAG, model);
            baseActivityListener.replaceFragmentWithBackStack(DirFormato4Fragment.class, true, bundle);
        }
    }

    public void setRefValidated(boolean refValidated) {
        binding.txtIdentyCardRef.setError(null);
        model.setReferenciado_por(refValidated ? model.getReferenciado_por() : "");
        model.setReferenciado_hint(refValidated ? model.getReferenciado_hint() : getString(R.string.referido));
//        model.setNacimiento("");
//        model.setZona_seccion("");

        model.setRefValidated(refValidated);
        band_refe = 0;
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

    BaseActivityListener baseActivityListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivityListener) {
            baseActivityListener = (BaseActivityListener) context;
        } else
            throw new RuntimeException(context.toString().concat(" is not OnInteractionActivity"));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivityListener = null;
    }

    File fileList;
    @Override
    public void imageFile(File imageFile) {
        Log.e(TAG, "imageFile: "+new Gson().toJson(imageFile));
        this.fileList=imageFile;
        sendImageMultiPart(fileList);
    }


    private void sendImageMultiPart(File file){
        showProgress();
        uploadFileController.uploadImage(file, new TTResultListener<GenericDTO>() {
            @Override
            public void success(GenericDTO result) {
                dismissProgress();

                imge_sended = true;
                urlImage = result.getResult();
                controlImage(true);

                imageProfile(urlImage);

                model.setImg_terminos(urlImage);
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }


    private void updateFile(File file, int indexFile){
        Log.e(TAG,"File: "+String.valueOf(indexFile)+", "+file.getAbsolutePath());
        switch (indexFile){
           /* case IMG_CED_FRT:
                imageProfile(file.getAbsolutePath());
                model.setImg_terminos(file.getAbsolutePath());
                break;*/
            /*case IMG_CED_ADV:
                model.setCedula_adverso(file.getAbsolutePath());
                break;
            case IMG_PAG_FRT:
                model.setPagare_frontal(file.getAbsolutePath());
                break;
            case IMG_PAG_ADV:
                model.setPagare_adverso(file.getAbsolutePath());
                break;*/
        }
    }

    private void controlImage(boolean control){
        binding.txtIdentyCard.setEnabled(control);
        binding.txtNameIncrip.setEnabled(control);
        binding.txtDateBird.setEnabled(true);
        binding.txtZone.setEnabled(true);
        binding.imgBSearchref.setEnabled(control);
        binding.imgBCleanRef.setEnabled(control);
    }

   /* ImageLoader img;
    private void imageProfile(String url){
        Log.i(TAG, "imageProfile: "+url);
        img = ImageLoader.getInstance();
        img.init(PinchZoomImageView.configurarImageLoader(getActivity()));
        //img.clearMemoryCache();
        //img.clearDiskCache();
       // img.displayImage(url, binding.imgVolante);
        img.displayImage(url, binding.imgVolante, new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build());
    }*/

    ImageLoader img;
    private void imageProfile(String url){
        Log.i(TAG, "imageProfile: "+url);
        img = ImageLoader.getInstance();
        img.init(PinchZoomImageView.configurarImageLoader(getActivity()));
        //img.clearMemoryCache();
        //img.clearDiskCache();
        img.displayImage(url, binding.imgVolante);
    }
    String urlImage="";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("Entra", "Entra a activity result lee documento");

        try
        {
            if(requestCode == PDF417_REQUEST_CODE)
            {
                if (!data.getStringExtra("number").contains("Fallo"))
                {
                    //devolver a la forma
                    binding.txtIdentyCard.setText(data.getStringExtra("number"));
                    binding.txtNameIncrip.setText(data.getStringExtra("name"));
                    //binding.txtLastname.setText(data.getStringExtra("lastname"));
                    //binding.imgBSearchref.callOnClick();
                    msgToast("Lectura correcta del Documento");
                }
                else
                {
                    msgToast("Error al leer cedula, intente nuevamente");
                }
            }
            else
            {
                super.onActivityResult(requestCode, resultCode, data);
                camera.onActivityResult(requestCode, resultCode, data, false);
            }
        }
        catch (Exception e){
            msgToast("Imposible leer documento");
        }
    }

    public boolean validarFecha() {
        boolean correcto = false;

        try {
            //Formato de fecha (día/mes/año)
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            //Comprobación de la fecha
            formatoFecha.parse(model.getNacimiento());
            correcto = true;

            String[] parts = model.getNacimiento().split("/");
            String dia = parts[0];
            String mes = parts[1] ;
            String ano = parts[2];

            if (ano.length()!=4 || Integer.parseInt(ano)<1900){
                correcto = false;
            } else if(dia.length()>2){
                correcto = false;
            } else if (mes.length()>2){
                correcto = false;
            }

        } catch (ParseException e) {
            //Si la fecha no es correcta, pasará por aquí
            correcto = false;
        }

        return correcto;
    }


    public boolean validaEdad() {

        String datos[] = model.getNacimiento().split("/");
        int ano = Integer.parseInt(datos[2]);
        int mes = Integer.parseInt(datos[1]);
        int dia = Integer.parseInt(datos[0]);

        Calendar today = Calendar.getInstance();

        int diff_year   = today.get(Calendar.YEAR) -  ano;
        int diff_month  = (today.get(Calendar.MONTH)+1) - mes;
        int diff_day    = today.get(Calendar.DAY_OF_MONTH) - dia;

        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
        }

        if (diff_year>=18){
            return true;
        }

        return false;

    }
}
