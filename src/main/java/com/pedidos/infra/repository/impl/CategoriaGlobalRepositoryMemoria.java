package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.CategoriaGlobal;
import com.pedidos.domain.repository.CategoriaGlobalRepository;
import java.util.*;

public class CategoriaGlobalRepositoryMemoria implements CategoriaGlobalRepository {
    private final Map<String, CategoriaGlobal> storage = new HashMap<>();

    @Override
    public Optional<CategoriaGlobal> buscarPorNome(String nome) {
        return storage.values().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    @Override
    public void salvar(CategoriaGlobal categoriaGlobal) {
        storage.put(categoriaGlobal.getId(), categoriaGlobal);
    }

    @Override
    public Optional<CategoriaGlobal> buscarPorId(String id) { return Optional.ofNullable(storage.get(id)); }

    @Override
    public List<CategoriaGlobal> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public void remover(String id) {
        storage.remove(id);
    }
}