package com.desblocadosuepb.uepbstudentmap.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.adapters.RdmAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.CompoeDAO;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;
import com.desblocadosuepb.uepbstudentmap.model.RDMVO;

/**
 * Esta classe é uma ListActivity usada para mostrar a listagem
 * dos Horários Pessoais (RDM's). Ela é iniciada na classe AulaAdapter.
 *
 * @author Eric
 * @version 2
 * @see android.app.ListActivity
 * @see com.desblocadosuepb.uepbstudentmap.adapters.AulaAdapter
 * @since Release 01 da aplicação
 */
public class RDMListActivity extends AppCompatActivity {

    /**
     * A constante EXTRA_AULA_ID.
     * <p>
     * É usada para garantir que RDMListActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_AULA_ID = "aulaId";
    /**
     * A constante EXTRA_CURSO.
     * <p>
     * É usada para garantir que RDMListActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_CURSO = "curso";
    /**
     * A constante EXTRA_CODIGO.
     * <p>
     * É usada para garantir que RDMListActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_CODIGO = "codigo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rdm_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ToDo: ver por que isso não funciona

        //Recupera a lista (RecyclerView) do layout
        RecyclerView rdmRecycler = (RecyclerView) findViewById(R.id.rdm_recycler);

        //Recura o conteúdo da Intent que iniciou a activity
        final int aulaId = (Integer) getIntent().getExtras().get(EXTRA_AULA_ID);
        final String codigo = getIntent().getStringExtra(EXTRA_CODIGO);
        final String curso = getIntent().getStringExtra(EXTRA_CURSO);

        //Cria e define o adaptador da lista junto com seu tipo de layout
        RdmAdapter adapter = new RdmAdapter(new RDMDAO(this).list());
        rdmRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rdmRecycler.setLayoutManager(layoutManager);

        //Define o que acontece quando o botão Remover é clicado
        adapter.setListener(new RdmAdapter.Listener() {
            @Override
            public void onClick(int position) {

                //Instancia o RDMVO respectivo ao item do layout que foi clicado
                final RDMVO horario = new RDMDAO(RDMListActivity.this).list().get(position);

                /*Cria uma novo diálogo de alerta para que o usuário possa
                  confimar que deseja realizar esta ação */
                AlertDialog.Builder dialog = new AlertDialog.Builder(RDMListActivity.this);
                dialog.setTitle("Atenção");
                dialog.setMessage("Adicionar neste horário?");
                dialog.setIcon(android.R.drawable.ic_dialog_alert);

                //Se o usuário confirmar a operação, adiciona a aula ao RDM
                dialog.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(horario.getCurso().equals(curso)){
                            CompoeDAO dao = new CompoeDAO(RDMListActivity.this);
                            if(!dao.exist(horario.getId(), codigo)) {
                                dao.insert(horario.getId(), aulaId);
                                Toast.makeText(RDMListActivity.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RDMListActivity.this, "Esta disciplina já foi" +
                                        " adicionada neste horário", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RDMListActivity.this,
                                    String.format("Não é possível adicionar uma disciplina de " +
                                            "%s em um horário de %s", curso, horario.getCurso()), Toast.LENGTH_SHORT).show();
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
    }
}
