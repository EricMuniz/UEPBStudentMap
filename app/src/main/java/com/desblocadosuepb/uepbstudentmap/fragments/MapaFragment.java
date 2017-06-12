package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.adapters.MapaTabAdapter;

/**
 * Esta classe é o Fragment onde as abas do mapa
 * são setados. Faz uso da classe MapaTabAdapter para
 * setar os MapaTabFragment's.
 *
 * @author Eric
 * @version 1.0
 * @see android.support.v4.app.Fragment
 * @see MapaTab1Fragment
 * @see MapaTab2Fragment
 * @see MapaTab3Fragment
 * @since release 2
 */
public class MapaFragment extends Fragment {

    /**
     * Contrutor vazio (default) de GradeCursoFragment.
     */
    public MapaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mapa, container, false);

        TabLayout tabLayout = (TabLayout) layout.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Ala Oeste"));
        tabLayout.addTab(tabLayout.newTab().setText("Ala Central"));
        tabLayout.addTab(tabLayout.newTab().setText("Ala Leste"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) layout.findViewById(R.id.pager);
        viewPager.setAdapter(new MapaTabAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Não faz nada mesmo
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Não faz nada mesmo
            }
        });

        return layout;
    }

}
