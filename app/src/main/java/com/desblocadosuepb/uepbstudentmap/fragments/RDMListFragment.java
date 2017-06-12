package com.desblocadosuepb.uepbstudentmap.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity;
import com.desblocadosuepb.uepbstudentmap.adapters.RdmAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;

/**
 * Esta classe é um ListFragment usado para mostrar a listagem
 * das RDM's. Ela é iniciada na na activity principal MainActivity.
 *
 * @author Eric
 * @version 1.1
 * @see android.support.v4.app.Fragment
 * @see com.desblocadosuepb.uepbstudentmap.activities.MainActivity
 * @since Release 01 da aplicação
 */
public class RDMListFragment extends Fragment {

    /**
     * Construtor vazio (default) de RDMListFragment.
     */
    public RDMListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        RecyclerView rdmRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_rdm_list, container, false);

        RdmAdapter adapter = new RdmAdapter(new RDMDAO(getContext()).list());
        rdmRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rdmRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new RdmAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), DetalhesActivity.class);
                intent.putExtra("rdmId", new RDMDAO(getContext()).list().get(position).getId());
                intent.putIntegerArrayListExtra("arrayExtra",
                        new CompoeDAO(getContext()).getListAulaId(new RDMDAO(getContext())
                                .list().get(position).getId()));
                startActivity(intent);
            }
        });

        return rdmRecycler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
