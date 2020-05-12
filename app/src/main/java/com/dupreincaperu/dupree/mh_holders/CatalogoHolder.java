package com.dupreincaperu.dupree.mh_holders;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.realm.Catalogo;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemCatalogoBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class CatalogoHolder extends RecyclerView.ViewHolder{
    private String TAG = CatalogoHolder.class.getName();
    private ItemCatalogoBinding binding;
    private Events events;

    public CatalogoHolder(@NonNull ItemCatalogoBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final Catalogo item, boolean isEnable, int numEditable, List<String> idEditable) {
        binding.imgBAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null && isEnable){
                    //setPosSelected(getAdapterPosition());
                    events.onAddCartClick(item, getAdapterPosition());
                }
            }
        });
        binding.imgBDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null && isEnable){
                    //setPosSelected(getAdapterPosition());
                    events.onDecreaseClick(item, getAdapterPosition());
                }
            }
        });
        binding.imgBIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null && isEnable){
                    //setPosSelected(getAdapterPosition());
                    events.onIncreaseClick(item, getAdapterPosition());
                }
            }
        });

        binding.tvAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null && isEnable){
                    events.onAddCartClick(item, getAdapterPosition());
                }
            }
        });

        Resources resources = BaseAPP.getContext().getResources();

        binding.tvPage.setText(resources.getString(R.string.concat_pagina, item.getPage()));
        binding.tvCode.setText(resources.getString(R.string.concat_codigo, item.getId()));
        binding.tvDescription.setText(item.getName());

        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        binding.tvValor.setText(resources.getString(R.string.concat_dolar, formatter.format(Float.parseFloat(item.getValor()))));

        binding.tvCantidad.setText(String.valueOf(item.getCantidad()));

        //validar si ya esta (o no) en el carrito.
        binding.ctnIncDecCant.setVisibility(item.getCantidad()>0 ? View.VISIBLE : View.GONE);
        binding.ctnAddCart.setVisibility(item.getCantidad()>0 ? View.GONE : View.VISIBLE);

        binding.imgBDecrease.setColorFilter(isEnable ? resources.getColor(R.color.azulDupree) :resources.getColor(R.color.gray_5),android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.imgBIncrease.setColorFilter(isEnable ? resources.getColor(R.color.azulDupree) :resources.getColor(R.color.gray_5),android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.imgBAddCart.setColorFilter(isEnable ? resources.getColor(R.color.azulDupree) :resources.getColor(R.color.gray_5),android.graphics.PorterDuff.Mode.MULTIPLY);
        binding.tvAddCart.setTextColor(isEnable ? resources.getColor(R.color.azulDupree) : resources.getColor(R.color.gray_5));

        if(item.getCantidad() == item.getCantidadServer()) {
            //SON IGUALES, NO HAY CAMBIO
            if(item.getCantidad()>=1) {// no esta en el server
                binding.imagen.setImageResource(R.drawable.ic_flor180x180);//no hay cambios
                binding.tvStatus.setText("");
                binding.tvStatus.setTextColor(resources.getColor(R.color.azulDupree));
            }else{// si esta en el server pero no hay cambios
                binding.imagen.setImageResource(R.drawable.ic_flor180x180);//no hay cambior
                binding.tvStatus.setText("");
                binding.tvStatus.setTextColor(resources.getColor(R.color.azulDupree));

            }
            removeEditable(idEditable, item.getId());
        }
        else if (item.getCantidad() != item.getCantidadServer())
        {
            if (item.getCantidad() == 0 && item.getCantidadServer() >= 1) {
                binding.imagen.setImageResource(R.drawable.ic_flor_red_180x180);//eliminar
                binding.tvStatus.setText(R.string.eliminar);
                binding.tvStatus.setTextColor(resources.getColor(R.color.red_1));
            } else if (item.getCantidad() >= 1 && item.getCantidadServer() == 0) {
                binding.imagen.setImageResource(R.drawable.ic_flor_pick_180x180);//agregar
                binding.tvStatus.setText(R.string.aniadir);
                binding.tvStatus.setTextColor(resources.getColor(R.color.colorAccent));
            } else {
                binding.imagen.setImageResource(R.drawable.ic_flor_orange_180x180);//editar
                binding.tvStatus.setText(R.string.editar);
                binding.tvStatus.setTextColor(resources.getColor(R.color.orange_600));
            }

            addEditable(idEditable, item.getId());
        }

        Log.e(TAG, "num. Items editable= "+String.valueOf(idEditable.size()));

        if(numEditable==0 && idEditable.size()==1){
            Log.e(TAG, "num. Items editable = SI EDITAR VARIABLE=true");
            if(events != null) {
                Log.e(TAG, "num. Items editable = SI EVENTS");
                events.onAddEditableClick(true);
            }
        } else if(numEditable==1 && idEditable.size()==0){
            Log.e(TAG, "num. Items editable = NO EDITAR");
            if(events != null)
                events.onAddEditableClick(false);
        }
    }

    private void addEditable(List<String> idEditable, String id){
        for(int i=0; i<idEditable.size();i++){
            if(idEditable.get(i).equals(id)){
                return;
            }
        }
        idEditable.add(id);//significa que este id se debe modificar
    }

    private void removeEditable(List<String> idEditable, String id){
        for(int i=0; i<idEditable.size();i++){
            if(idEditable.get(i).equals(id)){
                idEditable.remove(i);
            }
        }
    }

    public interface Events{
        void onAddCartClick(Catalogo dataRow, int row);

        void onIncreaseClick(Catalogo dataRow, int row);
        void onDecreaseClick(Catalogo dataRow, int row);

        void onAddEditableClick(boolean isEditable);
//        void addEditable(Catalogo dataRow, int row);
//        void removeEditable(Catalogo dataRow, int row);
    }
}
