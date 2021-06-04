package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPanelGte;
import com.dupreincaperu.dupree.databinding.ItemCircleprogressBinding;
import com.dupreincaperu.dupree.mh_holders.PanelGteHolder;

import java.util.List;

/**
 * Created by Marwuin on 8/3/2017.
 */

public class PanelGteListAdapter extends RecyclerView.Adapter<PanelGteHolder> {
    private List<ItemPanelGte> list;
    private PanelGteHolder.Events events;

    public PanelGteListAdapter(List<ItemPanelGte> list,PanelGteHolder.Events events){
        this.list = list;
        this.events = events;
    }

    @NonNull
    @Override
    public PanelGteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemCircleprogressBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_circleprogress, parent, false);

        //listPanel = events.onClickRoot(list,0);
        //return new PanelGteHolder(binding, events,events.onClickRoot(listPanel,0));
        return new PanelGteHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull PanelGteHolder holder, int position) {
        if(position > -1 && list.size() > position){
            holder.bindData(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }
}
