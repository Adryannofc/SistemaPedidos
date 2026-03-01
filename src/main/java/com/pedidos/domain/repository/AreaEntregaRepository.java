package com.pedidos.domain.repository;

import com.pedidos.domain.model.AreaEntrega;
import java.util.List;
import java.util.Optional;

public interface AreaEntregaRepository {

    void salvar(AreaEntrega area);

    Optional<AreaEntrega> buscarPorId(String id);

    List<AreaEntrega> buscarPorRestauranteId(String restauranteId);

    void deletar(String id);
}