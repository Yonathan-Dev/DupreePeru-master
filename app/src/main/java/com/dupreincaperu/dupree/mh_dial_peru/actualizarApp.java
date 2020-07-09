package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.dupreincaperu.dupree.R;

public class actualizarApp {

    public actualizarApp(final Context Contexto, String urlplaystore) {

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.actualizarapp);

        Button btn_actualizar;

        btn_actualizar  = (Button)dialogo.findViewById(R.id.btn_actualizar);

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent url = new Intent(Intent.ACTION_VIEW);
                url.setData(Uri.parse(urlplaystore));
                Contexto.startActivity(url);
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }
}
