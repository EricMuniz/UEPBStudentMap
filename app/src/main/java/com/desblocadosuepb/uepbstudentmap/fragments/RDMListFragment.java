package com.desblocadosuepb.uepbstudentmap.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.adapters.RDMListAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;
import com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper;

/**
 * Esta classe é um ListFragment usado para mostrar a listagem
 * das RDM's. Ela é iniciada na na activity principal MainActivity.
 *
 * @author Eric
 * @version 1
 * @since Release 01 da aplicação
 * @see android.support.v4.app.ListFragment
 * @see com.desblocadosuepb.uepbstudentmap.activities.MainActivity
 */
public class RDMListFragment extends ListFragment {

    /**
     * Construtor vazio (default) de RDMListFragment
     */
    public RDMListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TextView textView = new TextView(getActivity());
        //textView.setText(R.string.hello_blank_fragment);


        setListAdapter(new RDMListAdapter(getContext(), new RDMDAO(getContext()).list(), "listar"));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
