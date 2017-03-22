package com.desblocadosuepb.uepbstudentmap.dao;

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
 * @version 1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.model.AulaVO
 */
public class AulaDAO {

    private Context context;
    /**
     * A constante TABLENAME. O nome da tabela.
     */
    static final String TABLENAME = "AULA";
    /**
     * A constante TABLECOLUMNS. As colunas da tabela.
     */
    static final String[] TABLECOLUMNS = new String[]{"_id", "DISC_CODIGO", "TURNO", "PROFESSOR"};

    /**
     * Construtor da classe AulaDAO
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public AulaDAO(Context context){
        this.context = context;
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
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME, TABLECOLUMNS,
                    "DISC_CODIGO = ?",
                    new String[]{codigo},
                    null, null, null);

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
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return list;
    }

    public AulaVO getAula(int aulaId){

        AulaVO aula = new AulaVO();

        try{
            SQLiteDatabase database = new StudentMapDatabaseHelper(context).getReadableDatabase();
            Cursor cursor = database.query(TABLENAME,
                    TABLECOLUMNS,
                    "_id = ?",
                    new String[]{Integer.toString(aulaId)},
                    null,null,null);

            if(cursor.moveToFirst()) {
                aula.setId(cursor.getInt(cursor.getColumnIndex(TABLECOLUMNS[0])));
                aula.setDiscCodigo(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[1])));
                aula.setTurno(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[2])));
                aula.setProfessor(cursor.getString(cursor.getColumnIndex(TABLECOLUMNS[3])));
            }

            cursor.close();
            database.close();
        }catch (SQLException e){
            Toast.makeText(context, "Database Indisponível", Toast.LENGTH_SHORT).show();
        }

        return aula;
    }
}
