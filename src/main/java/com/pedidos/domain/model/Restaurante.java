package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.UUID;

public class Restaurante extends Usuario {

    private String cnpj;
    private boolean statusAtivo;
    private int categoriaGlobalId;
    private String telefone;

    public Restaurante(String nome, String email, String senhaHash,String cnpj) {
        super(nome, email, senhaHash, TipoUsuario.RESTAURANTE);
        this.cnpj = cnpj;
        this.statusAtivo = false;
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


    @Override
    public void exibirDetalhes() {
        TerminalUtils.cabecalho("PERFIL RESTAURANTE");
        System.out.println( "ID: " + getId());
        System.out.println( "Nome: " + getNome());
        System.out.println( "CNPJ: " + getCnpj());
        System.out.println( "Telefone: " + getTelefone());
        System.out.println( "Categoria: " + getCategoriaGlobalId());
        System.out.println( "Status: " + (isStatusAtivo() ? "ATIVO" : "BLOQUEADO"));

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        String regexTelefone = "^(55)?(?:([1-9]{2})?)(\\d{4,5})(\\d{4})$";
        if(telefone != null && telefone.matches(regexTelefone))
        {
            this.telefone = telefone;
        }
        else
        {
            throw new IllegalArgumentException("Telefone inválido!");
        }
    }

    @Override
    public String toString() {
        return "Restaurante{nome=" + getNome() + ", status=" + isStatusAtivo() + "}";
    }
}