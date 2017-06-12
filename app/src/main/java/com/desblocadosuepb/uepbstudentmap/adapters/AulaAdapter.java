package com.desblocadosuepb.uepbstudentmap.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.dao.AulaHorarioDAO;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;
import com.desblocadosuepb.uepbstudentmap.model.AulaVO;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.List;

/**
 * Esta classe é um RecyclerView.Adapter usado pela classe DetalhesActivity
 * para definir e preencher um layout personalizado para cada item
 * da sua RecyclerView.
 *
 * @author Eric
 * @version 2
 * @see com.desblocadosuepb.uepbstudentmap.activities.DetalhesActivity
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @since Release 01
 */
public class AulaAdapter extends RecyclerView.Adapter<AulaAdapter.ViewHolder> {

    private List<AulaVO> values;
    private Listener listener;
    private boolean remover;

    /**
     * Construtor da classe AulaAdapter
     *
     * @param values  lista com VO's
     * @param remover flag que indica que o botão do adapter deve ter o valor "REMOVER"
     */
    public AulaAdapter(List<AulaVO> values, boolean remover){
        this.values = values;
        this.remover = remover;
    }

    /**
     * A interface Listener.
     *
     * Usada para que o Listener do adaptador
     * seja definido pela classe que o instancia.
     */
    public interface Listener{
        /**
         * Método que define o que o item clicado
         * faz quando é clicado.
         *
         * @param position A posição do item clicado na RecyclerView
         */
        void onClick(int position);
    }

    /**
     * Classe interna que extende a ViewHolder padrão.
     * Quando instanciada, cria um novo CardView que
     * representa um item da RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        /**
         * Construtor da classe interna ViewHolder
         *
         * @param cardView O CardView
         */
        ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    /**
     * Define o Listener.
     *
     * @param listener O listener que vem da classe que implementar este adaptador
     */
    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public AulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Cria uma nova View
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_aula, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(AulaAdapter.ViewHolder holder, final int position) {
        //Recupera os VO's a partir da posição do item na RecyclerView
        AulaVO aula = values.get(position);
        DisciplinaVO disciplina = new DisciplinaDAO(holder.cardView.getContext()).getDisciplina(aula.getDiscCodigo());

        //Recupera a referênci do CardView, é a partir deste objeto que o layout é inflado
        CardView cardView = holder.cardView;

        //Define o valor do campo do nome da disciplina
        TextView rowDnome = (TextView) cardView.findViewById(R.id.card_view_aula_nome_disciplina);
        rowDnome.setText(disciplina.getNome());

        //Define o valor do campo do código da disciplina
        TextView rowDcodigo = (TextView) cardView.findViewById(R.id.card_view_aula_codigo_disciplina);
        rowDcodigo.setText(disciplina.getCodigo());

        //Define o valor do campo do curso da disciplina
        TextView rowDcurso = (TextView) cardView.findViewById(R.id.card_view_aula_curso_disciplina);
        rowDcurso.setText(String.format("Curso: %s", disciplina.getCurso()));

        //Define o valor do campo do período da disciplina
        TextView rowDperiodo = (TextView) cardView.findViewById(R.id.card_view_aula_periodo_disciplina);
        rowDperiodo.setText(String.format("Período: %s", disciplina.getPeriodo()));

        //Define o valor do campo do turno da aula
        TextView rowDturno = (TextView) cardView.findViewById(R.id.card_view_aula_turno);
        rowDturno.setText(String.format("Turno: %s", aula.getTurno()));

        //Defne o valor do campo do professor da aula
        TextView rowDprofessor = (TextView) cardView.findViewById(R.id.card_view_aula_professor);
        rowDprofessor.setText(String.format("Professor: %s", aula.getProfessor()));

        //Define os valores do campo dos horários da aula
        ListView rowListAula = (ListView) cardView.findViewById(R.id.card_view_aula_lista_horarios);
        rowListAula.setDividerHeight(0);
        rowListAula.setAdapter(new HorarioAdapter(cardView.getContext(),
                new AulaHorarioDAO(cardView.getContext()).list(aula.getId())));

        //Define o listener do botão. Troca o texto do botão se necessário
        Button rowButton = (Button) cardView.findViewById(R.id.card_view_aula_botao);
        if(remover)
            rowButton.setText(R.string.remover);
        rowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}