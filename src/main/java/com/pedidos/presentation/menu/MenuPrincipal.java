package com.pedidos.presentation.menu;
import com.pedidos.presentation.util.EntradaSegura;

import java.util.Scanner;

public class MenuPrincipal {
    static Scanner scn = new Scanner(System.in);

    public static void exibir(){

        while (true){

            System.out.println(" 1 - Login");
            System.out.println(" 2 - Sair.");

            int opcao = EntradaSegura.lerOpcao(scn, 1,2);

            switch (opcao){
                case 1:
                    System.out.print("Email: ");
                    String email = scn.nextLine();

                    System.out.print("Senha: ");
                    String senha = scn.nextLine();
                    break;

                case 2:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
            }
        }
    }
}
