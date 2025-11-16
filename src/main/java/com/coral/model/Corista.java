package com.coral.model;

public class Corista {
    private int id;
    private String nome;
    private String tipoVoz; // Voltando para String
    private boolean ativo;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTipoVoz() { return tipoVoz; }
    public void setTipoVoz(String tipoVoz) { this.tipoVoz = tipoVoz; }
    
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}