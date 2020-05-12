package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFolleto;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCatalogoPremiosBinding;
import com.dupreincaperu.dupree.mh_holders.CatalogoPremiosHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class CatalogoPremiosListAdapter extends RecyclerView.Adapter<CatalogoPremiosHolder> {
    private List<ItemFolleto> list;
    private String TAG = CatalogoPremiosListAdapter.class.getName();
    private CatalogoPremiosHolder.Events events;

    public CatalogoPremiosListAdapter(List<ItemFolleto> list, CatalogoPremiosHolder.Events events){
        this.list = list;
        this.events = events;
    }

    @NonNull
    @Override
    public CatalogoPremiosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemCatalogoPremiosBinding binding= DataBindingUtil.inflate(inflate, R.layout.item_catalogo_premios, parent, false);
        return new CatalogoPremiosHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogoPremiosHolder holder, int position) {
        if(list.size()>position){
            holder.bindData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return null != list ? list.size() : 0;
    }

}
