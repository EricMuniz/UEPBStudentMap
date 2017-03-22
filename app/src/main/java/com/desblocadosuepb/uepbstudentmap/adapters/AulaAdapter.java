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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.activities.RDMListActivity;
import com.desblocadosuepb.uepbstudentmap.dao.AulaHorarioDAO;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;
import com.desblocadosuepb.uepbstudentmap.model.AulaVO;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.List;

/**
 * Esta classe é um ArrayAdapter usado pela classe DetalhesActivity
 * para definir e preencher um layout personalizado para cada item
 * da sua ListView.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see android.widget.ArrayAdapter
 * @see com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity
 */
public class AulaAdapter extends ArrayAdapter<AulaVO> {

    private Context context;
    private DisciplinaVO disciplina;
    private List<AulaVO> values;
    private String acao = "detalhes";
    private int rdmId = 0;

    /**
     * Construtor da classe AulaAdapter
     *
     * @param context    O contexto onde o layout será inflado.
     * @param disciplina A disciplina usada para preencher os campos relacionados na ListView.
     * @param values     O Array de AulaVO para preencher os campos relacionados na ListView.
     */
    public AulaAdapter(Context context, DisciplinaVO disciplina, List<AulaVO> values){
        super(context, -1, values);
        this.context = context;
        this.disciplina = disciplina;
        this.values = values;
    }

    public AulaAdapter(Context context, List<AulaVO> values, String acao, int rdmId){
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.acao = acao;
        this.rdmId = rdmId;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        final AulaVO aula = values.get(position);
        disciplina = new DisciplinaDAO(context).getDisciplina(aula.getDiscCodigo());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.activity_detalhes, parent, false);

        TextView rowNome = (TextView) rowView.findViewById(R.id.row_Dnome);
        rowNome.setText(disciplina.getNome());

        TextView rowCodigo = (TextView) rowView.findViewById(R.id.row_Dcodigo);
        rowCodigo.setText(disciplina.getCodigo());

        TextView rowCurso = (TextView) rowView.findViewById(R.id.row_Dcurso);
        rowCurso.setText(String.format("Curso: %s", disciplina.getCurso()));

        TextView rowPeriodo = (TextView) rowView.findViewById(R.id.row_Dperiodo);
        rowPeriodo.setText(String.format("Período: %s", disciplina.getPeriodo()));

        TextView rowTurno = (TextView) rowView.findViewById(R.id.row_Dturno);
        rowTurno.setText(String.format("Turno: %s", aula.getTurno()));

        TextView rowProfessor = (TextView) rowView.findViewById(R.id.row_Dprofessor);
        rowProfessor.setText(String.format("Professor: %s", aula.getProfessor()));

        ListView rowListAula = (ListView) rowView.findViewById(R.id.row_list_aulas);
        rowListAula.setDividerHeight(0);
        rowListAula.setAdapter(new HorarioAdapter(context, new AulaHorarioDAO(context).list(aula.getId())));

        Button rowAdicionar = (Button) rowView.findViewById(R.id.row_Dadicionar);
        if(acao.equals("remover")) {
            rowAdicionar.setText("REMOVER");
            rowAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                    dialog.setTitle("Atenção");
                    dialog.setMessage("Remover deste horário?");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CompoeDAO dao = new CompoeDAO(context);
                            if(dao.delete(rdmId, aula.getId()))
                                Toast.makeText(context, "Removido com sucesso", Toast.LENGTH_SHORT).show();
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
        }else{
            rowAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), RDMListActivity.class);
                    intent.putExtra(RDMListActivity.EXTRA_AULA_ID, aula.getId());
                    intent.putExtra(RDMListActivity.EXTRA_CODIGO, aula.getDiscCodigo());
                    intent.putExtra(RDMListActivity.EXTRA_CURSO, disciplina.getCurso());
                    context.startActivity(intent);
                }
            });
        }
        return rowView;
    }
}
