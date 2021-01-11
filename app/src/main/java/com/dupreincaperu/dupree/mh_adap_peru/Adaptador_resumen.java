package com.dupreincaperu.dupree.mh_adap_peru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

import java.util.ArrayList;

public class Adaptador_resumen extends RecyclerView.Adapter<Adaptador_resumen.ViewHolderDatos> {

    ArrayList<Integer> ListNume;
    ArrayList<String>  ListCodi;
    ArrayList<String>  ListTipo;
    ArrayList<String>  ListNomb;
    ArrayList<Integer> ListCant;
    ArrayList<String>  ListValo;

    public Adaptador_resumen(ArrayList<Integer> listNume, ArrayList<String> listCodi, ArrayList<String> listTipo, ArrayList<String> listNomb, ArrayList<Integer> listCant, ArrayList<String> listValo) {
        ListNume = listNume;
        ListCodi = listCodi;
        ListTipo = listTipo;
        ListNomb = listNomb;
        ListCant = listCant;
        ListValo = listValo;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resumen,null,false);

        return new ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignardatos_nume(ListNume.get(position));
        holder.asignardatos_codi(ListCodi.get(position));
        holder.asignardatos_tipo(ListTipo.get(position));
        holder.asignardatos_nomb(ListNomb.get(position));
        holder.asignardatos_cant(ListCant.get(position));
        holder.asignardatos_valo(ListValo.get(position));
    }

    @Override
    public int getItemCount() {
        return ListNume.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        TextView nume;
        TextView codi;
        TextView tipo;
        TextView nomb;
        TextView cant;
        TextView valo;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            nume = (TextView) itemView.findViewById(R.id.nume);
            codi = (TextView) itemView.findViewById(R.id.codi);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
            nomb = (TextView) itemView.findViewById(R.id.nomb);
            cant = (TextView) itemView.findViewById(R.id.cant);
            valo = (TextView) itemView.findViewById(R.id.valo);
        }

        public void asignardatos_nume(int id) {
            nume.setText(String.valueOf(id));
        }

        public void asignardatos_codi(String codigo) {
            codi.setText(codigo);
        }

        public void asignardatos_tipo(String tipop) {
            tipo.setText(tipop);
        }

        public void asignardatos_nomb(String nombre) {
            nomb.setText(nombre);
        }

        public void asignardatos_cant(int cantidad) {
            cant.setText(String.valueOf(cantidad));
        }

        public void asignardatos_valo(String valor) {
            valo.setText("S/."+valor);
        }

    }




}
