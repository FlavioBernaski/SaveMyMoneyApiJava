package com.savemymoney.savemymoneyapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "conta")
public class Conta {
    @Id
    private UUID id;
    private boolean ativo = true;
    private long versao = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String descricao;
    private Integer saldo;
}
