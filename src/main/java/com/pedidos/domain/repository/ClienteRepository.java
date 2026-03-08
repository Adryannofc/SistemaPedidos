package com.pedidos.domain.repository;
import com.pedidos.domain.model.Usuario;

public interface ClienteRepository extends UsuarioRepository{
    Usuario buscarPorEmailSenha(String email, String senha);
}
