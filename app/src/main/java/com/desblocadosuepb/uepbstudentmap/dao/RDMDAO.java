package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.RDMVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO do Value Object RDM.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.model.RDMVO
 */
public class RDMDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    public static final String TABLENAME = "RDM";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    public static final String[] TABLECOLUMNS = new String[]{"_id", "NOME"};

    /**
     * Construtor da classe RDMDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public RDMDAO(Context context){
        this.context = context;
    }

    /**
     * Método para inserir um novo RDM no banco de dados.
     * O argumento nome especifica o nome do RDM.
     *
     * @param nome O nome do RDM
     */
    public void insert(String nome){
        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("NOME",nome);

            database.insert(TABLENAME, null, values);
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retorna uma lista com todos os rdms do banco de dados.
     *
     * @return A lista de rdms
     */
    public List<RDMVO> list(){

        List<RDMVO> lista = new ArrayList<>();

        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    null, null, null, null, null);

            while(cursor.moveToNext()){
                RDMVO horario = new RDMVO();
                horario.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                horario.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));

                lista.add(horario);
            }

            cursor.close();
            database.close();
        }catch(SQLiteException e){
            Toast.makeText(context, "Database Indiponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }
}
