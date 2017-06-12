package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO do Value Object Disciplina.
 *
 * @author Eric
 * @version 1.1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO
 */
//TODO Aperfeiçoar e Documentar

public class DisciplinaDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    private static final String TABLENAME = "DISCIPLINA";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    private static final String[] TABLECOLLUMNS = new String[]{"_id", "CODIGO", "NOME", "CURSO", "PERIODO"};

    /**
     * Construtor da classe DisciplinaDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public DisciplinaDAO(Context context) {
        this.context = context;
    }

    /**
     * Método para inserir uma nova disciplina no banco de dados.
     * O argumento disciplina especifica uma disciplina do tipo
     * DisciplinaVO.
     *
     * @param disciplina    A disciplina a ser inserida.
     * @return              True se a operação foi bem sucedida.
     */
    boolean insert(DisciplinaVO disciplina, SQLiteDatabase database) {
        try {
            //Cria uma variável para alocar os dados que vão para o banco
            ContentValues values = new ContentValues();

            //Define os valores na variável
            values.put(TABLECOLLUMNS[1], disciplina.getCodigo());
            values.put(TABLECOLLUMNS[2], disciplina.getNome());
            values.put(TABLECOLLUMNS[3], disciplina.getCurso());
            values.put(TABLECOLLUMNS[4], disciplina.getPeriodo());

            //Insere no banco
            return (database.insert(TABLENAME, null, values) > 1);
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    /**
     * Retorna uma lista com todas as Disciplinas do
     * banco de dados.
     *
     * @return A lista de disciplinas
     */
    public List<DisciplinaVO> list() {

        List<DisciplinaVO> list = new ArrayList<>();

        try {
            //Recupera a base de dados e realiza uma query para recuperar as disciplinas
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLLUMNS, null, null, null, null, null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            while (cursor.moveToNext()) {
                DisciplinaVO disciplina = new DisciplinaVO();
                disciplina.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[0])));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[1])));
                disciplina.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[2])));
                disciplina.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[3])));
                disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[4])));

                list.add(disciplina);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return list;
    }

    /**
     * Retorna uma lista com todas as Disciplinas de um determinado
     * curso. O argumento curso deve especificar o curso em questão.
     *
     * @param curso O curso ao qual as disciplina pertencem
     * @return A lista de diciplinas do curso especificado
     */
    public List<DisciplinaVO> list(String curso) {

        List<DisciplinaVO> list = new ArrayList<>();

        try {
            //Recupera a base de dados e realiza uma query para recuperar as disciplinas
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLLUMNS,
                    "CURSO = ?",
                    new String[]{curso},
                    null, null, null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            while (cursor.moveToNext()) {
                DisciplinaVO disciplina = new DisciplinaVO();
                disciplina.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[0])));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[1])));
                disciplina.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[2])));
                disciplina.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[3])));
                disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[4])));

                list.add(disciplina);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch(SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return list;
    }

    /**
     * Método que recupera uma única disciplina a partir
     * de um código espeficidado como argumento.
     *
     * @param codigo    O código da disciplina em questão
     * @return          A disciplina espeficidada pelo código
     */
    public DisciplinaVO getDisciplina(String codigo) {

        DisciplinaVO disciplina = new DisciplinaVO();

        try {
            //Recupera a base de dados e realiza uma query para recuperar
            // a disciplina a partir do código
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLLUMNS,
                    "CODIGO = ?",
                    new String[]{codigo},
                    null, null, null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            if (cursor.moveToFirst()) {
                disciplina.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[0])));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[1])));
                disciplina.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[2])));
                disciplina.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLLUMNS[3])));
                disciplina.setPeriodo(cursor.getInt(cursor.getColumnIndex(TABLECOLLUMNS[4])));
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }
        return disciplina;
    }
}
