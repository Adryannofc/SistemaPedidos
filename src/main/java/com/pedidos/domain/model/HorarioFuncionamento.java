package com.pedidos.domain.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class HorarioFuncionamento {

    private String restauranteId;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private UUID id = UUID.randomUUID();

    public HorarioFuncionamento(String restauranteId, DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.restauranteId = restauranteId;
        this.horaFim = horaFim;
        this.horaInicio = horaInicio;
        this.diaSemana = diaSemana;
        this.id = UUID.randomUUID();
    }

    public HorarioFuncionamento(DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim, UUID id) {
        this.horaFim = horaFim;
        this.horaInicio = horaInicio;
        this.diaSemana = diaSemana;
        this.id = id;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        this.restauranteId = restauranteId;
    }

    public UUID getId() {
        return id;
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

    @Override
    public String toString() {
        return "HorarioFuncionamento{" +
                "diaSemana=" + diaSemana +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                '}';
    }
}

