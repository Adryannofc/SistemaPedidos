package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.AreaEntrega;
import com.pedidos.domain.repository.AreaEntregaRepository;

import java.util.*;

public class AreaEntregaRepositoryMemoria implements AreaEntregaRepository {

    private final Map<String, AreaEntrega> storage = new HashMap<>();

    @Override
    public void salvar(AreaEntrega area) {
        storage.put(area.getId(), area);
    }

    @Override
    public Optional<AreaEntrega> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<AreaEntrega> buscarPorRestauranteId(String restauranteId) {
        return storage.values().stream()
                .filter(a -> a.getRestauranteId().equals(restauranteId))
                .toList();
    }

    @Override
    public void deletar(String id) {
        storage.remove(id);
    }
}
