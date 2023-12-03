package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Conta;
import com.savemymoney.savemymoneyapi.entities.QConta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ContaRepository
        extends CrudRepository<Conta, UUID>,
        PagingAndSortingRepository<Conta, UUID>,
        QuerydslPredicateExecutor<Conta>,
        QuerydslBinderCustomizer<QConta> {

    @Override
    default void customize(QuerydslBindings bindings, QConta entidade) {
    }

    @Query(value = "select c.id from Conta c where c.usuario.id = :idUsuario and c.ativo")
    List<UUID> listarIdAtivasDoUsuario(@Param("idUsuario") UUID idUsuario);

}
