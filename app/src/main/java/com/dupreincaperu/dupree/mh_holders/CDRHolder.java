package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemCDR;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCdrBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class CDRHolder extends RecyclerView.ViewHolder{
    private ItemCdrBinding binding;
    private Events events;

    public CDRHolder(@NonNull ItemCdrBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemCDR item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();

        binding.tvCamp.setText("".concat(String.valueOf(item.getCampana())));
        binding.tvFecha.setText("".concat(item.getFecha()));
        binding.tvFact.setText("".concat(String.valueOf(item.getFactura())));
        binding.tvProduct.setText("".concat(String.valueOf(item.getProductos())));
        binding.tvDescrip.setText("".concat(item.getDescripcion()));
        binding.tvCantidad.setText("".concat(String.valueOf(item.getCantidad())));
        binding.tvTReclamo.setText("".concat(String.valueOf(item.gettReclamo())));
        binding.tvProcede.setText("".concat(item.getProcede()));
        binding.tvObserv.setText("".concat(String.valueOf(item.getObserv())));

        //holder.cardViewBackGround.setBackgroundResource(R.color.transp_Accent);

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
        void onClickRoot(ItemCDR dataRow, int row);
    }
}
