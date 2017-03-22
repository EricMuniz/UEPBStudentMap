package com.desblocadosuepb.uepbstudentmap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;

public class NovoRDMActivity extends AppCompatActivity {

    private TextView rdmNome;
    private Spinner cursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_rdm);

        rdmNome = (TextView) findViewById(R.id.novo_rdm_nome);
        cursos = (Spinner) findViewById(R.id.novo_rdm_spinner);
    }

    public void onClickAddRDM(View view) {
        String nome = rdmNome.getText().toString();
        String curso = cursos.getSelectedItem().toString();

        RDMDAO dao = new RDMDAO(this);
        dao.insert(nome, curso);
        Toast.makeText(this, "Novo horário criado", Toast.LENGTH_SHORT).show();
        finish();
    }
}
