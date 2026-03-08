package com.pedidos.presentation.restaurante;

import com.pedidos.application.service.HistoricoService;
import com.pedidos.application.service.PedidoService;
import com.pedidos.domain.enums.StatusPedido;
import com.pedidos.domain.model.ItemPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class MenuHistoricoPedidos {
    private final HistoricoService historicoService;
    private final PedidoService pedidoService;
    private final Scanner scanner;

    public MenuHistoricoPedidos(HistoricoService historicoService, PedidoService pedidoService, Scanner scanner) {
        this.historicoService = historicoService;
        this.pedidoService = pedidoService;
        this.scanner = scanner;
    }

    public void exibir(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | HISTÓRICO DE PEDIDOS");
            System.out.println("1 - Listar Todos");
            System.out.println("2 - Pedidos Ativos");
            System.out.println("3 - Filtrar por Status");
            System.out.println("4 - Ver Detalhes");
            System.out.println("5 - Atualizar Status");
            System.out.println("6 - Ver Resumo");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 6);

            switch (opcao) {
                case 1:
                    try {
                        List<Pedido> pedidos = historicoService.listarPedidosPorRestaurante(restauranteLogado.getId());
                        if (pedidos.isEmpty()) {
                            System.out.println("Nenhum pedido encontrado.");
                        } else {
                            exibirListaPedidos(pedidos);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        List<Pedido> ativos = historicoService.listarPedidosAtivosRestaurante(restauranteLogado.getId());
                        if (ativos.isEmpty()) {
                            System.out.println("Nenhum pedido ativo no momento.");
                        } else {
                            exibirListaPedidos(ativos);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        System.out.println("Filtrar por status:");
                        System.out.println("1 - AGUARDANDO CONFIRMAÇÃO");
                        System.out.println("2 - CONFIRMADO");
                        System.out.println("3 - EM PREPARO");
                        System.out.println("4 - SAIU PARA ENTREGA");
                        System.out.println("5 - ENTREGUE");
                        System.out.println("6 - CANCELADO");
                        System.out.print("Escolha: ");
                        int numStatus = EntradaSegura.lerOpcao(scanner, 1, 6);

                        StatusPedido[] statusOpcoes = {
                                StatusPedido.AGUARDANDO_CONFIRMACAO,
                                StatusPedido.CONFIRMADO,
                                StatusPedido.EM_PREPARO,
                                StatusPedido.SAIU_PARA_ENTREGA,
                                StatusPedido.ENTREGUE,
                                StatusPedido.CANCELADO
                        };
                        StatusPedido statusSelecionado = statusOpcoes[numStatus - 1];

                        List<Pedido> filtrados = historicoService.filtrarPorStatus(restauranteLogado.getId(), statusSelecionado);
                        if (filtrados.isEmpty()) {
                            System.out.println("Nenhum pedido com status " + statusSelecionado + ".");
                        } else {
                            exibirListaPedidos(filtrados);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        List<Pedido> pedidos = historicoService.listarPedidosPorRestaurante(restauranteLogado.getId());
                        if (pedidos.isEmpty()) {
                            System.out.println("Nenhum pedido encontrado.");
                            TerminalUtils.pausar();
                            break;
                        }

                        exibirListaPedidos(pedidos);
                        System.out.print("Escolha o número do pedido: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, pedidos.size());
                        Pedido selecionado = pedidos.get(num - 1);

                        exibirDetalhesPedido(selecionado);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        List<Pedido> ativos = historicoService.listarPedidosAtivosRestaurante(restauranteLogado.getId());
                        if (ativos.isEmpty()) {
                            System.out.println("Nenhum pedido ativo para atualizar.");
                            TerminalUtils.pausar();
                            break;
                        }

                        exibirListaPedidos(ativos);
                        System.out.print("Escolha o número do pedido: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, ativos.size());
                        Pedido selecionado = ativos.get(num - 1);

                        System.out.println("Status atual: " + selecionado.getStatus());
                        System.out.println("Opções:");

                        if (selecionado.getStatus() == StatusPedido.AGUARDANDO_CONFIRMACAO) {
                            System.out.println("1 - CONFIRMADO");
                            System.out.println("2 - CANCELADO");
                            System.out.print("Escolha: ");
                            int opcaoStatus = EntradaSegura.lerOpcao(scanner, 1, 2);
                            StatusPedido novoStatus = opcaoStatus == 1 ? StatusPedido.CONFIRMADO : StatusPedido.CANCELADO;

                            if (novoStatus == StatusPedido.CANCELADO) {
                                System.out.print("Confirma cancelamento? (S/N): ");
                                String confirmacao = scanner.nextLine();
                                if (!confirmacao.equalsIgnoreCase("S")) {
                                    System.out.println("Operação cancelada.");
                                    TerminalUtils.pausar();
                                    break;
                                }
                            }

                            pedidoService.atualizarStatus(selecionado.getId(), novoStatus);
                            System.out.println("Status atualizado para " + novoStatus + " com sucesso.");

                        } else if (selecionado.getStatus() == StatusPedido.CONFIRMADO) {
                            System.out.println("1 - ENTREGUE");
                            System.out.println("2 - CANCELADO");
                            System.out.print("Escolha: ");
                            int opcaoStatus = EntradaSegura.lerOpcao(scanner, 1, 2);
                            StatusPedido novoStatus = opcaoStatus == 1 ? StatusPedido.ENTREGUE : StatusPedido.CANCELADO;

                            if (novoStatus == StatusPedido.CANCELADO) {
                                System.out.print("Confirma cancelamento? (S/N): ");
                                String confirmacao = scanner.nextLine();
                                if (!confirmacao.equalsIgnoreCase("S")) {
                                    System.out.println("Operação cancelada.");
                                    TerminalUtils.pausar();
                                    break;
                                }
                            }

                            pedidoService.atualizarStatus(selecionado.getId(), novoStatus);
                            System.out.println("Status atualizado para " + novoStatus + " com sucesso.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 6:
                    try {
                        Map<StatusPedido, Long> contagem = historicoService.contarPedidosPorStatus(restauranteLogado.getId());
                        System.out.println("AGUARDANDO: " + contagem.getOrDefault(StatusPedido.AGUARDANDO_CONFIRMACAO, 0L)
                                + " | CONFIRMADO: " + contagem.getOrDefault(StatusPedido.CONFIRMADO, 0L)
                                + " | EM PREPARO: " + contagem.getOrDefault(StatusPedido.EM_PREPARO, 0L)
                                + " | SAIU PARA ENTREGA: " + contagem.getOrDefault(StatusPedido.SAIU_PARA_ENTREGA, 0L)
                                + " | ENTREGUE: " + contagem.getOrDefault(StatusPedido.ENTREGUE, 0L)
                                + " | CANCELADO: " + contagem.getOrDefault(StatusPedido.CANCELADO, 0L));

                        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                        System.out.println("Total faturado (pedidos entregues): "
                                + fmt.format(historicoService.calcularTotalFaturado(restauranteLogado.getId())));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    TerminalUtils.pausar();
            }
        }
    }

    private void exibirListaPedidos(List<Pedido> pedidos) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            System.out.println((i + 1) + " - ID: " + p.getId().substring(0, 8)
                    + " | " + p.getDataPedido().format(fmt)
                    + " | " + p.getStatus()
                    + " | " + moeda.format(p.calcularTotal()));
        }
    }

    private void exibirDetalhesPedido(Pedido pedido) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        System.out.println("ID: " + pedido.getId());
        System.out.println("Data: " + pedido.getDataPedido().format(fmt));
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("Cliente ID: " + pedido.getClienteId());
        System.out.println("Itens:");
        for (ItemPedido item : pedido.getItens()) {
            System.out.println("  " + item.getNomeProduto()
                    + " x" + item.getQuantidade()
                    + " — " + moeda.format(item.calcularSubtotal()));
        }
        System.out.println("Taxa de entrega: " + moeda.format(pedido.getTaxaEntrega()));
        System.out.println("Total: " + moeda.format(pedido.calcularTotal()));
    }
}