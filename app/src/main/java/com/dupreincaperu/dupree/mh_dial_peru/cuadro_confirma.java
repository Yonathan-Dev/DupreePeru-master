package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;


public class cuadro_confirma {


    public  interface FinalizoCuadroDialogo{
        void ResultadoCuadroDialogo(String motivo);
    }

    private FinalizoCuadroDialogo interfaz;

    private final cuadro_confirma contexto;

    public cuadro_confirma(final Context Contexto, FinalizoCuadroDialogo actividad, final String[] nomb_moti) {

        interfaz = actividad;

        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_conf_dist);

        final Button no_confi = (Button)dialogo.findViewById(R.id.btn_canc_pedi);
        final ImageView cerrar = (ImageView)dialogo.findViewById(R.id.img_cerr_moti);

        final TextView alerta = (TextView) dialogo.findViewById(R.id.txtmenalert);
        final Spinner motivo = (Spinner)dialogo.findViewById(R.id.spn_motivo);
        motivo.setAdapter(new ArrayAdapter<String>(Contexto, android.R.layout.simple_spinner_dropdown_item,nomb_moti));

        no_confi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!motivo.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE MOTIVO")){
                    interfaz.ResultadoCuadroDialogo(motivo.getSelectedItem().toString());
                    dialogo.dismiss();
                } else {
                    alerta.setText("*Debe ingresar el motivo");
                }
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }



}
