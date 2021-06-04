package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_required_api.ListBoolean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import android.app.DialogFragment;

/**
 * Created by Marwuin on 11-May-17.
 */

public class ListString_Check extends DialogFragment {
    private final String TAG = ListString_Check.class.getName();

    public static ListString_Check newInstance() {

        Bundle args = new Bundle();

        ListString_Check fragment = new ListString_Check();
        fragment.setArguments(args);
        return fragment;
    }

    public ListString_Check() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private String[] itemsSelected;//true false de itams seleccionados
    private List<String> list;
    private String title;
    public void loadData(String title, List<String> list, String[] itemsSelected, ListenerResult listener){
        this.list=list;
        this.title=title;
        this.itemsSelected=itemsSelected;
        this.listener=listener;
    }

    boolean[] posSelected;
    ListBoolean listBoolean;

    //List<Boolean> list;

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final CharSequence[] items = new CharSequence[list.size()];

        posSelected = new boolean[list.size()];

        List<Boolean> booleanList = new ArrayList<Boolean>(Arrays.asList(new Boolean[list.size()]));
        Collections.fill(booleanList, Boolean.FALSE);
        listBoolean = new ListBoolean(booleanList);


        for(int i=0;i<list.size();i++){
            items[i]=list.get(i);
            posSelected[i]=false;

            if(itemsSelected!=null) {//elementos preselecionados
                for (int j = 0; j < itemsSelected.length; j++) {
                    if (list.get(i).equals(itemsSelected[j])) {
                        posSelected[i] = true;
                        //posSelected_serializable[i] = true;
                        listBoolean.getBooleanList().set(i, true);
                        break;
                    }
                }
            }
        }

        builder.setTitle(title)
                .setMultiChoiceItems(items, posSelected, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        Toast.makeText(
                                getActivity(),
                                "Seleccionaste: " + items[i],
                                Toast.LENGTH_SHORT)
                                .show();
                        listBoolean.getBooleanList().set(i, b);
                    }
                });

        builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(listener != null)
                    listener.result(listBoolean);

                dismiss();
            }
        });

        return builder.create();
    }


    private ListenerResult listener;
    public interface ListenerResult {
        void result(ListBoolean listBoolean);
    }
}
