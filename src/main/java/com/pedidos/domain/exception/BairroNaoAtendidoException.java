package com.pedidos.domain.exception;

public class BairroNaoAtendidoException extends RuntimeException {

    public BairroNaoAtendidoException(String bairro) {
        super("Entrega não disponível para o bairro: " + bairro);
    }

}
