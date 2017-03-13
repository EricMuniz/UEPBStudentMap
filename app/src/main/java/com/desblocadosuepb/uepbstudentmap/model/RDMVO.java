package com.desblocadosuepb.uepbstudentmap.model;

/**
 * Classe VO da tabela RDM.
 *
 * @author Eric
 * @version 1
 * @see com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper
 * @since Release 01
 */
public class RDMVO {

    private int id;
    private String nome;

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
}
