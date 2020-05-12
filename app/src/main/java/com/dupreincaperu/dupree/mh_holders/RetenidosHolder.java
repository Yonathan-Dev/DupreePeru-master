package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemRetenidosBinding;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemRetenido;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class RetenidosHolder extends RecyclerView.ViewHolder{
    private ItemRetenidosBinding binding;
    private Events events;

    public RetenidosHolder(@NonNull ItemRetenidosBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemRetenido item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();
        
        binding.tvNameRet.setText(item.getName());
        binding.tvCedulaRet.setText(resources.getString(R.string.c_i_two_point, item.getCedula()));

        binding.tvTelefonoRet.setText(resources.getString(R.string.concat_telefo_two_point, item.getCelular()));
        binding.tvAreaRef.setText(resources.getString(R.string.concat_area_two_point, item.getArea()));
        binding.tvZonaRef.setText(resources.getString(R.string.concat_zona_two_point, item.getZona()));
        binding.tvCodeRet.setText(item.getCode());
        binding.tvNumPedidosRet.setText(item.getNumPedido());
        binding.tvCarteraRet.setText(item.getCartera());

        binding.tvRCupoRet.setText(item.getR_cupo());
        binding.tvBloqueoRet.setText(item.getBloqueo());
        binding.tvNetoRet.setText(item.getNeto());
        binding.tvSaldoRet.setText(item.getSaldo());
        binding.tvCupoRet.setText(item.getCupo());
        binding.tvMinPunlicoRet.setText(item.getMinimoPublico());

        binding.tvMontoMinRet.setText(item.getMontoMinimo());
        binding.tvTotalPublicoRet.setText(item.getTotalPublico());

        binding.cardViewBackGround.setCardBackgroundColor(position%2!=0 ? resources.getColor(R.color.transp_Accent) : resources.getColor(R.color.transp_azulDupree));


        binding.ctnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickCall(item, getAdapterPosition());
            }
        });
        binding.imgBCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickCall(item, getAdapterPosition());
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemRetenido dataRow, int row);
        void onClickCall(ItemRetenido dataRow, int row);
    }
}
