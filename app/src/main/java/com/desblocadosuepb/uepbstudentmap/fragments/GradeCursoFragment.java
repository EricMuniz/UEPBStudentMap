package com.desblocadosuepb.uepbstudentmap.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.adapters.DisciplinaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;

/**
 * Esta classe é um Fragment usado para que o usário selecione
 * o curso que ele deseja para mostrar a listagem de Disciplinas.
 * Ela é iniciada na activity principal MainActivity.
 *
 * @author Eric
 * @version 1
 * @since Release 01 da aplicação
 * @see android.support.v4.app.Fragment
 * @see com.desblocadosuepb.uepbstudentmap.activities.MainActivity
 */

public class GradeCursoFragment extends Fragment {

    private Spinner cursos;
    private ListView listDisciplinas;

    /**
     * Contrutor vazio (default) de GradeCursoFragment
     */
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
