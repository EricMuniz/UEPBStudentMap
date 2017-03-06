package com.desblocadosuepb.uepbstudentmap.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.List;

/**
 * Created by Eric on 05/03/2017.
 */

//TODO Documentar

public class DisciplinaAdapter extends ArrayAdapter<DisciplinaVO>{

    private Context context;
    private List<DisciplinaVO> values;

    public DisciplinaAdapter(Context context, List<DisciplinaVO> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DisciplinaVO disciplina = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_horario_list, parent, false);

        TextView rowCpt = (TextView) rowView.findViewById(R.id.row_cpt);
        rowCpt.setText(disciplina.getCpt());

        TextView rowNome = (TextView) rowView.findViewById(R.id.row_nome);
        rowNome.setText(disciplina.getNome());

        TextView rowCurso = (TextView) rowView.findViewById(R.id.row_curso);
        rowCurso.setText(disciplina.getCurso());

        TextView rowPeriodo = (TextView) rowView.findViewById(R.id.row_periodo);
        rowPeriodo.setText(Integer.toString(disciplina.getPeriodo()));

        TextView rowProfessor = (TextView) rowView.findViewById(R.id.row_professor);
        rowProfessor.setText(disciplina.getProfessor());

        return rowView;
    }
}
