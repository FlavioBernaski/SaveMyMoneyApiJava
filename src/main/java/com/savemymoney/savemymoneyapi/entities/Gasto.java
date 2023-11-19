package com.savemymoney.savemymoneyapi.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "gasto")
public class Gasto {
    @Id
    private UUID id;
    private boolean ativo = true;
    private long versao = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String descricao;
    private Double valor;
    private Date dataEntrada;
    private int parcelas;
    @OneToOne
    @JoinColumn(name = "idCartao")
    private Cartao cartao;
}
