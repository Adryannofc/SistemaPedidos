package com.pedidos.domain.repository;

import com.pedidos.domain.model.Endereco;
import java.util.List;
import java.util.Optional;

public interface EnderecoRepository{

    void salvar(Endereco endereco);

    Optional<Endereco> buscarPorId(Long id);

    List<Endereco> listarTodos();

    void deletar(Long id);

}
