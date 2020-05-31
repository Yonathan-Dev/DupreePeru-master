package com.dupreincaperu.dupree.mh_fragments_login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreeinca.lib_api_rest.model.dto.request.Identy;
import com.dupreincaperu.dupree.mh_utilities.Validate;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotFragment extends Fragment {


    public ForgotFragment() {
        // Required empty public constructor
    }

    public static ForgotFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ForgotFragment fragment = new ForgotFragment();
        fragment.setArguments(args);
        return fragment;
    }

    EditText txtIdenty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgot2, container, false);

        txtIdenty = (EditText) v.findViewById(R.id.txtIdenty);
        Button btnForgot = (Button) v.findViewById(R.id.btnForgot);
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateAuth()){
                    httpNotifyForgot(txtIdenty.getText().toString());
                    txtIdenty.setText("");
                }
            }
        });

        return v;
    }

    public boolean validateAuth(){
        Validate valid=new Validate();
        if(txtIdenty.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtIdenty);
            return false;
        }
        return true;
    }

    private void httpNotifyForgot(String cedula){
        new Http(getActivity()).notifyForgot(new Identy(cedula));
    }
}
