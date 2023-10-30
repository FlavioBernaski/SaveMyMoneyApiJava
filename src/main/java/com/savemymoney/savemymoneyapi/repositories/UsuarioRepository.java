package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UsuarioRepository {
    public Usuario localizarPorEmail(String email) {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setEmail(email);
        usuario.setNome("Nome teste");
        usuario.setSenha("Senha");
        return usuario;
    }
}
