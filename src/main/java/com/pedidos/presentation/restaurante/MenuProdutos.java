package com.pedidos.presentation.restaurante;

import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuProdutos {
    private final Scanner scanner;

    public MenuProdutos(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exibir(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | PRODUTOS");
            System.out.println("1 - Listar Produtos");
            System.out.println("2 - Adicionar Produto");
            System.out.println("3 - Editar Produto");
            System.out.println("4 - Ativar/Inativar Produto");
            System.out.println("5 - Remover Produto");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 5);

            switch (opcao) {
                case 1:
                    try {
                        // TODO: wiring ProdutoService (UI-06)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 2:
                    try {
                        // TODO: wiring ProdutoService (UI-06)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 3:
                    try {
                        // TODO: wiring ProdutoService (UI-06)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 4:
                    try {
                        // TODO: wiring ProdutoService (UI-06)
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;
                case 5:
                    try {
                        // TODO: wiring ProdutoService (UI-06)
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