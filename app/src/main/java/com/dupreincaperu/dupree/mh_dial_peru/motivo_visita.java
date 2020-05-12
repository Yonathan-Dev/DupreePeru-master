package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;


public class motivo_visita {

    public  interface FinalizoMotivoDialogo{
        void ResultadoMotivoDialogo(String esta_visi, String dire_terc, String dire_refe);
    }

    private FinalizoMotivoDialogo interfaz;

    private final motivo_visita contexto;


    public motivo_visita(final Context Contexto, motivo_visita.FinalizoMotivoDialogo actividad) {

        interfaz = actividad;

        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.moti_visi);

        final Button   btn_cerr_moti = (Button)   dialogo.findViewById(R.id.btn_cerr_moti);
        final Button   btn_acep_moti = (Button)   dialogo.findViewById(R.id.btn_acep_moti);
        final TextView txt_aler_moti = (TextView) dialogo.findViewById(R.id.txt_aler_moti);
        final EditText edt_dire_terc = (EditText) dialogo.findViewById(R.id.edt_dire_terc);
        final EditText edt_dire_refe = (EditText) dialogo.findViewById(R.id.edt_dire_refe);
        final Spinner  spn_tipo_visi = (Spinner)  dialogo.findViewById(R.id.spn_tipo_visi);

        spn_tipo_visi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spn_tipo_visi.getSelectedItem().toString().equalsIgnoreCase("NUEVA UBICACION")){
                    edt_dire_terc.setVisibility(View.VISIBLE);
                    edt_dire_refe.setVisibility(View.VISIBLE);
                    txt_aler_moti.setVisibility(View.GONE);
                    txt_aler_moti.setText("");
                } else {
                    edt_dire_terc.setVisibility(View.GONE);
                    edt_dire_refe.setVisibility(View.GONE);
                    txt_aler_moti.setVisibility(View.GONE);
                    txt_aler_moti.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_acep_moti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!spn_tipo_visi.getSelectedItem().toString().equalsIgnoreCase("SELECCIONE")){
                    if (spn_tipo_visi.getSelectedItem().toString().equalsIgnoreCase("NUEVA UBICACION") && edt_dire_refe.getText().toString().equalsIgnoreCase("") && edt_dire_terc.getText().toString().equalsIgnoreCase("")){
                        txt_aler_moti.setVisibility(View.VISIBLE);
                        txt_aler_moti.setText("* ERROR CAMPOS OBLIGATORIOS");
                    } else{
                        interfaz.ResultadoMotivoDialogo(spn_tipo_visi.getSelectedItem().toString(), edt_dire_terc.getText().toString().trim(), edt_dire_refe.getText().toString().trim());
                        dialogo.dismiss();
                    }
                } else {
                    txt_aler_moti.setVisibility(View.VISIBLE);
                    txt_aler_moti.setText("* ERROR MOTIVO VISITA OBLIGATORIO");
                }
            }
        });

        btn_cerr_moti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }

}
