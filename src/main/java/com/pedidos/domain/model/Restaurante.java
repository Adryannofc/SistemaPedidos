package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurante extends Usuario {

    private final TipoUsuario tipo = TipoUsuario.RESTAURANTE;
    private String nomeFantasia;
    private String cnpj;
    private String endereco;
    private final List<HorarioFuncionamento> horariosFuncionamento;
    private final List<AreaEntrega> areasEntrega;

    // Construtor completo — carregado do banco (UUID já existente)
    public Restaurante(UUID uuid, String nome, String nomeFantasia, String email,
                       String senhaHash, String cnpj, String endereco) {
        super(uuid, nome, email, senhaHash);
        setNomeFantasia(nomeFantasia);
        setCnpj(cnpj);
        this.endereco = endereco;
        this.horariosFuncionamento = new ArrayList<>();
        this.areasEntrega = new ArrayList<>();
    }

    // Construtor de criação — UUID gerado automaticamente
    public Restaurante(String nome, String nomeFantasia, String email,
                       String senhaHash, String cnpj, String endereco) {
        super(nome, email, senhaHash);
        setNomeFantasia(nomeFantasia);
        setCnpj(cnpj);
        this.endereco = endereco;
        this.horariosFuncionamento = new ArrayList<>();
        this.areasEntrega = new ArrayList<>();
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        if (nomeFantasia == null || nomeFantasia.isBlank()) {
            throw new IllegalArgumentException("Nome fantasia não pode ser nulo ou vazio.");
        }
        this.nomeFantasia = nomeFantasia.trim();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            throw new IllegalArgumentException("CNPJ não pode ser nulo ou vazio.");
        }
        // Remove máscara e valida 14 dígitos numéricos
        String cnpjLimpo = cnpj.replaceAll("[.\\-/]", "");
        if (!cnpjLimpo.matches("\\d{14}")) {
            throw new IllegalArgumentException("CNPJ inválido. Informe 14 dígitos numéricos.");
        }
        this.cnpj = cnpjLimpo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<HorarioFuncionamento> getHorariosFuncionamento() {
        return horariosFuncionamento;
    }

    public void adicionarHorario(HorarioFuncionamento horario) {
        if (horario != null) {
            this.horariosFuncionamento.add(horario);
        }
    }

    public List<AreaEntrega> getAreasEntrega() {
        return areasEntrega;
    }

    public void adicionarAreaEntrega(AreaEntrega area) {
        if (area != null) {
            this.areasEntrega.add(area);
        }
    }
}

