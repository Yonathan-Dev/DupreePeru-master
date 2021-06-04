package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import com.dupreeinca.lib_api_rest.model.dto.response.PtosByCamp;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemPuntosAsesoraBinding;
import com.dupreincaperu.dupree.mh_holders.PuntosAsesoraHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class PuntosAsesoraListAdapter extends RecyclerView.Adapter<PuntosAsesoraHolder> {

    private List<PtosByCamp> list, listFilter;
    private PuntosAsesoraHolder.Events events;

    public PuntosAsesoraListAdapter(List<PtosByCamp> listPtosCamp, List<PtosByCamp> listFilter, PuntosAsesoraHolder.Events events){
        this.list = listPtosCamp;
        this.listFilter = listFilter;
        this.events=events;

        //para filtrar
        mFilter = new CustomFilter(PuntosAsesoraListAdapter.this);
    }


    @NonNull
    @Override
    public PuntosAsesoraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemPuntosAsesoraBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_puntos_asesora, parent, false);
        return new PuntosAsesoraHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull PuntosAsesoraHolder holder, int position) {
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
        private PuntosAsesoraListAdapter mAdapter;
        private CustomFilter(PuntosAsesoraListAdapter mAdapter) {
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
                for (final PtosByCamp mWords : list) {
                    if (String.valueOf(mWords.getCampana()).toLowerCase().contains(filterPattern)) {
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
