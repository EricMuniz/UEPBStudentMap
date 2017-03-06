package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.desblocadosuepb.uepbstudentmap.MainActivity;

/**
 * Created by Eric on 06/03/2017.
 */

public class StudentMapDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "studentmap";
    private static final int DB_VERSION = 1;

    public StudentMapDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DISCIPLINA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CPT TEXT, " +
                "NOME TEXT, " +
                "CURSO TEXT, " +
                "PERIODO INTEGER, " +
                "PROFESSOR TEXT);");

        //TODO Criar tabelas faltantes na base de dados

        populateTableDiscipline(db, "CPT07102",
                "Paradigmas de Linguagens de Programação",
                "Computação",
                4,
                "Jefferson Felipe Silva de Lima");
        populateTableDiscipline(db, "CPT07113",
                "Banco de Dados II",
                "Computação",
                6,
                "Rodrigo Alves Costa");
        populateTableDiscipline(db, "CPT07116",
                "Laboratório de Engenharia de Software I",
                "Computação",
                6,
                "Pablo Ribeiro Suárez");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Adicionar código para atualização da base de dados
    }


    private static void populateTableDiscipline(SQLiteDatabase database
            ,String cpt, String nome, String curso, int periodo, String professor){

        ContentValues disciplineValues = new ContentValues();

        disciplineValues.put(DisciplinaDAO.TABLECOLLUMNS[1], cpt);
        disciplineValues.put(DisciplinaDAO.TABLECOLLUMNS[2], nome);
        disciplineValues.put(DisciplinaDAO.TABLECOLLUMNS[3], curso);
        disciplineValues.put(DisciplinaDAO.TABLECOLLUMNS[4], periodo);
        disciplineValues.put(DisciplinaDAO.TABLECOLLUMNS[5], professor);

        database.insert(DisciplinaDAO.TABLENAME, null, disciplineValues);
    }
}
