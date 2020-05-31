package com.dupreincaperu.dupree.mh_fragments_menu.incorporaciones.listado.incripcion;


import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import com.image.lib_image.util.PermissionCamera;
import com.dupreeinca.lib_api_rest.model.dto.request.InscriptionDTO;
import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.FragmentDatosContactoBinding;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.mh_utilities.dialogs.DialogListOption;
import com.dupreincaperu.dupree.view.activity.BaseActivityListener;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatosContactoFragment extends BaseFragment implements View.OnClickListener, PermissionCamera.Events {

    public static String TAG = DatosContactoFragment.class.getName();
    private FragmentDatosContactoBinding binding;
    private InscriptionDTO model;
    private PermissionCamera camera;
    public DatosContactoFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getMainLayout() {
        return R.layout.fragment_datos_contacto;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle;
        if((bundle = getArguments()) != null && (model = bundle.getParcelable(TAG)) != null) {
            isOnCreate = true;

        } else {
            onBack();
        }
    }

    @Override
    protected void initViews(ViewDataBinding view) {
        binding = (FragmentDatosContactoBinding) view;
        binding.setCallback(this);
        binding.setModel(model);

        binding.imgServicios.setClickable(true);
        binding.imgServicios.setEnabled(true);
        binding.imgServicios.setOnClickListener(null);
        binding.imgServicios.setVisibility(View.VISIBLE);

        binding.imgRuc.setClickable(true);
        binding.imgRuc.setEnabled(true);
        binding.imgRuc.setOnClickListener(null);
        binding.imgRuc.setVisibility(View.VISIBLE);
        binding.linearImgServicios.setVisibility(View.VISIBLE);

        binding.imgCentralRiesgo.setClickable(true);
        binding.imgCentralRiesgo.setEnabled(true);
        binding.imgCentralRiesgo.setOnClickListener(null);
        binding.imgCentralRiesgo.setVisibility(View.VISIBLE);
        binding.linearImgCentralRiesgo.setVisibility(View.VISIBLE);

        binding.txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Validate val = new Validate();
                String email_modificado = "";

             //   binding.txtEmail.removeTextChangedListener(this);
              //  String email_digitado = binding.txtEmail.getText().toString();


               // boolean validacion = val.isValidEmail(email_digitado);
               // if (validacion == false){
               //      email_modificado = email_digitado.replaceAll("[^a-zA-Z0-9@.#$%^&*_&?$()]", "");
                //}else {
                //      email_modificado = email_digitado;
                //}
                //Log.e("TECLA", "presente:"+email_digitado+". Modificado:" + email_modificado);
//                binding.txtEmail.setText(email_modificado);
//                binding.txtEmail.setSelection(email_modificado.length());
//                binding.txtEmail.addTextChangedListener(this);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void onLoadedView() {
        camera = new PermissionCamera(this, this);
        if(isOnCreate){
            isOnCreate = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgCedFrontal:
                imageSelected = IMG_CED_FRT;
                showMenuImage();
                break;
            case R.id.imgCedAdverso:
                imageSelected = IMG_CED_ADV;
                showMenuImage();
                break;
            case R.id.imgPagFrontal:
                imageSelected = IMG_PAG_FRT;
                showMenuImage();
                break;
            case R.id.imgPagAdverso:
                imageSelected = IMG_PAG_ADV;
                showMenuImage();
                break;
            case R.id.btnContinuar:
                if(validate()) {
                    nextPage();
                }
                break;
            case R.id.imgServicios:
                imageSelected = IMG_SERV_PUB;
                showMenuImage();
                break;
            case R.id.imgRuc:
                imageSelected = IMG_RUC;
                showMenuImage();
                break;
            case R.id.imgCentralRiesgo:
                imageSelected = IMG_CENTRAL_RSG;
                showMenuImage();
                break;
        }
    }

    public boolean validate() {
        return validateContacto() && validateImages();
    }

    private boolean validateContacto(){
        Validate valid=new Validate();
        //contacto
       // if ( model.getCelular()!= "0" && (!model.getCelular().isEmpty() && model.getCelular().length() < 10))
        if ( model.getCelular()== "0" || model.getCelular().isEmpty())
        {
            //msgToast("Teléfono movil requerido... Verifique");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtCellphone);
            return false;
        }

        if ( model.getCorreo()== "0" || model.getCorreo().isEmpty())
        {
            //msgToast("Teléfono Fijo requerido... Verifique");

            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtEmail);
            return false;
        }

        if (!validarEmail(model.getCorreo())){
            valid.setLoginError(getResources().getString(R.string.email_novalido), binding.txtEmail);
            return false;
        }


        if( (model.getTelefono().isEmpty() && model.getCelular().isEmpty()) || (model.getTelefono() == "0"  && model.getCelular()== "0") )
        {
            msgToast("Debe diligenciar por lo menos uno de los teléfonos.");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtPhone);
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtCellphone);
            return false;
        }

        return true;
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validateImages(){
        if(TextUtils.isEmpty(model.getCedula_frontal())){
            msgToast("Adjunte frontal del DNI");
            return false;
        }

        if(TextUtils.isEmpty(model.getCedula_adverso())){
            msgToast("Adjunte adverso del DNI");
            return false;
        }

        if(TextUtils.isEmpty(model.getPagare_frontal())){
            msgToast("Adjunte frontal de la solicitud");
            return false;
        }

        if(TextUtils.isEmpty(model.getPagare_adverso())){
            msgToast("Adjunte adverso de la solicitud");
            return false;
        }

        if(TextUtils.isEmpty(model.getImg_terminos())){
            msgToast("Adjunte recibo de servicios");
            return false;
        }

        /*
        if(TextUtils.isEmpty(model.getImg_central())){
            msgToast("Adjunte central de riesgo");
            return true;
        }*/

        if(TextUtils.isEmpty(model.getImg_ruc())){
            msgToast("Adjunte fachada de casa");
            return false;
        }


        return true;
    }

    private final int IMG_CED_FRT=0, IMG_CED_ADV=1, IMG_PAG_FRT=2, IMG_PAG_ADV=3,IMG_SERV_PUB=4,IMG_RUC=5,IMG_CENTRAL_RSG=6;
    private int imageSelected=-1;
    public void
    nextPage(){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ReferenciasFragment.TAG, model);
        baseActivityListener.replaceFragmentWithBackStack(ReferenciasFragment.class, true, bundle);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String path = getActivity().getCacheDir() + "dupree_"+"documents"+"_temporary_file"+String.valueOf(imageSelected)+".jpg";

        camera.onActivityResult(requestCode, resultCode, data, false);
    }

    File fileList;
    @Override
    public void imageFile(File imageFile) {
        Log.e(TAG, "imageFile: "+new Gson().toJson(imageFile));
        this.fileList=imageFile;
//        sendImageMultiPart(fileList);
        updateFile(imageFile, imageSelected);
    }


    private void updateFile(File file, int indexFile){
        Log.e(TAG,"File: "+String.valueOf(indexFile)+", "+file.getAbsolutePath());
        switch (indexFile){
            case IMG_CED_FRT:
                model.setCedula_frontal(file.getAbsolutePath());
                break;
            case IMG_CED_ADV:
                model.setCedula_adverso(file.getAbsolutePath());
                break;
            case IMG_PAG_FRT:
                model.setPagare_frontal(file.getAbsolutePath());
                break;
            case IMG_PAG_ADV:
                model.setPagare_adverso(file.getAbsolutePath());
                break;
            case IMG_SERV_PUB:
                model.setImg_terminos(file.getAbsolutePath());
                break;
            case IMG_RUC:
                model.setImg_ruc(file.getAbsolutePath());
                break;
            case IMG_CENTRAL_RSG:
                model.setImg_central(file.getAbsolutePath());
                break;
        }
    }
}
