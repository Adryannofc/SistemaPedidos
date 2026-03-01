package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.UUID;

public class Cliente extends Usuario {

    private String cpf;
    private String telefone;

    public Cliente(String nome, String email, String senhaHash, String cpf, String telefone) {
        super(nome, email, senhaHash, TipoUsuario.CLIENTE);
        this.cpf = cpf;
        this.telefone = telefone;

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        String regexCpf = "(\\\\d{3}\\\\.\\\\d{3}\\\\.\\\\d{3}-\\\\d{2}|\\\\d{11})$";
        if (cpf != null && cpf.matches(regexCpf))
        {
            this.cpf = cpf;
        }
        else {
            throw new IllegalArgumentException("CPF inválido!");
        };

    }

    @Override
    public void exibirDetalhes(){
        TerminalUtils.cabecalho("PERFIL RESTAURANTE");
        System.out.println( "ID: " + getId());
        System.out.println( "Nome: " + getNome());
        System.out.println( "E-mail: " + getEmail());
        System.out.println(  "CPF: " + getCpf());
        System.out.println( "Telefone: " + getTelefone());
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
        return "Cliente{nome=" + getNome() + ", email=" + getEmail() + "}";
    }
}