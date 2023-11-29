package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Meta;
import com.savemymoney.savemymoneyapi.entities.QMeta;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MetaRepository
        extends CrudRepository<Meta, UUID>,
        PagingAndSortingRepository<Meta, UUID>,
        QuerydslPredicateExecutor<Meta>,
        QuerydslBinderCustomizer<QMeta> {

    @Override
    default void customize(QuerydslBindings bindings, QMeta entidade) {
    }

}
