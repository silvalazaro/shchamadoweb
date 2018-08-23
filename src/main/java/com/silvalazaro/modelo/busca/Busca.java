package com.silvalazaro.modelo.busca;

/**
 * Classe de busca para consultar entidades dinamicamente
 *
 * @author Lázaro Silva
 */
public class Busca {
    private Filtro filtro;
    private String[] atributos;

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public String[] getAtributos() {
        return atributos;
    }

    public void setAtributos(String[] atributos) {
        this.atributos = atributos;
    }
    
}
