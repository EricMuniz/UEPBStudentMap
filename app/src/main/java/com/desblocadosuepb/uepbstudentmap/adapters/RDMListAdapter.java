package com.desblocadosuepb.uepbstudentmap.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity;
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
 * @see android.widget.ArrayAdapter
 * @see com.desblocadosuepb.uepbstudentmap.activities.RDMListActivity
 * @since Release 01
 * @deprecated
 */
public class RDMListAdapter extends ArrayAdapter<RDMVO> {

    private Context context;
    private List<RDMVO> values;
    private int aulaId;
    private String curso;
    private String discCodigo;
    private String acao = "adicionar";

    /**
     * Construtor da classe RDMListAdapter
     *
     * @param context    O contexto onde o layout será inflado.
     * @param values     O Array de RDMVO para preencher os campos relacionados na ListView.
     * @param aulaId     O ID da AulaVO que será adicionado no banco.
     * @param curso      O curso da disciplina que se quer inserir no horário.
     * @param discCodigo O código da disciplina para saber se ela já existe no horário
     */
    public RDMListAdapter(Context context, List<RDMVO> values,
                          int aulaId, String curso, String discCodigo){
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.aulaId = aulaId;
        this.curso = curso;
        this.discCodigo = discCodigo;
    }

    /**
     * Instantiates a new Rdm list adapter.
     *
     * @param context the context
     * @param values  the values
     */
    public RDMListAdapter(Context context, List<RDMVO> values, String acao){
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.acao = acao;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        final RDMVO horario = values.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.card_view_rdm, parent, false);

        TextView rowListHorario = (TextView) rowView.findViewById(R.id.card_view_rdm_nome);
        rowListHorario.setText(String.format("Nome: %s", horario.getNome()));

        TextView rowListCurso = (TextView) rowView.findViewById(R.id.card_view_rdm_curso);
        rowListCurso.setText(String.format("Curso: %s", horario.getCurso()));

        TextView rowListAulaQuant = (TextView) rowView.findViewById(R.id.card_view_rdm_quant_aulas);
        rowListAulaQuant.setText(String.format("Contêm %s disciplina(s)",
                new CompoeDAO(getContext()).countDisc(horario.getId())));

        if(acao.equals("listar")){
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetalhesActivity.class);
                    intent.putExtra("rdmId", horario.getId());
                    intent.putIntegerArrayListExtra("arrayExtra", new CompoeDAO(context).getListAulaId(horario.getId()));
                    context.startActivity(intent);
                }
            });
        }else{
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                    dialog.setTitle("Atenção");
                    dialog.setMessage("Adicionar neste horário?");
                    dialog.setIcon(android.R.drawable.ic_dialog_alert);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (horario.getCurso().equals(curso)) {
                                CompoeDAO dao = new CompoeDAO(getContext());
                                if (!dao.exist(horario.getId(), discCodigo)) {
                                    dao.insert(horario.getId(), aulaId);
                                    Toast.makeText(context, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Esta disciplina já foi adicionada" +
                                            " neste horário", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(),
                                        String.format("Impossível adicionar uma disciplina de %s\n" +
                                                        "em um horário de %s",
                                                curso, horario.getCurso()),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    dialog.show();
                }
            });
        } 

        return rowView;
    }
}
