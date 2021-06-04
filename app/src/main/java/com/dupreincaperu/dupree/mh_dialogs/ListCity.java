package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import com.dupreeinca.lib_api_rest.model.dto.response.CiudadDTO;

import java.util.ArrayList;
import java.util.List;

//import android.app.DialogFragment;

/**
 * Created by Marwuin on 11-May-17.
 */

public class ListCity extends DialogFragment {
    private final String TAG = ListCity.class.getName();
    public static  final String BROACAST_REG_TYPE_CITY="reg_type_city";
    public static  final String BROACAST_REG_TYPE_CITY_2="reg_type_city_2";

    public static ListCity newInstance() {
        Bundle args = new Bundle();

        ListCity fragment = new ListCity();
        fragment.setArguments(args);
        return fragment;
    }

    public ListCity() {
        //new HttpGaver(getActivity()).obtainCountries();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createRadioListDialog();
    }

    private int numItem=-1;
    private List<CiudadDTO> ciudadList;
    private String title;
    private String itemSelected;
    public void loadData(String title, List<CiudadDTO> ciudadList, String itemSelected, ListenerResponse listenerResponse){
        this.ciudadList=ciudadList;
        this.title=title;
        this.itemSelected=itemSelected;
        this.listenerResponse=listenerResponse;
    }

    public AlertDialog createRadioListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Type listType = new TypeToken<ArrayList<Departamento>>(){}.getType();
        //List<Departamento> listDpto = new Gson().fromJson(mPreferences.getDpto(getActivity()), listType);

        List<String> list = new ArrayList<>();
        for(int i=0;i<ciudadList.size();i++){
            list.add(ciudadList.get(i).getName_ciudad());
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
                            listenerResponse.result(ciudadList.get(which));

                        dismiss();
                    }
                });

        return builder.create();
    }

    ListenerResponse listenerResponse;
    public interface ListenerResponse {
        void result(CiudadDTO item);
    }
}
