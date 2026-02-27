package com.pedidos.presentation.menu;

import com.pedidos.application.service.AdminService;
import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuAdmin {
    private static Scanner scn = new Scanner(System.in);
    private final Usuario usuario;
    private final AdminService adminService;
    public MenuAdmin(Usuario usuario, AdminService adminService) {
        this.usuario = usuario;
        this.adminService = adminService;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(usuario.getNome());
            System.out.println(" 1 - Gerenciar usuários");
            System.out.println(" 2 - Gerenciar categorias");
            System.out.println(" 9 - Configuracoes");
            System.out.println(" 0 - Sair");

            int opcao = EntradaSegura.lerOpcao(scn, 0, 9);

            switch (opcao) {
                case 1 -> System.out.println("Em construção...");
                case 2 -> System.out.println("Em construção...");
                case 9 -> telaPerfil();
                case 0 -> { return; }
            }
            TerminalUtils.pausar();
        }
    }

    public void telaPerfil() {
        TerminalUtils.limparTela();
        TerminalUtils.cabecalho("PERFIL");
        adminService.visualizarPerfil(usuario);

        System.out.println("[1] Mudar Senha");
        System.out.println("[0] Voltar");
        System.out.print("- Opcao: ");

        switch (EntradaSegura.lerOpcao(scn, 0, 1)) {
            case 1: trocarSenha();
                break;
            case 0: return;
        }
    }

    public void trocarSenha() {
        String senhaAtual = EntradaSegura.lerString(scn, "Senha atual: ");
        String novaSenha = EntradaSegura.lerString(scn, "Nova senha: ");
        String confirmacao = EntradaSegura.lerString(scn, "Confirme a nova senha: ");
        try {
            adminService.alterarSenha(usuario, senhaAtual, novaSenha, confirmacao);
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }
}
