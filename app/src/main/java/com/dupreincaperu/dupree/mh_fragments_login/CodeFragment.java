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
import com.dupreincaperu.dupree.mh_required_api.RequiredCode;
import com.dupreincaperu.dupree.mh_utilities.Validate;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeFragment extends Fragment {

    private final String TAG = "CodeFragment";

    public CodeFragment() {
        // Required empty public constructor
    }


    public static CodeFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CodeFragment fragment = new CodeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    EditText txtCode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_code, container, false);
        txtCode = (EditText) v.findViewById(R.id.txtCode);
        Button btnCode = (Button) v.findViewById(R.id.btnCode);

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateACode()){
                    httpValidateCode(txtCode.getText().toString());
                }
            }
        });
        return v;
    }

    public boolean validateACode(){
        Validate valid=new Validate();
        if(txtCode.getText().toString().isEmpty()){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtCode);
            return false;
        }
        return true;
    }

    private void httpValidateCode(String code){
        new Http(getActivity()).validateCode(new RequiredCode(code));
    }

}
