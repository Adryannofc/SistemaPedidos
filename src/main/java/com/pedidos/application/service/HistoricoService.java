package com.pedidos.application.service;

import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.PedidoRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de consulta pura para operações de histórico de pedidos.
 * Sem mutações — apenas leitura, busca, ordenação e filtragem.
 */
public class HistoricoService {

    private final PedidoRepository pedidoRepository;

    public HistoricoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Lista todos os pedidos de um cliente ordenados por data DESC.
     */
    public List<Pedido> listarPedidosPorCliente(String clienteId) {
        List<Pedido> pedidos = pedidoRepository.buscarPorCliente(clienteId);

        return pedidos.stream()
                .sorted(Comparator.comparing(Pedido::getDataPedido).reversed())
                .toList();
    }

    /**
     * Lista todos os pedidos de um restaurante ordenados por data DESC.
     */
    public List<Pedido> listarPedidosPorRestaurante(String restauranteId) {
        List<Pedido> pedidos = pedidoRepository.buscarPorRestaurante(restauranteId);

        return pedidos.stream()
                .sorted(Comparator.comparing(Pedido::getDataPedido).reversed())
                .toList();
    }

    /**
     * Filtra pedidos de um restaurante por status, ordenados por data DESC.
     */
    public List<Pedido> filtrarPorStatus(String restauranteId, StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.buscarPorRestaurante(restauranteId);

        return pedidos.stream()
                .filter(p -> p.getStatus() == status)
                .sorted(Comparator.comparing(Pedido::getDataPedido).reversed())
                .toList();
    }

    /**
     * Filtra pedidos de um cliente por status, ordenados por data DESC.
     */
    public List<Pedido> filtrarPedidosClientePorStatus(String clienteId, StatusPedido status) {
        List<Pedido> pedidos = pedidoRepository.buscarPorCliente(clienteId);

        return pedidos.stream()
                .filter(p -> p.getStatus() == status)
                .sorted(Comparator.comparing(Pedido::getDataPedido).reversed())
                .toList();
    }

    /**
     * Busca um pedido específico por ID.
     * @throws IllegalArgumentException se pedido não encontrado
     */
    public Pedido buscarPedidoPorId(String pedidoId) {
        return pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    /**
     * Lista pedidos ativos (não entregues e não cancelados) de um restaurante.
     * Ordenados por data DESC.
     */
    public List<Pedido> listarPedidosAtivosRestaurante(String restauranteId) {
        List<Pedido> pedidos = pedidoRepository.buscarPorRestaurante(restauranteId);

        return pedidos.stream()
                .filter(p -> p.getStatus() != StatusPedido.ENTREGUE && p.getStatus() != StatusPedido.CANCELADO)
                .sorted(Comparator.comparing(Pedido::getDataPedido).reversed())
                .toList();
    }

    /**
     * Conta pedidos agrupados por status de um restaurante.
     * @return mapa com status como chave e contagem como valor
     */
    public Map<StatusPedido, Long> contarPedidosPorStatus(String restauranteId) {
        List<Pedido> pedidos = pedidoRepository.buscarPorRestaurante(restauranteId);

        return pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getStatus, Collectors.counting()));
    }

    /**
     * Calcula total faturado (apenas pedidos entregues) de um restaurante.
     * @return BigDecimal com total ou BigDecimal.ZERO se nenhum pedido entregue
     */
    public BigDecimal calcularTotalFaturado(String restauranteId) {
        List<Pedido> pedidos = pedidoRepository.buscarPorRestaurante(restauranteId);

        return pedidos.stream()
                .filter(p -> p.getStatus() == StatusPedido.ENTREGUE)
                .map(Pedido::calcularTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
