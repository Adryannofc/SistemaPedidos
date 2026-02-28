package com.pedidos.domain.produto;

import java.math.BigDecimal;
import java.util.UUID;

public class Produto {
    private String id = UUID.randomUUID().toString();
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String categoriaCardapioId;
    private String restauranteId;
    private boolean statusAtivo = true;


    public Produto(String nome, String descricao, BigDecimal preco, String categoriaCardapioId, String restauranteId, boolean statusAtivo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoriaCardapioId = categoriaCardapioId;
        this.restauranteId = restauranteId;
        this.statusAtivo = statusAtivo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        String regexLetras = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        if (nome != null && nome.matches(regexLetras)) {
            this.nome = nome.trim();
        } else {
            System.err.println("Nome inválido! Utilize apenas letras.");
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao == null || descricao.trim().length() < 10)
        {
            throw new IllegalArgumentException("A descrição deve ter pelo menos 10 caracteres para ajudar o cliente.");
        };
        this.descricao = descricao.trim();
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço não pode ser nulo ou negativo.");
        }
        this.preco = preco;
    }

    public String getCategoriaCardapioId() {
        return categoriaCardapioId;
    }

    public void setCategoriaCardapioId(String categoriaCardapioId) {
        this.categoriaCardapioId = categoriaCardapioId;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        if (restauranteId == null || restauranteId.isBlank())
        {
            throw new IllegalArgumentException("O ID do restaurante é obrigatório.");
        }
        this.restauranteId = restauranteId;
    }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", categoriaCardapioId='" + categoriaCardapioId + '\'' +
                ", restauranteId='" + restauranteId + '\'' +
                ", statusAtivo=" + statusAtivo +
                '}';
    }
}
