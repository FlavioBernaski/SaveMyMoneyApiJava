package com.savemymoney.savemymoneyapi.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "usuario")
public class Usuario {
    @Id
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    private LocalDateTime dataExclusao;
    private long versao = System.currentTimeMillis();


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(LocalDateTime dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public long getVersao() {
        return versao;
    }

    public void setVersao(long versao) {
        this.versao = versao;
    }
}
