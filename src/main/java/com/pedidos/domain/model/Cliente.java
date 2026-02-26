package com.pedidos.domain.model;

import java.util.UUID;

public class Cliente extends Usuario {

    private String cpf;
    private String telefone;

    public Cliente(UUID uuid, String nome, String email, String senha, String cpf, String telefone) {
        super(uuid, nome, email, senha);
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
}
