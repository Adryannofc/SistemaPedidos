package com.pedidos.presentation.restaurante;

import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuAreaEntrega {
    private final Scanner scanner;

    public MenuAreaEntrega(Scanner scanner) {
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
                         // TODO: wiring AreaEntregaService (UI-07)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 2:
                    try {
                        // TODO: wiring AreaEntregaService (UI-07)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 3:
                    try {
                        // TODO: wiring AreaEntregaService (UI-07)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 4:
                    try {
                        // TODO: wiring AreaEntregaService (UI-07)
                        System.out.println("Em construção...");
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