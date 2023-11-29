package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Conta;
import com.savemymoney.savemymoneyapi.entities.QConta;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ContaRepository
        extends CrudRepository<Conta, UUID>,
        PagingAndSortingRepository<Conta, UUID>,
        QuerydslPredicateExecutor<Conta>,
        QuerydslBinderCustomizer<QConta> {

    @Override
    default void customize(QuerydslBindings bindings, QConta entidade) {
    }

}
