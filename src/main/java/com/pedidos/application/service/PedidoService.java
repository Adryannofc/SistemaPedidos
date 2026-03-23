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

    /**
     * Monstando o pedido final
     * @param clienteId cliente que pediu
     * @param restauranteId restaurante que foi pedido
     * @param carrinho itens do pedido
     * @return o pedido como um todo
     */
    public Pedido criarPedido(String clienteId, String restauranteId, Carrinho carrinho) {
        Pedido pedido = new Pedido(null, clienteId, restauranteId, BigDecimal.ZERO);
        carrinho.getItens().forEach(pedido::adicionarItem);
        pedido.calcularTotal();
        pedidoRepository.salvar(pedido);
        return pedido;
    }

    /**
     * Atualiza o status do pedido por parte do restaurante(ex: se já saiu para entrega)
     * @param pedidoId
     * @param novoStatus
     */
    public void atualizarStatus(String pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido nao encontrado"));

        validarTransicao(pedido.getStatus(), novoStatus);
        pedido.setStatus(novoStatus);
        pedidoRepository.salvar(pedido);
    }

    /**
     * Recebe o estado atual para fazer tratamentos
     * @param atual
     * @param novo
     */
    private void validarTransicao(StatusPedido atual, StatusPedido novo) {
        switch (atual) {
            case AGUARDANDO_CONFIRMACAO:
                if (novo == StatusPedido.CONFIRMADO || novo == StatusPedido.CANCELADO) return;
                break;

            case CONFIRMADO:
                if (novo == StatusPedido.EM_PREPARO || novo == StatusPedido.CANCELADO) return;
                break;

            case EM_PREPARO:
                if (novo == StatusPedido.SAIU_PARA_ENTREGA || novo == StatusPedido.CANCELADO) return;
                break;

            case SAIU_PARA_ENTREGA:
                if (novo == StatusPedido.ENTREGUE) return;
                break;

            case ENTREGUE:
                throw new IllegalStateException("Pedido já entregue — status não pode ser alterado.");

            case CANCELADO:
                throw new IllegalStateException("Pedido cancelado — status não pode ser alterado.");
        }
        throw new IllegalStateException("Transição de status inválida.");
    }

    /**
     * Metodo para pegar o pedido(usado geralmente para realizar alterações)
     * @param pedidoId
     * @return pedido pelo id
     */
    public Pedido buscarPorId(String pedidoId) {
        return pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido nao encontrado"));
    }

    /**
     * Usado para listar pedidos por cliente no menu do restaurante
     * @param clienteId
     * @return um pedido relacionado a tal cliente pelo id
     */
    public List<Pedido> listarPorCliente(String clienteId) {
        return pedidoRepository.buscarPorCliente(clienteId);
    }

    public List<Pedido> listarPorRestaurante(String restauranteId) {
        return pedidoRepository.buscarPorRestaurante(restauranteId);
    }
}
