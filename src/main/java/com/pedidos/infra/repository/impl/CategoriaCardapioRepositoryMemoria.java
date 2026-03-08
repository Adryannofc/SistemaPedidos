package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.CategoriaCardapio;
import com.pedidos.domain.repository.CategoriaCardapioRepository;
import java.util.*;

public class CategoriaCardapioRepositoryMemoria implements CategoriaCardapioRepository {

    private final Map<String, CategoriaCardapio> storage = new HashMap<>();

    @Override
    public void salvar(CategoriaCardapio categoria) {
        storage.put(categoria.getId(), categoria);
    }

    @Override
    public Optional<CategoriaCardapio> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<CategoriaCardapio> buscarPorRestauranteId(String restauranteId) {
        return storage.values().stream()
                .filter(categoria -> categoria.getRestauranteId().equals(restauranteId))
                .toList();
    }

    @Override
    public List<CategoriaCardapio> listarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void remover(String id) {
        storage.remove(id);
    }
}