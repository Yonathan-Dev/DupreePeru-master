package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemReportesBinding;
import com.dupreincaperu.dupree.mh_holders.ReportesHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class ReportesListAdapter extends RecyclerView.Adapter<ReportesHolder>{
    private List<ModelList> list;
    private ReportesHolder.Events events;

    public ReportesListAdapter(List<ModelList> list, ReportesHolder.Events events) {
        this.list = list;
        this.events = events;
    }

    @NonNull
    @Override
    public ReportesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemReportesBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_reportes, viewGroup, false);
        return new ReportesHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportesHolder holder, int i) {
        if(list.size() > i) {
            holder.bindData(list.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}
