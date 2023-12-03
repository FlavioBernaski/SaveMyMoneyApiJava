package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Cartao;
import com.savemymoney.savemymoneyapi.entities.QCartao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartaoRepository
        extends CrudRepository<Cartao, UUID>,
        PagingAndSortingRepository<Cartao, UUID>,
        QuerydslPredicateExecutor<Cartao>,
        QuerydslBinderCustomizer<QCartao> {

    @Override
    default void customize(QuerydslBindings bindings, QCartao entidade) {
    }

    @Query(value = "select c.id from Cartao c where c.conta.id = :idConta and c.ativo")
    List<UUID> listarIdAtivosPorConta(@Param("idConta") UUID idConta);
}
