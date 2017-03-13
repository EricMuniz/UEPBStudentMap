package com.desblocadosuepb.uepbstudentmap.model;

/**
 * Classe VO da tabela COMPOE.
 *
 * @author Eric
 * @version 1
 * @see com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper
 * @since Release 01
 */
public class CompoeVO {

    private int id;
    private String horarioNome;
    private int aulaId;

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
     * Gets horario nome.
     *
     * @return the horario nome
     */
    public String getHorarioNome() {
        return horarioNome;
    }

    /**
     * Sets horario nome.
     *
     * @param horarioNome the horario nome
     */
    public void setHorarioNome(String horarioNome) {
        this.horarioNome = horarioNome;
    }

    /**
     * Gets aula id.
     *
     * @return the aula id
     */
    public int getAulaId() {
        return aulaId;
    }

    /**
     * Sets aula id.
     *
     * @param aulaId the aula id
     */
    public void setAulaId(int aulaId) {
        this.aulaId = aulaId;
    }
}
