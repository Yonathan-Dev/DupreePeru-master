package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

public class cuadro_mensaje_bd {

    private final cuadro_mensaje_bd contexto;

    public cuadro_mensaje_bd(final Context Contexto, final String msn, int img) {


        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_mensaje_bd);


        TextView mensaje = (TextView)dialogo.findViewById(R.id.txt_mens_info_base_dato);
        ImageView img_mens =  (ImageView)dialogo.findViewById(R.id.img_mens_info_base_dato);


        if (img ==1){
            img_mens.setImageResource(R.drawable.cargar);
        } else if (img==2){
            img_mens.setImageResource(R.drawable.borrar);
        } else if(img==3){
            img_mens.setImageResource(R.drawable.base_limpio);
        } else if(img==4){
            img_mens.setImageResource(R.drawable.sincronizar);
        }


        mensaje.setText(msn);

        final Button acept = (Button)dialogo.findViewById(R.id.btnaceptar_mens);

        acept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

}
