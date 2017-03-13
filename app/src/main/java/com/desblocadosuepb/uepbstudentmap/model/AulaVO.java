package com.desblocadosuepb.uepbstudentmap.model;

/**
 * Classe VO da tabela AULA.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper
 */
public class AulaVO {

    private int id;
    private String discCodigo;
    private String turno;
    private String professor;

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
     * Gets disc cpt.
     *
     * @return the disc cpt
     */
    public String getDiscCodigo() {
        return discCodigo;
    }

    /**
     * Sets disc cpt.
     *
     * @param discCodigo the disc cpt
     */
    public void setDiscCodigo(String discCodigo) {
        this.discCodigo = discCodigo;
    }

    /**
     * Gets turno.
     *
     * @return the turno
     */
    public String getTurno() {
        return turno;
    }

    /**
     * Sets turno.
     *
     * @param turno the turno
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * Gets professor.
     *
     * @return the professor
     */
    public String getProfessor() {
        return professor;
    }

    /**
     * Sets professor.
     *
     * @param professor the professor
     */
    public void setProfessor(String professor) {
        this.professor = professor;
    }

}
