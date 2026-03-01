package com.pedidos.domain.model;

import com.pedidos.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private final UUID id;
    private String clienteId;
    private String restauranteId;
    private List<ItemPedido> itens;
    private StatusPedido status = StatusPedido.AGUARDANDO_CONFIRMACAO;
    private BigDecimal taxaEntrega;
    private BigDecimal total;
    private LocalDateTime dataPedido;

    public Pedido(UUID id, UUID clienteId, UUID restauranteId, BigDecimal taxaEntrega) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.clienteId = UUID.randomUUID().toString();
        this.restauranteId = UUID.randomUUID().toString();
        this.taxaEntrega = (taxaEntrega != null) ? taxaEntrega : BigDecimal.ZERO;
        this.dataPedido = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
        this.itens = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getRestauranteId() {
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", clienteId='" + clienteId + '\'' +
                ", restauranteId='" + restauranteId + '\'' +
                ", itens=" + itens +
                ", status=" + status +
                ", taxaEntrega=" + taxaEntrega +
                ", total=" + total +
                ", dataPedido=" + dataPedido +
                '}';
    }
}
