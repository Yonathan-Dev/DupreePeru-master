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

    public dialogoPedido(final Context Contexto, cierroDialogo actividad, final String mensaje) {

        interfaz = actividad;

        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogopedido);

        final ImageView img_cerr_dial = (ImageView)dialogo.findViewById(R.id.img_cerr_dial);
        final TextView  txt_parr_unos = (TextView) dialogo.findViewById(R.id.txt_parr_unos);

        txt_parr_unos.setText(mensaje);

        img_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoDialogo();
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }

}
