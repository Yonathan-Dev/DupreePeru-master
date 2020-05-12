package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

public class dialogo_datos {

    public  interface DatosCuadroDialogo{
        void ResultadoDatos(String modi_dire, String modi_refe);
    }

    private DatosCuadroDialogo interfaz;

    private final dialogo_datos contexto;

    public dialogo_datos(final Context Contexto, DatosCuadroDialogo actividad) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_datos);
        ImageView guardar = (ImageView)dialogo.findViewById(R.id.img_guar_dato);
        ImageView cerrar  = (ImageView)dialogo.findViewById(R.id.img_cerr_dial);
        final TextInputEditText tie_modi_dire = (TextInputEditText) dialogo.findViewById(R.id.tie_modi_dire);
        final TextInputEditText tie_modi_refe = (TextInputEditText) dialogo.findViewById(R.id.tie_modi_refe);
        TextView txt_mens_modi =  (TextView) dialogo.findViewById(R.id.txt_mens_modi);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tie_modi_dire.getText().toString().trim().equalsIgnoreCase("") && !tie_modi_refe.getText().toString().trim().equalsIgnoreCase("")){
                    interfaz.ResultadoDatos(tie_modi_dire.getText().toString().toUpperCase(), tie_modi_refe.getText().toString().toUpperCase());
                    dialogo.dismiss();
                } else {
                    txt_mens_modi.setText("INGRESE TODO LOS DATOS");
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
