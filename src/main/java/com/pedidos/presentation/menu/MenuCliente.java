package com.pedidos.presentation.menu;

import com.pedidos.application.service.ClienteService;
import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;
import java.util.Scanner;

public class MenuCliente {
    Scanner scn = new Scanner(System.in);
    private final Usuario usuario;
    private final ClienteService clienteService;
    public MenuCliente(Usuario usuario, ClienteService clienteService){
        this.usuario = usuario;
        this.clienteService = clienteService;
    }

    public void iniciar(){
        while (true){
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(usuario.getNome());
            System.out.println(" 1 - Ver restaurantes");
            System.out.println(" 2 - Fazer pedido");
            System.out.println(" 3 - Meus pedidos");
            System.out.println(" 0 - Sair");

            int opcao = EntradaSegura.lerOpcao(scn,0,3);

            switch (opcao){
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
