package com.pedidos.domain.repository;

import com.pedidos.domain.model.HorarioFuncionamento;
import java.util.List;
import java.util.Optional;

public interface HorarioFuncionamentoRepository {

    void salvar(HorarioFuncionamento horario);

    Optional<HorarioFuncionamento> buscarPorId(String id);

    List<HorarioFuncionamento> listarTodos();

    void deletar(String id);
}
