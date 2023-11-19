package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.Gasto;
import com.savemymoney.savemymoneyapi.entities.QGasto;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface GastoRepository
        extends CrudRepository<Gasto, UUID>,
        PagingAndSortingRepository<Gasto, UUID>,
        QuerydslPredicateExecutor<Gasto>,
        QuerydslBinderCustomizer<QGasto> {

    @Override
    default public void customize(QuerydslBindings bindings, QGasto usuario) {
    }

}
