package com.pedidos.domain.model;

import java.util.UUID;

public abstract class Usuario {
    private final UUID uuid;
    private String nome;
    private String email;
    private String senhaHash;

    // Construtor com UUID explícito (ex: carregado do banco)
    public Usuario(UUID uuid, String nome, String email, String senhaHash) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID não pode ser nulo.");
        }
        this.uuid = uuid;
        setNome(nome);
        setEmail(email);
        this.senhaHash = senhaHash;
    }

    // Construtor sem UUID — gera automaticamente
    public Usuario(String nome, String email, String senhaHash) {
        this(UUID.randomUUID(), nome, email, senhaHash);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        String regexLetras = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        if (nome.matches(regexLetras)) {
            this.nome = nome.trim();
        } else {
            throw new IllegalArgumentException("Nome inválido! Utilize apenas letras.");
        }
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        }
        String validarFormatoEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.matches(validarFormatoEmail)) {
            this.email = email.toLowerCase().trim();
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

    // Verifica se o hash fornecido corresponde ao hash armazenado
    // Evita expor senhaHash diretamente para camadas externas
    public boolean verificarSenha(String senhaHash) {
        return this.senhaHash != null && this.senhaHash.equals(senhaHash);
    }

    // Uso restrito à infraestrutura (ex: repositórios que precisam persistir/comparar)
    protected String getSenhaHash() {
        return this.senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException("Hash de senha inválido.");
        }
        this.senhaHash = senhaHash;
    }
}
