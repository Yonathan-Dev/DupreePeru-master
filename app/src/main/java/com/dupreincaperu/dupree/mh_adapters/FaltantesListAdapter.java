package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.Faltante;
import com.dupreincaperu.dupree.databinding.ItemFaltantesBinding;
import com.dupreincaperu.dupree.mh_holders.FaltantesHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class FaltantesListAdapter extends RecyclerView.Adapter<FaltantesHolder> {
    private List<Faltante> list, listFilter;
    private FaltantesHolder.Events events;

    public FaltantesListAdapter(List<Faltante> listFaltante, List<Faltante> listFilter, FaltantesHolder.Events events){

        this.list = listFaltante;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(FaltantesListAdapter.this);
    }

    @Override
    public FaltantesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFaltantesBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_faltantes, parent, false);
        return new FaltantesHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull FaltantesHolder holder, int position) {
        if(listFilter.size() > position) {
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
        private FaltantesListAdapter mAdapter;
        private CustomFilter(FaltantesListAdapter mAdapter) {
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
                for (final Faltante mWords : list) {
                    if (String.valueOf(mWords.getId()).toLowerCase().contains(filterPattern)) {
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
