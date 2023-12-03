package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QMovimentacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoRepository
        extends CrudRepository<Movimentacao, UUID>,
        PagingAndSortingRepository<Movimentacao, UUID>,
        QuerydslPredicateExecutor<Movimentacao>,
        QuerydslBinderCustomizer<QMovimentacao> {

    @Override
    default void customize(QuerydslBindings bindings, QMovimentacao entidade) {
    }

    @Query(value = "select m.id from Movimentacao m where m.conta.id = :idConta and m.ativo")
    List<UUID> listarIdAtivosPorConta(@Param("idConta") UUID idConta);

}
