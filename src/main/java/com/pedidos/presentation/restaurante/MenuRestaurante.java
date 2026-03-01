package com.pedidos.presentation.restaurante;

import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuRestaurante {
    private final MenuProdutos menuProdutos;
    private final MenuCategoriasCardapio menuCategorias;
    private final MenuAreaEntrega menuAreaEntrega;
    private final MenuHorarios menuHorarios;
    private final MenuHistoricoPedidos menuHistorico;
    private final Scanner scanner;

    public MenuRestaurante(MenuProdutos menuProdutos, MenuCategoriasCardapio menuCategorias,
                           MenuAreaEntrega menuAreaEntrega, MenuHorarios menuHorarios,
                           MenuHistoricoPedidos menuHistorico, Scanner scanner) {
        this.menuProdutos = menuProdutos;
        this.menuCategorias = menuCategorias;
        this.menuAreaEntrega = menuAreaEntrega;
        this.menuHorarios = menuHorarios;
        this.menuHistorico = menuHistorico;
        this.scanner = scanner;
    }

    public void exibir(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("MENU RESTAURANTE | " + restauranteLogado.getNome());
            System.out.println("1 - Produtos");
            System.out.println("2 - Categorias do Cardápio");
            System.out.println("3 - Área de Entrega");
            System.out.println("4 - Horários de Funcionamento");
            System.out.println("5 - Histórico de Pedidos");
            System.out.println("6 - Perfil e Configurações");
            System.out.println("0 - Logout");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 6);

            switch (opcao) {
                case 1:
                    menuProdutos.exibir(restauranteLogado);
                    break;
                case 2:
                    menuCategorias.exibir(restauranteLogado);
                    break;
                case 3:
                    menuAreaEntrega.exibir(restauranteLogado);
                    break;
                case 4:
                    menuHorarios.exibir(restauranteLogado);
                    break;
                case 5:
                    menuHistorico.exibir(restauranteLogado);
                    break;
                case 6:
                    try {
                        // TODO: Perfil e Configurações
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