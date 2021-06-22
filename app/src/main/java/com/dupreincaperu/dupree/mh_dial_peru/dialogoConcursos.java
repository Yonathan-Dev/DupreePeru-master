package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_concurso;
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_resumen;

import org.json.JSONObject;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class dialogoConcursos {

    public  interface resultadoConcurso{
        void ResultadoConcurso(String band);
    }

    private resultadoConcurso interfaz;

    private final dialogoConcursos contexto;

    public dialogoConcursos(final Context Contexto, resultadoConcurso actividad, ArrayList listDescripcion, ArrayList listImagen, ArrayList listPuntos) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogoconcursos);

        final ImageView             img_cerr_dial = dialogo.findViewById(R.id.img_cerr_dial);
        final RecyclerView          rcv_conc_prem = dialogo.findViewById(R.id.rcv_conc_prem);
        final FloatingTextButton    ftb_segu_pant = dialogo.findViewById(R.id.ftb_segu_pant);


        rcv_conc_prem.setLayoutManager(new LinearLayoutManager(Contexto, LinearLayoutManager.VERTICAL,false));

        Adaptador_concurso adapter = new Adaptador_concurso(listDescripcion, listImagen, listPuntos);
        rcv_conc_prem.setAdapter(adapter);

        ftb_segu_pant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                interfaz.ResultadoConcurso("1");
            }
        });
        img_cerr_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

}
