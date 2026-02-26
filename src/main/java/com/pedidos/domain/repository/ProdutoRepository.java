package com.pedidos.domain.repository;

import com.pedidos.domain.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository {

    void salvar(Produto produto);

    Optional<Produto> buscarPorId(UUID uuid);

    List<Produto> listarTodos();

    void deletar(UUID uuid);
}
