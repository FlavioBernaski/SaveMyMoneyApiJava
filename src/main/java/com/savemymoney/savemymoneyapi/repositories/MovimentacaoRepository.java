package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QGasto;
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
        QuerydslBinderCustomizer<QGasto> {

    @Override
    default public void customize(QuerydslBindings bindings, QGasto entidade) {
    }

}
