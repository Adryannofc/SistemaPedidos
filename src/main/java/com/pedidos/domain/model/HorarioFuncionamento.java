package com.pedidos.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HorarioFuncionamento {

    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public HorarioFuncionamento(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.horaFim = horaFim;
        this.horaInicio = horaInicio;
        this.diaSemana = diaSemana;
    }

    public DayOfWeek getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DayOfWeek diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public boolean estaAberto(LocalDateTime dataHora) {
            return dataHora.getDayOfWeek() == diaSemana &&
                    !dataHora.toLocalTime().isBefore(horaInicio) &&
                    !dataHora.toLocalTime().isAfter(horaFim);
        }

    }
