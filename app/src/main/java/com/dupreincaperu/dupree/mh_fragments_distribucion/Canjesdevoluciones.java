package com.dupreincaperu.dupree.mh_fragments_distribucion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;

public class Canjesdevoluciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canjesdevoluciones);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String nume_iden = parametros.getString("nume_iden");
            Toast.makeText(getBaseContext(),""+nume_iden, Toast.LENGTH_SHORT).show();
        }
    }


}