package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.adapters.DisciplinaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;

/**
 * A simple {@link Fragment} subclass.
 */

//TODO Documentar

public class GradeCursoFragment extends Fragment {

    private Spinner cursos;
    private ListView listDisciplinas;

    public GradeCursoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_horario_curso, container, false);

        cursos = (Spinner) layout.findViewById(R.id.spinner);
        listDisciplinas = (ListView) layout.findViewById(R.id.list_disciplinas);
        Button buscar = (Button) layout.findViewById(R.id.buscar_cursos);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCurso = String.valueOf(cursos.getSelectedItem());

                listDisciplinas.setAdapter(new DisciplinaAdapter(getContext(), new DisciplinaDAO(getContext()).list(nomeCurso)));
            }
        });

        return layout;
    }

}