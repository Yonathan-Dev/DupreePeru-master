package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.dupreeinca.lib_api_rest.util.models.ModelList;

import java.util.List;

//import android.app.DialogFragment;

/**
 * Created by Marwuin on 11-May-17.
 */

public class SingleListDialog extends DialogFragment {
    private final String TAG = SingleListDialog.class.getName();

    public static SingleListDialog newInstance() {

        Bundle args = new Bundle();

        SingleListDialog fragment = new SingleListDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public SingleListDialog() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private int numItem=-1;
    private List<ModelList> data;
    private String title;
    private String itemSelected;
    public void loadData(String title, List<ModelList> data, String itemSelected, ListenerResponse response){
        this.title=title;
        this.data=data;
        this.itemSelected=itemSelected;
        this.response=response;
    }

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final CharSequence[] items = new CharSequence[data.size()];
        for(int i=0;i<data.size();i++){
            items[i]=data.get(i).getName();
            if(data.get(i).getName().equals(itemSelected))
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
                                .show();*/
                        if(response != null){
                            response.result(data.get(which));
                        }

                        dismiss();
                    }
                });

        return builder.create();
    }



    private ListenerResponse response;
    public interface ListenerResponse {
        void result(ModelList item);
    }
}
