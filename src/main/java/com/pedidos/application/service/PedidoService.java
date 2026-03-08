package com.pedidos.application.service;

import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.PedidoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final AreaEntregaService areaEntregaService;
    private final HorarioService horarioService;
    private final ProdutoService produtoService;
    private final EnderecoService enderecoService;

    public PedidoService(PedidoRepository pedidoRepository,
                         AreaEntregaService areaEntregaService,
                         HorarioService horarioService,
                         ProdutoService produtoService,
                         EnderecoService enderecoService) {
        this.pedidoRepository   = pedidoRepository;
        this.areaEntregaService = areaEntregaService;
        this.horarioService     = horarioService;
        this.produtoService     = produtoService;
        this.enderecoService    = enderecoService;
    }

    public Pedido criarPedido(Cliente cliente, Carrinho carrinho, String enderecoId) {

        String restauranteId = carrinho.getRestauranteId();

        // 1. Restaurante aberto?
        if (!horarioService.restauranteEstaAberto(restauranteId, LocalDateTime.now())) {
            throw new IllegalStateException(
                    "Restaurante fechado no momento. Verifique os horarios de funcionamento.");
        }

        // 2. Produtos todos ativos?
        List<String> inativos = carrinho.getItens().stream()
                .map(item -> produtoService.buscarPorId(item.getProdutoId()))
                .filter(p -> !p.isStatusAtivo())
                .map(Produto::getNome)
                .collect(Collectors.toList());

        if (!inativos.isEmpty()) {
            throw new IllegalStateException(
                    "Produto(s) indisponivel(is): " + inativos + ". Remova do carrinho.");
        }

        // 3. Endereço pertence ao cliente?
        Endereco endereco = enderecoService.buscarPorId(enderecoId);
        if (!endereco.getClienteId().equals(cliente.getId())) {
            throw new IllegalArgumentException("Endereco nao pertence ao cliente.");
        }

        // 4. Bairro atendido + taxa
        BigDecimal taxaEntrega = areaEntregaService.buscarTaxaPorBairro(
                restauranteId, endereco.getBairro());


        // 6. Persiste
        Pedido pedido = new Pedido(null, cliente.getId(), restauranteId, taxaEntrega);
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
                if (novo == StatusPedido.EM_PREPARO || novo == StatusPedido.CANCELADO) return;
                if (novo == StatusPedido.AGUARDANDO_CONFIRMACAO)
                    throw new IllegalStateException(
                            "Nao e possivel voltar ao status anterior.");
                break;

            case EM_PREPARO:
                if (novo == StatusPedido.SAIU_PARA_ENTREGA || novo == StatusPedido.CANCELADO) return;
                if (novo == StatusPedido.AGUARDANDO_CONFIRMACAO || novo == StatusPedido.CONFIRMADO)
                    throw new IllegalStateException(
                            "Nao e possivel voltar ao status anterior.");
                break;

            case SAIU_PARA_ENTREGA:
                if (novo == StatusPedido.ENTREGUE) return;
                if (novo == StatusPedido.AGUARDANDO_CONFIRMACAO
                        || novo == StatusPedido.EM_PREPARO
                        || novo == StatusPedido.CONFIRMADO)
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
}