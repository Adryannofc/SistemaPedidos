package com.pedidos.application.service;

import com.pedidos.domain.model.Produto;
import com.pedidos.domain.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(String nome, String descricao, BigDecimal preco, String categoriaCardapioId, String restauranteId) {
        validarPreco(preco);

        Produto produto = new Produto(nome, descricao, preco, categoriaCardapioId, restauranteId);
        produtoRepository.salvar(produto);
        return produto;
    }

    public List<Produto> listarPorRestaurante(String restauranteId) {
        return produtoRepository.listarTodos().stream().filter(p -> p.getRestauranteId().equals(restauranteId)).collect(Collectors.toList());
    }

    public void editarProduto(String produtoId, String restauranteId, String novoNome, String novaDescricao, BigDecimal novoPreco, String novaCategoriaCardapioId) {

        Produto produto = buscarProdutoDono(produtoId, restauranteId);

        if(novoNome != null && !novoNome.isBlank()) {
            produto.setNome(novoNome);
        }

        if(novaDescricao != null && !novaDescricao.isBlank()) {
            produto.setDescricao(novaDescricao);
        }

        if(novoPreco != null) {
            validarPreco(novoPreco);
            produto.setPreco(novoPreco);
        }

        if (novaCategoriaCardapioId != null && !novaCategoriaCardapioId.isBlank()) {
            produto.setCategoriaCardapioId(novaCategoriaCardapioId);
        }

        produtoRepository.salvar(produto);
    }

    public void ativarInativar(String produtoId, String restauranteId) {
        Produto produto = buscarProdutoDono(produtoId, restauranteId);
        produto.setStatusAtivo(!produto.isStatusAtivo()); // toggle
        produtoRepository.salvar(produto);
    }

    public void removerProduto(String produtoId, String restauranteId) {
        buscarProdutoDono(produtoId, restauranteId); // garante que existe e pertence ao restaurante
        produtoRepository.deletar(produtoId);
    }

    private Produto buscarProdutoDono(String produtoId, String restauranteId) {
        Produto produto = produtoRepository.buscarPorId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        if (!produto.getRestauranteId().equals(restauranteId)) {
            throw new IllegalArgumentException("Produto não pertence a este restaurante.");
        }

        return produto;
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero.");
        }
    }
}
