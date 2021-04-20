package com.dupreincaperu.dupree.mh_fragments_main;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dupreeinca.lib_api_rest.model.dto.response.BarrioDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.CiudadDTO;
import com.dupreeinca.lib_api_rest.model.dto.response.DepartamentoDTO;
import com.dupreeinca.lib_api_rest.model.view.Profile;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_dial_peru.dialogoPolitica;
import com.dupreincaperu.dupree.mh_dialogs.ListCity;
import com.dupreincaperu.dupree.mh_dialogs.ListDpto;
import com.dupreincaperu.dupree.mh_dialogs.ListString;
import com.dupreincaperu.dupree.mh_fragments_login.AuthFragment;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_required_api.RequiredRegister_NEW_2018;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterAsesoraFragment_NEW extends Fragment implements dialogoPolitica.cierroPolitico{

    public static final String TAG = "RegisterAsesoraFragment";
    public static final String BROACAST_REG_ERROR="reg_type_error";

    EditText txtName, txtLastname, txtSpnDpto, txtSpnCity, txtPhone, txtEmail, txtComentario;

    Button btnRegister;

    public RegisterAsesoraFragment_NEW() {
        // Required empty public constructor
    }

    private ProgressDialog pDialog;
    FrameLayout ctnRegister;
    ScrollView scrollRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_asesora_new, container, false);
        //Datos personales
        ctnRegister = (FrameLayout) v.findViewById(R.id.ctnRegister);
        scrollRegister = (ScrollView) v.findViewById(R.id.scrollRegister);

        txtName = (EditText) v.findViewById(R.id.txtName);
        txtLastname = (EditText) v.findViewById(R.id.txtLastname);

        //Direccion residencia
        txtSpnDpto = (EditText) v.findViewById(R.id.txt_spn_departamento);
        txtSpnCity = (EditText) v.findViewById(R.id.txt_spn_name_ciudad);

        //Contacto
        txtPhone = (EditText) v.findViewById(R.id.txtPhone);
        txtEmail = (EditText) v.findViewById(R.id.txtEmail);

        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtEmail.removeTextChangedListener(this);
                String email_digitado = txtEmail.getText().toString();
                String email_modificado = email_digitado.replaceAll("[^a-zA-Z0-9@.#$%^&*_&?$()]", "");

                Log.e("TECLA", "presente:"+email_digitado+". Modificado:" + email_modificado);
                txtEmail.setText(email_modificado);
                txtEmail.setSelection(email_modificado.length());
                txtEmail.addTextChangedListener(this);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        txtComentario = (EditText) v.findViewById(R.id.txtComentario);

        btnRegister = (Button) v.findViewById(R.id.btnRegister);

        //events
        txtSpnDpto.setOnClickListener(mListenerClick);
        txtSpnCity.setOnClickListener(mListenerClick);

        //events direccion envio
        btnRegister.setOnClickListener(mListenerClick);
        localBroadcastReceiver = new LocalBroadcastReceiver();
        Log.e(TAG,"new LocalBroadcastReceiver()");

        getListDpto(mPreferences.getDpto(getActivity()));

        createProgress();

        return v;
    }

    public Profile getPerfil(){
        String jsonPerfil = mPreferences.getJSON_TypePerfil(getActivity());
        if(jsonPerfil!=null)
            return new Gson().fromJson(jsonPerfil, Profile.class);

        return null;
    }

    private void createProgress(){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//K0LM6F
    }

    public void showProgressDialog(){
        pDialog.setMessage(getResources().getString(R.string.msg_espere));
        pDialog.show();
    }

    public void dismissProgressDialog(){
        if(pDialog!=null)
            pDialog.dismiss();
    }

    public void error(){
        dismissProgressDialog();
    }

    List<DepartamentoDTO> listDpto=null;
    DepartamentoDTO dptoSelected;
    List<CiudadDTO> listCiudad=null;
    CiudadDTO ciudad=null;

    public void getListDpto(String data){
        Type listType = new TypeToken<ArrayList<DepartamentoDTO>>(){}.getType();
        listDpto = new Gson().fromJson(data, listType);
    }

    public String getJSONCiudad(List<CiudadDTO> listCiudad){
        return new Gson().toJson(listCiudad);
    }

    public void getDpto(String data){
        Type listType = new TypeToken<ArrayList<DepartamentoDTO>>(){}.getType();
        listDpto = new Gson().fromJson(data, listType);
    }

    public List<BarrioDTO> extractListBarrio(String data){
        Type listType = new TypeToken<ArrayList<BarrioDTO>>(){}.getType();
        return new Gson().fromJson(data, listType);
    }


    View.OnClickListener mListenerClick =
            new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.txt_spn_departamento:
                            if(listDpto!=null)
                                showDpto(ListDpto.BROACAST_REG_TYPE_DPTO, getString(R.string.departamento), listDpto, txtSpnDpto.getText().toString());
                            break;
                        case R.id.txt_spn_name_ciudad:
                            if(listCiudad!=null)
                                showCity(ListCity.BROACAST_REG_TYPE_CITY, getString(R.string.ciudad), listCiudad, txtSpnCity.getText().toString());
                            break;
                        case R.id.btnRegister:
                            register();
                            break;
                    }

                }
            };

    public static RegisterAsesoraFragment_NEW newInstance() {
        Bundle args = new Bundle();
        RegisterAsesoraFragment_NEW fragment = new RegisterAsesoraFragment_NEW();
        fragment.setArguments(args);
        return fragment;
    }

    //List<String> data = new ArrayList<String>();
    public void showList(String objectFragment, String title, List<String> data, String itemSelected){
        ListString dialogList = new ListString();
        dialogList.loadData(TAG, objectFragment, title, data, itemSelected);
        dialogList.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    public void showDpto(String tag, String title, List<DepartamentoDTO> data, String itemSelected){
        ListDpto dialogDpto = new ListDpto();
        dialogDpto.loadData(title, data, itemSelected, new ListDpto.ListenerResponse() {
            @Override
            public void result(DepartamentoDTO item) {
                Log.i(TAG, tag);
                switch (tag){
                    case ListDpto.BROACAST_REG_TYPE_DPTO:
                        //limpiar
                        clearCity();

                        dptoSelected = item;
                        txtSpnDpto.setError(null);
                        txtSpnDpto.setText(dptoSelected.getName_dpto());
                        txtSpnDpto.setTag(dptoSelected.getId_dpto());
                        listCiudad=dptoSelected.getCiudades();
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
                switch (tag){
                    case ListCity.BROACAST_REG_TYPE_CITY:
                        ciudad = item;
                        txtSpnCity.setError(null);
                        txtSpnCity.setText(ciudad.getName_ciudad());
                        txtSpnCity.setTag(ciudad.getId_ciudad());

                        break;
                }
            }
        });
        dialogCity.show(getActivity().getSupportFragmentManager(),"mDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG,"onDestroyView()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,"onDetach()");
    }

    @Override
    public void onResume() {
        super.onResume();

        registerBroadcat();
        Log.i(TAG,"onResume()");
        //setSelectedItem(oldItem);
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
        unregisterBroadcat();
    }

    public void registerBroadcat(){
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                localBroadcastReceiver,
                new IntentFilter(TAG));
    }

    public void unregisterBroadcat(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                localBroadcastReceiver);
    }

    private List<BarrioDTO> barrioList;
    private List<BarrioDTO> filterBarrio;

    private static long lastTime=0;
    private static String intentRepeat="";
    private BroadcastReceiver localBroadcastReceiver;

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // safety check
            if (intent == null || intent.getAction() == null) {
                return;
            }

            Log.e(TAG,"Calendar.getInstance().getTimeInMillis()= "+String.valueOf(Calendar.getInstance().getTimeInMillis()));
            Log.e(TAG,"lastTime= "+String.valueOf(lastTime));
            //si en un tiempo menor a un segundo, llega el mismo resultado, se desecha
            //esto garantiza que se pueda ejecutar la misma accion manualmente, mas evita repeticiones del broadcast que hacen algunos telefonos
            if( (Calendar.getInstance().getTimeInMillis()-lastTime<1000) && intentRepeat.equals(intent.getExtras().toString())) {// si no ha pasado medio segundo puede ser un rebote
                Log.e(TAG,"intentRepeat()");
                return;
            }

            lastTime=Calendar.getInstance().getTimeInMillis();
            intentRepeat=intent.getExtras().toString();


            if (intent.getAction().equals(TAG)) {
                switch (intent.getStringExtra(TAG)){
                    //Datos personales
                    case BROACAST_REG_ERROR:
                        dismissProgressDialog();
                        break;
                }
            }

        }
    }

    String urlVolante;//para evitar repetir el broacast


    private void clearCity(){
        listCiudad=null;
        ciudad=null;
        txtSpnCity.setText("");
    }
    /**
     * RESPUESTAS HTTP
     */
    public void register(){
        if(validateRegister()){
            new dialogoPolitica(getContext(), RegisterAsesoraFragment_NEW.this);
            //sendDataRegister();
        }
    }

    private void sendDataRegister(){
        showProgressDialog();
        Http http = new Http(getActivity());
        http.register_main(obtainDataUser());
        http.setGenericListener(new Http.GenericListener() {
            @Override
            public void onProcess(String message) {
                dismissProgressDialog();
                clearAllData();
                toastMSG(message);
            }

            @Override
            public void onFailed() {
                dismissProgressDialog();

            }
        });
    }

    public Boolean validateRegister() {
        Validate valid=new Validate();
        //datos personales
        if (txtName.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtName);
            return false;
        } else if (txtLastname.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtLastname);
            return false;
        } else if (txtSpnDpto.getText().toString().isEmpty()){
            msgToast("Ingrese departamento");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtSpnDpto);
            return false;
        } else if (txtSpnCity.getText().toString().isEmpty()){
            msgToast("Ingrese provincia");
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtSpnCity);
            return false;
        } else if (txtPhone.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtPhone);
            return false;
        } else if (txtPhone.getText().toString().length()!=7 && txtPhone.getText().toString().length()!=9){
            valid.setLoginError(getResources().getString(R.string.campo_telefono), txtPhone);
            return false;
        } else if (txtEmail.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtEmail);
            return false;
        } else if (!txtEmail.getText().toString().isEmpty() && valid.isValidEmail(txtEmail.getText().toString())){
            valid.setLoginError(getResources().getString(R.string.formato_incorrecto), txtEmail);
            return false;
        } else if (txtComentario.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtComentario);
            return false;
        }

        return true;

    }

    public RequiredRegister_NEW_2018 obtainDataUser()
    {
        return new RequiredRegister_NEW_2018(
                txtName.getText().toString()+" "+txtLastname.getText().toString(),
                txtSpnDpto.getText().toString(),
                ciudad.getName_ciudad(),
                txtPhone.getText().toString(),
                txtEmail.getText().toString(),
                txtComentario.getText().toString()

        );
    }

    private void msgToast(String msg){
        Log.e("onError", msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void clearAllData(){
        txtName.setText("");
        txtLastname.setText("");

        txtSpnDpto.setText("");
        txtSpnCity.setText("");

        //Contacto
        txtPhone.setText("");
        txtEmail.setText("");

        txtComentario.setText("");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG,"new setUserVisibleHint(): "+String.valueOf(isVisibleToUser));
    }

    private void toastMSG(String msg){
        Log.e(TAG, "toast, "+msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void ResultadoPolitico(String peti) {

        if (peti.equalsIgnoreCase("")){
            sendDataRegister();
        } else if(peti.equalsIgnoreCase("politica")){
            Uri pdf2 = Uri.parse(getString(R.string.api_poli));
            Intent i = new Intent(Intent.ACTION_VIEW, pdf2);
            startActivity(i);
        } else if(peti.equalsIgnoreCase("terminos")){
            Uri pdf1 = Uri.parse(getString(R.string.api_term));
            Intent j = new Intent(Intent.ACTION_VIEW, pdf1);
            startActivity(j);
        }

    }
}
