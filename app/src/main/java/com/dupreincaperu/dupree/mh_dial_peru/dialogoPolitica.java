package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_fragments_login.AuthFragment;

import org.w3c.dom.Text;

public class dialogoPolitica {

    public  interface cierroPolitico{
        void ResultadoPolitico(String peti);
    }

    private cierroPolitico interfaz;

    private final dialogoPolitica contexto;

    public dialogoPolitica(final Context Contexto, cierroPolitico actividad) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogopolitica);

        ImageView img_cerr_poli = dialogo.findViewById(R.id.img_cerr_poli);
        CheckBox  chk_poli_priv = dialogo.findViewById(R.id.chk_poli_priv);
        CheckBox  chk_term_cond = dialogo.findViewById(R.id.chk_term_cond);
        TextView txt_poli_priv  = dialogo.findViewById(R.id.txt_poli_priv);
        TextView txt_term_cond  = dialogo.findViewById(R.id.txt_term_cond);

        Button    btn_segu_vent = dialogo.findViewById(R.id.btn_segu_vent);
        TextView  txt_mens_poli = dialogo.findViewById(R.id.txt_mens_poli);

        img_cerr_poli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        btn_segu_vent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_mens_poli.setText("");
                if (chk_poli_priv.isChecked() && chk_term_cond.isChecked()){
                    dialogo.dismiss();
                    interfaz.ResultadoPolitico("");
                } else if(!chk_poli_priv.isChecked() && !chk_term_cond.isChecked()){
                    txt_mens_poli.setText("Debe confirmar lectura de los dos documentos.");
                } else if(!chk_poli_priv.isChecked()){
                    txt_mens_poli.setText("Debe confirmar lectura de la Politica de Privacidad y Protección de Datos Personal.");
                } else if(!chk_term_cond.isChecked()){
                    txt_mens_poli.setText("Debe confirmar lectura de los Términos y condiciones de uso.");
                }

            }
        });

        txt_poli_priv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoPolitico("politica");
            }
        });

        txt_term_cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.ResultadoPolitico("terminos");
            }
        });

        txt_poli_priv.setText(Html.fromHtml("Confirmo que he leido y entendido la <font color='#0000FF'><u>Política de Privacidad y Protección de Datos Personal.</u></font>"));
        txt_term_cond.setText(Html.fromHtml("Confirmo que he leído y entendido los <font color='#0000FF'><u>Términos y Condiciones de Uso.</u></font>"));

        dialogo.show();
    }
}
