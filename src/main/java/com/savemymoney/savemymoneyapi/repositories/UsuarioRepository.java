package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.QUsuario;
import com.savemymoney.savemymoneyapi.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UsuarioRepository
        extends CrudRepository<Usuario, UUID>,
        PagingAndSortingRepository<Usuario, UUID>,
        QuerydslPredicateExecutor<Usuario>,
        QuerydslBinderCustomizer<QUsuario> {

    @Override
    default public void customize(QuerydslBindings bindings, QUsuario entidade) {
    }

    @Query("select u from Usuario u where u.email = :email")
    Usuario localizarPorEmail(@Param("email") String email);
}
