package com.pedidos.domain.repository;

import com.pedidos.domain.model.CategoriaCardapio;
import java.util.List;
import java.util.Optional;

public interface CategoriaCardapioRepository {

    void salvar(CategoriaCardapio categoria);

    Optional<CategoriaCardapio> buscarPorId(String id)
            ;
    List<CategoriaCardapio> buscarPorRestauranteId(String restauranteId);

    List<CategoriaCardapio> listarTodos();

    void remover(String id);
}