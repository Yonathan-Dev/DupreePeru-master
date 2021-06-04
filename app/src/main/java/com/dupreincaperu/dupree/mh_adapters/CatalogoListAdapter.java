package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCatalogoBinding;
import com.dupreincaperu.dupree.mh_holders.CatalogoHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwuin on 8/3/2017.
 */

public class CatalogoListAdapter extends RecyclerView.Adapter<CatalogoHolder> {
    private String TAG = CatalogoListAdapter.class.getName();

    private List<Catalogo> list;
    private CatalogoHolder.Events events;
    private boolean enable = true;
    private List<String> idEditable;//para saber si hay elementos que modificar

    public CatalogoListAdapter(List<Catalogo> list, CatalogoHolder.Events events){
        this.list = list;
        this.events = events;
        idEditable=new ArrayList<>();
    }

    @NonNull
    @Override
    public CatalogoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemCatalogoBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_catalogo, parent, false);
        return new CatalogoHolder(binding, events);
    }

    private int numEditable=0;
    @Override
    public void onBindViewHolder(@NonNull CatalogoHolder holder, int position) {
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
