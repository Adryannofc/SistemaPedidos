package com.pedidos.application.service;

import com.pedidos.domain.model.CategoriaCardapio;
import com.pedidos.domain.model.CategoriaGlobal;
import com.pedidos.domain.repository.CategoriaCardapioRepository;
import com.pedidos.domain.repository.CategoriaGlobalRepository;
import com.pedidos.domain.repository.ProdutoRepository;
import com.pedidos.domain.repository.RestauranteRepository;

import java.util.List;

public class CategoriaService {
    private final CategoriaCardapioRepository categoriaCardapioRepository;
    private final CategoriaGlobalRepository categoriaGlobalRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaGlobalRepository categoriaGlobalRepository,
                            CategoriaCardapioRepository categoriaCardapioRepository,
                            RestauranteRepository restauranteRepository,
                            ProdutoRepository produtoRepository) {
        this.categoriaGlobalRepository = categoriaGlobalRepository;
        this.categoriaCardapioRepository = categoriaCardapioRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    public void criarCategoriaGlobal(String nome, String descricao) {
        boolean nomeExiste = categoriaGlobalRepository.listarTodos().stream()
                .anyMatch(c -> c.getNome().equalsIgnoreCase(nome));
        if (nomeExiste) {
            throw new IllegalArgumentException("Já existe uma categoria global com esse nome.");
        }
        categoriaGlobalRepository.salvar(new CategoriaGlobal(nome, descricao));
    }

    public List<CategoriaGlobal> listarCategoriasGlobais() {
        return categoriaGlobalRepository.listarTodos();
    }

    public void editarCategoriaGlobal(String id, String novoNome, String novaDescricao) {
        CategoriaGlobal categoria = categoriaGlobalRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        boolean nomeExiste = categoriaGlobalRepository.listarTodos().stream()
                .anyMatch(c -> c.getNome().equalsIgnoreCase(novoNome) && !c.getId().equals(id));
        if (nomeExiste) {
            throw new IllegalArgumentException("Já existe uma categoria global com esse nome.");
        }

        categoria.setNome(novoNome);
        categoria.setDescricao(novaDescricao);
        categoriaGlobalRepository.salvar(categoria);
    }

    public void removerCategoriaGlobal(String id) {
        categoriaGlobalRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        boolean temRestauranteVinculado = restauranteRepository.listarTodos().stream()
                .anyMatch(u -> {
                    com.pedidos.domain.model.Restaurante r = (com.pedidos.domain.model.Restaurante) u;
                    return r.getCategoriaGlobalId().equals(id);
                });
        if (temRestauranteVinculado) {
            throw new IllegalArgumentException("Categoria em uso por um restaurante — remoção bloqueada.");
        }

        categoriaGlobalRepository.remover(id);
    }

    public void criarCategoriaCardapio(String nome, String descricao, String restauranteId) {
        boolean nomeExiste = categoriaCardapioRepository.buscarPorRestauranteId(restauranteId).stream()
                .anyMatch(c -> c.getNome().equalsIgnoreCase(nome));
        if (nomeExiste) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome neste cardápio.");
        }
        categoriaCardapioRepository.salvar(new CategoriaCardapio(nome, descricao, restauranteId));
    }

    public List<CategoriaCardapio> listarCategoriasCardapio(String restauranteId) {
        return categoriaCardapioRepository.buscarPorRestauranteId(restauranteId);
    }

    public void editarCategoriaCardapio(String id, String novoNome, String novaDescricao) {
        CategoriaCardapio categoria = categoriaCardapioRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        boolean nomeExiste = categoriaCardapioRepository.buscarPorRestauranteId(categoria.getRestauranteId()).stream()
                .anyMatch(c -> c.getNome().equalsIgnoreCase(novoNome) && !c.getId().equals(id));
        if (nomeExiste) {
            throw new IllegalArgumentException("Já existe uma categoria com esse nome neste cardápio.");
        }

        categoria.setNome(novoNome);
        categoria.setDescricao(novaDescricao);
        categoriaCardapioRepository.salvar(categoria);
    }

    public void removerCategoriaCardapio(String id) {
        CategoriaCardapio categoria = categoriaCardapioRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        boolean temProdutoVinculado = produtoRepository.listarTodos().stream()
                .anyMatch(p -> p.getCategoriaCardapioId().equals(id));
        if (temProdutoVinculado) {
            throw new IllegalArgumentException("Categoria em uso por um produto — remoção bloqueada.");
        }

        categoriaCardapioRepository.remover(id);
    }
}
