package com.dupreincaperu.dupree.mh_holders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.enums.EnumStatusPreInsc;
import com.dupreeinca.lib_api_rest.model.dto.response.realm.Posibles_Nuevas;
import com.dupreincaperu.dupree.BaseAPP;
import com.dupreincaperu.dupree.R;
import com.dupreincaperu.dupree.databinding.ItemListNuevasBinding;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class ListNuevasHolder extends RecyclerView.ViewHolder{
    private ItemListNuevasBinding binding;
    private Events events;

    public ListNuevasHolder(@NonNull ItemListNuevasBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final Posibles_Nuevas item) {
        binding.tvStatus.setText(item.getEstado());
        binding.tvName.setText("".concat(item.getNombre()+" "+item.getApellido()));

        //Se realiza esta validacion por que muestra null en la pantalla
        String direccion = (item.getDireccion()== null) ?"":item.getDireccion();
        String barrio    = (item.getBarrio()== null) ?"":item.getBarrio();

        binding.tvCedula.setText(BaseAPP.getContext().getResources().getString(R.string.concat_cedula, item.getCedula()));
        binding.tvCelular1.setText(BaseAPP.getContext().getResources().getString(R.string.concat_celular, item.getMovil1()));
        binding.tvCelular2.setText(BaseAPP.getContext().getResources().getString(R.string.concat_celular, item.getMovil2()));
        binding.tvDir.setText(BaseAPP.getContext().getResources().getString(R.string.concat_dir, direccion));
        binding.tvBarrio.setText(BaseAPP.getContext().getResources().getString(R.string.concat_barrio, barrio));
        binding.tvStatus.setText(EnumStatusPreInsc.PENDIENTE.getKey());
        binding.imagen.setImageResource(R.drawable.pendiente);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(events != null)
                    events.onClickRoot(item, getAdapterPosition());
            }
        });

         binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(events != null)
                 //   events.onBorrarLinea(item.getCedula());
                    events.onDeleteRegistroId(item.getId());
            }
        });
    }

    public interface Events{
        void onClickRoot(Posibles_Nuevas dataRow, int row);
  //      void onBorrarLinea(String cedula);
        void onDeleteRegistroId(int id); //Eliminar Registro por ID

    }
}
