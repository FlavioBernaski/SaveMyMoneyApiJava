package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Meta;
import com.savemymoney.savemymoneyapi.entities.QMeta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MetaRepository
        extends CrudRepository<Meta, UUID>,
        PagingAndSortingRepository<Meta, UUID>,
        QuerydslPredicateExecutor<Meta>,
        QuerydslBinderCustomizer<QMeta> {

    @Override
    default void customize(QuerydslBindings bindings, QMeta entidade) {
    }

    @Query(value = "select m.id from Meta m where m.usuario.id = :idUsuario and m.ativo")
    List<UUID> listarIdAtivasPorUsuario(@Param("idUsuario") UUID idUsuario);
}
