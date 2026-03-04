package com.pedidos.presentation.restaurante;

import com.pedidos.application.service.AreaEntregaService;
import com.pedidos.domain.model.AreaEntrega;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuAreaEntrega {
    private final AreaEntregaService areaEntregaService;
    private final Scanner scanner;

    public MenuAreaEntrega(AreaEntregaService areaEntregaService, Scanner scanner) {
        this.areaEntregaService = areaEntregaService;
        this.scanner = scanner;
    }

    public void exibir(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | ÁREA DE ENTREGA");
            System.out.println("1 - Listar Áreas");
            System.out.println("2 - Adicionar Área");
            System.out.println("3 - Editar Área");
            System.out.println("4 - Remover Área");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 4);

            switch (opcao) {
                case 1:
                    try {
                        List<AreaEntrega> areas = areaEntregaService.listarAreasPorRestaurante(restauranteLogado.getId());
                        if (areas.isEmpty()) {
                            System.out.println("Nenhuma área de entrega cadastrada.");
                        } else {
                            NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                            for (int i = 0; i < areas.size(); i++) {
                                AreaEntrega a = areas.get(i);
                                System.out.println((i + 1) + " - Bairro: " + a.getBairro()
                                        + " | Taxa: " + fmt.format(a.getTaxaEntrega())
                                        + " | Distância: " + a.getDistanciaKm() + " km"
                                        + " | Previsão: " + a.getPrevisaoMinutos() + " min");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        System.out.print("Bairro: ");
                        String bairro = scanner.nextLine();

                        if (bairro.isBlank()) {
                            System.out.println("O bairro não pode ser vazio.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Distância em km: ");
                        String distanciaStr = scanner.nextLine();
                        BigDecimal distancia;
                        try {
                            distancia = new BigDecimal(distanciaStr.replace(",", "."));
                        } catch (Exception e) {
                            System.out.println("Distância inválida.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Taxa de entrega: ");
                        String taxaStr = scanner.nextLine();
                        BigDecimal taxa;
                        try {
                            taxa = new BigDecimal(taxaStr.replace(",", "."));
                        } catch (Exception e) {
                            System.out.println("Taxa inválida.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Previsão em minutos: ");
                        int previsao = EntradaSegura.lerOpcao(scanner, 1, 999);

                        areaEntregaService.criarAreaEntrega(restauranteLogado.getId(), bairro, distancia, taxa, previsao);
                        System.out.println("Área de entrega para " + bairro + " cadastrada com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        List<AreaEntrega> areas = areaEntregaService.listarAreasPorRestaurante(restauranteLogado.getId());
                        if (areas.isEmpty()) {
                            System.out.println("Nenhuma área para editar.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < areas.size(); i++) {
                            AreaEntrega a = areas.get(i);
                            System.out.println((i + 1) + " - " + a.getBairro());
                        }
                        System.out.print("Escolha o número da área: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, areas.size());
                        AreaEntrega selecionada = areas.get(num - 1);

                        System.out.print("Bairro atual: " + selecionada.getBairro() + ". Novo bairro: ");
                        String novoBairro = scanner.nextLine();

                        System.out.print("Distância atual: " + selecionada.getDistanciaKm() + " km. Nova distância: ");
                        String distanciaStr = scanner.nextLine();
                        BigDecimal novaDistancia;
                        try {
                            novaDistancia = new BigDecimal(distanciaStr.replace(",", "."));
                        } catch (Exception e) {
                            System.out.println("Distância inválida.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Taxa atual: " + selecionada.getTaxaEntrega() + ". Nova taxa: ");
                        String taxaStr = scanner.nextLine();
                        BigDecimal novaTaxa;
                        try {
                            novaTaxa = new BigDecimal(taxaStr.replace(",", "."));
                        } catch (Exception e) {
                            System.out.println("Taxa inválida.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.print("Previsão atual: " + selecionada.getPrevisaoMinutos() + " min. Nova previsão: ");
                        int novaPrevisao = EntradaSegura.lerOpcao(scanner, 1, 999);

                        areaEntregaService.editarAreaEntrega(selecionada.getId(), novoBairro, novaDistancia, novaTaxa, novaPrevisao);
                        System.out.println("Área de entrega atualizada com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        List<AreaEntrega> areas = areaEntregaService.listarAreasPorRestaurante(restauranteLogado.getId());
                        if (areas.isEmpty()) {
                            System.out.println("Nenhuma área de entrega cadastrada.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < areas.size(); i++) {
                            System.out.println((i + 1) + " - " + areas.get(i).getBairro());
                        }
                        System.out.print("Escolha o número da área: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, areas.size());
                        AreaEntrega selecionada = areas.get(num - 1);

                        System.out.print("Tem certeza? Clientes deste bairro não poderão mais fazer pedidos. (S/N): ");
                        String confirmacao = scanner.nextLine();
                        if (confirmacao.equalsIgnoreCase("S")) {
                            areaEntregaService.removerAreaEntrega(selecionada.getId());
                            System.out.println("Área de entrega removida com sucesso.");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
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
}