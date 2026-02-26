package com.pedidos.domain.model;

import java.util.UUID;

public class Restaurante extends Usuario {

    private String cnpj;
    private boolean statusAtivo;
    private int categoriaGlobalId;


    public Restaurante(UUID uuid, String nome, String email, String senha,String cnpj, int categoriaGlobalId) {
        super(uuid, nome, email, senha);
        this.cnpj = cnpj;
        this.statusAtivo = false;
        this.categoriaGlobalId = categoriaGlobalId;
    }

    public boolean isStatusAtivo() {
        return statusAtivo;
    }

    public void setStatusAtivo(boolean statusAtivo) {
        this.statusAtivo = statusAtivo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        String regexCnpj = "^(\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}|\\d{14})$";
        if (cnpj != null && cnpj.matches(regexCnpj)) {
            this.cnpj = cnpj;
        }
        else
        {
            throw new IllegalArgumentException("CNPJ Inválido");
        }
    }

    public int getCategoriaGlobalId() {
        return categoriaGlobalId;
    }

    public void setCategoriaGlobalId(int categoriaGlobalId) {
        if (categoriaGlobalId <= 0)
        {
            throw new IllegalArgumentException("O ID da categoria deve ser um número positivo.");
        }
        else {
            this.categoriaGlobalId = categoriaGlobalId;
        }
    }
}