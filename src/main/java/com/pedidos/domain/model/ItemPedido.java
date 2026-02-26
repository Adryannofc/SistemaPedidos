package com.pedidos.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemPedido {

    private UUID produtoId;
    private String nomeProduto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido(UUID produtoId, String nomeProduto, int quantidade, BigDecimal precoUnitario) {
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public UUID getProdutoId() {
        return produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal calcularSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}
