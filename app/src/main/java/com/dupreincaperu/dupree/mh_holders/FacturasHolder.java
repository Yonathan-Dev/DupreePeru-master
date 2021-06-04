package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFacturaDTO;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemFacturaBinding;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class FacturasHolder extends RecyclerView.ViewHolder{
    private ItemFacturaBinding binding;
    private Events events;

    public FacturasHolder(@NonNull ItemFacturaBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemFacturaDTO item) {
        Resources resources = BaseAPP.getContext().getResources();
        binding.tvIdFactura.setText(resources.getString(R.string.concat_id_factura, item.getIdFactura()));

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.tvPrecioTotal.setText(resources.getString(R.string.concat_precio_total, formatter.format(Float.parseFloat(item.getPrecioTotal()))));
        binding.tvCampana.setText(resources.getString(R.string.concat_campana, item.getCampana()));

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemFacturaDTO dataRow, int row);
    }
}
