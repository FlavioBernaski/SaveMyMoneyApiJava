package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.QRenda;
import com.savemymoney.savemymoneyapi.entities.Renda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RendaRepository
        extends CrudRepository<Renda, UUID>,
        PagingAndSortingRepository<Renda, UUID>,
        QuerydslPredicateExecutor<Renda>,
        QuerydslBinderCustomizer<QRenda> {

    @Override
    default void customize(QuerydslBindings bindings, QRenda entidade) {
    }

    @Query(value = "select r.id from Renda r where r.conta.id = :idConta and r.ativo")
    List<UUID> listarIdAtivasPorConta(@Param("idConta")UUID idConta);
}
