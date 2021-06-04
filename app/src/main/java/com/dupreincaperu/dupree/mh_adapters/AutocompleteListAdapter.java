package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreincaperu.dupree.R;
import com.dupreeinca.lib_api_rest.model.dto.response.BarrioDTO;
import com.dupreincaperu.dupree.databinding.ItemAddBarrioBinding;
import com.dupreincaperu.dupree.mh_holders.AutocompleteHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class AutocompleteListAdapter extends RecyclerView.Adapter<AutocompleteHolder> {
    private List<BarrioDTO> list;
    private List<BarrioDTO> listFilter;
    private AutocompleteHolder.Events events;

    private CustomFilter mFilter;
    public AutocompleteListAdapter(List<BarrioDTO> list, List<BarrioDTO> listFilter, AutocompleteHolder.Events events){
        this.listFilter = listFilter;
        this.list = list;
        this.events=events;
        mFilter = new CustomFilter(AutocompleteListAdapter.this);
    }

    @NonNull
    @Override
    public AutocompleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemAddBarrioBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_add_barrio, parent, false);
        return new AutocompleteHolder(binding, events);
    }

    public CustomFilter getmFilter() {
        return mFilter;
    }

    @Override
    public void onBindViewHolder(@NonNull AutocompleteHolder holder, int position) {
        if(listFilter.size()>position) {
            holder.bindData(listFilter.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (null != listFilter ? listFilter.size() : 0);
    }

    public class CustomFilter extends Filter {
        private AutocompleteListAdapter mAdapter;
        private CustomFilter(AutocompleteListAdapter mAdapter) {
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
                for (final BarrioDTO mWords : list) {
                    if (mWords.getName_barrio().toLowerCase().contains(filterPattern)) {
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
