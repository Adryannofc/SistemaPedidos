package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.ClienteRepository;

import java.util.*;

public class ClienteRepositoryMemoria implements ClienteRepository {

    private final HashMap<String, Usuario> storage = new HashMap<>();

    @Override
    public void salvar(Usuario usuario) {
        storage.put(usuario.getUuid().toString(), usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Usa Objects.equals() para evitar NullPointerException
     * caso getEmail() retorne null em algum registro corrompido.
     */
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return storage.values().stream()
                .filter(u -> Objects.equals(u.getEmail(), email))
                .findFirst();
    }

    /**
     * Retorna uma visão não-modificável da lista interna,
     * impedindo que código externo altere a coleção diretamente.
     */
    @Override
    public List<Usuario> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public void deletar(String id) {
        storage.remove(id);
    }

    @Override
    public Usuario buscarPorEmailSenha(String email, String senhaHash) {
        return storage.values().stream()
                .filter(u -> Objects.equals(u.getEmail(), email) && u.verificarSenha(senhaHash))
                .findFirst()
                .orElse(null);
    }
}

