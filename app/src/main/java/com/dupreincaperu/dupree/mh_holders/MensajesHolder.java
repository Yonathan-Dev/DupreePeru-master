package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemBandeja;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemBandejaEntradaBinding;
import com.dupreincaperu.dupree.mh_utilities.ProcessImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class MensajesHolder extends RecyclerView.ViewHolder{
    private ItemBandejaEntradaBinding binding;
    private Events events;
    private ImageLoader img;

    public MensajesHolder(@NonNull ItemBandejaEntradaBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemBandeja item, int numItemSelected) {
        Resources resources = BaseAPP.getContext().getResources();
        img = ImageLoader.getInstance();
        img.init(ProcessImage.configurarImageLoader(BaseAPP.getContext()));
        ///Imagen del album

        binding.textBody.setText(item.getMensaje().concat("\n"));
        binding.txtvDate.setText(item.getFecha());

        binding.profileImage.setImageResource(item.getItemSelected() ? R.drawable.img_check2 : R.drawable.ic_flor180x180_no_transp);

        binding.ctxTop.setBackground(resources.getDrawable(R.drawable.rounded_background_chat_receive_top_blue));
        binding.ctxBottom.setBackground(resources.getDrawable(R.drawable.rounded_background_chat_receive_bottom_blue));

        if(item.getImagen().isEmpty())
            binding.imgUrl.setVisibility(View.GONE);
        else {
            binding.imgUrl.setVisibility(View.VISIBLE);
            img.displayImage(item.getImagen(), binding.imgUrl, new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build());
        }

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null) {
                    events.onClickRoot(item, getAdapterPosition(), true);
                }
            }
        });

        binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(events != null) {
                    events.onLongClickRoot(item, getAdapterPosition());
                }
                return true;
            }
        });

        binding.imgUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null) {
                    events.onClickImage(item, getAdapterPosition());
                }
            }
        });
        binding.imgUrl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(events != null) {
                    events.onLongClickRoot(item, getAdapterPosition());
                }
                return true;
            }
        });
    }

    public interface Events{
        void onClickRoot(ItemBandeja dataRow, int row, boolean force);
        void onLongClickRoot(ItemBandeja dataRow, int row);

        void onClickImage(ItemBandeja dataRow, int row);
    }
}
