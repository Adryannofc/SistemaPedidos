package com.pedidos.domain.repository;

import java.util.Optional;
import java.util.List;

public interface ProdutoRepository {

    void salvar(Produto produto);

    Optional<Produto> buscarPorId(Long id);

    List<Produto> listarTodos();

    void deletar(Long id);
}
