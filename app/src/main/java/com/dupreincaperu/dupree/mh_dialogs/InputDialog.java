package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.mh_utilities.Validate;

/**
 * Created by cloudemotion on 6/8/17.
 */

public class InputDialog extends DialogFragment {
    String TAG = InputDialog.class.getName();

    public InputDialog() {
    }

    public static InputDialog newInstance() {
        Bundle args = new Bundle();

        InputDialog fragment = new InputDialog();
        fragment.setArguments(args);
        return fragment;
    }

    private String title;
    private String placeHolder;
    public void loadData(String title, String placeHolder, ResponseListener listener){
        this.title=title;
        this.placeHolder=placeHolder;
        this.listener=listener;
    }

    int style = DialogFragment.STYLE_NO_TITLE;
    int theme = R.style.MyDialogTransparent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(style, theme);
    }

    private String m_Text = "";
    EditText input;
    EditText code_txtCode1;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle("Title");

        // Set up the input
        input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setTextSize(22);

        input.setHint(placeHolder);
        builder.setView(input);

        // Set up the buttons
        builder.setTitle(title);
        builder.setPositiveButton(R.string.buscar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                if(ValidateImput()){
                    if(listener != null){
                        listener.result(m_Text);
                    }
                    dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel();
                dismiss();
            }
        });

        return builder.show();
    }

    public Boolean ValidateImput() {
        Validate valid = new Validate();
        if (TextUtils.isEmpty(m_Text) )
        {
            msgToast(getResources().getString(R.string.ingresar_cedula_valida));
            valid.setLoginError(getResources().getString(R.string.campo_requerido), input);
            return false;
        }

        return  true;
    }

    private void msgToast(String msg){
        Log.e("onError", msg);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private ResponseListener listener;
    public interface ResponseListener {
        void result(String inputText);
    }

}
