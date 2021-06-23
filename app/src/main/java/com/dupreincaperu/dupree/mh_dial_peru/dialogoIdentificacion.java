package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import static com.dupreincaperu.dupree.BaseAPP.getContext;

public class dialogoIdentificacion {


    public  interface IdentificaionCuadroDialogo{
        void ResultadoNumeroIdentificacion(String nume_iden);
    }

    private IdentificaionCuadroDialogo interfaz;

    private final dialogoIdentificacion contexto;

    public dialogoIdentificacion(final Context Contexto, IdentificaionCuadroDialogo actividad) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogoidentificacion);

        final ImageView img_cerr_dial = dialogo.findViewById(R.id.img_cerr_dial);
        final EditText  edt_nume_iden = dialogo.findViewById(R.id.edt_nume_iden);
        final Button    btn_busc_iden = dialogo.findViewById(R.id.btn_busc_iden);

        img_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        btn_busc_iden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_nume_iden.getText().toString().trim().equalsIgnoreCase("")){
                    edt_nume_iden.setError(true? "Campo identificación obligatorio" : "");
                } else if(edt_nume_iden.getText().toString().trim().length()!=8 && edt_nume_iden.getText().toString().trim().length()!=11){
                    edt_nume_iden.setError(true? "Longitud de identificación incorrecto" : "");
                } else {
                    interfaz.ResultadoNumeroIdentificacion(edt_nume_iden.getText().toString().toUpperCase());
                    dialogo.dismiss();
                }
            }
        });

        dialogo.show();
    }


}
