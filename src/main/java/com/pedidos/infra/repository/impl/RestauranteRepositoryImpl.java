package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.RestauranteRepository;

import java.util.*;

public class RestauranteRepositoryImpl implements RestauranteRepository {

    private final List<Usuario> lista = new ArrayList<>();

    @Override
    public void salvar(Usuario usuario) {
        lista.removeIf(u -> u.getUuid().equals(usuario.getUuid()));
        lista.add(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.empty();
    }

    /**
     * Usa Objects.equals() para evitar NullPointerException
     * caso getEmail() retorne null em algum registro corrompido.
     */
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return lista.stream()
                .filter(u -> Objects.equals(u.getEmail(), email))
                .findFirst();
    }

    /**
     * Retorna uma visão não-modificável da lista interna,
     * impedindo que código externo altere a coleção diretamente.
     */
    @Override
    public List<Usuario> listarTodos() {
        return Collections.unmodifiableList(lista);
    }

    @Override
    public void deletar(Long id) {
        // UUID-based repo — deleção por Long não aplicável nesta implementação
    }

    @Override
    public Usuario buscarPorEmailSenha(String email, String senhaHash) {
        return lista.stream()
                .filter(u -> Objects.equals(u.getEmail(), email) && u.verificarSenha(senhaHash))
                .findFirst()
                .orElse(null);
    }
}

