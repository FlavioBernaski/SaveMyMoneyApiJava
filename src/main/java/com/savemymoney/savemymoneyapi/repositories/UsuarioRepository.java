package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UsuarioRepository extends BaseRepository<Usuario, UUID> {
    @Query(value = "select u from usuario u where u.email = :email")
    Usuario localizarPorEmail(@Param("email") String email);
}
