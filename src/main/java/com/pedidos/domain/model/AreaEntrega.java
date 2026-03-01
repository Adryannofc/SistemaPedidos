package com.pedidos.domain.model;

import java.math.BigDecimal;

public class AreaEntrega {

        private String bairro;
        private BigDecimal distanciaKm;
        private BigDecimal taxaEntrega;
        private int previsaoMinutos;

        public AreaEntrega(String bairro, BigDecimal distanciaKm, BigDecimal taxaEntrega, int previsaoMinutos) {
            this.bairro = bairro;
            this.distanciaKm = distanciaKm;
            this.taxaEntrega = taxaEntrega;
            this.previsaoMinutos = previsaoMinutos;
        }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public BigDecimal getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(BigDecimal distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public BigDecimal getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(BigDecimal taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public int getPrevisaoMinutos() {
        return previsaoMinutos;
    }

    public void setPrevisaoMinutos(int previsaoMinutos) {
        this.previsaoMinutos = previsaoMinutos;
    }

    @Override
    public String toString() {
        return "AreaEntrega{" +
                "bairro='" + bairro + '\'' +
                ", distanciaKm=" + distanciaKm +
                ", taxaEntrega=" + taxaEntrega +
                ", previsaoMinutos=" + previsaoMinutos +
                '}';
    }
}
