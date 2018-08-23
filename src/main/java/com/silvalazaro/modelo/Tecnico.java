package com.silvalazaro.modelo;

import javax.persistence.Entity;

/**
 * Funcionário responsável por realizar o atendimento do chamado
 *
 * @author Lázaro Silva
 */
@Entity
public class Tecnico extends Modelo {

    private String nome;
    private String shCodigo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getShCodigo() {
        return shCodigo;
    }

    public void setShCodigo(String shCodigo) {
        this.shCodigo = shCodigo;
    }
    
}
