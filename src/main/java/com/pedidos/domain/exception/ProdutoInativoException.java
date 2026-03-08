package com.pedidos.domain.exception;

public class ProdutoInativoException extends RuntimeException {

    public ProdutoInativoException(String nomeProduto) {
        super("O produto " + nomeProduto + " não está disponível no momento.");
    }
}
