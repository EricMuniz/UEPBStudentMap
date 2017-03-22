package com.desblocadosuepb.uepbstudentmap.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.desblocadosuepb.uepbstudentmap.adapters.RDMListAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;

/**
 * Esta classe é uma ListActivity usada para mostrar a listagem
 * dos Horários Pessoais (RDM's). Ela é iniciada na classe AulaAdapter.
 *
 * @author Eric
 * @version 1
 * @see android.app.ListActivity
 * @see com.desblocadosuepb.uepbstudentmap.adapters.AulaAdapter
 * @since Release 01 da aplicação
 */
public class RDMListActivity extends ListActivity {

    /**
     * A constante EXTRA_AULA_ID.
     * <p>
     * É usada para garantir que RDMListActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_AULA_ID = "aulaId";
    /**
     * The constant EXTRA_CURSO.
     */
    public static final String EXTRA_CURSO = "curso";

    public static final String EXTRA_CODIGO = "codigo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int aulaId = (Integer) getIntent().getExtras().get(EXTRA_AULA_ID);
        String codigo = getIntent().getStringExtra(EXTRA_CODIGO);
        String curso = getIntent().getStringExtra(EXTRA_CURSO);

        setListAdapter(new RDMListAdapter(this, new RDMDAO(this).list(), aulaId, curso, codigo));
    }
}
