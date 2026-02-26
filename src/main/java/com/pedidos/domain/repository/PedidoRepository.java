package com.pedidos.domain.repository;

import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    void salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(Long id);

    List<Pedido> listarTodos();

    void deletar(Long id);

}
