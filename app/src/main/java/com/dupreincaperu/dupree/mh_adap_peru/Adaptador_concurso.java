package com.dupreincaperu.dupree.mh_adap_peru;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dupreincaperu.dupree.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador_concurso extends RecyclerView.Adapter<Adaptador_concurso.ViewHolderDatos> {

    ArrayList<String> listDescripcion;
    ArrayList<String>  listImagen;
    ArrayList<String>  listPuntos;

    public Adaptador_concurso(ArrayList<String> listDescripcion, ArrayList<String> listImagen, ArrayList<String> listPuntos) {
        this.listDescripcion = listDescripcion;
        this.listImagen      = listImagen;
        this.listPuntos      = listPuntos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_concurso,null,false);

        return new ViewHolderDatos(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignardatos_desc(listDescripcion.get(position));
        holder.asignardatos_imag(listImagen.get(position));
        holder.asignardatos_punt(listPuntos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDescripcion.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        CircleImageView civ_imag_prem;
        TextView  txt_desc_prem;
        TextView  txt_punt_prem;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            civ_imag_prem = (CircleImageView) itemView.findViewById(R.id.civ_imag_prem);
            txt_desc_prem = (TextView)  itemView.findViewById(R.id.txt_desc_prem);
            txt_punt_prem = (TextView)  itemView.findViewById(R.id.txt_punt_prem);
        }

        public void asignardatos_imag(String url) {

            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(civ_imag_prem);
        }

        public void asignardatos_desc(String descripcion) {
            txt_desc_prem.setText(descripcion);
        }

        public void asignardatos_punt(String puntos) {

            if (puntos.equalsIgnoreCase("")){
                puntos =  "0 Puntos";
                txt_punt_prem.setVisibility(View.GONE);
            } else {
                txt_punt_prem.setVisibility(View.VISIBLE);
                puntos = puntos+" Puntos";
            }
            txt_punt_prem.setText(puntos);
        }


    }




}
