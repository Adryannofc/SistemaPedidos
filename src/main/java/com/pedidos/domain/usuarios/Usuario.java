
package com.pedidos.domain.usuarios;

public abstract class Usuario {
    private static int contadorId = 0;
    private int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(int id, String nome, String email, String senha) {
        this.id = ++contadorId;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return this.id;
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

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        String regexSenha = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*])(?=\\S+$).{8,}$";
        if (senha != null && senha.matches(regexSenha)) {
            this.senha = senha;
        } else {
            System.err.println("A senha deve ter 8+ caracteres, incluindo maiúscula, número e símbolo.");
        }

    }
}
