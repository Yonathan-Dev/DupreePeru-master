package com.dupreincaperu.dupree.mh_adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemCDR;
import com.dupreincaperu.dupree.databinding.ItemCdrBinding;
import com.dupreincaperu.dupree.mh_holders.CDRHolder;

import java.util.List;

/**
 * Created by Marwuin on 8/3/2017.
 */

public class CDRListAdapter extends RecyclerView.Adapter<CDRHolder> {

    private List<ItemCDR> list, listFilter;
    private CDRHolder.Events events;

    public CDRListAdapter(List<ItemCDR> list, List<ItemCDR> listFilter, CDRHolder.Events events){
        this.list = list;
        this.listFilter = listFilter;
        this.events = events;

        //para filtrar
        mFilter = new CustomFilter(CDRListAdapter.this);
    }

    @NonNull
    @Override
    public CDRHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemCdrBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_cdr, parent, false);
        return new CDRHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull CDRHolder holder, int position) {
        if(listFilter.size() > position){
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
        private CDRListAdapter mAdapter;
        private CustomFilter(CDRListAdapter mAdapter) {
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
                for (final ItemCDR mWords : list) {
                    if (String.valueOf(mWords.getProductos()).toLowerCase().contains(filterPattern)) {
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
