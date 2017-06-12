package com.desblocadosuepb.uepbstudentmap.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.R;
import com.desblocadosuepb.uepbstudentmap.dao.RDMDAO;
import com.desblocadosuepb.uepbstudentmap.model.RDMVO;

/**
 * Esta classe é um DialogFragment que é aparece
 * quando o usuário desejar criar um novo RDM.
 *
 * @author Eric
 * @version 1.0
 * @see android.support.v4.app.DialogFragment
 * @see MapaFragment
 * @since release 2
 */
public class NovoRDMDialogFragment extends DialogFragment {

    /**
     * Construtor vazio (default) de NovoRDMDialogFragment.
     */
    public NovoRDMDialogFragment() {
        // Required empty public constructor
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Cria um novo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.label_add_rdm); //Define o título
        builder.setView(R.layout.fragment_novo_rdmdialog); //Define o layout

        //Define o que acontede quando o botão ADICIONAR do dialog é clicado
        builder.setPositiveButton(R.string.adicionar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Spinner cursos = (Spinner) getDialog().findViewById(R.id.new_rdm_spinner);
                EditText nome = (EditText) getDialog().findViewById(R.id.new_rdm_name);

                //Recupera o curso e o nome do novo RDM
                String rdmNome = nome.getText().toString();
                String rdmCurso = String.valueOf(cursos.getSelectedItem());

                //Cria o novo RDMVO
                RDMVO  rdmvo = new RDMVO();
                rdmvo.setNome(rdmNome);
                rdmvo.setCurso(rdmCurso);

                //Insere o novo RDM na base de dados
                RDMDAO dao = new RDMDAO(getContext());
                dao.insert(rdmvo);

                //Mostra uma mensagem na tela confirmando a criação do novo RDM
                Toast.makeText(getContext(), "Novo horário criado.", Toast.LENGTH_SHORT).show();
                dismiss(); //Dispensa o dialog depois da operação
            }
        });

        //Define o que acontece quando o botão CANCELAR é clicado
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss(); //Dispensa o
            }
        });

        return builder.create();
    }
}
