package com.pedidos.domain.repository;

import com.pedidos.domain.model.Usuario;

public interface RestauranteRepository  extends UsuarioRepository{
    Usuario buscarPorEmailSenha(String email, String senha);
}
