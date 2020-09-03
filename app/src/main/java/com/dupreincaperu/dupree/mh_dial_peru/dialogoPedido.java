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

import java.util.Collections;


public class dialogoPedido {


    public  interface cierroDialogo{
        void ResultadoDialogo();
    }

    private cierroDialogo interfaz;

    private final dialogoPedido contexto;

    public dialogoPedido(final Context Contexto, cierroDialogo actividad, final String codi_camp_actu, final String codi_camp_sigu, final String fech_inic, final String fech_fina) {

        interfaz = actividad;

        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogopedido);

        final Button    btn_cerr_dial = (Button)   dialogo.findViewById(R.id.btn_cerr_dial);
        final TextView  txt_parr_unos = (TextView) dialogo.findViewById(R.id.txt_parr_unos);
        final TextView  txt_parr_doss = (TextView) dialogo.findViewById(R.id.txt_parr_doss);

        txt_parr_unos.setText("La campaña "+codi_camp_actu+", ya no esta disponible.");
        txt_parr_doss.setText("Puede ingresar a digitar el pedido de "+codi_camp_sigu+ " a partir del "+fech_inic+" hasta el "+fech_fina+".");

        btn_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoDialogo();
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }



}
