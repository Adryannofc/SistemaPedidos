package com.pedidos.presentation.admin;
import com.pedidos.application.service.AdminService;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuCategorias {
    private final AdminService adminService;
    private final Scanner scanner;

    public MenuCategorias(AdminService adminService, Scanner scanner){
        this.adminService = adminService;
        this.scanner = scanner;

    }

    public void exibir(){

        while (true){
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("CATEGORIAS GLOBAIS");
            System.out.println("1 - Listar todas as categorias");
            System.out.println("2 - Adicionar nova categoria");
            System.out.println("3 - Editar categoria");
            System.out.println("4 - Remover categoria");
            System.out.println("0 - Voltar ao MenuAdmin");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner,0,4);

            switch (opcao) {
                case 1:
                    try {
                        // TODO: Listar todas as categorias
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        // TODO: Adicionar nova categoria
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        // TODO: Editar categoria
                        System.out.println("Em construção...");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        // TODO: Remover categoria
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
