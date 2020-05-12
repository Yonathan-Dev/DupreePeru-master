package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.dupreeinca.lib_api_rest.model.dto.response.DepartamentoDTO;

import java.util.ArrayList;
import java.util.List;

//import android.app.DialogFragment;

/**
 * Created by Marwuin on 11-May-17.
 */

public class ListDpto extends DialogFragment {
    private final String TAG = ListDpto.class.getName();
    public static  final String BROACAST_REG="register";
    public static  final String BROACAST_DATA="broacast_data";
    public static  final String BROACAST_REG_TYPE_DPTO="reg_type_dpto";
    public static  final String BROACAST_REG_TYPE_DPTO_2="reg_type_dpto_2";

    public static ListDpto newInstance() {

        Bundle args = new Bundle();

        ListDpto fragment = new ListDpto();
        fragment.setArguments(args);
        return fragment;
    }

    public ListDpto() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private int numItem=-1;
    private List<DepartamentoDTO> departamentoList;
    private String title;
    private String itemSelected;
    public void loadData(String title, List<DepartamentoDTO> departamentoList, String itemSelected, ListenerResponse listenerResponse){
        this.departamentoList=departamentoList;
        this.title=title;
        this.itemSelected=itemSelected;
        this.listenerResponse = listenerResponse;
    }

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        List<String> list = new ArrayList<>();
        for(int i=0;i<departamentoList.size();i++){
            list.add(departamentoList.get(i).getName_dpto());
        }

        final CharSequence[] items = new CharSequence[list.size()];
        for(int i=0;i<list.size();i++){
            items[i]=list.get(i);
            if(list.get(i).equals(itemSelected))
                numItem=i;
        }

        builder.setTitle(title)
                .setSingleChoiceItems(items, numItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Toast.makeText(
                                getActivity(),
                                "Seleccionaste: " + items[which],
                                Toast.LENGTH_SHORT)
                                .show(); */

                        if(listenerResponse != null)
                            listenerResponse.result(departamentoList.get(which));
                        dismiss();
                    }
                });

        return builder.create();
    }

    private ListenerResponse listenerResponse;
    public interface ListenerResponse {
        void result(DepartamentoDTO item);
    }
}
