package com.dupreincaperu.dupree.mh_adap_peru;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

import java.util.ArrayList;

public class Adaptador_pedido_registrado extends RecyclerView.Adapter<Adaptador_pedido_registrado.ViewHolderDatos> {

    ArrayList<String> ListGuia;
    ArrayList<String> ListPedido;
    ArrayList<String> ListUbicacion;
    ArrayList<String> ListCont;
    ArrayList<String> ListFecha;

    public Adaptador_pedido_registrado(ArrayList<String> listGuia, ArrayList<String> listPedido, ArrayList<String> listUbicacion, ArrayList<String> listCont, ArrayList<String> listFecha) {
        ListGuia = listGuia;
        ListPedido = listPedido;
        ListUbicacion = listUbicacion;
        ListCont    = listCont;
        ListFecha = listFecha;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_pedi,null,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignardatos_guia(ListGuia.get(position));
        holder.asignardatos_pedi(ListPedido.get(position));
        holder.asignardatos_ubic(ListUbicacion.get(position));
        holder.asignardatos_cont(ListCont.get(position));
        holder.asignardatos_fech(ListFecha.get(position));
    }

    @Override
    public int getItemCount() {
        return ListGuia.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView guia;
        TextView pedido;
        TextView ubicacion;
        TextView cont;
        TextView fecha;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            guia = (TextView) itemView.findViewById(R.id.guia);
            pedido = (TextView) itemView.findViewById(R.id.pedi);
            ubicacion = (TextView) itemView.findViewById(R.id.ubicacion);
            cont = (TextView) itemView.findViewById(R.id.cont);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
        }

        public void asignardatos_guia(String datos) {
            guia.setText(datos);
        }

        public void asignardatos_pedi(String datos) {
            pedido.setText(datos);
        }

        public void asignardatos_ubic(String datos) {
            ubicacion.setText(datos);
        }

        public void asignardatos_cont(String datos) {
            cont.setText(datos);
        }

        public void asignardatos_fech(String datos) {
            fecha.setText(datos);
        }
    }
}
