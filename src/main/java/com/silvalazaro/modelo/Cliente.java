package com.silvalazaro.modelo;

import java.util.List;
import javax.persistence.Entity;

/**
 * Representa um cliente (pessoa física ou jurídica) que solicita um serviço
 * através da abertura do Chamado.
 *
 * @author Lázaro Silva
 */
@Entity
public class Cliente extends Modelo {

    private String cnpj;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private List<Chamado> chamados;
    private String shCodigo;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public String getShCodigo() {
        return shCodigo;
    }

    public void setShCodigo(String shCodigo) {
        this.shCodigo = shCodigo;
    }

}
