package com.savemymoney.savemymoneyapi.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity(name = "gasto")
@Getter
@Setter
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
