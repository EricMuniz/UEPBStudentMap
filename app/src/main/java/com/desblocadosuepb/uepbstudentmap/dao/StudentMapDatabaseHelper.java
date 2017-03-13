package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe que representa a base de dados da aplicação.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see android.database.sqlite.SQLiteOpenHelper
 */
public class StudentMapDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "studentmap";
    private static final int DB_VERSION = 1;

    /**
     * Construtor da classe StudentMapDatabaseHelper.
     * Ele recebe um contexto como argumento e depois chama o construtor
     * da super classe passando esse contexto como argumento, além de passar
     * também o nome da base de dados e sua versão.
     *
     * @param context   O contexto onde a classe está sendo instanciada.
     */
    public StudentMapDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* Tabela RDM */
        db.execSQL("CREATE TABLE RDM (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOME VARCHAR(45));");

        /* Tabela COMPOE */
        db.execSQL("CREATE TABLE COMPOE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RDM_ID VARCHAR(45), " +
                "AULA_ID INTEGER, " +
                "FOREIGN KEY (RDM_ID) REFERENCES HORARIO(_id), " +
                "FOREIGN KEY (AULA_ID) REFERENCES AULA(_id));");

        /* Tabela DISCIPLINA */
        db.execSQL("CREATE TABLE DISCIPLINA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CODIGO TEXT, " +
                "NOME TEXT, " +
                "CURSO TEXT, " +
                "PERIODO INTEGER);");

        /* Tabela AULA */
        db.execSQL("CREATE TABLE AULA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DISC_CODIGO TEXT, " +
                "TURNO VARCHAR(7), " +
                "PROFESSOR VARCHAR(100), " +
                "FOREIGN KEY(DISC_CODIGO) REFERENCES DISCIPLINA(CODIGO));");

        /* Tabela AULAHORARIO */
        db.execSQL("CREATE TABLE AULAHORARIO (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_AULA INTEGER, " +
                "HORA VARCHAR(5), " +
                "DIASEMANA VARCHAR(7), " +
                "SALA VARCHAR(15), " +
                "FOREIGN KEY (ID_AULA) REFERENCES AULA(_id));");

        populateTableDisciplina(db, "CPT07102",
                "Paradigmas de Linguagens de Programação",
                "Computação",
                4);
        populateTableDisciplina(db, "CPT07113",
                "Banco de Dados II",
                "Computação",
                6);
        populateTableDisciplina(db, "CPT07116",
                "Laboratório de Engenharia de Software I",
                "Computação",
                6);

        populateTableAula(db, "CPT07116", "Diurno", "Pablo Ribeiro Suárez");
        populateTableHorario(db, 1, "07:00", "Terça", "101");
        populateTableHorario(db, 1, "07:00", "Quinta", "101");

        populateTableAula(db, "CPT07116", "Noturno", "Pablo Ribeiro Suárez");
        populateTableHorario(db, 2, "20:00", "Quinta", "101");

        populateTableAula(db, "CPT07113", "Diurno", "Rodrigo Costa Alves");
        populateTableHorario(db, 3, "10:00", "Quarta", "201");
        populateTableHorario(db, 3, "08:00", "Sexta", "201");

        populateTableAula(db, "CPT07113", "Noturno", "Jandilson");
        populateTableHorario(db, 4, "18:00", "Segunda", "205");

        populateTableRDM(db, "Meu horário 2016.2");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Adicionar código para atualização da base de dados
    }


    private static void populateTableDisciplina(SQLiteDatabase database
            ,String cpt, String nome, String curso, int periodo){

        ContentValues disciplinaValues = new ContentValues();

        disciplinaValues.put(DisciplinaDAO.TABLECOLLUMNS[1], cpt);
        disciplinaValues.put(DisciplinaDAO.TABLECOLLUMNS[2], nome);
        disciplinaValues.put(DisciplinaDAO.TABLECOLLUMNS[3], curso);
        disciplinaValues.put(DisciplinaDAO.TABLECOLLUMNS[4], periodo);

        database.insert(DisciplinaDAO.TABLENAME, null, disciplinaValues);
    }

    private static void populateTableAula(SQLiteDatabase database
            ,String discCpt,String turno, String professor){

        ContentValues aulaValues = new ContentValues();
        aulaValues.put(AulaDAO.TABLECOLUMNS[1], discCpt);
        aulaValues.put(AulaDAO.TABLECOLUMNS[2], turno);
        aulaValues.put(AulaDAO.TABLECOLUMNS[3], professor);
        
        database.insert(AulaDAO.TABLENAME, null, aulaValues);
    }

    private static void populateTableHorario(SQLiteDatabase database
            ,int idAula, String hora, String diaSemana, String sala){

        ContentValues multiValues = new ContentValues();
        multiValues.put(AulaHorarioDAO.TABLECOLUMNS[1], idAula);
        multiValues.put(AulaHorarioDAO.TABLECOLUMNS[2], hora);
        multiValues.put(AulaHorarioDAO.TABLECOLUMNS[3], diaSemana);
        multiValues.put(AulaHorarioDAO.TABLECOLUMNS[4], sala);

        database.insert(AulaHorarioDAO.TABLENAME, null, multiValues);
    }

    private static void populateTableRDM(SQLiteDatabase database
            ,String nome){

        ContentValues rdmValues = new ContentValues();
        rdmValues.put(RDMDAO.TABLECOLUMNS[1], nome);

        database.insert(RDMDAO.TABLENAME, null, rdmValues);
    }
    
}
