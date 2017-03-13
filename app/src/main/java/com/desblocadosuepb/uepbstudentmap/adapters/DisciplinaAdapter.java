package com.desblocadosuepb.uepbstudentmap.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity;
import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.List;

/**
 * Esta classe é um ArrayAdapter usado pela classe GradeCursoFragment
 * para definir e preencher um layout personalizado para cada item
 * da sua ListView.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see android.widget.ArrayAdapter
 * @see com.desblocadosuepb.uepbstudentmap.fragments.GradeCursoFragment
 */
public class DisciplinaAdapter extends ArrayAdapter<DisciplinaVO>{

    private Context context;
    private List<DisciplinaVO> values;

    /**
     * Construtor da classe DisciplinaAdapter.
     *
     * @param context O contexto onde o layout será inflado.
     * @param values  O Array de DisciplinaVO para preencher os campos relacionados na ListView.
     */
    public DisciplinaAdapter(Context context, List<DisciplinaVO> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    @NonNull
    public View getView(int position, final View convertView, @NonNull ViewGroup parent) {

        final DisciplinaVO disciplina = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.row_disciplina_list, parent, false);

        TextView rowCpt = (TextView) rowView.findViewById(R.id.row_codigo);
        rowCpt.setText(disciplina.getCodigo());

        TextView rowNome = (TextView) rowView.findViewById(R.id.row_nome);
        rowNome.setText(disciplina.getNome());

        TextView rowCurso = (TextView) rowView.findViewById(R.id.row_curso);
        rowCurso.setText(String.format("Curso: %s", disciplina.getCurso()));

        TextView rowPeriodo = (TextView) rowView.findViewById(R.id.row_periodo);
        rowPeriodo.setText(String.format("Período: %s", Integer.toString(disciplina.getPeriodo())));

        Button rowDetalhes = (Button) rowView.findViewById(R.id.row_detalhes);
        rowDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetalhesActivity.class);
                intent.putExtra(DetalhesActivity.EXTRA_CPT, disciplina.getCodigo());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
