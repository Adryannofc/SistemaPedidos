package com.pedidos.domain.repository;

import com.pedidos.domain.model.Restaurante;
import java.time.LocalDateTime;
import java.util.List;

public interface RestauranteQueryRepository {

    List<Restaurante> listarAtivos();

    List<Restaurante> listarAtivosEAbertos(LocalDateTime agora);

    List<Restaurante> listarAtivosPorCategoria(String categoriaGlobalId);

    List<Restaurante> listarFavoritosAtivos(List<String> favoritosIds);

    boolean estaAberto(String id, LocalDateTime now);
}

