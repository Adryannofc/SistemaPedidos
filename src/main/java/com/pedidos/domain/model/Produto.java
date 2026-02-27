package com.pedidos.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Produto {

    private final String id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoriaCardapioId;
    private String restauranteId;
    private boolean statusAtivo;

    // Construtor completo — carregado do banco (UUID já existente)
    public Produto(String nome, String descricao, BigDecimal preco, String categoriaCardapioId, String restauranteId) {
        this.id = UUID.randomUUID().toString();
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        this.categoriaCardapioId = categoriaCardapioId;
        this.restauranteId = restauranteId;
        this.statusAtivo = true; // começa ativo por padrão
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

    public String getId() {
        return id;
    }

    public String getCategoriaCardapioId() {
        return categoriaCardapioId;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
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
}

