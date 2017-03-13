package com.desblocadosuepb.uepbstudentmap.dao;

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
 * @version 1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.model.AulaHorarioVO
 */
public class AulaHorarioDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    public static final String TABLENAME = "AULAHORARIO";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    public static final String[] TABLECOLUMNS = new String[]{"_id", "ID_AULA", "HORA", "DIASEMANA", "SALA"};

    /**
     * Construtor da classe AulaHorarioDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public AulaHorarioDAO(Context context){
        this.context = context;
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
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    "ID_AULA = ?",
                    new String[]{Integer.toString(aulaId)},
                    null,null,null);

            while(cursor.moveToNext()){
                AulaHorarioVO multivalores = new AulaHorarioVO();
                multivalores.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                multivalores.setIdAula(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[1])));
                multivalores.setHora(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                multivalores.setDiaSemana(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));
                multivalores.setSala(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[4])));

                lista.add(multivalores);
            }

            cursor.close();
            database.close();
        }catch (SQLiteException e){
            Toast.makeText(context, "Database indisponível", Toast.LENGTH_SHORT).show();
        }

        return lista;
    }
}
