package com.desblocadosuepb.uepbstudentmap.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.desblocadosuepb.uepbstudentmap.adapters.AulaAdapter;
import com.desblocadosuepb.uepbstudentmap.dao.AulaDAO;
import com.desblocadosuepb.uepbstudentmap.dao.DisciplinaDAO;
import com.desblocadosuepb.uepbstudentmap.model.AulaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é uma ListActivity usada para mostrar a listagem
 * das Disciplinas. Ela é iniciada na classe DisciplinaAdapter.
 *
 * @author Eric
 * @version 1
 * @since Release 01 da aplicação
 * @see android.app.ListActivity
 * @see com.desblocadosuepb.uepbstudentmap.adapters.DisciplinaAdapter
 */

public class DetalhesActivity extends ListActivity {

    /**
     * A constante EXTRA_CPT.
     *
     * É usada para garantir que DetalhesActivity
     * e qualquer classe que tiver a intenção de iniciar esta acvitity façam
     * uso da mesma String para passar conteúdo através de uma Intent.
     */
    public static final String EXTRA_CPT = "codigo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getIntegerArrayListExtra("arrayExtra")!= null) {
            int rdmId = (Integer) intent.getExtras().get("rdmId");
            ArrayList<Integer> listaAulaId = intent.getIntegerArrayListExtra("arrayExtra");
            List<AulaVO> listaAulasVO = new ArrayList<>();
            for (Integer aulaId : listaAulaId){
                listaAulasVO.add(new AulaDAO(this).getAula(aulaId));
            }

            setListAdapter(new AulaAdapter(this, listaAulasVO, "remover", rdmId));
        }else {
            String codigo = intent.getStringExtra(EXTRA_CPT);

            setListAdapter(new AulaAdapter(this, new DisciplinaDAO(this).getDisciplina(codigo),
                    new AulaDAO(this).list(codigo)));
        }
    }
}
