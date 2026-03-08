package com.pedidos.infra.repository.impl;

import com.pedidos.domain.repository.FavoritosRepository;
import com.pedidos.domain.model.*;
import com.pedidos.domain.repository.UsuarioRepository;

import java.util.List;

public class FavoritosRepositoryMemoria implements FavoritosRepository {

    private final UsuarioRepository usuarioRepository;

    public FavoritosRepositoryMemoria(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void adicionarFavorito(String clienteId, String restauranteId) {
        Usuario usuario = usuarioRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com ID: " + clienteId));

        if (!(usuario instanceof Cliente cliente)) {
            throw new IllegalArgumentException(
                    "Usuário com ID " + clienteId + " não é um Cliente.");
        }

        if (cliente.getFavoritos().contains(restauranteId)) {
            return;
        }

        cliente.adicionarFavorito(restauranteId);
        usuarioRepository.salvar(cliente);
    }

    @Override
    public void removerFavorito(String clienteId, String restauranteId) {
        Usuario usuario = usuarioRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com ID: " + clienteId));

        if (!(usuario instanceof Cliente cliente)) {
            throw new IllegalArgumentException(
                    "Usuário com ID " + clienteId + " não é um Cliente.");
        }

        if (!cliente.getFavoritos().contains(restauranteId)) {
            throw new IllegalArgumentException("Restaurante não está nos favoritos");
        }

        cliente.removerFavorito(restauranteId);
        usuarioRepository.salvar(cliente);
    }

    @Override
    public List<String> listarFavoritos(String clienteId) {
        Usuario usuario = usuarioRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Usuário não encontrado com ID: " + clienteId));

        if (!(usuario instanceof Cliente cliente)) {
            throw new IllegalArgumentException(
                    "Usuário com ID " + clienteId + " não é um Cliente.");
        }

        List<String> favoritos = cliente.getFavoritos();
        return favoritos.isEmpty() ? List.of() : favoritos;
    }

    @Override
    public boolean isFavorito(String clienteId, String restauranteId) {
        return usuarioRepository.buscarPorId(clienteId)
                .filter(u -> u instanceof Cliente)
                .map(u -> (Cliente) u)
                .map(c -> c.getFavoritos().contains(restauranteId))
                .orElse(false);
    }
}
