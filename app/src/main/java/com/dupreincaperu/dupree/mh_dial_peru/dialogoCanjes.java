package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;


public class dialogoCanjes {


    public  interface mostrarCanjes{
        void ResultadoDialogo(String nume_iden);
    }

    private mostrarCanjes interfaz;

    private final dialogoCanjes contexto;

    public dialogoCanjes(final Context Contexto, mostrarCanjes actividad, String nume_iden) {

        interfaz = actividad;

        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogocanjes);

        final Button    btn_acep_dial = (Button)   dialogo.findViewById(R.id.btn_acep_dial);
        final Button    btn_cerr_dial = (Button)   dialogo.findViewById(R.id.btn_cerr_dial);


        btn_acep_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoDialogo(nume_iden);
                dialogo.dismiss();
            }
        });

        btn_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }



}
