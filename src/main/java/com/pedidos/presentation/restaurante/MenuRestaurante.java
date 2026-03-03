package com.pedidos.presentation.restaurante;

import com.pedidos.application.service.CategoriaService;
import com.pedidos.application.service.RestauranteService;
import com.pedidos.domain.model.CategoriaGlobal;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.List;
import java.util.Scanner;

public class MenuRestaurante {
    private final MenuProdutos menuProdutos;
    private final MenuCategoriasCardapio menuCategorias;
    private final MenuAreaEntrega menuAreaEntrega;
    private final MenuHorarios menuHorarios;
    private final MenuHistoricoPedidos menuHistorico;
    private final RestauranteService restauranteService;
    private final CategoriaService categoriaService;
    private final Scanner scanner;

    public MenuRestaurante(MenuProdutos menuProdutos, MenuCategoriasCardapio menuCategorias,
                           MenuAreaEntrega menuAreaEntrega, MenuHorarios menuHorarios,
                           MenuHistoricoPedidos menuHistorico, RestauranteService restauranteService,
                           CategoriaService categoriaService, Scanner scanner) {
        this.menuProdutos = menuProdutos;
        this.menuCategorias = menuCategorias;
        this.menuAreaEntrega = menuAreaEntrega;
        this.menuHorarios = menuHorarios;
        this.menuHistorico = menuHistorico;
        this.restauranteService = restauranteService;
        this.categoriaService = categoriaService;
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
                    exibirMenuPerfil(restauranteLogado);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    TerminalUtils.pausar();
            }
        }
    }

    private void exibirMenuPerfil(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | PERFIL E CONFIGURAÇÕES");
            System.out.println("1 - Visualizar Perfil");
            System.out.println("2 - Editar Dados");
            System.out.println("3 - Editar E-mail");
            System.out.println("4 - Alterar Categoria Global");
            System.out.println("5 - Alterar Senha");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 5);

            switch (opcao) {
                case 1:
                    try {
                        restauranteService.visualizarPerfil(restauranteLogado);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        System.out.print("Nome atual: " + restauranteLogado.getNome() + ". Novo nome: ");
                        String novoNome = scanner.nextLine();

                        System.out.print("CNPJ atual: " + restauranteLogado.getCnpj() + ". Novo CNPJ: ");
                        String novoCnpj = scanner.nextLine();

                        System.out.print("Telefone atual: " + restauranteLogado.getTelefone() + ". Novo telefone: ");
                        String novoTelefone = scanner.nextLine();

                        restauranteService.editarPerfil(restauranteLogado, novoNome, novoCnpj, novoTelefone);
                        System.out.println("Dados atualizados com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        System.out.print("E-mail atual: " + restauranteLogado.getEmail() + ". Novo e-mail: ");
                        String novoEmail = scanner.nextLine();

                        restauranteService.editarEmail(restauranteLogado, novoEmail);
                        System.out.println("E-mail atualizado com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        List<CategoriaGlobal> categorias = categoriaService.listarCategoriasGlobais();
                        if (categorias.isEmpty()) {
                            System.out.println("Nenhuma categoria global disponível.");
                            TerminalUtils.pausar();
                            break;
                        }

                        for (int i = 0; i < categorias.size(); i++) {
                            System.out.println((i + 1) + " - " + categorias.get(i).getNome());
                        }
                        System.out.print("Escolha o número da categoria: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, categorias.size());
                        String novaCategoriaId = categorias.get(num - 1).getId();

                        restauranteService.alterarCategoria(restauranteLogado, novaCategoriaId);
                        System.out.println("Categoria atualizada com sucesso.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        System.out.print("Senha atual: ");
                        String senhaAtual = scanner.nextLine();
                        System.out.print("Nova senha: ");
                        String novaSenha = scanner.nextLine();
                        System.out.print("Confirmar nova senha: ");
                        String confirmacao = scanner.nextLine();

                        if (!novaSenha.equals(confirmacao)) {
                            System.out.println("As senhas não coincidem.");
                        } else {
                            restauranteService.alterarSenha(restauranteLogado, senhaAtual, novaSenha);
                            System.out.println("Senha alterada com sucesso.");
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