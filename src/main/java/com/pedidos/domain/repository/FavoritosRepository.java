package com.pedidos.domain.repository;

import java.util.List;

public interface FavoritosRepository {
    void adicionarFavorito(String clienteId, String restauranteId);

    void removerFavorito(String clienteId, String restauranteId);

    List<String> listarPorCliente(String clienteId);

    boolean isFavorito(String clienteId, String restauranteId);
}