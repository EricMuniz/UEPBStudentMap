package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.adapters.DisciplinaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;

/**
 * A simple {@link ListFragment} subclass.
 */
public class HorarioListFragment extends ListFragment {

    public HorarioListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);

        //TODO Criar o Adapter dos horários, HorarioVO e HorarioDAO
        setListAdapter(new DisciplinaAdapter(getContext(), new DisciplinaDAO(getContext()).list()));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id){

    }

}
