package com.pedidos.domain.model;

import com.pedidos.domain.enums.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private final String id;
    private String clienteId;
    private String restauranteId;
    private List<ItemPedido> itens;
    private StatusPedido status = StatusPedido.AGUARDANDO_CONFIRMACAO;
    private BigDecimal taxaEntrega;
    private BigDecimal total;
    private LocalDateTime dataPedido;

    public Pedido(String id, String clienteId, String restauranteId, BigDecimal taxaEntrega) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        this.clienteId = (clienteId != null) ? clienteId : UUID.randomUUID().toString();
        this.restauranteId = (restauranteId != null) ? restauranteId : UUID.randomUUID().toString();
        this.taxaEntrega = (taxaEntrega != null) ? taxaEntrega : BigDecimal.ZERO;
        this.dataPedido = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
        this.itens = new ArrayList<>();
    }

    public String getId() {
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

    public void setStatus(StatusPedido status) {
        this.status = status;
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
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(taxaEntrega);
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
