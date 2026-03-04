package com.pedidos.presentation.restaurante;

import com.pedidos.application.service.HorarioService;
import com.pedidos.domain.model.HorarioFuncionamento;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuHorarios {
    private final HorarioService horarioService;
    private final Scanner scanner;

    public MenuHorarios(HorarioService horarioService, Scanner scanner) {
        this.horarioService = horarioService;
        this.scanner = scanner;
    }

    public void exibir(Restaurante restauranteLogado) {
        while (true) {
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho("RESTAURANTE | HORÁRIOS DE FUNCIONAMENTO");
            System.out.println("1 - Listar Horários");
            System.out.println("2 - Adicionar Horário");
            System.out.println("3 - Editar Horário");
            System.out.println("4 - Remover Horário");
            System.out.println("0 - Voltar");
            System.out.print("\nEscolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner, 0, 4);

            switch (opcao) {
                case 1:
                    try {
                        List<HorarioFuncionamento> horarios = horarioService.listarHorarioPorRestaurante(restauranteLogado.getId());
                        if (horarios.isEmpty()) {
                            System.out.println("Nenhum horário cadastrado.");
                        } else {
                            for (int i = 0; i < horarios.size(); i++) {
                                HorarioFuncionamento h = horarios.get(i);
                                System.out.println((i + 1) + " - " + h.getDiaSemana()
                                        + " | " + h.getHoraInicio() + " às " + h.getHoraFim()
                                        + " | ID: " + h.getId());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        List<DayOfWeek> disponiveis = horarioService.listarDiasDisponiveis(restauranteLogado.getId());
                        if (disponiveis.isEmpty()) {
                            System.out.println("Todos os dias já estão cadastrados.");
                            TerminalUtils.pausar();
                            break;
                        }

                        System.out.println("Dias disponíveis:");
                        for (int i = 0; i < disponiveis.size(); i++) {
                            System.out.println((i + 1) + " - " + disponiveis.get(i));
                        }
                        System.out.print("Escolha o número do dia: ");
                        int numDia = EntradaSegura.lerOpcao(scanner, 1, disponiveis.size());
                        DayOfWeek dia = disponiveis.get(numDia - 1);

                        System.out.print("Hora de início (HH:mm): ");
                        LocalTime inicio = LocalTime.parse(scanner.nextLine().trim());

                        System.out.print("Hora de fim (HH:mm): ");
                        LocalTime fim = LocalTime.parse(scanner.nextLine().trim());

                        horarioService.criarHorario(restauranteLogado.getId(), dia, inicio, fim);
                        System.out.println("Horário cadastrado com sucesso.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato inválido. Use HH:mm (ex: 18:00).");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        List<HorarioFuncionamento> horarios = horarioService.listarHorarioPorRestaurante(restauranteLogado.getId());
                        if (horarios.isEmpty()) {
                            System.out.println("Nenhum horário cadastrado.");
                            TerminalUtils.pausar();
                            break;
                        }
                        for (int i = 0; i < horarios.size(); i++) {
                            HorarioFuncionamento h = horarios.get(i);
                            System.out.println((i + 1) + " - " + h.getDiaSemana()
                                    + " | " + h.getHoraInicio() + " às " + h.getHoraFim());
                        }
                        System.out.print("Escolha o número do horário: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, horarios.size());
                        HorarioFuncionamento selecionado = horarios.get(num - 1);

                        System.out.print("Nova hora de início (" + selecionado.getHoraInicio() + "): ");
                        LocalTime novoInicio = LocalTime.parse(scanner.nextLine().trim());

                        System.out.print("Nova hora de fim (" + selecionado.getHoraFim() + "): ");
                        LocalTime novoFim = LocalTime.parse(scanner.nextLine().trim());

                        horarioService.editarHorario(selecionado.getId(), novoInicio, novoFim);
                        System.out.println("Horário atualizado com sucesso.");
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato inválido. Use HH:mm (ex: 18:00).");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        List<HorarioFuncionamento> horarios = horarioService.listarHorarioPorRestaurante(restauranteLogado.getId());
                        if (horarios.isEmpty()) {
                            System.out.println("Nenhum horário cadastrado.");
                            TerminalUtils.pausar();
                            break;
                        }
                        for (int i = 0; i < horarios.size(); i++) {
                            HorarioFuncionamento h = horarios.get(i);
                            System.out.println((i + 1) + " - " + h.getDiaSemana()
                                    + " | " + h.getHoraInicio() + " às " + h.getHoraFim());
                        }
                        System.out.print("Escolha o número do horário: ");
                        int num = EntradaSegura.lerOpcao(scanner, 1, horarios.size());
                        HorarioFuncionamento selecionado = horarios.get(num - 1);

                        System.out.print("Tem certeza? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            horarioService.removerHorario(selecionado.getId());
                            System.out.println("Horário removido com sucesso.");
                        } else {
                            System.out.println("Operação cancelada.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 0:
                    return;
            }
        }
    }
}