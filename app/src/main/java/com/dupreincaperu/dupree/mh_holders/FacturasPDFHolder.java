package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFactura;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemFacturaPdfBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class FacturasPDFHolder extends RecyclerView.ViewHolder{
    private ItemFacturaPdfBinding binding;
    private Events events;

    public FacturasPDFHolder(@NonNull ItemFacturaPdfBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemFactura item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();

        binding.tvCamp.setText(String.valueOf(item.getCampana()));
        binding.tvFecha.setText(item.getFecha());
        binding.tvFact.setText(String.valueOf(item.getFactura()));
        binding.cardViedfactura.setCardBackgroundColor(position%2!=0 ? resources.getColor(R.color.transp_Accent) : resources.getColor(R.color.transp_azulDupree));

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemFactura dataRow, int row);
    }
}
