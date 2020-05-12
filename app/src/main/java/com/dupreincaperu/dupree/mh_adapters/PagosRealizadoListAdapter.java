package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPagosrealizados;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemPagosRealizadosBinding;
import com.dupreincaperu.dupree.mh_holders.PagosRealizHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class PagosRealizadoListAdapter extends RecyclerView.Adapter<PagosRealizHolder> {

    private List<ItemPagosrealizados> list, listFilter;
    private PagosRealizHolder.Events events;

    public PagosRealizadoListAdapter(List<ItemPagosrealizados> list, List<ItemPagosrealizados> listFilter, PagosRealizHolder.Events events){
        this.list = list;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(PagosRealizadoListAdapter.this);

    }

    @NonNull
    @Override
    public PagosRealizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemPagosRealizadosBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_pagos_realizados, parent, false);
        return new PagosRealizHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosRealizHolder holder, int position) {
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
        private PagosRealizadoListAdapter mAdapter;
        private CustomFilter(PagosRealizadoListAdapter mAdapter) {
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
                for (final ItemPagosrealizados mWords : list) {
                    if (String.valueOf(mWords.getFecha()).toLowerCase().contains(filterPattern)) {
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
