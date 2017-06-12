package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.AulaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO do Value Object Aula.
 *
 * @author Eric
 * @version 1.1
 * @see com.desblocadosuepb.uepbstudentmap.model.AulaVO
 * @since Release 01
 */
public class AulaDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    private static final String TABLENAME = "AULA";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    private static final String[] TABLECOLUMNS = new String[]{"_id", "DISC_CODIGO", "TURNO", "PROFESSOR"};

    /**
     * Construtor da classe AulaDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public AulaDAO(Context context){
        this.context = context;
    }

    /**
     * Insere uma nova tupla na base de dados.
     *
     * @param aula     a aula
     * @param database a base de dados
     * @return True se a inserção der certo
     */
    boolean insert(AulaVO aula, SQLiteDatabase database){
        try{
            //Cria uma variável para alocar os dados que vão para o banco
            ContentValues values = new ContentValues();

            //Define os valores na variável
            values.put(TABLECOLUMNS[1], aula.getDiscCodigo());
            values.put(TABLECOLUMNS[2], aula.getTurno());
            values.put(TABLECOLUMNS[3], aula.getProfessor());

            //Insere no banco
            return (database.insert(TABLENAME, null, values) > 1);
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    /**
     * Retorna uma lista com todos as Aulas de uma determinada
     * disciplina. O argumento codigo deve especificar o codigo
     * da disciplina em questão.
     *
     * @param codigo O codigo da disciplina.
     * @return A lista de aulas da disciplina especificada pelo código.
     */
    public List<AulaVO> list(String codigo){

        List<AulaVO> list = new ArrayList<>();

        try {
            //Recupera a base da dados e realiza uma query buscando pelo código
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    "DISC_CODIGO = ?",
                    new String[]{codigo},
                    null, null, null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            while (cursor.moveToNext()) {
                AulaVO aula = new AulaVO();
                aula.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                aula.setDiscCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));
                aula.setTurno(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                aula.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));

                list.add(aula);
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
     * Retorna uma lista com todas as aulas.
     *
     * @return the list
     */
    public List<AulaVO> list(){

        List<AulaVO> list = new ArrayList<>();

        try {
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS, null, null, null, null, null);

            while (cursor.moveToNext()) {
                AulaVO aula = new AulaVO();
                aula.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                aula.setDiscCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));
                aula.setTurno(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                aula.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));

                list.add(aula);
            }

            cursor.close();
            database.close();
        }catch(SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return list;
    }

    /**
     * Retorna uma aula a partir de um dado ID.
     *
     * @param aulaId O ID da aula
     * @return a aula
     */
    public AulaVO getAula(int aulaId){

        AulaVO aula = new AulaVO();

        try{
            //Recupera a base da dados e realiza uma query buscando pelo código
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    TABLECOLUMNS,
                    "_id = ?",
                    new String[]{Integer.toString(aulaId)},
                    null,null,null);

            //Se o cursor mover para o primeiro registro da query, então a aula existe no banco
            if(cursor.moveToFirst()) {
                aula.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                aula.setDiscCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));
                aula.setTurno(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                aula.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return aula;
    }
}
