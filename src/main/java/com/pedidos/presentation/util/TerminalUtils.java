package com.pedidos.presentation.util;

import java.util.Scanner;

public class TerminalUtils {
    static Scanner scn = new Scanner(System.in);

    public static void limparTela(){
            for (int i = 0; i < 15; i++) {
                System.out.println();
            }
    }
    public static void cabecalho(String titulo){

        System.out.println("╔═══════════════════════════════════╗");
        System.out.printf("║ %-33s ║\n", centralizarTexto(33,titulo));
        System.out.println("╚═══════════════════════════════════╝");

    }

    public static String centralizarTexto(int largura, String titulo){
        int espacos = (largura - titulo.length()) / 2;
        String textoCentralizado = " ".repeat(espacos) + titulo;
        return textoCentralizado;
    }

    public static void pausar(){
        System.out.printf("\n Pressione Enter para continuar...");
        scn.nextLine();
    }

}
