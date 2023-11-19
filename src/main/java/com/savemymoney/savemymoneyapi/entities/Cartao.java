package com.savemymoney.savemymoneyapi.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "cartao")
public class Cartao {
    @Id
    private UUID id;
    private boolean ativo = true;
    private long versao = System.currentTimeMillis();
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String descricao;
    private Date vencimentoFatura;
}
