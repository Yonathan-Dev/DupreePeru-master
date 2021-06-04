package com.dupreincaperu.dupree.mh_holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.util.models.ModelList;
import com.dupreincaperu.dupree.databinding.ItemReportesBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class ReportesHolder extends RecyclerView.ViewHolder{
    private ItemReportesBinding binding;
    private Events events;

    public ReportesHolder(@NonNull ItemReportesBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ModelList item) {
        binding.tvTitle.setText(item.getName());
        binding.tvSubtitle.setText(item.getName());
        binding.icon.setImageResource(item.getId());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ModelList dataRow, int row);
    }
}
