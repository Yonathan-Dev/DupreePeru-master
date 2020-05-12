package com.dupreincaperu.dupree.mh_fragments_login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//import com.dupree2.Dupree.R;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_http.Http;
import com.dupreincaperu.dupree.mh_required_api.RequiredNewPwd;
import com.dupreincaperu.dupree.mh_utilities.Validate;
import com.dupreincaperu.dupree.mh_utilities.mPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordFragment extends Fragment {


    public PasswordFragment() {
        // Required empty public constructor
    }

    public static PasswordFragment newInstance() {

        Bundle args = new Bundle();

        PasswordFragment fragment = new PasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    EditText txtNewPwd, txtRepeatNewPwd;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_password, container, false);
        txtNewPwd = (EditText) v.findViewById(R.id.txtNewPwd);
        txtRepeatNewPwd = (EditText) v.findViewById(R.id.txtRepeatNewPwd);
        Button btnNewPwd = (Button) v.findViewById(R.id.btnNewPwd);
        btnNewPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateNewPwd()){
                    httpValidatePwd(new RequiredNewPwd(mPreferences.getCodeSMS(getActivity()), txtNewPwd.getText().toString(), txtRepeatNewPwd.getText().toString()));
                }
            }
        });
        return v;
    }

    public boolean validateNewPwd(){
        Validate valid=new Validate();
        if(valid.isValidPwd(txtNewPwd.getText().toString())){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtNewPwd);
            return false;
        } else if(valid.isValidPwd(txtRepeatNewPwd.getText().toString())){
            valid.setLoginError(getResources().getString(R.string.campo_requerido), txtRepeatNewPwd);
            return false;
        } else if(!txtRepeatNewPwd.getText().toString().equals(txtNewPwd.getText().toString())){
            valid.setLoginError(getResources().getString(R.string.claves_diferentes), txtRepeatNewPwd);
            return false;
        }
        return true;
    }

    private void httpValidatePwd(RequiredNewPwd requiredNewPwd){
        new Http(getActivity()).validateNewPwd(requiredNewPwd);
    }

}
