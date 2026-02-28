package com.pedidos.domain.repository;

import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.model.Pedido;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {

    void salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(UUID id);

    List<Pedido> listarTodos();

    List<Pedido> buscarPorCliente(UUID clienteId);

    List<Pedido> buscarPorRestaurante(UUID restauranteId);

    List<Pedido> listarAtivosPorRestaurante(UUID restauranteId);

    List<Pedido> filtrarPorStatus(UUID restauranteId, StatusPedido status);

    void deletar(UUID id);


}
