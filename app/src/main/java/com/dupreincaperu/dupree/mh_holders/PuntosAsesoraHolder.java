package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.PtosByCamp;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemPuntosAsesoraBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class PuntosAsesoraHolder extends RecyclerView.ViewHolder{
    private ItemPuntosAsesoraBinding binding;
    private Events events;

    public PuntosAsesoraHolder(@NonNull ItemPuntosAsesoraBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final PtosByCamp item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();
        binding.tvCamp.setText("".concat(String.valueOf(item.getCampana())));
        binding.tvPedido.setText("".concat(String.valueOf(item.getPuntos_Pedido())));
        binding.tvReferidos.setText("".concat(item.getPuntos_Referido()));
        binding.tvPtsReferido.setText("".concat(String.valueOf(item.getTotal_Referidos())));//
        binding.tvTotal.setText("".concat(String.valueOf(item.getTotal_Puntos())));//
        binding.tvDevolucion.setText("".concat(item.getPerdidos_Devolucion()));
        binding.tvCartera.setText("".concat(String.valueOf(item.getPerdidos_Cartera())));
        binding.tvInactividad.setText("".concat(String.valueOf(item.getPerdidos_Inactividad())));
        binding.tvPerdidos.setText("".concat(item.getPuntos_Pedido()));
        binding.tvAdicionales.setText("".concat(String.valueOf(item.getPuntos_Adicionales())));

        binding.tvEfectivos.setText("".concat(String.valueOf(item.getPuntos_Efectivos())));
        binding.tvRedimidos.setText("".concat(item.getPuntos_Redimidos()));
        binding.tvPago.setText("".concat(String.valueOf(item.getEstado_Pago())));

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
        void onClickRoot(PtosByCamp dataRow, int row);
    }
}
