package com.silvalazaro.modelo;

import java.util.Date;
import javax.persistence.Entity;

/**
 * Representa um Chamado - solicitação de serviço
 *
 * @author Lázaro Silva
 */
@Entity
public class Chamado extends Modelo {

    private Cliente cliente;
    private String telefone;
    private Categoria categoria;
    private int realizado;
    private Date data;
    private String tecnico;
    private String descricao;
    private String email;
    private int prioridade;
    private String endereco;
    private String shCodigo;
    private byte[] audio;
    private byte[] foto;
    private ChamadoTipo shTipo;
    private String shPrioridade;

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getRealizado() {
        return realizado;
    }

    public void setRealizado(int realizado) {
        this.realizado = realizado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getShCodigo() {
        return shCodigo;
    }

    public void setShCodigo(String shCodigo) {
        this.shCodigo = shCodigo;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public ChamadoTipo getShTipo() {
        return shTipo;
    }

    public void setShTipo(ChamadoTipo shTipo) {
        this.shTipo = shTipo;
    }

    public String getShPrioridade() {
        return shPrioridade;
    }

    public void setShPrioridade(String shPrioridade) {
        this.shPrioridade = shPrioridade;
    }

}
