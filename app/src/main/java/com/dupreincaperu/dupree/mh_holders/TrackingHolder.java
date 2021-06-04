package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.enums.EnumTracking;
import com.dupreeinca.lib_api_rest.model.dto.response.Tracking;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemTrackingBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class TrackingHolder extends RecyclerView.ViewHolder{
    private ItemTrackingBinding binding;
    private Events events;
    private String[] title ={EnumTracking.RECIBIDO.getKey(),EnumTracking.FACTURADO.getKey(), EnumTracking.EMBALADO.getKey(), EnumTracking.DESPACHADO.getKey(), EnumTracking.ENTREGADO.getKey()};

    public TrackingHolder(@NonNull ItemTrackingBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final Tracking item) {
        Resources resources = BaseAPP.getContext().getResources();

        int position = getAdapterPosition();

        if(position < 5)
            binding.tvTitleRcv.setText(title[position]);

        binding.tvDateRcv.setText(item.getFecha());
        binding.tvDateRcv.setVisibility(item.getFecha().isEmpty() ? View.GONE : View.VISIBLE);

        binding.tvDetailRcv.setText(item.getNombre());
        //backend esta repitiendo el titulo
        binding.tvDetailRcv.setVisibility(item.getNombre().equals(title[position]) ? View.GONE : View.VISIBLE);


        if(item.getCheck().equals("1"))
        {
            binding.imgIconCircle.setBackground(resources.getDrawable(R.drawable.circle_green));
            binding.tvTitleRcv.setTypeface(binding.tvTitleRcv.getTypeface(), Typeface.BOLD);
            binding.tvTitleRcv.setTextColor(resources.getColor(R.color.green_check));
        }
        else{
            //binding.imgIconCircle.setBackground(mContext.getResources().getDrawable(R.drawable.circle_concentric_green));
            binding.imgIconCircle.setBackground(resources.getDrawable(R.drawable.circle_concentric_gray));
            binding.tvTitleRcv.setTypeface(binding.tvTitleRcv.getTypeface(), Typeface.NORMAL);
            binding.tvTitleRcv.setTextColor(resources.getColor(R.color.gray_5));
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(Tracking dataRow, int row);
    }
}
