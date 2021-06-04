package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.IncentivoRef;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemIncentivosReferidoBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class IncentivosRefHolder extends RecyclerView.ViewHolder{
    private ItemIncentivosReferidoBinding binding;
    private Events events;

    public IncentivosRefHolder(@NonNull ItemIncentivosReferidoBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final IncentivoRef item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();

        binding.tvName.setText("".concat(String.valueOf(item.getNombre())));
        binding.tvCamp.setText("".concat(item.getCampana()));
        binding.tvCedula.setText("".concat(String.valueOf(item.getCedula())));
        binding.tvSakdo.setText("".concat(String.valueOf(item.getSaldo())));
        binding.tvObserv.setText("".concat(item.getObservaciones()));

        binding.cardViewBackGround.setCardBackgroundColor(position%2!=0 ? resources.getColor(R.color.transp_Accent) : resources.getColor(R.color.transp_azulDupree));


        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(IncentivoRef dataRow, int row);
    }
}
