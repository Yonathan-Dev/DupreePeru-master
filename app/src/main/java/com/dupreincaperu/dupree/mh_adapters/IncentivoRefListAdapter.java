package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreeinca.lib_api_rest.model.dto.response.IncentivoRef;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemIncentivosReferidoBinding;
import com.dupreincaperu.dupree.mh_holders.IncentivosRefHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class IncentivoRefListAdapter extends RecyclerView.Adapter<IncentivosRefHolder> {

    private List<IncentivoRef> list, listFilter;
    private IncentivosRefHolder.Events events;

    public IncentivoRefListAdapter(List<IncentivoRef> list, List<IncentivoRef> listFilter, IncentivosRefHolder.Events events){
        this.list = list;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(IncentivoRefListAdapter.this);
    }

    @NonNull
    @Override
    public IncentivosRefHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemIncentivosReferidoBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_incentivos_referido, parent, false);
        return new IncentivosRefHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull IncentivosRefHolder holder, int position) {
        if(listFilter.size()>position){
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
        private IncentivoRefListAdapter mAdapter;
        private CustomFilter(IncentivoRefListAdapter mAdapter) {
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
                for (final IncentivoRef mWords : list) {
                    if (String.valueOf(mWords.getCedula()).toLowerCase().contains(filterPattern)) {
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
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
