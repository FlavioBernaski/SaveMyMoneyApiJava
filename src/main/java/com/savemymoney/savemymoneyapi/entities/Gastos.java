package com.savemymoney.savemymoneyapi.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "gastos")
public class Gastos {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
}
