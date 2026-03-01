
package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

import java.util.UUID;

public abstract class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senhaHash;
    private TipoUsuario tipoUsuario;

    public Usuario(String nome, String email, String senhaHash, TipoUsuario tipoUsuario) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.tipoUsuario = tipoUsuario;
    }

    public abstract void exibirDetalhes();

    public String getId() {
        return id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        if (nome == null || !nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
            throw new IllegalArgumentException("Nome inválido! Utilize apenas letras.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email.toLowerCase().trim();
    }

    public String getSenhaHash() {
        return this.senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank()) {
            throw new IllegalArgumentException("Hash de senha inválido.");
        }
        this.senhaHash = senhaHash;
    }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public boolean verificarSenha(String senhaHash) {
        return this.senhaHash.equals(senhaHash);
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
