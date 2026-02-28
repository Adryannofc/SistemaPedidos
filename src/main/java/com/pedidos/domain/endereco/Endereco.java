package com.pedidos.domain.endereco;

import java.util.UUID;

public class Endereco {
    private String id;
    private String clienteId;
    private String apelido;
    private String rua;
    private String bairro;
    private String numero;
    private String cidade;
    private String complemento;
    private Boolean isPadrao;

    public Endereco(String id, String clienteId, String apelido, String rua, String bairro, String numero, String cidade, String complemento, Boolean isPadrao) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        if(clienteId == null || clienteId.isBlank())
        {
            throw new IllegalArgumentException("O ID do cliente é obrigatório para cadastrar um endereço.");
        }
        this.clienteId = clienteId;
        this.apelido = apelido;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.complemento = complemento;
        this.isPadrao = (isPadrao != null) ? isPadrao : false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Boolean getPadrao() {
        return isPadrao;
    }

    public void setPadrao(Boolean padrao) {
        isPadrao = padrao;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id='" + id + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", apelido='" + apelido + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", numero='" + numero + '\'' +
                ", cidade='" + cidade + '\'' +
                ", complemento='" + complemento + '\'' +
                ", isPadrao=" + isPadrao +
                '}';
    }
}
