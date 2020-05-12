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

public class dialogo_acceso {

    public dialogo_acceso(final Context Contexto, String msn) {

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogo_acceso);

        TextView mensaje = (TextView)dialogo.findViewById(R.id.txt_dial_acce);
        mensaje.setText(msn.toUpperCase());

        Button cerrar = (Button)dialogo.findViewById(R.id.btn_cerr_dial_acce);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                System.exit(0);
            }
        });

        dialogo.show();
    }
}
