package com.pedidos.application.service;

import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.repository.FavoritosRepository;
import com.pedidos.domain.repository.RestauranteQueryRepository;

import java.time.LocalDateTime;
import java.util.List;

public class FavoritosService {

    private final FavoritosRepository favoritosRepository;

    private final RestauranteQueryRepository restauranteQueryRepository;

    public void marcarFavorito(String clienteId, String restauranteId){
        if (restauranteId == null || restauranteId.isBlank()) {
            throw new IllegalArgumentException("Restaurante é obrigatório");
        }
        if(favoritosRepository.isFavorito(clienteId, restauranteId)){
            throw new IllegalStateException("Restaurante já é favorito");
        }
        favoritosRepository.adicionarFavorito(clienteId, restauranteId);
    }

    public List<Restaurante> listarFavoritos(String clienteId) {
        List<String> favoritosIds = favoritosRepository.listarFavoritos(clienteId);

        if (favoritosIds.isEmpty()) {
            return List.of();
        }

        List<Restaurante> restaurantesAtivos = restauranteQueryRepository.listarFavoritosAtivos(favoritosIds);

        return restaurantesAtivos.stream()
                .filter(Restaurante::isStatusAtivo)
                .toList();
    }

    public FavoritosService(FavoritosRepository favoritosRepository, RestauranteQueryRepository restauranteQueryRepository) {
        this.favoritosRepository = favoritosRepository;
        this.restauranteQueryRepository = restauranteQueryRepository;
    }
}
