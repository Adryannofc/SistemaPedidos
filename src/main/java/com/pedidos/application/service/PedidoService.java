package com.pedidos.application.service;

import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.PedidoRepository;

import java.math.BigDecimal;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(String clienteId, String restauranteId, Carrinho carrinho) {
        Pedido pedido = new Pedido(null, clienteId, restauranteId, BigDecimal.ZERO);
        carrinho.getItens().forEach(pedido::adicionarItem);
        pedido.calcularTotal();
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    public void atualizarStatus(String pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido nao encontrado"));

        validarTransicao(pedido.getStatus(), novoStatus);
        pedido.setStatus(novoStatus);
        pedidoRepository.salvar(pedido);
    }

    private void validarTransicao(StatusPedido atual, StatusPedido novo) {
        switch (atual) {
            case AGUARDANDO_CONFIRMACAO:
                if (novo == StatusPedido.CONFIRMADO || novo == StatusPedido.CANCELADO) return;
                if (novo == StatusPedido.ENTREGUE)
                    throw new IllegalStateException(
                            "Pedido deve ser confirmado antes de ser entregue.");
                break;

            case CONFIRMADO:
                if (novo == StatusPedido.ENTREGUE || novo == StatusPedido.CANCELADO) return;
                if (novo == StatusPedido.AGUARDANDO_CONFIRMACAO)
                    throw new IllegalStateException(
                            "Nao e possivel voltar ao status anterior.");
                break;

            case ENTREGUE:
                throw new IllegalStateException("Pedido ja entregue — status nao pode ser alterado.");

            case CANCELADO:
                throw new IllegalStateException("Pedido cancelado — status nao pode ser alterado.");
        }
        throw new IllegalStateException("Transicao de status invalida.");
    }

    public Pedido buscarPorId(String pedidoId) {
        return pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido nao encontrado"));
    }

    public List<Pedido> listarPorCliente(String clienteId) {
        return pedidoRepository.buscarPorCliente(clienteId);
    }

    public List<Pedido> listarPorRestaurante(String restauranteId) {
        return pedidoRepository.buscarPorRestaurante(restauranteId);
    }
}
