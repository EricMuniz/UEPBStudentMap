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
 * @version 1.1
 * @see com.desblocadosuepb.uepbstudentmap.model.RDMVO
 * @since Release 01
 */
public class RDMDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    static final String TABLENAME = "RDM";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    static final String[] TABLECOLUMNS = new String[]{"_id", "NOME", "CURSO"};

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
     * @param rdmvo o objeto RDM para ser inserido no banco.
     */
    public void insert(RDMVO rdmvo){
        try{
            //Recupera a base de dados e cria uma variável para alocar os dados que vão para o banco
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getWritableDatabase();
            ContentValues values = new ContentValues();

            //Define os valores
            values.put("NOME", rdmvo.getNome());
            values.put("CURSO", rdmvo.getCurso());

            //Insere na base
            database.insert(TABLENAME, null, values);
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
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
            //Recupera a base de dados e realiza uma query para recuperar os RDM's
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    null, null, null, null, null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            while(cursor.moveToNext()){
                RDMVO horario = new RDMVO();
                horario.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                horario.setNome(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));
                horario.setCurso(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));

                lista.add(horario);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch(SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indiponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }
}
