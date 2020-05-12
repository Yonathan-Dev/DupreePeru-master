package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemRetenidosBinding;
import com.dupreincaperu.dupree.mh_holders.RetenidosHolder;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemRetenido;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class RetenidosListAdapter extends RecyclerView.Adapter<RetenidosHolder> {
    private final String STATUS_AUTORIZADO = "AUTORIZADO";
    private final String STATUS_RECHAZADO = "RECHAZADO";
    private final String STATUS_PENDIENTE = "PENDIENTE";
    private List<ItemRetenido> list, listFilter;

    private RetenidosHolder.Events events;

    public RetenidosListAdapter(List<ItemRetenido> list, List<ItemRetenido> listFilter, RetenidosHolder.Events events){

        this.list = list;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(RetenidosListAdapter.this);

    }

    @NonNull
    @Override
    public RetenidosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemRetenidosBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_retenidos, parent, false);
        return new RetenidosHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull RetenidosHolder holder, int position) {
        if(listFilter.size()>position) {
            holder.bindData(listFilter.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (null != listFilter ? listFilter.size() : 0);
    }

    /**
     * FILTER
     */
    private CustomFilter mFilter;

    public CustomFilter getmFilter() {
        return mFilter;
    }

    public class CustomFilter extends Filter {
        private RetenidosListAdapter mAdapter;
        private CustomFilter(RetenidosListAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            listFilter.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                listFilter.addAll(list);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final ItemRetenido mWords : list) {
                    if (mWords.getCedula().toLowerCase().contains(filterPattern)) {
                        listFilter.add(mWords);
                    }
                }
            }
            System.out.println("Count Number " + listFilter.size());
            results.values = listFilter;
            results.count = listFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //System.out.println("Count Number 2 " + ((List<RespondeUsers.Users>) results.values).size());
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
