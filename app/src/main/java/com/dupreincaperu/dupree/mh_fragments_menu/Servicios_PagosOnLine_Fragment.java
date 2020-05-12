package com.dupreincaperu.dupree.mh_fragments_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dupreincaperu.dupree.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Servicios_PagosOnLine_Fragment extends Fragment {

    private final String TAG = "Servicios_PagosOnLine_Frag";


    public Servicios_PagosOnLine_Fragment() {
        // Required empty public constructor
    }

//rcvPagos
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicios_pagos_on_line, container, false);



        return v;
    }



}
