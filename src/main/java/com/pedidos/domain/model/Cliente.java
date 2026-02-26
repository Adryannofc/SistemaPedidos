package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

import java.util.UUID;

public class Cliente extends Usuario {

    private final TipoUsuario tipo = TipoUsuario.CLIENTE;
    private String cpf;
    private String endereco;
    private String telefone;

    // Construtor completo — carregado do banco (UUID já existente)
    public Cliente(UUID uuid, String nome, String email, String senhaHash, String cpf, String endereco) {
        super(uuid, nome, email, senhaHash);
        setCpf(cpf);
        this.endereco = endereco;
    }

    // Construtor de criação — UUID gerado automaticamente
    public Cliente(String nome, String email, String senhaHash, String cpf, String endereco) {
        super(nome, email, senhaHash);
        setCpf(cpf);
        this.endereco = endereco;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("CPF não pode ser nulo ou vazio.");
        }
        // Remove máscara e valida 11 dígitos numéricos
        String cpfLimpo = cpf.replaceAll("[.\\-]", "");
        if (!cpfLimpo.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. Informe 11 dígitos numéricos.");
        }
        this.cpf = cpfLimpo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
