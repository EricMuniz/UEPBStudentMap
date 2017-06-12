package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class MapaTab2Fragment extends Fragment {


    /**
     * Construtor vazio (default) de MapaTab2Fragment.
     */
    public MapaTab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mapa_tab2, container, false);
    }

}
