package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.AulaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO do Value Object Compoe. Esta é uma
 * tabela M/N no banco de dados, uma relação entre Aula e RDM.
 *
 * @author Eric
 * @version 1
 * @see com.desblocadosuepb.uepbstudentmap.model.CompoeVO
 * @since Release 01
 */
public class CompoeDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    public static final String TABLENAME = "COMPOE";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    public static final String[] TABLECOLUMNS = new String[]{"_id", "RDM_ID", "AULA_ID"};

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
     *
     * O argumento rdmId especifica o ID de uma linha na tabela
     * RDM.
     * O argumento aulaId espeficica o ID de uma inha na tabela
     * AULA.
     *
     * @param rdmId     O ID do RDM.
     * @param aulaId    O ID da Aula.
     */
    public void insert(int rdmId, int aulaId){
        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("RDM_ID", rdmId);
            values.put("AULA_ID", aulaId);

            database.insert(TABLENAME, null, values);
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean delete(int rdmId, int aulaId){

        boolean deleted = false;

        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();

            database.delete(TABLENAME, "RDM_ID = ? AND AULA_ID = ?",
                    new String[]{Integer.toString(rdmId), Integer.toString(aulaId)});

            deleted = true;
            database.close();
        }catch(SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return deleted;
    }

    //ToDo falta comentar/documentar este método
    public int countDisc(int rdmId){

        int count = 0;

        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"COUNT(_id) as count"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            if(cursor.moveToFirst()){
                count = cursor.getInt(0);
            }

            cursor.close();
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return count;
    }
    
    public boolean exist(int rdmId, String discCodigo){
        
        boolean exist = false;
        
        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"AULA_ID"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            while(cursor.moveToNext()){
                AulaVO aula = new AulaDAO(context).getAula(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[2])));
                if(aula.getDiscCodigo().equals(discCodigo)){
                    exist = true;
                    break;
                }
            }

            cursor.close();
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return exist;
    }

    public ArrayList<Integer> getListAulaId(int rdmId){

        ArrayList<Integer> lista = new ArrayList<>();

        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    new String[]{"AULA_ID"},
                    "RDM_ID = ?",
                    new String[]{Integer.toString(rdmId)},
                    null,null,null);

            while(cursor.moveToNext()){
                int aulaId = cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[2]));
                lista.add(aulaId);
            }

            cursor.close();
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }

}
