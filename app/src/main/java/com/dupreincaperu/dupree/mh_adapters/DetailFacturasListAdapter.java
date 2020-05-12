package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemDetailFacturaDTO;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemDetailFacturaBinding;
import com.dupreincaperu.dupree.mh_holders.DetailFacturasHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class DetailFacturasListAdapter extends RecyclerView.Adapter<DetailFacturasHolder>{
    private List<ItemDetailFacturaDTO> list;
    private DetailFacturasHolder.Events events;

    public DetailFacturasListAdapter(List<ItemDetailFacturaDTO> list, DetailFacturasHolder.Events events) {
        this.list = list;
        this.events = events;
    }

    @NonNull
    @Override
    public DetailFacturasHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemDetailFacturaBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_detail_factura, viewGroup, false);
        return new DetailFacturasHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailFacturasHolder holder, int position) {
        if(list.size() > position) {
            holder.bindData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}
