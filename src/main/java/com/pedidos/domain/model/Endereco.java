package com.pedidos.domain.model;

import java.util.UUID;

public class Endereco {

    private final String id;
    private String clienteId;
    private String apelido;
    private String rua;
    private String bairro;
    private boolean isPadrao;

    public Endereco(String clienteId, String apelido, String rua, String bairro) {
        this.id = UUID.randomUUID().toString();
        this.clienteId = clienteId;
        this.apelido = apelido;
        this.rua = rua;
        this.bairro = bairro;
        this.isPadrao = false;
    }

    public String getId() { return id; }
    public String getClienteId() { return clienteId; }
    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }
    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public boolean isPadrao() { return isPadrao; }
    public void setPadrao(boolean padrao) { this.isPadrao = padrao; }
}