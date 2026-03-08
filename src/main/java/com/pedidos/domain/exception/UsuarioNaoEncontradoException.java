package com.pedidos.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String email){
        super("Usuário não enconttrado para o email" + email);
    }
}
