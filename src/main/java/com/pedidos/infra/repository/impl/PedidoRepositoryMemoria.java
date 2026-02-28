package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.repository.PedidoRepository;

import java.util.HashMap;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;


public class PedidoRepositoryMemoria implements PedidoRepository {

    private final Map<UUID, Pedido> pedidos = new HashMap<>();

    @Override
    public void salvar(Pedido pedido) {
        pedidos.put(pedido.getId(), pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public List<Pedido> buscarPorCliente(UUID clienteId) {
        return pedidos.values()
                .stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> buscarPorRestaurante(UUID restauranteId) {
        return pedidos.values()
                .stream()
                .filter(p -> p.getRestauranteId().equals(restauranteId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> listarAtivosPorRestaurante(UUID restauranteId) {
        return pedidos.values()
                .stream()
                .filter(p -> p.getRestauranteId().equals(restauranteId))
                .filter(p -> p.getStatus() != StatusPedido.ENTREGUE
                                    && p.getStatus() != StatusPedido.CANCELADO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> filtrarPorStatus(UUID restauranteId, StatusPedido status) {
        return pedidos.values()
                .stream()
                .filter(p -> p.getRestauranteId().equals(restauranteId))
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(UUID id) {
        pedidos.remove(id);
    }

}
