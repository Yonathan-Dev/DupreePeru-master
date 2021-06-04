package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

/**
 * Fragmento con diálogo básico
 */
public class SimpleDialog3Button extends DialogFragment {
    private final String TAG = SimpleDialog3Button.class.getName();

    private String titulo="";
    private String message="";

    public SimpleDialog3Button() {
    }

    private String titleAprobar="";
    private String titleRechazar="";
    public void loadData(String titulo, String message, String titleAprobar, String titleRechazar) {
        this.titulo = titulo;
        this.message = message;
        this.titleAprobar = titleAprobar;
        this.titleRechazar = titleRechazar;
    }

    public void setListener(ListenerResult listener) {
        this.listener = listener;
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

        builder.setTitle(titulo)
                .setMessage(message)
                .setPositiveButton(titleAprobar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                                if(listener != null)
                                    listener.result(true);

                                dismiss();
                            }
                        })
                .setNegativeButton(titleRechazar,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                                if(listener != null)
                                    listener.result(false);

                                dismiss();
                            }
                        })
                .setNeutralButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });

        return builder.create();
    }

    private ListenerResult listener;
    public interface ListenerResult {
        void result(boolean status);
    }
}