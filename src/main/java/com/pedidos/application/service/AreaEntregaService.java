package com.pedidos.application.service;


import com.pedidos.domain.exception.BairroNaoAtendidoException;
import com.pedidos.domain.model.AreaEntrega;
import com.pedidos.domain.repository.AreaEntregaRepository;

import java.math.BigDecimal;
import java.util.List;

public class AreaEntregaService {

    private final AreaEntregaRepository areaEntregaRepository;

    public AreaEntregaService(AreaEntregaRepository areaEntregaRepository) {
        this.areaEntregaRepository = areaEntregaRepository;
    }


    /**
     * gera area de entrega e faz tratamentos
     */
    public AreaEntrega criarAreaEntrega(String restauranteId,
                                        String bairro,
                                        BigDecimal distanciaKm,
                                        BigDecimal taxaEntrega,
                                        int previsaoMinutos) {

        if (bairro == null) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }

        if (taxaEntrega.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa");
        }

        if (distanciaKm.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Previsão de entrega deve ser maior que zero");
        }

        if (previsaoMinutos <= 0) {
            throw new IllegalArgumentException("Previsão de entrega deve ser maior que zero");
        }

        boolean jaExiste = areaEntregaRepository
                .buscarPorRestauranteId(restauranteId)
                .stream()
                .anyMatch(a -> a.getBairro().equalsIgnoreCase(bairro));

        if (jaExiste) {
            throw new IllegalStateException("Bairro já cadastrado para esse restaurante");
        }

        AreaEntrega areaEntrega = new AreaEntrega(
                restauranteId,
                bairro,
                distanciaKm,
                taxaEntrega,
                previsaoMinutos
        );

        areaEntregaRepository.salvar(areaEntrega);

        return areaEntrega;
    }

    /**
     * areas que o restaurante entrega
     */
    public List<AreaEntrega> listarAreasPorRestaurante(String restauranteId) {
        return areaEntregaRepository.buscarPorRestauranteId(restauranteId);

    }

    /**
     * busca area pelo id
     */
    public AreaEntrega buscarPorId(String id) {
        return areaEntregaRepository
                .buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Área de entrega não encontrada"));
    }

    /**
     * edita a area já existente e faz tratamento
     */
    public void editarAreaEntrega(String id,
                                  String novoBairro,
                                  BigDecimal novaDistancia,
                                  BigDecimal novoTaxa,
                                  int novoPrevisao) {

        AreaEntrega areaEntrega = buscarPorId(id);

        if (novoBairro == null) {
            throw new IllegalArgumentException("Bairro é obrigatório");
        }

        if (novoTaxa.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa");
        }

        if (novaDistancia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Previsão de entrega deve ser maior que zero");
        }

        if (novoPrevisao <= 0) {
            throw new IllegalArgumentException("Previsão de entrega deve ser maior que zero");
        }

        areaEntrega.setBairro(novoBairro);
        areaEntrega.setDistanciaKm(novaDistancia);
        areaEntrega.setTaxaEntrega(novoTaxa);
        areaEntrega.setPrevisaoMinutos(novoPrevisao);

        areaEntregaRepository.salvar(areaEntrega);

    }

    /**
     * remove area entrega ja registrada
     */
    public void removerAreaEntrega(String id) {

        AreaEntrega areaEntrega = buscarPorId(id);

        areaEntregaRepository.deletar(areaEntrega.getId());
    }


    /**
     * pega taxa de entrega do bairro
     */
    public BigDecimal buscarTaxaPorBairro(String restauranteId,
                                          String bairro) {

        return areaEntregaRepository
                .buscarPorRestauranteId(restauranteId)
                .stream()
                .filter(a -> a.getBairro().equalsIgnoreCase(bairro))
                .findFirst()
                .map(AreaEntrega::getTaxaEntrega)
                .orElseThrow(() ->
                        new BairroNaoAtendidoException("Bairro '" + bairro + "' não é atendido por este restaurante"));

    }

    /**
     * puxa area de entrega por bairro
     */
    public AreaEntrega buscarAreaPorBairro(String restauranteId,
                                           String bairro) {

        return areaEntregaRepository
                .buscarPorRestauranteId(restauranteId)
                .stream()
                .filter(a -> a.getBairro().equalsIgnoreCase(bairro))
                .findFirst()
                .orElseThrow(() ->
                        new BairroNaoAtendidoException("Bairro '" + bairro + "' não é atendido por este restaurante"));

    }
}
