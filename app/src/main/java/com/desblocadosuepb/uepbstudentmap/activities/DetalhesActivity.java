package com.desblocadosuepb.uepbstudentmap.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.adapters.AulaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.AulaDAO;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;
import com.desblocadosuepb.uepbstudentmap.model.AulaVO;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é uma Activity usada para mostrar a listagem
 * das Disciplinas. Ela é iniciada nas classes RDMListFragment e GradeCursoFragment.
 *
 * @author Eric
 * @version 2
 * @see android.support.v7.app.AppCompatActivity
 * @see com.desblocadosuepb.uepbstudentmap.fragments.RDMListFragment
 * @see com.desblocadosuepb.uepbstudentmap.fragments.GradeCursoFragment
 * @since Release 01 da aplicação
 */
public class DetalhesActivity extends AppCompatActivity {

    /**
     * A constante EXTRA_CPT.
     * <p>
     * É usada para garantir que DetalhesActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_CPT = "codigo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        /*  Esta Activity pode ser usada de duas formas, com uma leve alteração
        *   no layout dos itens de sua lista:
        *
        *   1 - Se ela for iniciada a partir da classe RDMListFragment, no layout
        *   de cada item da lista (RecyclerView) a opção será de Remover a aula do RDM.
        *   Nesse caso, a activity foi inciada com um Array de ID's(Int) que é usado para
        *   recuperar as aulas de cada ID e criar uma List que é passada para o adapter
        *   do RecyclerView e com o ID do RDM da qual a aula será removida.
        * */

        //Recupera o conteúdo da Intent que tiver iniciado esta Activity
        Intent intent = getIntent();

        if(intent.getIntegerArrayListExtra("arrayExtra")!= null) {
            //Recupera o ID do RDM e o Array de ID's das aulas do mesmo
            final int rdmId = (Integer) intent.getExtras().get("rdmId");
            ArrayList<Integer> listaAulaId = intent.getIntegerArrayListExtra("arrayExtra");
            //Cria uma lista de AulaVO que em seguida é preenchida recuperando as aulas com os ID's
            final List<AulaVO> listaAulasVO = new ArrayList<>();
            for (Integer aulaId : listaAulaId){
                listaAulasVO.add(new AulaDAO(this).getAula(aulaId));
            }

            //Recupera a lista (RecyclerView) do layout
            RecyclerView aulaRecycler = (RecyclerView) findViewById(R.id.aula_detalhes_recyclerview);
            //Cria e define o adaptador da lista junto com seu tipo de layout
            AulaAdapter adapter = new AulaAdapter(listaAulasVO, true);
            aulaRecycler.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            aulaRecycler.setLayoutManager(layoutManager);

            //Define o que acontece quando o botão Remover é clicado
            adapter.setListener(new AulaAdapter.Listener() {
                @Override
                public void onClick(final int position) {
                    /*Cria uma novo diálogo de alerta para que o usuário possa
                      confimar que deseja realizar esta ação */
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetalhesActivity.this);
                    dialog.setTitle("Atenção");
                    dialog.setMessage("Remover disciplina?");
                    dialog.setIcon(android.R.drawable.ic_dialog_alert);

                    //Se o usuário confirmar a operação, a aula é deletada do RDM
                    dialog.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CompoeDAO dao = new CompoeDAO(getApplicationContext());
                            if(dao.delete(rdmId, listaAulasVO.get(position).getId())){
                                Toast.makeText(DetalhesActivity.this, "Removido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //Fecha o dialógo se o usuário cancelar a operação
                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    //Mostra o diálogo na tela quando o botão Remover é clicado
                    dialog.show();
                }
            });
        }else {
            /*  2 - Se a activity for iniciada a partir da classe GradeCursoFragment,
            *   no layout de cada item da lista (RecyclerView) a opção do botão será de
            *   Adicionar a aula em questão em um RDM existente.
            *   Em seguida uma nova activity é iniciada, responsável por tratar da seleção
            *   do RDM.
            * */

            /*Recupera o código da disciplina a cuja aula pertence, é passado para esta
              activity por meio de uma Intent na classe GradeCursoFragment */
            final String codigo = intent.getStringExtra(EXTRA_CPT);

            //Recupera a lista (RecyclerView) do layout
            RecyclerView aulaRecycler = (RecyclerView) findViewById(R.id.aula_detalhes_recyclerview);
            //Cria e define o adaptador da lista junto com seu tipo de layout
            AulaAdapter adapter = new AulaAdapter(new AulaDAO(getApplicationContext()).list(codigo), false);
            aulaRecycler.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            aulaRecycler.setLayoutManager(layoutManager);

            //Define o que acontece quando o botão Adicionar é clicado
            adapter.setListener(new AulaAdapter.Listener() {
                @Override
                public void onClick(int position) {

                    //Recupera os dados da disciplina e da aula que serão adicionados
                    AulaVO aula = new AulaDAO(getApplicationContext()).list(codigo).get(position);
                    DisciplinaVO disciplina = new DisciplinaDAO(getApplicationContext()).getDisciplina(aula.getDiscCodigo());

                    //Cria a intent que vai iniciar RDMListActivity
                    Intent intent = new Intent(DetalhesActivity.this, RDMListActivity.class);
                    //Define o conteúdo a ser passado na intent
                    intent.putExtra(RDMListActivity.EXTRA_AULA_ID, aula.getId());
                    intent.putExtra(RDMListActivity.EXTRA_CODIGO, aula.getDiscCodigo());
                    intent.putExtra(RDMListActivity.EXTRA_CURSO, disciplina.getCurso());
                    //Inicia RDMListActivity
                    startActivity(intent);
                }
            });
        }
    }
}
