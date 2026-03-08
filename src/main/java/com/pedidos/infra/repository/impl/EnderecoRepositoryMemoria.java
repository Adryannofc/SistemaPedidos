package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.Endereco;
import com.pedidos.domain.repository.EnderecoRepository;
import java.util.*;

public class EnderecoRepositoryMemoria implements EnderecoRepository {

    private final Map<String, Endereco> storage = new HashMap<>();

    @Override
    public void salvar(Endereco endereco) {
        storage.put(endereco.getId(), endereco);
    }

    @Override
    public Optional<Endereco> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Endereco> buscarPorClienteId(String clienteId) {
        return storage.values().stream()
                .filter(e -> e.getClienteId().equals(clienteId))
                .toList();
    }

    @Override
    public Optional<Endereco> buscarPadraoDoCliente(String clienteId) {
        return storage.values().stream()
                .filter(e -> e.getClienteId().equals(clienteId))
                .filter(Endereco::isPadrao)
                .findFirst();
    }

    @Override
    public List<Endereco> listarTodos() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deletar(String id) {
        storage.remove(id);
    }
}