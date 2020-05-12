package com.dupreincaperu.dupree.mh_utilities.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;

import java.util.List;

/**
 * Created by WAMS-10 on 29/05/2017.
 */

@SuppressWarnings("ALL")
public class DialogListOption {

    private static final String TAG = "DialogListOption";
    private final Activity activity;
    private String[] btnMsg;
    private List<ModelList> lists;
    private DialogSelectItem dialogSelectItem;
    private DialogSelectModel dialogSelectModel;
    private boolean cancelable;
    private ListView listView;
    private TextView title;
    private ArrayAdapter adapter;


    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public DialogListOption(Activity activity) {
        this.activity = activity;
    }

    public void setDialogSelectItem(DialogSelectItem dialogSelectItem) {
        this.dialogSelectItem = dialogSelectItem;
    }

    public void setDialogSelectModel(DialogSelectModel dialogSelectModel) {
        this.dialogSelectModel = dialogSelectModel;
    }

    public void showDialog(String title, List<ModelList> lists) {
        this.lists = lists;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(activity);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cancelable = true;
                //dialog.setTitle("Select:");
                dialog.setCancelable(cancelable);
                dialog.setContentView(R.layout.dialog_list_menu);
                instanceObjectsObject(dialog, title);
                dialog.show();
            }
        });

    }

//    public void showDialog(int title, List<ModelList> lists) {
//        showDialog(GaverApp.getContext().getResources().getString(title), lists);
//    }

    public void showDialog(List<ModelList> lists) {
        this.lists = lists;
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Dialog dialog = new Dialog(activity);
                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cancelable = true;
                //dialog.setTitle("Select:");
                dialog.setCancelable(cancelable);
                dialog.setContentView(R.layout.dialog_list_menu);
                instanceObjectsObject(dialog, "");
                dialog.show();
            }
        });

    }

    private void updateView(int index){
        View v = listView.getChildAt(index - listView.getFirstVisiblePosition());

        if(v == null)
            return;

        TextView someText = (TextView) v.findViewById(R.id.text_dialog);
        someText.setText("Hi! I updated you manually!");
    }

    private void instanceObjectsObject(final Dialog dialog, String message) {
        adapter = new ArrayAdapter<>(activity, R.layout.dialog_list_item_menu, lists);
        title = (TextView) dialog.findViewById(R.id.title);
        title.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
        title.setText(message);
        listView = (ListView) dialog.findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialogSelectModel != null)
                    dialogSelectModel.onModelSelected(lists.get(position));
                dialog.dismiss();
            }
        });
    }

    public interface DialogSelectItem {
        void onItemSelected(int index, View view, ListView listView);
    }

    public interface DialogSelectModel {
        void onModelSelected(ModelList item);
    }

}