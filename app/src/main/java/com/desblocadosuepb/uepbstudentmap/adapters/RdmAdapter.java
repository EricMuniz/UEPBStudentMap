package com.desblocadosuepb.uepbstudentmap.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.model.RDMVO;

import java.util.List;

/**
 * Esta classe é um RecyclerView.Adapter usado pelas classes
 * RDMListFragment e RDMListActivity para definir e
 * preencher um layout personalizado para cada item
 * das suas RecyclerView's.
 * <p>
 * Este adapter substitui a classe RDMListAdapter que havia
 * antes no projeto.
 *
 * @author Eric
 * @version 1
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @see com.desblocadosuepb.uepbstudentmap.fragments.RDMListFragment
 * @see com.desblocadosuepb.uepbstudentmap.activities.RDMListActivity
 * @since release 2
 */
public class RdmAdapter extends RecyclerView.Adapter<RdmAdapter.ViewHolder> {

    private List<RDMVO> values;
    private Listener listener;

    /**
     * Construtor da classe RdmAdapter.
     *
     * @param values lista com os VO's
     */
    public RdmAdapter(List<RDMVO> values){
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
         *  Construtor da classe interna ViewHolder
         *
         * @param cardView O CardView
         */
        ViewHolder(CardView cardView){
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
    public RdmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Cria uma nova View
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_rdm, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Recupera o VO a partir da posição do item na RecyclerView
        RDMVO rdm = values.get(position);

        //Recupera a referênci do CardView, é a partir deste objeto que o layout é inflado
        CardView cardView = holder.cardView;

        //Define o valor do campo do nome do rdm
        TextView rowListHorario = (TextView) cardView.findViewById(R.id.card_view_rdm_nome);
        rowListHorario.setText(String.format("Nome: %s", rdm.getNome()));

        //Define o valor do campo do curso do rdm
        TextView rowListCurso = (TextView) cardView.findViewById(R.id.card_view_rdm_curso);
        rowListCurso.setText(String.format("Cusro: %s", rdm.getCurso()));

        //Define o valor do campo da quantidade de aulas do rdm
        TextView rowListAulaQuant = (TextView) cardView.findViewById(R.id.card_view_rdm_quant_aulas);
        rowListAulaQuant.setText(String.format("Contêm %s disciplinas",
                new CompoeDAO(cardView.getContext()).countDisc(rdm.getId())));

        //Define o listener do CardView
        cardView.setOnClickListener(new View.OnClickListener() {
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
