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

public class EstadoPreincripcion {
    TextView txt_nomb_comp, txt_codi_zona, txt_codi_sect, txt_camp_ingr, txt_ulti_camp, txt_sald_docu;
    TextView txt_tipo_clie;
    public EstadoPreincripcion(final Context Contexto, String nomb_comp, String codi_zona, String codi_sect, String camp_ingr, String ulti_camp, String sald_docu, String tipo_clie) {

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.estado_preinscripcion);

        txt_nomb_comp = (TextView) dialogo.findViewById(R.id.txt_nomb_comp);
        txt_codi_zona = (TextView) dialogo.findViewById(R.id.txt_codi_zona);
        txt_codi_sect = (TextView) dialogo.findViewById(R.id.txt_codi_sect);
        txt_camp_ingr = (TextView) dialogo.findViewById(R.id.txt_camp_ingr);
        txt_ulti_camp = (TextView) dialogo.findViewById(R.id.txt_ulti_camp);
        txt_sald_docu = (TextView) dialogo.findViewById(R.id.txt_sald_docu);
        txt_tipo_clie = (TextView) dialogo.findViewById(R.id.txt_tipo_clie);

        txt_nomb_comp.setText(nomb_comp);
        txt_codi_zona.setText(codi_zona);
        txt_codi_sect.setText(codi_sect);
        txt_camp_ingr.setText(camp_ingr);
        txt_ulti_camp.setText(ulti_camp);
        txt_sald_docu.setText(sald_docu);
        txt_tipo_clie.setText(tipo_clie);

        dialogo.show();
    }
}
