package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.HorarioFuncionamento;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.HorarioFuncionamentoRepository;

import java.util.*;
import java.util.stream.Collectors;

public class HorarioFuncionamentoRepositoryMemoria implements HorarioFuncionamentoRepository {

    private final HashMap<String, HorarioFuncionamento> storage = new HashMap<>();

     @Override
     public void salvar(HorarioFuncionamento horarioFuncionamento) {
         storage.put(horarioFuncionamento.getId().toString(), horarioFuncionamento);
     }

    @Override
    public Optional<HorarioFuncionamento> buscarPorId(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<HorarioFuncionamento> listarTodos() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public List<HorarioFuncionamento> buscarPorRestauranteId(String restauranteId) {
        return storage.values()
                .stream()
                .filter(h -> restauranteId.equals(h.getRestauranteId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletar(String id) {}
}
