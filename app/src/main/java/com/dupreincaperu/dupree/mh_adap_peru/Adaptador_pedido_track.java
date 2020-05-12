package com.dupreincaperu.dupree.mh_adap_peru;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

import java.util.ArrayList;

public class Adaptador_pedido_track extends RecyclerView.Adapter<Adaptador_pedido_track.ViewHolderDatos> {

    ArrayList<String> ListNume;
    ArrayList<String> ListPedi;
    ArrayList<String> ListConf;
    ArrayList<String> ListMoti;
    ArrayList<String> ListHora;

    public Adaptador_pedido_track(ArrayList<String> listNume, ArrayList<String> listPedi, ArrayList<String> listConf, ArrayList<String> listMoti, ArrayList<String> listHora) {
        ListNume = listNume;
        ListPedi = listPedi;
        ListConf = listConf;
        ListMoti = listMoti;
        ListHora = listHora;
    }

    @NonNull
    @Override
    public Adaptador_pedido_track.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_track,null,false);

        return new Adaptador_pedido_track.ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adaptador_pedido_track.ViewHolderDatos holder, int position) {
        holder.asignardatos_nume(ListNume.get(position));
        holder.asignardatos_pedi(ListPedi.get(position));
        holder.asignardatos_conf(ListConf.get(position));
        holder.asignardatos_moti(ListMoti.get(position));
        holder.asignardatos_hora(ListHora.get(position));
    }

    @Override
    public int getItemCount() {
        return ListNume.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nume;
        TextView pedi;
        TextView conf;
        TextView moti;
        TextView hora;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nume = (TextView) itemView.findViewById(R.id.nume);
            pedi = (TextView) itemView.findViewById(R.id.pedi_trac);
            conf = (TextView) itemView.findViewById(R.id.confirmado);
            moti = (TextView) itemView.findViewById(R.id.motivado);
            hora = (TextView) itemView.findViewById(R.id.hora);
        }

        public void asignardatos_nume(String datos) {
            nume.setText(datos);
        }

        public void asignardatos_pedi(String datos) {
            pedi.setText(datos);
        }

        public void asignardatos_conf(String datos) {
            conf.setText(datos);
        }

        public void asignardatos_moti(String datos) {
            moti.setText(datos);
        }

        public void asignardatos_hora(String datos) {
            hora.setText(datos);
        }
    }




}
