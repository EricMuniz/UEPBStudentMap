package com.desblocadosuepb.uepbstudentmap.model;

/**
 * Classe VO da tabela AULAHORARIO.
 *
 * @author Eric
 * @version 1
 * @since Release 01
 * @see com.desblocadosuepb.uepbstudentmap.dao.StudentMapDatabaseHelper
 */
public class AulaHorarioVO {

    private int id;
    private int idAula;
    private String hora;
    private String diaSemana;
    private String sala;

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
     * Gets id aula.
     *
     * @return the id aula
     */
    public int getIdAula() {
        return idAula;
    }

    /**
     * Sets id aula.
     *
     * @param idAula the id aula
     */
    public void setIdAula(int idAula) {
        this.idAula = idAula;
    }

    /**
     * Gets hora.
     *
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * Sets hora.
     *
     * @param hora the hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Gets dia semana.
     *
     * @return the dia semana
     */
    public String getDiaSemana() {
        return diaSemana;
    }

    /**
     * Sets dia semana.
     *
     * @param diaSemana the dia semana
     */
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     * Gets sala.
     *
     * @return the sala
     */
    public String getSala() {
        return sala;
    }

    /**
     * Sets sala.
     *
     * @param sala the sala
     */
    public void setSala(String sala) {
        this.sala = sala;
    }
}
