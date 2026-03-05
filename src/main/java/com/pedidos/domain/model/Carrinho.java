package com.pedidos.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrinho {

    private final String clienteId;
    private final String restauranteId;
    private final List<ItemPedido> itens = new ArrayList<>();

    public Carrinho(String clienteId, String restauranteId) {
        this.clienteId     = clienteId;
        this.restauranteId = restauranteId;
    }

    public void adicionarItem(String produtoId, String nomeProduto,
                              int quantidade, BigDecimal precoUnitario) {
        // se o produto já está no carrinho, soma a quantidade
        for (ItemPedido item : itens) {
            if (item.getProdutoId().equals(produtoId)) {
                itens.remove(item);
                itens.add(new ItemPedido(produtoId, nomeProduto,
                        item.getQuantidade() + quantidade, precoUnitario));
                return;
            }
        }
        itens.add(new ItemPedido(produtoId, nomeProduto, quantidade, precoUnitario));
    }

    public void removerItem(String produtoId) {
        itens.removeIf(i -> i.getProdutoId().equals(produtoId));
    }

    public void limpar() {
        itens.clear();
    }

    public BigDecimal calcularSubtotal() {
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getClienteId()     { return clienteId; }
    public String getRestauranteId() { return restauranteId; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
    public boolean estaVazio()       { return itens.isEmpty(); }
}