
package com.pedidos.domain.model;

import java.util.UUID;

public abstract class Usuario {
    private final UUID uuid; // 'final' impede que o ID seja trocado depois de criado
    private String nome;
    private String email;
    private String senhaHash;

    public Usuario(UUID uuid, String nome, String email, String senha) {
        this.uuid = (uuid != null) ? uuid : UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senhaHash = senha;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        String regexLetras = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        if (nome != null && nome.matches(regexLetras)) {
            this.nome = nome.trim();
        } else {
            throw new IllegalArgumentException("Nome inválido! Utilize apenas letras.");
        }

    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        String validarFormatoEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email != null && email.matches(validarFormatoEmail)) {
            this.email = email.toLowerCase().trim();
        } else {
            throw new IllegalArgumentException("Email inválido.");
        }

    }

    public String getSenhaHash() {
        return this.senhaHash;
    }

    public void setSenhaHash(String senha) {
        String regexSenha = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$";
        if (senha != null && senha.matches(regexSenha)) {
            this.senhaHash = senha;
        } else {
            throw new IllegalArgumentException("A senha deve ter 8+ caracteres, incluindo maiúscula, número e símbolo.");
        }

    }
}
