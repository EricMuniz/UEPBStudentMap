package com.desblocadosuepb.uepbstudentmap.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity;
import com.desblocadosuepb.uepbstudentmap.adapters.DisciplinaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;

/**
 * Esta classe é um Fragment usado para que o usário selecione
 * o curso que ele deseja para mostrar a listagem de Disciplinas.
 * Ela é iniciada na activity principal MainActivity.
 *
 * @author Eric
 * @version 1.1
 * @see android.support.v4.app.Fragment
 * @see com.desblocadosuepb.uepbstudentmap.activities.MainActivity
 * @since Release 01 da aplicação
 */
public class GradeCursoFragment extends Fragment {

    private Spinner cursos;
    private RecyclerView listDisciplinas;

    /**
     * Contrutor vazio (default) de GradeCursoFragment.
     */
    public GradeCursoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_horario_curso, container, false);

        cursos = (Spinner) layout.findViewById(R.id.spinner);
        listDisciplinas = (RecyclerView) layout.findViewById(R.id.list_disciplinas);
        Button buscar = (Button) layout.findViewById(R.id.buscar_cursos);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCurso = String.valueOf(cursos.getSelectedItem());

                DisciplinaAdapter adapter = new DisciplinaAdapter(new DisciplinaDAO(getContext()).list(nomeCurso));
                listDisciplinas.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                listDisciplinas.setLayoutManager(layoutManager);

                adapter.setListener(new DisciplinaAdapter.Listener() {
                    @Override
                    public void onClick(int position) {
                        Intent intent = new Intent(getContext(), DetalhesActivity.class);
                        intent.putExtra(DetalhesActivity.EXTRA_CPT,
                                new DisciplinaDAO(getContext()).list().get(position).getCodigo());
                        startActivity(intent);
                    }
                });
            }
        });

        return layout;
    }

}
