package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.Produto;
import com.pedidos.domain.repository.ProdutoRepository;
import java.util.*;

public class ProdutoRepositoryMemoria implements ProdutoRepository {
    private final Map<String, Produto> storage = new HashMap<>();

    @Override
    public void salvar(Produto produto) {
        storage.put(produto.getId(), produto);
    }


    public Optional<Produto> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Produto> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    public List<Produto> buscarPorRestauranteId(String restauranteId) {
        return storage.values().stream()
                .filter(produto -> produto.getRestauranteId().equals(restauranteId))
                .toList();
    }

    public List<Produto> listarAtivosPorRestaurante(String restauranteId) {
        return storage.values().stream()
                .filter(p -> p.isStatusAtivo())
                .filter(p -> p.getRestauranteId().equals(restauranteId))
                .toList();
    }

    @Override
    public void deletar(String id) {
        storage.remove(id);
    }

}
