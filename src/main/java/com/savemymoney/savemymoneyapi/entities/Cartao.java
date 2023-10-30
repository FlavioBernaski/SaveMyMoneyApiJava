package com.savemymoney.savemymoneyapi.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity(name = "cartao")
public class Cartao {
    @Id
    private UUID id;
    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String descricao;
    private Date vencimentoFatura;
    private LocalDateTime dataExclusao;
    private long versao = System.currentTimeMillis();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getVencimentoFatura() {
        return vencimentoFatura;
    }

    public void setVencimentoFatura(Date vencimentoFatura) {
        this.vencimentoFatura = vencimentoFatura;
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
