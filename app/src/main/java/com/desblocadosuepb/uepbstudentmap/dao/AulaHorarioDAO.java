package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.desblocadosuepb.uepbstudentmap.model.AulaHorarioVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO do Value Object AulaHorario.
 *
 * @author Eric
 * @version 1.1
 * @see com.desblocadosuepb.uepbstudentmap.model.AulaHorarioVO
 * @since Release 01
 */
public class AulaHorarioDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    private static final String TABLENAME = "AULAHORARIO";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    private static final String[] TABLECOLUMNS = new String[]{"_id", "ID_AULA", "HORA", "DIASEMANA", "SALA"};

    /**
     * Construtor da classe AulaHorarioDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public AulaHorarioDAO(Context context){
        this.context = context;
    }

    /**
     * Insere uma nova tupla na base de dados.
     *
     * @param aulaHorario o horário
     * @param database    a base de dados
     * @return True se a inserção der certo
     */
    boolean insert(AulaHorarioVO aulaHorario, SQLiteDatabase database){
        try{
            //Cria uma varável para alocar os dados que vão para o banco
            ContentValues values = new ContentValues();

            //Define os valores na variável
            values.put(TABLECOLUMNS[1], aulaHorario.getIdAula());
            values.put(TABLECOLUMNS[2], aulaHorario.getHora());
            values.put(TABLECOLUMNS[3], aulaHorario.getDiaSemana());
            values.put(TABLECOLUMNS[4], aulaHorario.getSala());

            //Insere no banco
            return (database.insert(TABLENAME, null, values) > 1);
        }catch(SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    /**
     * Retorna uma lista com os horários de uma determinada
     * aula. O argumento aulaId deve especificar o ID
     * da aula em questão.
     *
     * @param aulaId O ID da aula.
     * @return A lista de horários da aula especificada pelo ID.
     */
    public List<AulaHorarioVO> list(int aulaId){

        List<AulaHorarioVO> lista = new ArrayList<>();

        try{
            //Recupera a base de dados e realiza uma query buscando pelo ID da aula
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    "ID_AULA = ?",
                    new String[]{Integer.toString(aulaId)},
                    null,null,null);

            //Enquanto houver registros no resultado da query, monta os objetos e adiciona na lista
            while(cursor.moveToNext()){
                AulaHorarioVO multivalores = new AulaHorarioVO();
                multivalores.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                multivalores.setIdAula(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[1])));
                multivalores.setHora(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                multivalores.setDiaSemana(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));
                multivalores.setSala(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[4])));

                lista.add(multivalores);
            }

            cursor.close(); //Fecha o cursor
            database.close(); //Fecha a base
        }catch (SQLiteException e){
            //Caso o banco esteja inacessível mostra uma mensagem na tela
            Toast.makeText(context, "Database indisponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }
}
