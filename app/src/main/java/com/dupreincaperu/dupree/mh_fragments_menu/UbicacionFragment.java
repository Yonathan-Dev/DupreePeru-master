package com.dupreincaperu.dupree.mh_fragments_menu;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dupreeinca.lib_api_rest.controller.ReportesController;
import com.dupreeinca.lib_api_rest.controller.UserController;
import com.dupreeinca.lib_api_rest.model.base.TTError;
import com.dupreeinca.lib_api_rest.model.base.TTResultListener;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreeinca.lib_api_rest.model.dto.response.DataUser;
import com.dupreeinca.lib_api_rest.model.dto.response.GenericDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.PerfilDTO;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.RequeridUbicacion;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.dupreincaperu.dupree.view.fragment.BaseFragment;
import com.google.gson.Gson;


/**
 * A simple {@link Fragment} subclass.
 */
public class UbicacionFragment extends BaseFragment implements View.OnClickListener {

    private LocalizacionHelper location;
    private View view;
    private ReportesController reportesController;
    private UserController userController;
    private String userName;
    private EditText idUser;
    private Profile perfil;
    private TextInputLayout txtIdUser;

    public UbicacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ubicacion, container, false);

        Button btnLocation = view.findViewById(R.id.btnLocation);
        idUser = view.findViewById(R.id.idUser);
        txtIdUser = view.findViewById(R.id.txtIdUser);
        btnLocation.setOnClickListener(this);
        localizationInit();
        return view;
    }

    @Override
    protected int getMainLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(ViewDataBinding view) {
    }

    @Override
    protected void onLoadedView() {
        reportesController = new ReportesController(getContext());
        userController = new UserController(getContext());
        if(perfil != null){
            if(!perfil.getPerfil().equals(Profile.ADESORA)){
                idUser.setVisibility(View.VISIBLE);
                txtIdUser.setVisibility(View.VISIBLE);
            }else {
                idUser.setText(perfil.getValor());
            }
        }

    }

    private void localizationInit() {
        location = new LocalizacionHelper(this.getContext());

        if (location.mGoogleApiClient != null) {
            location.mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (location != null && location.mGoogleApiClient != null) {
            location.mGoogleApiClient.connect();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLocation:
                sendLocation();
                break;
        }
    }

    private void sendLocation(){
        RequeridUbicacion obtenerDatosUbicacion = obtenerDatosUsuario();
        if(obtenerDatosUbicacion != null){
            showProgress();
            reportesController.reporteUbicacionCliente(obtenerDatosUbicacion,new TTResultListener<GenericDTO>() {
                @Override
                public void success(GenericDTO result) {
                    dismissProgress();
                    msgToast(getString(R.string.msg_ubicacion_ok));
                    idUser.setText("");
                }

                @Override
                public void error(TTError error) {
                    dismissProgress();
                    if(error.getMessage() != null) {
                        msgToast(error.getMessage());
                    }
                }
            });
        }else{
            msgToast(getString(R.string.msg_ubicacion_no_ok));
        }

    }

    private RequeridUbicacion obtenerDatosUsuario() {
        DataUser user = checkPerfil(new Identy(this.perfil.getValor()));
        String idUserSend = null;
        if(user!=null){
            this.userName = user.getNombre()+"-"+user.getApellido();
        }

        if(perfil != null){
            if(!perfil.getPerfil().equals(Profile.ADESORA)){
                if(this.idUser.getText().toString().isEmpty()){
                    msgToast("por favor ingrese la cédula del asesor");
                    this.idUser.setError("Ingrese la cédula del asesor");
                    return null;
                }else{
                    idUserSend = this.idUser.getText().toString();
                }
            }else{
                idUserSend = this.perfil.getValor();
            }
        }
        return new RequeridUbicacion(
                String.valueOf(location.latitude),
                String.valueOf(location.longitude),
                this.userName,
                this.perfil.getPerfil(),
                idUserSend,
                idUserSend
        );

    }

    private DataUser checkPerfil(Identy identy){
        String jsonPerfil = mPreferences.getJSON_PerfilUser(getActivity());
        DataUser perfilUser = null;

        if(jsonPerfil!=null){
            perfilUser = new Gson().fromJson(jsonPerfil, DataUser.class);
            if(perfilUser!=null) {
                return perfilUser;
            }
        } else {
            getDataUser(identy);
        }
        return perfilUser;
    }
    public Profile getPerfil(){
        String jsonPerfil = mPreferences.getJSON_TypePerfil(getActivity());
        if(jsonPerfil!=null)
            return new Gson().fromJson(jsonPerfil, Profile.class);

        return null;
    }

    private void getDataUser(Identy data){
        showProgress();
        userController.getPerfilUser(data,new TTResultListener<PerfilDTO>() {
            @Override
            public void success(PerfilDTO result) {
                dismissProgress();
                setData(result.getPerfilList().get(0));
            }

            @Override
            public void error(TTError error) {
                dismissProgress();
                checkSession(error);
            }
        });
    }

    private void setData(DataUser dataUser){
        this.userName = dataUser.getNombre()+"-"+dataUser.getApellido();
    }


    public void loadData(Profile perfil){
        this.perfil=perfil;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (location != null && location.mGoogleApiClient.isConnected()) {
            location.mGoogleApiClient.disconnect();
        }
    }
}
