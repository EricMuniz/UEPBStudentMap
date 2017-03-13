package com.desblocadosuepb.uepbstudentmap.model;

/**
 * Classe VO da tabela DISCIPLINA.
 *
 * @author Eric
 * @version 1
 * @see com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper
 * @since Release 01
 */
//TODO Documentar

public class DisciplinaVO {

    private int id;
    private String codigo;
    private String nome;
    private String curso;
    private int periodo;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets codigo.
     *
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets codigo.
     *
     * @param codigo the codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets curso.
     *
     * @return the curso
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Sets curso.
     *
     * @param curso the curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Gets periodo.
     *
     * @return the periodo
     */
    public int getPeriodo() {
        return periodo;
    }

    /**
     * Sets periodo.
     *
     * @param periodo the periodo
     */
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

}
