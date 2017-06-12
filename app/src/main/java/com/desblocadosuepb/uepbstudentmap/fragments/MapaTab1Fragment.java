package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;

/**
 * Esta classe é um Fragment que representa
 * umas das abas do MapaFragment.
 *
 * @author Eric
 * @version 1.0
 * @see android.support.v4.app.Fragment
 * @see MapaFragment
 * @since release 2
 */
public class MapaTab1Fragment extends Fragment {


    /**
     * Construtor vazio (default) de MapaTab1Fragment.
     */
    public MapaTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_mapa_tab1, container, false);

        final AppCompatImageButton sala01 = (AppCompatImageButton) layout.findViewById(R.id.sala01);
        sala01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Apenas um teste :)", Toast.LENGTH_SHORT).show();
                sala01.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });

        AppCompatImageButton sala02 = (AppCompatImageButton) layout.findViewById(R.id.sala02);
        sala02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sala01.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        });

        return layout;
    }

}
