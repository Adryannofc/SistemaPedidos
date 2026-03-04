package com.pedidos.application.service;

import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.AreaEntregaRepository;
import com.pedidos.domain.repository.PedidoRepository;
import com.pedidos.domain.repository.ProdutoRepository;

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
    /*** private final EnderecoService enderecoService*/

    public PedidoService(PedidoRepository pedidoRepository,
                         AreaEntregaService areaEntregaService,
                         HorarioService horarioService,
                         ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.areaEntregaService = areaEntregaService;
        this.horarioService = horarioService;
        this.produtoService = produtoService;
        /** this.enderecoService = enderecoService*/
    }

    /**

     public Pedido criarPedido(Cliente cliente,
     Carrinho carrinho,
     String enderecoId) {

     ///Restaurante Aberto///

     String restauranteId = carrinho.getRestauranteId(); /// teste()

     boolean restauranteAberto = horarioService.restauranteEstaAberto(restauranteId,
     LocalDateTime.now());

     if (!restauranteAberto) {
     throw new IllegalStateException("Restaurante fechado no momento. Verifique os horários de funcionamento.");
     }

     ///Produtos Inativos///

     List<String> produtosInativos = carrinho.getItens().stream()
     .map(item -> produtoService.buscarPorId(item.getProdutoId()))
     .filter(produto -> !produto.isStatusAtivo())
     .map(Produto::getNome)
     .collect(Collectors.toList());

     if (!produtosInativos.isEmpty()) {
     throw new IllegalStateException(
     "Produto(s) indisponível(is): " + produtosInativos +
     ". Remova do carrinho para continuar");
     }
     } */

    /**Bairro atendido + taxa


     Endereco endereco = enderecoService.buscarPorId(enderecoId);

     if(endereco == null) {
     throw new IllegalArgumentException("Endereço não encontrado");
     }

     if(!endereco.getClienteId().equals(cliente.getId())) {
     throw new IllegalArgumentException("Endereço não pertence ao cliente");
     }

     AreaEntrega areaEntrega =
     areaEntregaService.buscarAreaPorBairro(restauranteId, endereco.getBairro());

     BigDecimal taxaEntrega = areaEntrega.getTaxaEntrega();



     BigDecimal subtotal = carrinho.getItens().stream()
     .map(item -> item.getPrecoUnitario())
     .multiply(BigDecimal.valueOf(item.getQuantidade()))
     .reduce(BigDecimal, BigDecimal::add);



     BigDecimal total = subtotal
     .add(taxaEntrega)
     .setScale(2, RoundingMode.HALF_UP);

     Pedido pedido = new Pedido(
     cliente.getId(),
     restauranteId,
     StatusPedido.AGUARDANDO_CONFIRMACAO,
     LocalDateTime.now(),
     taxaEntrega,
     total
     );

     /** conversão ItemCarrinho -> ItemPedido

     List<ItemPedido> itensPedido = carrinho.getItens().stream()
     .map(i -> new ItemPedido(
     i.getProdutoId(),
     i.getNomeProduto(),
     i.getQuantidade(),
     i.getPrecoUnitario()))
     .collect(Collectors.toList());

     pedido.setItens(itensPedido);

     pedidoRepository.salvar(pedido);

     return pedido;

     */

    public void atualizarStatus(String pedidoId, StatusPedido novoStatus) {

        Pedido pedido = pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        validarTransicao(pedido.getStatus(), novoStatus);

        pedido.setStatus(novoStatus);

        pedidoRepository.salvar(pedido);
    }

    private void validarTransicao(StatusPedido atual, StatusPedido novo) {

        switch (atual) {

            case AGUARDANDO_CONFIRMACAO:

                if (novo == StatusPedido.CONFIRMADO ||
                        novo == StatusPedido.CANCELADO) {
                    return;
                }

                if (novo == StatusPedido.ENTREGUE) {
                    throw new IllegalStateException(
                            "Transição inválida: pedido deve ser confirmado antes de ser entregue");
                }

                break;

            case CONFIRMADO:

                if (novo == StatusPedido.ENTREGUE ||
                        novo == StatusPedido.CANCELADO) {
                    return;
                }

                if (novo == StatusPedido.AGUARDANDO_CONFIRMACAO) {
                    throw new IllegalStateException(
                            "Transição inválida: não é possível voltar ao status anterior");
                }

                break;

            case ENTREGUE:
                throw new IllegalStateException(
                        "Pedido já entregue — status não pode ser alterado");

            case CANCELADO:
                throw new IllegalStateException(
                        "Pedido cancelado — status não pode ser alterado");
        }

        throw new IllegalStateException("Transição de status inválida");
    }

    /**
     * busca por id
     */
    public Pedido buscarPorId(String pedidoId) {

        return pedidoRepository.buscarPorId(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

}

