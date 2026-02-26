package com.pedidos.presentation.menu;

import com.pedidos.presentation.util.EntradaSegura;

import java.util.Scanner;

public class MenuAdmin {
    public static Scanner scn = new Scanner(System.in);

    public static void exibir(){

        while (true){

            System.out.println(" 1 - Gerenciar usuários");
            System.out.println(" 2 - Gerenciar categorias");
            System.out.println(" 0 - Sair");

            int opcao = EntradaSegura.lerOpcao(scn,0,2);

            switch (opcao){

                case 1 :
                    System.out.println("Em construção...");
                    break;

                case 2 :
                    System.out.println("Em construção...");
                    break;

                case 0 :
                    return;
            }
        }
    }
}
