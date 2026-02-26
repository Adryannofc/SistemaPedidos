package com.pedidos.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Produto {

    private final UUID uuid;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private boolean disponivel;

    // Construtor completo — carregado do banco (UUID já existente)
    public Produto(UUID uuid, String nome, String descricao, BigDecimal preco) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo.");
        }
        this.uuid = uuid;
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        this.disponivel = true;
    }

    // Construtor de criação — UUID gerado automaticamente
    public Produto(String nome, String descricao, BigDecimal preco) {
        this(UUID.randomUUID(), nome, descricao, preco);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser nulo ou negativo.");
        }
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}

