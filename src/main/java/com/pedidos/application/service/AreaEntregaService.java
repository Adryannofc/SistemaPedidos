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

    public AreaEntrega cadastrarArea(String restauranteId, String bairro,
                                     BigDecimal distanciaKm, BigDecimal taxaEntrega,
                                     int previsaoMinutos) {
        validarTaxa(taxaEntrega);
        AreaEntrega area = new AreaEntrega(restauranteId, bairro,
                distanciaKm, taxaEntrega, previsaoMinutos);
        areaEntregaRepository.salvar(area);
        return area;
    }

    public List<AreaEntrega> listarPorRestaurante(String restauranteId) {
        return areaEntregaRepository.buscarPorRestauranteId(restauranteId);
    }

    public void editarArea(String areaId, String novoBairro,
                           BigDecimal novaDistancia, BigDecimal novaTaxa,
                           int novaPrevisao) {
        AreaEntrega area = buscarOuLancar(areaId);

        if (novoBairro != null && !novoBairro.isBlank()) area.setBairro(novoBairro);
        if (novaDistancia != null)                        area.setDistanciaKm(novaDistancia);
        if (novaTaxa != null) {
            validarTaxa(novaTaxa);
            area.setTaxaEntrega(novaTaxa);
        }
        if (novaPrevisao > 0) area.setPrevisaoMinutos(novaPrevisao);

        areaEntregaRepository.salvar(area);
    }

    public void removerArea(String areaId) {
        buscarOuLancar(areaId); // garante que existe antes de deletar
        areaEntregaRepository.deletar(areaId);
    }

    /** Chamado pelo PedidoService no checkout — lança BairroNaoAtendidoException se não encontrar. */
    public BigDecimal buscarTaxaPorBairro(String restauranteId, String bairro) {
        return areaEntregaRepository.buscarPorRestauranteId(restauranteId).stream()
                .filter(a -> a.getBairro().equalsIgnoreCase(bairro))
                .map(AreaEntrega::getTaxaEntrega)
                .findFirst()
                .orElseThrow(() -> new BairroNaoAtendidoException(bairro));
    }

    // --- helpers privados ---

    private AreaEntrega buscarOuLancar(String id) {
        return areaEntregaRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Área de entrega não encontrada."));
    }

    private void validarTaxa(BigDecimal taxa) {
        if (taxa == null || taxa.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Taxa de entrega não pode ser negativa.");
        }
    }
}