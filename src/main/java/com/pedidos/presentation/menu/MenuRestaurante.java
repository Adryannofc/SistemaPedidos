package com.pedidos.presentation.menu;

import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuRestaurante {
    public static Scanner scn = new Scanner(System.in);
    private final Usuario usuario;

    public MenuRestaurante(Usuario usuario) {
        this.usuario = usuario;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(usuario.getNome());
            System.out.println(" 1 - Gerenciar produtos");
            System.out.println(" 2 - Ver pedidos");
            System.out.println(" 3 - Configurar horários");
            System.out.println(" 0 - Sair");

            int opcao = EntradaSegura.lerOpcao(scn, 0, 3);

            switch (opcao) {
                case 1:
                    System.out.println("Em construção...");
                    break;

                case 2:
                    System.out.println("Em construção...");
                    break;

                case 3:
                    System.out.println("Em construção...");
                    break;

                case 0:
                    return;

            }
        }
    }
}
