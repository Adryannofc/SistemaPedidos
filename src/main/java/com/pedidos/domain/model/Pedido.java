package com.pedidos.domain.model;

import com.pedidos.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private UUID id;
    private UUID clienteId;
    private UUID restauranteId;
    private List<ItemPedido> itens = new ArrayList();
    private StatusPedido status = StatusPedido.AGUARDANDO_CONFIRMACAO;
    private BigDecimal taxaEntrega;
    private BigDecimal total;
    private LocalDateTime dataPedido;

    public Pedido(UUID clienteId, UUID restauranteId, BigDecimal taxaEntrega) {
        this.id = UUID.randomUUID();
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.taxaEntrega = taxaEntrega;
        this.dataPedido = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public BigDecimal calcularTotal() {
        BigDecimal soma = BigDecimal.ZERO;

        for (ItemPedido item : itens) {
            soma = soma.add(item.calcularSubtotal());
        }

        total = soma.add(taxaEntrega);
        return total;
    }

}
