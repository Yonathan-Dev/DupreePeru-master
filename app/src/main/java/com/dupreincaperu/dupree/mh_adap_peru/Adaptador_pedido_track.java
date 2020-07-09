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
    ArrayList<String> ListEsta;
    ArrayList<String> ListMoti;
    ArrayList<String> ListHora;

    public Adaptador_pedido_track(ArrayList<String> listNume, ArrayList<String> listPedi, ArrayList<String> listEsta, ArrayList<String> listMoti, ArrayList<String> listHora) {
        ListNume = listNume;
        ListPedi = listPedi;
        ListEsta = listEsta;
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
        holder.asignardatos_esta(ListEsta.get(position));
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
        TextView esta;
        TextView moti;
        TextView hora;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nume = (TextView) itemView.findViewById(R.id.nume);
            pedi = (TextView) itemView.findViewById(R.id.pedi_trac);
            esta = (TextView) itemView.findViewById(R.id.estado);
            moti = (TextView) itemView.findViewById(R.id.motivado);
            hora = (TextView) itemView.findViewById(R.id.hora);
        }

        public void asignardatos_nume(String id) {
            nume.setText(id);
        }

        public void asignardatos_pedi(String nume_fact) {
            pedi.setText(nume_fact);
        }

        public void asignardatos_esta(String estado) {
            esta.setText(estado);
            if (estado.equalsIgnoreCase("CONFIRMADO") && !estado.equalsIgnoreCase("-")){
                nume.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.registrado);
            } else if (estado.equalsIgnoreCase("MOTIVADO") && !estado.equalsIgnoreCase("-")){
                nume.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.rechazado);
            } else if (estado.equalsIgnoreCase("ASIGNADO") && !estado.equalsIgnoreCase("-")){
                nume.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.pendiente);
            }
        }

        public void asignardatos_moti(String nomb_moti) {
            moti.setText(nomb_moti);
        }

        public void asignardatos_hora(String acti_hora) {
            hora.setText(acti_hora);
        }
    }




}
