package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;

import java.util.List;

//import android.app.DialogFragment;

/**
 * Created by Marwuin on 11-May-17.
 */

public class ListString_Radio extends DialogFragment {
    private final String TAG = "ListString_Radio";
    public static  final String BROACAST_DATA="broacast_data";





    public static ListString_Radio newInstance() {

        Bundle args = new Bundle();

        ListString_Radio fragment = new ListString_Radio();
        fragment.setArguments(args);
        return fragment;
    }

    public ListString_Radio() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private int numItem=-1;
    private String itemsSelected;//true false de itams seleccionados
    private List<String> list;
    private String title;
    private String tagFragment;
    private String objectFragment;
    public void loadData(String tagFragment, String objectFragment, String title, List<String> list, String itemsSelected){
        this.list=list;
        this.title=title;
        this.tagFragment=tagFragment;
        this.objectFragment=objectFragment;
        this.itemsSelected=itemsSelected;
    }

    int posSelected;
    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final CharSequence[] items = new CharSequence[list.size()];
        for(int i=0;i<list.size();i++){
            items[i]=list.get(i);
            if(list.get(i).equals(itemsSelected))
                numItem=i;
        }

        builder.setTitle(title)
                .setSingleChoiceItems(items, numItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getActivity(),
                                "Seleccionaste: " + items[which],
                                Toast.LENGTH_SHORT)
                                .show();

                        posSelected=which;
                        //publishResult(items[which].toString());
                        //dismiss();
                    }
                });

        builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e(TAG, "posiciob: "+String.valueOf(posSelected));
                publishResult(String.valueOf(posSelected));
                dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }


    private void publishResult(String data){
        Log.i(TAG,"publishResult: "+data);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                new Intent(tagFragment)
                        .putExtra(tagFragment, objectFragment)
                        .putExtra(BROACAST_DATA, data));

    }
}
