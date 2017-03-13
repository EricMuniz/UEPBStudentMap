package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

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
        }catch (SQLiteException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }
    }

}
