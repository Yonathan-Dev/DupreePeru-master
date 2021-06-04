package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
//import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Marwuin on 11-May-17.
 */

public class ListString extends DialogFragment {
    private final String TAG = "ListString";
    public static  final String BROACAST_REG="register";
    public static  final String BROACAST_DATA="broacast_data";
    public static  final String BROACAST_REG_TYPE_DOC="reg_type_doc";
    public static  final String BROACAST_REG_TYPE_VIA="reg_type_via";
    public static  final String BROACAST_REG_TYPE_LETRA1="reg_type_letra1";
    public static  final String BROACAST_REG_TYPE_BIS="reg_type_bis";
    public static  final String BROACAST_REG_TYPE_SUR1="reg_type_sur1";
    public static  final String BROACAST_REG_TYPE_LETRA2="reg_type_letra2";
    public static  final String BROACAST_REG_TYPE_SUR2="reg_type_sur2";
    //public static  final String BROACAST_REG_TYPE_DPTO="reg_type_dpto";
    public static  final String BROACAST_REG_TYPE_CITY="reg_type_city";
    public static  final String BROACAST_REG_TYPE_BARRIO="reg_type_barrio";
    public static  final String BROACAST_REG_TYPE_DIR_SEND="reg_type_dir";

    public static  final String BROACAST_REG_TYPE_VIA_2="reg_type_via_2";
    public static  final String BROACAST_REG_TYPE_LETRA1_2="reg_type_letra1_2";
    public static  final String BROACAST_REG_TYPE_BIS_2="reg_type_bis_2";
    public static  final String BROACAST_REG_TYPE_SUR_21="reg_type_sur_21";
    public static  final String BROACAST_REG_TYPE_LETRA2_2="reg_type_letra2_2";
    public static  final String BROACAST_REG_TYPE_SUR_22="reg_type_sur_22";
    //public static  final String BROACAST_REG_TYPE_DPTO_2="reg_type_dpto_2";
    public static  final String BROACAST_REG_TYPE_CITY_2="reg_type_city_2";
    public static  final String BROACAST_REG_TYPE_BARRIO_2="reg_type_barrio_2";


    public static  final String BROACAST_INSCRIP_TYPE_DOC="inscrip_type_doc";
    public static  final String BROACAST_INSCRIP_TYPE_DOC_2="inscrip_type_doc_2";
    public static  final String BROACAST_INSCRIP_TYPE_PARENTESCO="inscrip_type_parentesco";
    public static  final String BROACAST_INSCRIP_TYPE_PARENTESCO_2="inscrip_type_parentesco_2";


    public static  final String BROACAST_PANEL_CAMPANA="panel_campana";

    public static ListString newInstance() {
        
        Bundle args = new Bundle();
        
        ListString fragment = new ListString();
        fragment.setArguments(args);
        return fragment;
    }

    public ListString() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private int numItem=-1;
    private List<String> list;
    private String title;
    private String tagFragment;
    private String objectFragment;
    private String itemSelected;
    public void loadData(String tagFragment, String objectFragment, String title, List<String> list, String itemSelected){
        this.list=list;
        this.title=title;
        this.tagFragment=tagFragment;
        this.objectFragment=objectFragment;
        this.itemSelected=itemSelected;
    }

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                        Toast.makeText(
                                getActivity(),
                                "Seleccionaste: " + items[which],
                                Toast.LENGTH_SHORT)
                                .show();
                        publishResult(items[which].toString());
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
