package com.savemymoney.savemymoneyapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cartao")
public class Meta {
    public static final String TIPO_FIXA = "f";
    public static final String TIPO_SOMADA = "s";

    @Id
    private UUID id;
    private boolean ativo = true;
    private long versao = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String descricao;
    private Double valor;
    private String tipo = TIPO_FIXA;
}
