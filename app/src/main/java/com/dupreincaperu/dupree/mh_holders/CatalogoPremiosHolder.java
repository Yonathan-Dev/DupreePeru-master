package com.dupreincaperu.dupree.mh_holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemFolleto;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCatalogoPremiosBinding;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class CatalogoPremiosHolder extends RecyclerView.ViewHolder{
    private ItemCatalogoPremiosBinding binding;
    private Events events;
    private ImageLoader img;

    private ImageLoaderConfiguration configurarImageLoader() {
        DisplayImageOptions opcionesDefault = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)//@drawable/logocompany
                .showImageOnFail(R.drawable.no_image)
                .showImageForEmptyUri(R.drawable.no_image)
                .showImageOnLoading(R.drawable.loading)
                .resetViewBeforeLoading(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(BaseAPP.getContext())
                .defaultDisplayImageOptions(opcionesDefault)
                .threadPriority(Thread.NORM_PRIORITY-2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build();
        return config;
    }


    public CatalogoPremiosHolder(@NonNull ItemCatalogoPremiosBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemFolleto item) {
        //quitar la imagen que manda backend en caso de no disponibles
        if(item.getImage().contains("")) {
            img = ImageLoader.getInstance();
            img.init(configurarImageLoader());
            img.displayImage(item.getImage(), binding.imgPremio);
        }

        binding.txtNamePremio.setText(item.getName());
        binding.txtCodePremio.setText(item.getCode());

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemFolleto dataRow, int row);
    }
}
