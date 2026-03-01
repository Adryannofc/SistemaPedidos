package com.pedidos.presentation.admin;

import com.pedidos.application.service.AdminService;
import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.List;
import java.util.Scanner;

public class MenuAdmin {
    private final AdminService adminService;
    private final Scanner scanner;
    private final MenuCategorias menuCategorias;

    public MenuAdmin(AdminService adminService, Scanner scanner, MenuCategorias menuCategorias) {
        this.adminService = adminService;
        this.scanner = scanner;
        this.menuCategorias = menuCategorias;
    }

    public void exibir(Admin adminLogado) {

        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(adminLogado.getNome());
            System.out.println("1 - Listar Restaurantes");
            System.out.println("2 - Aprovar Restaurante");
            System.out.println("3 - Bloquear Restaurante");
            System.out.println("4 - Remover Restaurante");
            System.out.println("5 - Gerenciar Categorias Globais");
            System.out.println("6 - Alterar Senha");
            System.out.println("7 - Visualizar Perfil");
            System.out.println("0 - Logout");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 7);

            switch (opcao) {
                case 1:
                    try {
                        List<Restaurante> restaurantes = adminService.listarRestaurantes();
                        if (restaurantes.isEmpty()) {
                            System.out.println("Nenhum restaurante cadastrado.");
                        } else {
                            for (Restaurante r : restaurantes) {
                                System.out.println("ID: " + r.getId() + " | Nome: " + r.getNome() + " | Ativo: " + r.isStatusAtivo());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        System.out.print("Digite o ID do restaurante: ");
                        String id = scanner.nextLine();
                        adminService.aprovarRestaurante(id);
                        System.out.println("Restaurante aprovado com sucesso!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        System.out.print("Digite o ID do restaurante: ");
                        String id = scanner.nextLine();
                        adminService.bloquearRestaurante(id);
                        System.out.println("Restaurante bloqueado com sucesso!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        System.out.print("Digite o ID do restaurante: ");
                        String id = scanner.nextLine();
                        adminService.removerRestaurante(id);
                        System.out.println("Restaurante removido com sucesso!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        menuCategorias.exibir();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 6:
                    try {
                        System.out.print("Senha atual: ");
                        String senhaAtual = scanner.nextLine();
                        System.out.print("Nova senha: ");
                        String novaSenha = scanner.nextLine();
                        System.out.print("Confirmar nova senha: ");
                        String confirmacao = scanner.nextLine();
                        adminService.alterarSenha(adminLogado, senhaAtual, novaSenha, confirmacao);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 7:
                    try {
                        adminService.visualizarPerfil(adminLogado);
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