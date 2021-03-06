package com.desblocadosuepb.uepbstudentmap.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.List;

/**
 * Esta classe é um RecyclerView.Adapter usado pela classe GradeCursoFragment
 * para definir e preencher um layout personalizado para cada item
 * da sua ListView.
 *
 * @author Eric
 * @version 2
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @see com.desblocadosuepb.uepbstudentmap.fragments.GradeCursoFragment
 * @since Release 01
 */
public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.ViewHolder>{

    private List<DisciplinaVO> values;
    private Listener listener;

    /**
     * Construtor da classe DisciplinaAdapter.
     *
     * @param values lista com VO's
     */
    public DisciplinaAdapter(List<DisciplinaVO> values){
        this.values = values;
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
    static class ViewHolder extends RecyclerView.ViewHolder{
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
    public DisciplinaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Cria uma nova View
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_disciplina, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(DisciplinaAdapter.ViewHolder holder, final int position) {
        //Recupera o VO a partir da posição do item na RecyclerView
        DisciplinaVO disciplina = values.get(position);

        //Recupera a referênci do CardView, é a partir deste objeto que o layout é inflado
        CardView cardView = holder.cardView;

        //Define o valor do campo do nome da disciplina
        TextView rowNome = (TextView) cardView.findViewById(R.id.card_view_disciplina_nome);
        rowNome.setText(disciplina.getNome());

        //Define o valor do campo do código da disciplina
        TextView rowCodigo = (TextView) cardView.findViewById(R.id.card_view_disciplina_codigo);;
        rowCodigo.setText(disciplina.getCodigo());

        //Define o valor do campo do curso da disciplina
        TextView rowCurso = (TextView) cardView.findViewById(R.id.card_view_disciplina_curso);
        rowCurso.setText(String.format("Curso: %s", disciplina.getCurso()));

        //Define o valor do campo do período da disciplina
        TextView rowPeriodo = (TextView) cardView.findViewById(R.id.card_view_disciplina_periodo);
        rowPeriodo.setText(String.format("Período: %s", disciplina.getPeriodo()));

        //Define o listener do botão
        Button rowDetalhes = (Button) cardView.findViewById(R.id.card_view_disciplina_botao_detalhes);
        rowDetalhes.setOnClickListener(new View.OnClickListener() {
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
