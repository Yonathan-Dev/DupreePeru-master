package com.dupreincaperu.dupree.mh_adapters;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemBandeja;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemBandejaEntradaBinding;
import com.dupreincaperu.dupree.mh_holders.MensajesHolder;

import java.util.List;

/**
 * Created by marwuinh@gmail.com on 8/3/2017.
 */

public class MensajesListAdapter extends RecyclerView.Adapter<MensajesHolder>{

    private String TAG = MensajesListAdapter.class.getName();
    private List<ItemBandeja> list;
    private MensajesHolder.Events events;

    private int lastNumItemSelected=-1;
    private int numItemSelected=0;

    public MensajesListAdapter(List<ItemBandeja> list, MensajesHolder.Events events){
        this.list = list;
        this.events = events;
    }

    @NonNull
    @Override
    public MensajesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        ItemBandejaEntradaBinding binding = DataBindingUtil.inflate(inflate, R.layout.item_bandeja_entrada, parent, false);
        return new MensajesHolder(binding, events);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajesHolder holder, int position) {
        if(list.size() > position){
            holder.bindData(list.get(position), numItemSelected);
        }
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public void calculateItemsSelected(int rowSelected){
        if(list.get(rowSelected).getItemSelected())
            numItemSelected=numItemSelected-1;
        else
            numItemSelected=numItemSelected+1;

        Log.e("calculateItemsSelected",String.valueOf(numItemSelected));
        list.get(rowSelected).setItemSelected(!list.get(rowSelected).getItemSelected());
    }

    public void clearItemSelected(){
        numItemSelected=0;
        for(int i = 0; i< list.size(); i++){
            list.get(i).setItemSelected(false);
        }
        notifyDataSetChanged();
    }

    public int getNumItemSelected() {
        return numItemSelected;
    }

    public int getLastNumItemSelected() {
        return lastNumItemSelected;
    }

    public void setLastNumItemSelected(int lastNumItemSelected) {
        this.lastNumItemSelected = lastNumItemSelected;
    }

}
