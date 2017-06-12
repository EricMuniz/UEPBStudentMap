package com.desblocadosuepb.uepbstudentmap.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.desblocadosuepb.uepbstudentmap.model.AulaHorarioVO;
import com.desblocadosuepb.uepbstudentmap.model.AulaVO;
import com.desblocadosuepb.uepbstudentmap.model.DisciplinaVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Classe que representa a base de dados da aplicação.
 *
 * @author Eric
 * @version 1.1
 * @see android.database.sqlite.SQLiteOpenHelper
 * @since Release 01
 */
public class StudentMapDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "studentmap";
    private static final int DB_VERSION = 1;
    private Context context;

    /**
     * Construtor da classe StudentMapDatabaseHelper.
     * Ele recebe um contexto como argumento e depois chama o construtor
     * da super classe passando esse contexto como argumento, além de passar
     * também o nome da base de dados e sua versão.
     *
     * @param context O contexto onde a classe está sendo instanciada.
     */
    public StudentMapDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

        populateTableDisciplina(context, db);
        populateTableAula(context, db);
        populateTableHorario(context, db);

        populateTableRDM(db, "Meu horário 2016.2", "Computação");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Adicionar código para atualização da base de dados
    }

    private static void createTables(SQLiteDatabase database){
        // Tabela RDM
        database.execSQL("CREATE TABLE RDM (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NOME VARCHAR(45), " +
                "CURSO TEXT);");

        // Tabela COMPOE
        database.execSQL("CREATE TABLE COMPOE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "RDM_ID VARCHAR(45), " +
                "AULA_ID INTEGER, " +
                "FOREIGN KEY (RDM_ID) REFERENCES HORARIO(_id), " +
                "FOREIGN KEY (AULA_ID) REFERENCES AULA(_id));");

        // Tabela DISCIPLINA
        database.execSQL("CREATE TABLE DISCIPLINA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CODIGO TEXT, " +
                "NOME TEXT, " +
                "CURSO TEXT, " +
                "PERIODO INTEGER);");

        // Tabela AULA
        database.execSQL("CREATE TABLE AULA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DISC_CODIGO TEXT, " +
                "TURNO VARCHAR(7), " +
                "PROFESSOR VARCHAR(100), " +
                "FOREIGN KEY(DISC_CODIGO) REFERENCES DISCIPLINA(CODIGO));");

        // Tabela AULAHORARIO
        database.execSQL("CREATE TABLE AULAHORARIO (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_AULA INTEGER, " +
                "HORA VARCHAR(5), " +
                "DIASEMANA VARCHAR(7), " +
                "SALA VARCHAR(15), " +
                "FOREIGN KEY (ID_AULA) REFERENCES AULA(_id));");
    }

    private static void populateTableDisciplina(Context context, SQLiteDatabase database){

        //Recupera o gerenciador de recursos para abrir o arquivo
        AssetManager assetManager = context.getAssets();

        try{
            //Abre um arquivo que povoa a base de dados inicial
            InputStream arquivo = assetManager.open("Disciplinas.txt");
            InputStreamReader leitorArquivo = new InputStreamReader(arquivo);
            BufferedReader leitorLinhas = new BufferedReader(leitorArquivo);

            //Recupera a primeira linha do arquivo
            String linha = leitorLinhas.readLine();

            //Enquanto houver linhas no arquivo, divide o conteúdo de cada uma
            // para pegar os valores dos VO's
            while(linha != null){
                //Quebra a linha nos pontos em que houver uma barra separando os valores
                String[] valores = linha.split("/");

                //Cria um novo VO
                DisciplinaVO disciplinaVO = new DisciplinaVO();
                disciplinaVO.setCodigo(valores[0]);
                disciplinaVO.setNome(valores[1]);
                disciplinaVO.setCurso(valores[2]);
                disciplinaVO.setPeriodo(Integer.parseInt(valores[3]));

                //Insere na base de dados
                DisciplinaDAO dao = new DisciplinaDAO(context);
                dao.insert(disciplinaVO, database);

                //Recupera a próxima linha do arquivo
                linha = leitorLinhas.readLine();
            }

            arquivo.close();
        }catch(IOException e){
            //Exibe uma mensagem na tela caso ocorra algum erro ao abrir o arquivo
            System.err.printf("Erro ao abrir o arquivo: %s\n", e.getMessage());
        }
    }

    private static void populateTableAula(Context context, SQLiteDatabase database){

        //Recupera o gerenciador de recursos para abrir o arquivo
        AssetManager assetManager = context.getAssets();

        try{
            //Abre um arquivo que povoa a base de dados inicial
            InputStream arquivo = assetManager.open("Aulas.txt");
            InputStreamReader leitorArquivo = new InputStreamReader(arquivo, "ISO-8859-1");
            BufferedReader leitorLinhas = new BufferedReader(leitorArquivo);

            //Recupera a primeira linha do arquivo
            String linha = leitorLinhas.readLine();

            //Enquanto houver linhas no arquivo, divide o conteúdo de cada uma
            // para pegar os valores dos VO's
            while(linha != null){
                //Quebra a linha nos pontos em que houver uma barra separando os valores
                String[] valores = linha.split("/");

                //Cria um novo VO
                AulaVO aulaVO = new AulaVO();
                aulaVO.setDiscCodigo(valores[0]);
                aulaVO.setTurno(valores[1]);
                aulaVO.setProfessor(valores[2]);

                //Insere na base de dados
                AulaDAO dao = new AulaDAO(context);
                dao.insert(aulaVO, database);

                //Recupera a próxima linha do arquivo
                linha = leitorLinhas.readLine();
            }
        }catch(IOException e){
            //Exibe uma mensagem na tela caso ocorra algum erro ao abrir o arquivo
            System.err.printf("Erro ao abrir o arquivo: %s\n", e.getMessage());
        }
    }

    private static void populateTableHorario(Context context, SQLiteDatabase database){

        //Recupera o gerenciador de recursos para abrir o arquivo
        AssetManager assetManager = context.getAssets();

        try{
            //Abre um arquivo que povoa a base de dados inicial
            InputStream arquivo = assetManager.open("Aula-Horário.txt");
            InputStreamReader leitorArquivo = new InputStreamReader(arquivo, "ISO-8859-1");
            BufferedReader leitorLinhas = new BufferedReader(leitorArquivo);

            //Recupera a primeira linha do arquivo
            String linha = leitorLinhas.readLine();

            //Enquanto houver linhas no arquivo, divide o conteúdo de cada uma
            // para pegar os valores dos VO's
            while(linha != null){
                //Quebra a linha nos pontos em que houver uma barra separando os valores
                String[] valores = linha.split("/");

                //Cria um novo VO
                AulaHorarioVO aulaHorarioVO = new AulaHorarioVO();
                aulaHorarioVO.setIdAula(Integer.parseInt(valores[0]));
                aulaHorarioVO.setHora(valores[1]);
                aulaHorarioVO.setDiaSemana(valores[2]);
                aulaHorarioVO.setSala(valores[3]);

                //Insere na base de dados
                AulaHorarioDAO dao = new AulaHorarioDAO(context);
                dao.insert(aulaHorarioVO, database);

                //Recupera a próxima linha do arquivo
                linha = leitorLinhas.readLine();
            }
        }catch(IOException e){
            //Exibe uma mensagem na tela caso ocorra algum erro ao abrir o arquivo
            System.err.printf("Erro ao abrir o arquivo: %s\n", e.getMessage());
        }
    }

    //Este método está aqui apenas como um teste
    private static void populateTableRDM(SQLiteDatabase database
            ,String nome, String curso){

        ContentValues rdmValues = new ContentValues();
        rdmValues.put(RDMDAO.TABLECOLUMNS[1], nome);
        rdmValues.put(RDMDAO.TABLECOLUMNS[2], curso);

        database.insert(RDMDAO.TABLENAME, null, rdmValues);
    }
    
}
