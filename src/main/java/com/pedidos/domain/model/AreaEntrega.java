package com.pedidos.domain.model;

import java.math.BigDecimal;

public class AreaEntrega {

        private String bairro;
        private double distanciaKm;
        private BigDecimal taxaEntrega;
        private int previsaoMinutos;

        public AreaEntrega(String bairro, double distanciaKm, BigDecimal taxaEntrega, int previsaoMinutos) {
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

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
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
}
