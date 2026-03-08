package com.pedidos.domain.repository;

import com.pedidos.domain.model.Endereco;
import java.util.List;
import java.util.Optional;

public interface EnderecoRepository {

    void salvar(Endereco endereco);

    Optional<Endereco> buscarPorId(String id);

    List<Endereco> buscarPorClienteId(String clienteId);

    Optional<Endereco> buscarPadraoDoCliente(String clienteId);

    List<Endereco> listarTodos();

    void deletar(String id);

}
