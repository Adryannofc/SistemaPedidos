package com.pedidos.presentation.menu;

import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuAdmin {
    public static Scanner scn = new Scanner(System.in);
    private final Usuario usuario;

    public MenuAdmin(Usuario usuario) {
        this.usuario = usuario;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(usuario.getNome());
            System.out.println(" 1 - Gerenciar usuários");
            System.out.println(" 2 - Gerenciar categorias");
            System.out.println(" 0 - Sair");

            int opcao = EntradaSegura.lerOpcao(scn, 0, 2);

            switch (opcao) {
                case 1 -> System.out.println("Em construção...");
                case 2 -> System.out.println("Em construção...");
                case 0 -> { return; }
            }
            TerminalUtils.pausar();
        }
    }
}
