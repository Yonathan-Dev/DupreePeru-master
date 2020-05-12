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

public class dialogo_celular {


    public  interface CelularCuadroDialogo{
        void ResultadoNumeroCelular(String nume_celu);
    }

    private CelularCuadroDialogo interfaz;

    private final dialogo_celular contexto;

    public dialogo_celular(final Context Contexto, CelularCuadroDialogo actividad) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_celular);

        final TextView edt_nume_celu = (TextView)dialogo.findViewById(R.id.edt_nume_celu);
        final TextView txt_mens_nume_celu = (TextView)dialogo.findViewById(R.id.txt_mens_nume_celu);

        Button aceptar = (Button)dialogo.findViewById(R.id.btn_acep_nume_celu);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_nume_celu.getText().toString().length()==9){
                    interfaz.ResultadoNumeroCelular(edt_nume_celu.getText().toString());
                    dialogo.dismiss();
                    txt_mens_nume_celu.setVisibility(View.GONE);
                } else {
                    txt_mens_nume_celu.setVisibility(View.VISIBLE);
                    txt_mens_nume_celu.setText("NÚMERO INCORRECTO");
                }

            }
        });

        dialogo.show();
    }


}
