package com.dupreincaperu.dupree.mh_dial_peru;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Response;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_adap_peru.Adaptador_resumen;

import org.json.JSONObject;

import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class dialogoResumen {

    public  interface resultadoResumen{
        void ResultadoResumen(String band);
    }

    private resultadoResumen interfaz;

    private final dialogoResumen contexto;

    public dialogoResumen(final Context Contexto, resultadoResumen actividad, ArrayList numeProductos,  ArrayList codigoProductos, ArrayList tipoProductos, ArrayList nombreProductos, ArrayList cantidadProductos, ArrayList valorProductos, String tota_pedi) {

        interfaz = actividad;
        contexto = this;

        final Dialog dialogo = new Dialog(Contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialogoresumen);

        final FloatingTextButton ftb_agre_pedi = dialogo.findViewById(R.id.ftb_agre_pedi);
        final FloatingTextButton ftb_grab_pedi = dialogo.findViewById(R.id.ftb_grab_pedi);
        final RecyclerView       rcv_resu_pedi = dialogo.findViewById(R.id.rcv_resu_pedi);
        final TextView           txt_tota_pedi = dialogo.findViewById(R.id.txt_tota_pedi);


        rcv_resu_pedi.setLayoutManager(new LinearLayoutManager(Contexto, LinearLayoutManager.VERTICAL,false));

        Adaptador_resumen adapter = new Adaptador_resumen(numeProductos, codigoProductos, tipoProductos, nombreProductos, cantidadProductos, valorProductos);
        rcv_resu_pedi.setAdapter(adapter);

        txt_tota_pedi.setText("TOTAL S/. "+tota_pedi);

        ftb_grab_pedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Contexto);
                dialogo1.setTitle("Atención");
                dialogo1.setIcon(R.drawable.ic_atencion);
                dialogo1.setMessage("¿ Esta seguro que desea grabar el pedido ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Aceptar",   new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo.dismiss();
                        interfaz.ResultadoResumen("1");
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();
            }
        });
        ftb_agre_pedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Contexto);
                dialogo1.setTitle("Atención");
                dialogo1.setIcon(R.drawable.ic_atencion);
                dialogo1.setMessage("¿ Esta seguro que desea agregar pedidos ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Aceptar",   new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo.dismiss();
                        interfaz.ResultadoResumen("0");
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        dialogo1.dismiss();
                    }
                });
                dialogo1.show();
            }
        });

        dialogo.show();
    }

}
