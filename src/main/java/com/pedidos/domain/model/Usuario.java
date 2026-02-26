
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

    public String getUuid() {
        return id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        String regexLetras = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        if (nome != null && nome.matches(regexLetras)) {
            this.nome = nome.trim();
        } else {
            System.err.println("Nome inválido! Utilize apenas letras.");
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
            System.err.println("Email inválido.");
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
            System.err.println("A senha deve ter 8+ caracteres, incluindo maiúscula, número e símbolo.");
        }
    }

    public TipoUsuario getTipoUsuario() { return tipoUsuario; }

    public boolean verificarSenha(String senhaHash) {
        return this.senhaHash.equals(senhaHash);
    }
}
