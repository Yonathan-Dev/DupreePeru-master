package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.ItemCarrito;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCatalogoBinding;
import com.dupreincaperu.dupree.mh_holders.CarritoHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class CarritoListAdapter extends RecyclerView.Adapter<CarritoHolder> {
    private String TAG = CarritoListAdapter.class.getName();

    private List<ItemCarrito> list;
    private CarritoHolder.Events events;
    private boolean enable = true;
    private List<String> idEditable;//para saber si hay elementos que modificar

    public CarritoListAdapter(List<ItemCarrito> list, CarritoHolder.Events events){
        this.list = list;
        this.events = events;
        idEditable=new ArrayList<>();
    }

    @NonNull
    @Override
    public CarritoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemCatalogoBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_catalogo, parent, false);
        return new CarritoHolder(binding, events);
    }

    private int numEditable=0;
    @Override
    public void onBindViewHolder(@NonNull CarritoHolder holder, int position) {
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
