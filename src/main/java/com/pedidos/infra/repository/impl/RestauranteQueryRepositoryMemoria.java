package com.pedidos.infra.repository.impl;

import com.pedidos.domain.model.HorarioFuncionamento;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.HorarioFuncionamentoRepository;
import com.pedidos.domain.repository.RestauranteQueryRepository;
import com.pedidos.domain.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RestauranteQueryRepositoryMemoria implements RestauranteQueryRepository {

    private final UsuarioRepository usuarioRepository;
    private final HorarioFuncionamentoRepository horarioRepository;

    public RestauranteQueryRepositoryMemoria(UsuarioRepository usuarioRepository,
                                              HorarioFuncionamentoRepository horarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.horarioRepository = horarioRepository;
    }

    @Override
    public List<Restaurante> listarAtivos() {
        return usuarioRepository.listarTodos()
                .stream()
                .filter(u -> u instanceof Restaurante)
                .map(u -> (Restaurante) u)
                .filter(Restaurante::isStatusAtivo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurante> listarAtivosEAbertos(LocalDateTime agora) {
        return usuarioRepository.listarTodos()
                .stream()
                .filter(u -> u instanceof Restaurante)
                .map(u -> (Restaurante) u)
                .filter(Restaurante::isStatusAtivo)
                .filter(r -> horarioRepository.buscarPorRestauranteId(r.getId())
                        .stream()
                        .anyMatch(h -> h.estaAberto(agora)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurante> listarAtivosPorCategoria(String categoriaGlobalId) {
        return usuarioRepository.listarTodos()
                .stream()
                .filter(u -> u instanceof Restaurante)
                .map(u -> (Restaurante) u)
                .filter(Restaurante::isStatusAtivo)
                .filter(r -> r.getCategoriaGlobalId() != null
                        && r.getCategoriaGlobalId().equals(categoriaGlobalId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurante> listarFavoritosAtivos(List<String> favoritosIds) {
        return usuarioRepository.listarTodos()
                .stream()
                .filter(u -> u instanceof Restaurante)
                .map(u -> (Restaurante) u)
                .filter(r -> favoritosIds.contains(r.getId()))
                .filter(Restaurante::isStatusAtivo)
                .collect(Collectors.toList());
    }

    @Override
    public boolean estaAberto(String restauranteId, LocalDateTime agora) {
        return horarioRepository.buscarPorRestauranteId(restauranteId).stream()
                .anyMatch(h -> h.estaAberto(agora));
    }
}

