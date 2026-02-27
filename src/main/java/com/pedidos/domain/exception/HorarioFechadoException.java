package com.pedidos.domain.exception;

public class HorarioFechadoException extends RuntimeException {

    public HorarioFechadoException(String nomeRestaurante) {
        super("O restaurante " + nomeRestaurante + " está fechado no momento.");
    }
}
