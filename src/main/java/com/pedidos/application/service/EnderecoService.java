package com.pedidos.application.service;

import com.pedidos.domain.model.Endereco;
import com.pedidos.domain.repository.EnderecoRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco criarEndereco (String clienteId,
                                   String apelido,
                                   String rua,
                                   String numero,
                                   String bairro,
                                   String cidade,
                                   String complemento
                                   ) {

        if (bairro == null || bairro.isBlank()) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }

        if (rua == null || rua.isBlank()) {
            throw new IllegalArgumentException("Rua é obrigatória");
        }

        if (cidade == null || cidade.isBlank()) {
            throw new IllegalArgumentException("Cidade é obrigatória");
        }

        boolean jaPossuiPadrao =
                enderecoRepository.buscarPadraoDoCliente(clienteId).isPresent();

        boolean isPadrao = !jaPossuiPadrao;

        Endereco endereco = new Endereco(
                clienteId,
                apelido,
                rua,
                bairro,
                isPadrao
        );

        enderecoRepository.salvar(endereco);

        return endereco;

    }

    public List<Endereco> listarEnderecosPorCliente(String clienteId) {
        List<Endereco> lista = new ArrayList<>(
                enderecoRepository.buscarPorClienteId(clienteId)
        );
        lista.sort(Comparator.comparing(Endereco::isPadrao).reversed());
        return lista;
    }

    public Endereco buscarPorId(String id) {

        return enderecoRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Endereço não encontrado"));

    }

    public void editarEndereco(String id,
                               String clienteId,
                               String novoApelido,
                               String novaRua,
                               String novoBairro) {

        Endereco endereco = buscarPorId(id);

        if (!endereco.getClienteId().equals(clienteId)) {
            throw new IllegalArgumentException("Endereço não pertence ao cliente");
        }

        if (novoBairro == null || novoBairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }

        if (novaRua == null || novaRua.trim().isEmpty()) {
            throw new IllegalArgumentException("Rua é obrigatória");
        }


        endereco.setApelido(novoApelido);
        endereco.setRua(novaRua);
        endereco.setBairro(novoBairro);

        enderecoRepository.salvar(endereco);
    }

    public void definirPadrao(String id, String clienteId) {

        Endereco endereco = buscarPorId(id);

        if (!endereco.getClienteId().equals(clienteId)) {
            throw new IllegalArgumentException("Endereço não pertence ao cliente");
        }

        if (endereco.isPadrao()) {
            throw new IllegalStateException("Este endereço já é o padrão");
        }

        enderecoRepository.buscarPadraoDoCliente(clienteId)
                .ifPresent(padraoAtual -> {
                    padraoAtual.setPadrao(false);
                    enderecoRepository.salvar(padraoAtual);
                });

        endereco.setPadrao(true);
        enderecoRepository.salvar(endereco);
    }


    public void removerEndereco(String id, String clienteId) {

        Endereco endereco = buscarPorId(id);

        if (!endereco.getClienteId().equals(clienteId)) {
            throw new IllegalArgumentException("Endereço não pertence ao cliente");
        }

        if (endereco.isPadrao()) {
            throw new IllegalStateException(
                    "Não é possível remover o endereço padrão. Defina outro endereço como padrão antes de remover."
            );
        }

        enderecoRepository.deletar(id);
    }

}
