package com.dupreincaperu.dupree.mh_dialogs;

import android.app.Dialog;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.RecyclerAutocompleteBinding;
import com.dupreincaperu.dupree.mh_adapters.AutocompleteListAdapter;
import com.dupreeinca.lib_api_rest.model.dto.response.BarrioDTO;
import com.dupreincaperu.dupree.mh_holders.AutocompleteHolder;
import com.dupreincaperu.dupree.mh_utilities.Validate;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 6/8/17.
 */

public class MH_Dialogs_Barrio extends DialogFragment implements AutocompleteHolder.Events{
    private final String TAG = MH_Dialogs_Barrio.class.getName();
    private RecyclerAutocompleteBinding binding;
    public static  final String BROACAST_REG_TYPE_BARRIO="reg_type_barrio";
    public static  final String BROACAST_REG_TYPE_BARRIO_2="reg_type_barrio_2";

    public MH_Dialogs_Barrio() {
    }

    @Override
    public void onResume() {
        super.onResume();
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        //window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        //window.setLayout((int) (size.x * 0.85), (int) (size.y * 0.85));
        window.setLayout((int) (size.x * 1.00), (int) (size.y * 1.00));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    public static MH_Dialogs_Barrio newInstance() {
        Bundle args = new Bundle();

        MH_Dialogs_Barrio fragment = new MH_Dialogs_Barrio();
        fragment.setArguments(args);
        return fragment;

    }

    private List<BarrioDTO> list;
    private List<BarrioDTO> listFilter;
    public void loadData(List<BarrioDTO> list, List<BarrioDTO> listFilter, ListenerResponse listenerResponse){
        this.list =list;
        this.listFilter = listFilter;
        this.listenerResponse=listenerResponse;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    AutocompleteListAdapter adapterAutocomplete;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);

        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.recycler_autocomplete, null, false);

        final Drawable d = new ColorDrawable(Color.BLACK);//DUPREE RGB
        d.setAlpha(170);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.getWindow().setContentView(binding.getRoot());
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);

        binding.txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapterAutocomplete.getmFilter().filter(s.toString());
            }
        });
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(),1));
        binding.recycler.setHasFixedSize(true);

        adapterAutocomplete = new AutocompleteListAdapter(list, listFilter, this);
        binding.recycler.setAdapter(adapterAutocomplete);


        binding.btnCancelarBarrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        binding.btnAgregarBarrio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateTermins()){
                    if(listenerResponse != null)
                        listenerResponse.result(new BarrioDTO("0", binding.txtFilter.getText().toString()));

                    dismiss();
                }
            }
        });

        return dialog;
    }

    public Boolean ValidateTermins() {
        Validate valid = new Validate();

        if ( binding.txtFilter.getText().toString().isEmpty() )
        {
            valid.setLoginError(getResources().getString(R.string.campo_requerido), binding.txtFilter);
            return false;
        }

        return  true;
    }


    @Override
    public void onClickRoot(BarrioDTO dataRow, int row) {
        if(listenerResponse != null)
            listenerResponse.result(dataRow);

        dismiss();
    }

    ListenerResponse listenerResponse;
    public interface ListenerResponse {
        void result(BarrioDTO item);
    }

}
