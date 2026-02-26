package com.pedidos.presentation.menu;

import com.pedidos.presentation.util.EntradaSegura;

import java.util.Scanner;

public class MenuCliente {

    public static Scanner scn = new Scanner(System.in);

    public static void exibir(){

        while (true){
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
