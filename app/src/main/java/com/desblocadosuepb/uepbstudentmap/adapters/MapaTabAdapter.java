package com.desblocadosuepb.uepbstudentmap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.desblocadosuepb.uepbstudentmap.fragments.MapaTab1Fragment;
import com.desblocadosuepb.uepbstudentmap.fragments.MapaTab2Fragment;
import com.desblocadosuepb.uepbstudentmap.fragments.MapaTab3Fragment;

/**
 * Esta classe é um FragmentStatePagerAdapter usado como
 * adaptador que seta as abas do fragmento do mapa.
 *
 * @author Eric
 * @version 1
 * @see FragmentStatePagerAdapter
 * @see com.desblocadosuepb.uepbstudentmap.fragments.MapaFragment
 * @since release 2
 */
public class MapaTabAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    /**
     * Construtor da classe MapaTabAdapter.
     *
     * @param fragmentManager O FragmentManager
     * @param numOfTabs       O número de abas
     */
    public MapaTabAdapter(FragmentManager fragmentManager, int numOfTabs){
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MapaTab1Fragment();
            case 1:
                return new MapaTab2Fragment();
            case 2:
                return new MapaTab3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
