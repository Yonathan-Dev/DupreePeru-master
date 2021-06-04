package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPQR;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemSeguimientoPqrBinding;
import com.dupreincaperu.dupree.mh_holders.PQRHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class SeguimientoPQRListAdapter extends RecyclerView.Adapter<PQRHolder> {
    private List<ItemPQR> list, listFilter;
    private PQRHolder.Events events;

    public SeguimientoPQRListAdapter(List<ItemPQR> list, List<ItemPQR> listFilter, PQRHolder.Events events){
        this.list = list;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(SeguimientoPQRListAdapter.this);
    }

    @NonNull
    @Override
    public PQRHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemSeguimientoPqrBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_seguimiento_pqr, parent, false);
        return new PQRHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull PQRHolder holder, int position) {
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
        private SeguimientoPQRListAdapter mAdapter;
        private CustomFilter(SeguimientoPQRListAdapter mAdapter) {
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
                for (final ItemPQR mWords : list) {
                    if (String.valueOf(mWords.getCaso()).toLowerCase().contains(filterPattern)) {
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
