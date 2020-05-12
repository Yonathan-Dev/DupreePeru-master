package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPagosrealizados;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemPagosRealizadosBinding;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class PagosRealizHolder extends RecyclerView.ViewHolder{
    private ItemPagosRealizadosBinding binding;
    private Events events;

    public PagosRealizHolder(@NonNull ItemPagosRealizadosBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemPagosrealizados item) {
        int position = getAdapterPosition();
        Resources resources = BaseAPP.getContext().getResources();
        
        binding.tvFecha.setText(String.valueOf(item.getFecha()));
        binding.tvBanco.setText(item.getBanco());

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.tvValor.setText(resources.getString(R.string.concat_dolar, formatter.format(item.getValor())));

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
        void onClickRoot(ItemPagosrealizados dataRow, int row);
    }
}
