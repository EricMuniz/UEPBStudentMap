package com.desblocadosuepb.uepbstudentmap.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.model.RDMVO;

import java.util.List;

/**
 * Esta classe é um ArrayAdapter usado pela classe RDMListActivity
 * para definir e preencher um layout personalizado para cada item
 * da sua ListView.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see android.widget.ArrayAdapter
 * @see com.desblocadosuepb.uepbstudentmap.activities.RDMListActivity
 */
public class RDMListAdapter extends ArrayAdapter<RDMVO> {

    private Context context;
    private List<RDMVO> values;
    private int aulaId;

    /**
     * Construtor da classe RDMListAdapter
     *
     * @param context O contexto onde o layout será inflado.
     * @param values  O Array de RDMVO para preencher os campos relacionados na ListView.
     * @param aulaId  O ID da AulaVO que será adicionado no banco.
     */
    public RDMListAdapter(Context context, List<RDMVO> values, int aulaId){
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.aulaId = aulaId;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        final RDMVO horario = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.activity_rdm_list, parent, false);

        TextView rowListHorario = (TextView) rowView.findViewById(R.id.row_list_rdm);
        rowListHorario.setText(String.format("Nome: %s", horario.getNome()));
        rowListHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

                dialog.setTitle("Adicionar neste horário?");
                dialog.setIcon(android.R.drawable.ic_dialog_alert);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CompoeDAO dao = new CompoeDAO(getContext());
                        dao.insert(horario.getId(), aulaId);
                    }
                });

                dialog.show();
            }
        });


        return rowListHorario;
    }
}
