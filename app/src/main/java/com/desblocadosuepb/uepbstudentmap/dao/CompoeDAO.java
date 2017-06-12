package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.AulaVO;

import java.util.ArrayList;

/**
 * Classe DAO do Value Object Compoe. Esta é uma
 * tabela M/N no banco de dados, uma relação entre Aula e RDM.
 *
 * @author Eric
 * @version 1.1
 * @see com.desblocadosuepb.uepbstudentmap.model.CompoeVO
 * @since Release 01
 */
public class CompoeDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    private static final String TABLENAME = "COMPOE";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    private static final String[] TABLECOLUMNS = new String[]{"_id", "RDM_ID", "AULA_ID"};

    /**
     * Construtor da classe CompoeDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public CompoeDAO(Context context){
        this.context = context;
    }

    /**
     * Método que insere uma nova tupla da relação
     * COMPOE no banco de dados.
     * O argumento rdmId especifica o ID de uma linha na tabela
     * RDM.
     * O argumento aulaId espeficica o ID de uma inha na tabela
     * AULA.
     *
     * @param rdmId  O ID do RDM.
     * @param aulaId O ID da Aula.
     */
    public void insert(int rdmId, int aulaId){
        try{
            //Recupera a base de dados e cria uma variável para alocar os dados que vão para o banco
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            //Define os valores na variável
            values.put("RDM_ID", rdmId);
            values.put("AULA_ID", aulaId);

            //Insere no banco
            database.insert(TABLENAME, null, values);
            //Fecha o banco
            database.close();
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Remove uma tupla da base de dados.
     *
     * @param rdmId  O ID do RDM
     * @param aulaId O ID da Aula
     * @return True se a operação der certo
     */
    public boolean delete(int rdmId, int aulaId){

        boolean deleted = false;

        try{
            //Recupera a base de dados
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();

            //Realiza a remoção através de uma query com o ID da aula e o ID do RDM
            database.delete(TABLENAME, "RDM_ID = ? AND AULA_ID = ?",
                    new String[]{Integer.toString(rdmId), Integer.toString(aulaId)});

            deleted = true;
            //Fecha a base
            database.close();
        }catch(SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return deleted;
    }

    /**
     * Conta quantas disciplinas estão relacionadas
     * com um determinado RDM.
     *
     * @param rdmId o ID do RDm
     * @return a quantidade de disciplinas
     */
    public int countDisc(int rdmId){

        int count = 0;

        try{
            //Recupera a base de dados e realiza uma query que conta as disciplinas
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"COUNT(_id) as count"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            if(cursor.moveToFirst()){
                //Recupera o valor retornado na query
                count = cursor.getInt(0);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return count;
    }

    /**
     * Checa se um determinada disciplina já está
     * relacionada com um determinado RDM.
     *
     * @param rdmId      o ID do RDM
     * @param discCodigo o código da disciplina
     * @return True se a disciplina já está relacionada
     */
    public boolean exist(int rdmId, String discCodigo){
        
        boolean exist = false;
        
        try{
            //Recupera a base de dados e realiza uma query com o ID do RDM
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"AULA_ID"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            //Enquanto houver registros no resultado da query, checa se alguma aula é da disciplina
            //que se quer inserir
            while(cursor.moveToNext()){
                AulaVO aula = new AulaDAO(context).getAula(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[2])));
                if(aula.getDiscCodigo().equals(discCodigo)){
                    //Se a disciplina já existe, quebra o loop
                    exist = true;
                    break;
                }
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return exist;
    }

    /**
     * Recupera uma lista com os ID's das aulas
     * relacionadas a um determinado RDM.
     *
     * @param rdmId o ID do RDM
     * @return o ArrayList com os ID's
     */
    public ArrayList<Integer> getListAulaId(int rdmId){

        ArrayList<Integer> lista = new ArrayList<>();

        try{
            //Recupera a base de dados e realiza uma query com o ID do RDM
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"AULA_ID"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            //Enquanto houver registros no resultado da query, adiciona na lista
            while(cursor.moveToNext()){
                int aulaId = cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[2]));
                lista.add(aulaId);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }

}
