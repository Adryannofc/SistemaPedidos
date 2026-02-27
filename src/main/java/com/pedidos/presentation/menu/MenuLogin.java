package com.pedidos.presentation.menu;

import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuLogin {
    private final AutenticacaoService autenticacaoService;
    private final Scanner scan = new Scanner(System.in);
    public MenuLogin(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                    LOGIN                                     ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");

            String email = EntradaSegura.lerString(scan, "E-MAIL: ");
            String senha = EntradaSegura.lerString(scan, "SENHA : ");

            try {
                Usuario usuario = autenticacaoService.autenticar(email, senha);
                System.out.println("Bem-vindo, " + usuario.getNome() + "!");

                switch (usuario.getTipoUsuario()) {
                    case ADMIN -> new MenuAdmin(usuario).iniciar();
                    case RESTAURANTE -> new MenuRestaurante(usuario).iniciar();
                    case CLIENTE -> new MenuCliente(usuario).iniciar();
                }
            } catch (RuntimeException e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        }
    }
}
