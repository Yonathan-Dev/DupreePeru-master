package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemDetailFacturaDTO;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemDetailFacturaBinding;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class DetailFacturasHolder extends RecyclerView.ViewHolder{
    private ItemDetailFacturaBinding binding;
    private Events events;

    public DetailFacturasHolder(@NonNull ItemDetailFacturaBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemDetailFacturaDTO item) {
        Resources resources = BaseAPP.getContext().getResources();
        binding.tvProducto.setText(resources.getString(R.string.concat_producto, item.getName()));
        binding.tvCantidadPedida.setText(resources.getString(R.string.concat_cantidad_pedida, item.getCantidadPed()));
        binding.tvCantidadDespachada.setText(resources.getString(R.string.concat_cantidad_despachada, item.getCantidadDesp()));

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.tvValor.setText(resources.getString(R.string.concat_valor, formatter.format(Float.parseFloat(item.getValor()))));

//        binding.cardViewBackGround.setCardBackgroundColor(getAdapterPosition()%2!=0 ? BaseAPP.getContext().getResources().getColor(R.color.transp_Accent) : BaseAPP.getContext().getResources().getColor(R.color.transp_azulDupree));


        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemDetailFacturaDTO dataRow, int row);
    }
}
