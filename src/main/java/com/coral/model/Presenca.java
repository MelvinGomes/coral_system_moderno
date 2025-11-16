package com.coral.model;

public class Presenca {
    // A coluna 'id' da tabela presencas não é tão relevante aqui,
    // mas os outros campos são.
    
    private int idParticipante; // Antigo idCorista
    private TipoParticipante tipoParticipante;
    private int idAgenda;
    private boolean presente;

    // Getters e Setters
    public int getIdParticipante() { return idParticipante; }
    public void setIdParticipante(int idParticipante) { this.idParticipante = idParticipante; }

    public TipoParticipante getTipoParticipante() { return tipoParticipante; }
    public void setTipoParticipante(TipoParticipante tipoParticipante) { this.tipoParticipante = tipoParticipante; }

    public int getIdAgenda() { return idAgenda; }
    public void setIdAgenda(int idAgenda) { this.idAgenda = idAgenda; }
    
    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }
}