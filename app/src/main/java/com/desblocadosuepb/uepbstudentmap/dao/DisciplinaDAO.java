package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 06/03/2017.
 */

//TODO Aperfeiçoar e Documentar

public class DisciplinaDAO {

    private Context context;
    static final String TABLENAME = "DISCIPLINA";
    static final String[] TABLECOLLUMNS = new String[]{"_id", "CPT", "NOME", "CURSO", "PERIODO", "PROFESSOR"};

    public DisciplinaDAO(Context context) {
        this.context = context;
    }

    public boolean insert(DisciplinaVO disciplina) {
        SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLECOLLUMNS[1], disciplina.getCpt());
        values.put(TABLECOLLUMNS[2], disciplina.getNome());
        values.put(TABLECOLLUMNS[3], disciplina.getCurso());
        values.put(TABLECOLLUMNS[4], disciplina.getPeriodo());
        values.put(TABLECOLLUMNS[5], disciplina.getProfessor());

        return (database.insert(TABLENAME, null, values) > 1);
    }

    public List<DisciplinaVO> list() {

        List<DisciplinaVO> list = new ArrayList<>();

        SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
        Cursor cursor = database.query(TABLENAME, TABLECOLLUMNS, null, null, null, null, null);

        while (cursor.moveToNext()) {
            DisciplinaVO disciplina = new DisciplinaVO();
            disciplina.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[0])));
            disciplina.setCpt(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[1])));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[2])));
            disciplina.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[3])));
            disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[4])));
            disciplina.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[5])));

            list.add(disciplina);
        }

        cursor.close();
        database.close();

        return list;
    }

    public List<DisciplinaVO> list(String curso) {

        List<DisciplinaVO> list = new ArrayList<>();

        SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
        Cursor cursor = database.query(TABLENAME, TABLECOLLUMNS,
                "CURSO = ?",
                new String[]{curso},
                null, null, null);

        while (cursor.moveToNext()) {
            DisciplinaVO disciplina = new DisciplinaVO();
            disciplina.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[0])));
            disciplina.setCpt(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[1])));
            disciplina.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[2])));
            disciplina.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[3])));
            disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[4])));
            disciplina.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[5])));

            list.add(disciplina);
        }

        cursor.close();
        database.close();

        return list;
    }
}
