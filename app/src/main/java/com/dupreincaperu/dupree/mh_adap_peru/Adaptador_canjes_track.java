package com.dupreincaperu.dupree.mh_adap_peru;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dupreincaperu.dupree.R;

import java.util.ArrayList;

public class Adaptador_canjes_track extends RecyclerView.Adapter<Adaptador_canjes_track.ViewHolderDatos> {

    ArrayList<String> ListNume;
    ArrayList<String> ListIden;
    ArrayList<String> ListCodi;
    ArrayList<String> ListDesc;
    ArrayList<String> ListReco;
    ArrayList<String> ListReci;

    public Adaptador_canjes_track(ArrayList<String> listIden, ArrayList<String> listNume, ArrayList<String> listCodi, ArrayList<String> listDesc, ArrayList<String> listReco, ArrayList<String> listReci) {
        ListNume = listNume;
        ListIden = listIden;
        ListCodi = listCodi;
        ListDesc = listDesc;
        ListReco = listReco;
        ListReci = listReci;
    }

    @NonNull
    @Override
    public Adaptador_canjes_track.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_canj_track,null,false);

        return new Adaptador_canjes_track.ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adaptador_canjes_track.ViewHolderDatos holder, int position) {
        holder.asignardatos_nume(ListNume.get(position));
        holder.asignardatos_iden(ListIden.get(position));
        holder.asignardatos_codi(ListCodi.get(position));
        holder.asignardatos_nomb(ListDesc.get(position));
        holder.asignardatos_reco(ListReco.get(position));
        holder.asignardatos_reci(ListReci.get(position));
    }

    @Override
    public int getItemCount() {
        return ListNume.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView nume;
        TextView txt_nume_iden;
        TextView txt_codi_prod;
        TextView txt_nomb_prod;
        TextView txt_cant_movi;
        TextView txt_cant_fina;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            nume = (TextView) itemView.findViewById(R.id.nume);
            txt_nume_iden = (TextView) itemView.findViewById(R.id.txt_nume_iden);
            txt_codi_prod = (TextView) itemView.findViewById(R.id.txt_codi_prod);
            txt_nomb_prod = (TextView) itemView.findViewById(R.id.txt_nomb_prod);
            txt_cant_movi = (TextView) itemView.findViewById(R.id.txt_cant_movi);
            txt_cant_fina = (TextView) itemView.findViewById(R.id.txt_cant_fina);
        }

        public void asignardatos_nume(String id) {
            nume.setText(id);
        }

        public void asignardatos_iden(String nume_iden) {
            txt_nume_iden.setText(nume_iden);
        }

        public void asignardatos_codi(String codi_prod) {
            txt_codi_prod.setText(codi_prod);
        }

        public void asignardatos_nomb(String nomb_prod) {
            txt_nomb_prod.setText(nomb_prod);
        }

        public void asignardatos_reco(String cant_movi) {
            txt_cant_movi.setText(cant_movi);
        }

        public void asignardatos_reci(String cant_reco) {
            txt_cant_fina.setText(cant_reco);

            if (!cant_reco.equalsIgnoreCase("0") && !cant_reco.equalsIgnoreCase("-")){
                nume.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.registrado);
            } else if (cant_reco.equalsIgnoreCase("0") && !cant_reco.equalsIgnoreCase("-")){
                nume.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.pendiente);
            }

        }
    }

}
