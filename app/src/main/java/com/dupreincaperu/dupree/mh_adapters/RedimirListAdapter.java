package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemRedimirBinding;
import com.dupreincaperu.dupree.mh_holders.RedimirHolder;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPremios;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class RedimirListAdapter extends RecyclerView.Adapter<RedimirHolder> {
    private List<ItemPremios> list, listFilter;
    private RedimirHolder.Events events;

    public RedimirListAdapter(List<ItemPremios> list, List<ItemPremios> listFilter, RedimirHolder.Events events){
        this.list = list;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(RedimirListAdapter.this);
    }

    @NonNull
    @Override
    public RedimirHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemRedimirBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_redimir, parent, false);
        return new RedimirHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull RedimirHolder holder, int position) {
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
        private RedimirListAdapter mAdapter;
        private CustomFilter(RedimirListAdapter mAdapter) {
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
                for (final ItemPremios mWords : list) {
                    if (mWords.getCodigo().toLowerCase().contains(filterPattern)) {
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
