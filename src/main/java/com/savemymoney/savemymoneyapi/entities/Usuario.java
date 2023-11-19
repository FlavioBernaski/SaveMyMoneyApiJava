package com.savemymoney.savemymoneyapi.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario {
    @Id
    private UUID id;
    private boolean ativo = true;
    private long versao = System.currentTimeMillis();
    private String nome;
    private String email;

    private String senha;


    public Usuario() {
    }

    public Usuario(String email) {
        this.email = email;
    }

}