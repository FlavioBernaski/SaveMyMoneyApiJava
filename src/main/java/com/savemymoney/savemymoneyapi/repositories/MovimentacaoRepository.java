package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QMovimentacao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MovimentacaoRepository
        extends CrudRepository<Movimentacao, UUID>,
        PagingAndSortingRepository<Movimentacao, UUID>,
        QuerydslPredicateExecutor<Movimentacao>,
        QuerydslBinderCustomizer<QMovimentacao> {

    @Override
    default void customize(QuerydslBindings bindings, QMovimentacao entidade) {
    }

}
