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

    private SQLiteDatabase database;
    private Cursor cursor;


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

        try{
            database = new StudentMapDatabaseHelper(getContext()).getReadableDatabase();

            cursor = database.query("COMPOE",
                    new String[]{"_id", "RDM_ID", "AULA_ID"},
                    null,null,null,null,null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"RDM_ID", "AULA_ID"},
                    new int[]{android.R.id.text1, android.R.id.text1},0);
            setListAdapter(listAdapter);
        }catch (SQLiteException e){
            Toast.makeText(getContext(), "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
