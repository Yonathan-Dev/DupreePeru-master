package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemRedimirBinding;
import com.dupreeinca.lib_api_rest.model.dto.response.ItemPremios;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class RedimirHolder extends RecyclerView.ViewHolder{
    private ItemRedimirBinding binding;
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

    public RedimirHolder(@NonNull ItemRedimirBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemPremios item) {

        Resources resources = BaseAPP.getContext().getResources();
        img = ImageLoader.getInstance();
        img.init(configurarImageLoader());
        img.displayImage(item.getImagen(), binding.imagen);//cuadrar la imagen del grupo

        binding.tvCode.setText(resources.getString(R.string.concat_codigo, item.getCodigo()));
        binding.tvDescription.setText(item.getNombre());
        binding.tvPage.setText(item.getDescripcion());

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.tvValor.setText(resources.getString(R.string.concat_pts, formatter.format(Float.parseFloat(item.getPuntos()))));

        binding.tvCantidad.setText(String.valueOf(item.getCantidad()));

        //verifica si ya esta en el server seleccionado (no se puede modificar)
        if(item.isSelected()){
            item.setInTheCart(true);//indica que ya esta en el carrito (server), y debe inhabilitar este elemento
            binding.imgBIncrease.setEnabled(false);
            binding.imgBDecrease.setEnabled(false);
            binding.imgBIncrease.setClickable(false);
            binding.imgBDecrease.setClickable(false);

            binding.imgBIncrease.setColorFilter(R.color.gray_5, PorterDuff.Mode.MULTIPLY);
            binding.imgBDecrease.setColorFilter(R.color.gray_5, PorterDuff.Mode.MULTIPLY);
        }


        //validar si ya esta (o no) en el carrito para mostrar las vistas.
        binding.ctnIncDecCant.setVisibility(item.isInTheCart() ? View.VISIBLE : View.GONE);
        binding.ctnAddCart.setVisibility(item.isInTheCart() ? View.GONE : View.VISIBLE);

        binding.tvAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onAddCartClick(item, getAdapterPosition());
            }
        });

        binding.imgBAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onAddCartClick(item, getAdapterPosition());
            }
        });


        binding.imgBIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onIncreaseClick(item, getAdapterPosition());
            }
        });

        binding.imgBDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onDecreaseClick(item, getAdapterPosition());
            }
        });

        binding.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickImage(item, getAdapterPosition());
            }
        });
    }

    public interface Events{
        void onAddCartClick(ItemPremios dataRow, int row);

        void onIncreaseClick(ItemPremios dataRow, int row);
        void onDecreaseClick(ItemPremios dataRow, int row);

        void onClickImage(ItemPremios dataRow, int row);
    }
}
