package com.desblocadosuepb.uepbstudentmap.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.model.AulaHorarioVO;

import java.util.List;

/**
 * Esta classe é um ArrayAdapter usado pela classe AulaAdapter
 * para definir e preencher um layout personalizado para cada item
 * da sua ListView interna.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see android.widget.ArrayAdapter
 * @see com.desblocadosuepb.uepbstudentmap.adapters.AulaAdapter
 */
public class HorarioAdapter extends ArrayAdapter<AulaHorarioVO> {

    private Context context;
    private List<AulaHorarioVO> values;

    /**
     * Construtor da classe HorarioAdapter
     *
     * @param context O contexto onde o layout será inflado.
     * @param values  O Array de AulaHorarioVO para preencher os campos relacionados na ListView.
     */
    public HorarioAdapter(Context context, List<AulaHorarioVO> values){
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        AulaHorarioVO horario = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowAulaView = inflater.inflate(R.layout.row_aula_list, parent, false);

        TextView rowHorario = (TextView) rowAulaView.findViewById(R.id.row_horario);
        rowHorario.setText(String.format("%s %s - Sala: %s",
                horario.getDiaSemana(), horario.getHora(), horario.getSala()));

        return rowAulaView;
    }

}
