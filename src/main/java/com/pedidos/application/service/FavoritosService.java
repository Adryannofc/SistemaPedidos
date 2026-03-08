package com.pedidos.application.service;

import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.repository.FavoritosRepository;
import com.pedidos.domain.repository.RestauranteQueryRepository;

import java.time.LocalDateTime;
import java.util.List;

public class FavoritosService {

    private final FavoritosRepository favoritosRepository;

    private final RestauranteQueryRepository restauranteQueryRepository;

    public FavoritosService(FavoritosRepository favoritosRepository, RestauranteQueryRepository restauranteQueryRepository) {
        this.favoritosRepository = favoritosRepository;
        this.restauranteQueryRepository = restauranteQueryRepository;
    }

    public void marcarFavorito(String clienteId, String restauranteId){
        if (restauranteId == null || restauranteId.isBlank()) {
            throw new IllegalArgumentException("Restaurante é obrigatório");
        }
        if(favoritosRepository.isFavorito(clienteId, restauranteId)){
            throw new IllegalStateException("Restaurante já é favorito");
        }
        favoritosRepository.adicionarFavorito(clienteId, restauranteId);
    }

    public void removerFavorito(String clienteId, String restauranteId) {
        if (restauranteId == null || restauranteId.isBlank()) {
            throw new IllegalArgumentException("Restaurante é obrigatório");
        }
        if (!favoritosRepository.isFavorito(clienteId, restauranteId)) {
            throw new IllegalStateException("Restaurante não está nos favoritos");
        }
        favoritosRepository.removerFavorito(clienteId, restauranteId);
    }


    public List<Restaurante> listarFavoritos(String clienteId) {
        List<String> favoritosIds = favoritosRepository.listarFavoritos(clienteId);

        if (favoritosIds.isEmpty()) {
            return List.of();
        }

        List<Restaurante> restaurantesAtivos = restauranteQueryRepository.listarFavoritosAtivos(favoritosIds);

        return restaurantesAtivos.stream()
                .filter(Restaurante::isStatusAtivo)
                .filter(r -> restauranteQueryRepository.estaAberto(r.getId(), LocalDateTime.now()))
                .toList();
    }

    public List<Restaurante> listarTodosFavoritos(String clienteId) {
        List<String> favoritosIds = favoritosRepository.listarFavoritos(clienteId);

        if (favoritosIds.isEmpty()) {
            return List.of();
        }

        return restauranteQueryRepository.listarFavoritosAtivos(favoritosIds);
    }

    public boolean isFavorito(String clienteId, String restauranteId) {
        return favoritosRepository.isFavorito(clienteId, restauranteId);
    }

    public List<Restaurante> listarRestaurantesDisponiveisParaFavoritar(String clienteId) {
        List<Restaurante> restaurantesAtivos = restauranteQueryRepository.listarAtivos();

        List<String> favoritosIds = favoritosRepository.listarFavoritos(clienteId);

        return restaurantesAtivos.stream()
                .filter(r -> !favoritosIds.contains(r.getId()))
                .toList();
    }

}
