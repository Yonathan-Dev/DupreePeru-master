package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Oferta;
import com.dupreincaperu.dupree.databinding.ItemOffersBinding;
import com.dupreincaperu.dupree.mh_holders.OfertasHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class OffersListAdapter extends RecyclerView.Adapter<OfertasHolder> {
    private String TAG = OffersListAdapter.class.getName();
    private boolean enable=true;
    private List<String> idEditable;//para saber si hay elementos que modificar
    private List<Oferta> list;
    private OfertasHolder.Events events;

    public OffersListAdapter(List<Oferta> list, OfertasHolder.Events events){
        this.list = list;
        this.events = events;
        idEditable=new ArrayList<>();
    }

    @NonNull
    @Override
    public OfertasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemOffersBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_offers, parent, false);
        return new OfertasHolder(binding, events);
    }

    Boolean blockEventsAutomatic=false;
    private int numEditable=0;
    @Override
    public void onBindViewHolder(@NonNull OfertasHolder holder, int position) {
        if(list.size() > position) {
            holder.bindData(list.get(position), isEnable(), numEditable, idEditable);
        }
        numEditable = idEditable.size();
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void clearEditable(){
        numEditable=0;
        idEditable.clear();
    }
}
