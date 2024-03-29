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

public class dialogoMensaje {

    public dialogoMensaje(final Context Contexto, String msn) {

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogomensaje);

        TextView mensaje = (TextView)dialogo.findViewById(R.id.txt_dial_pers);
        mensaje.setText(msn);

        ImageView img_cerr_dial = (ImageView)dialogo.findViewById(R.id.img_cerr_dial);
        img_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
