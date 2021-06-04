package com.dupreincaperu.dupree.mh_holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.enums.EnumStatusPreInsc;
import com.dupreeinca.lib_api_rest.model.dto.response.Preinscripcion;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemListPreinscripBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class ListPreHolder extends RecyclerView.ViewHolder{
    private ItemListPreinscripBinding binding;
    private Events events;

    public ListPreHolder(@NonNull ItemListPreinscripBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final Preinscripcion item) {
        binding.tvStatus.setText(item.getEstado());
        binding.tvName.setText("".concat(item.getNombre()));

        binding.tvCedula.setText(BaseAPP.getContext().getResources().getString(R.string.concat_cedula, item.getCedula()));
        binding.tvCelular.setText(BaseAPP.getContext().getResources().getString(R.string.concat_celular, item.getCelular()));
        binding.tvfechaNac.setText(BaseAPP.getContext().getResources().getString(R.string.concat_fecha_nacimiento, item.getFecha()));
        binding.tvCiudad.setText(BaseAPP.getContext().getResources().getString(R.string.concat_ciudad, item.getName_ciudad()));
        binding.tvBarrio.setText(BaseAPP.getContext().getResources().getString(R.string.concat_barrio, item.getBarrio()));
        binding.tvfechaReg.setText(BaseAPP.getContext().getResources().getString(R.string.concat_fecha_registro, item.getFecha()));
        binding.tvObservacion.setText(BaseAPP.getContext().getResources().getString(R.string.concat_observacion, item.getObservacion()));

        if(item.getEstado().equals(EnumStatusPreInsc.AUTORIZADO.getKey())){
            binding.imagen.setImageResource(R.drawable.registrado);
        } else if(item.getEstado().equals(EnumStatusPreInsc.RECHAZADO.getKey())){
            binding.imagen.setImageResource(R.drawable.rechazado);
        } else if(item.getEstado().equals(EnumStatusPreInsc.PENDIENTE.getKey())){
            binding.imagen.setImageResource(R.drawable.pendiente);
        } else if(item.getEstado().equals(EnumStatusPreInsc.REVISION.getKey())){
            binding.imagen.setImageResource(R.drawable.revision);
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
        void onClickRoot(Preinscripcion dataRow, int row);
    }
}
