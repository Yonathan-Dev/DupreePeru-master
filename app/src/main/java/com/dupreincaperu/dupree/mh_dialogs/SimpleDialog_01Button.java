package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Fragmento con diálogo básico
 */
public class SimpleDialog_01Button extends DialogFragment {
    private final String TAG = "SimpleDialog_01Button";

    private String titulo="";
    private String message="";
    private int iconId=0;

    public SimpleDialog_01Button() {
    }

    public void loadData(int iconId, String titulo, String message) {
        this.iconId = iconId;
        this.titulo = titulo;
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(iconId);
        builder.setTitle(titulo)
                .setMessage(message)
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });

        return builder.create();
    }


}