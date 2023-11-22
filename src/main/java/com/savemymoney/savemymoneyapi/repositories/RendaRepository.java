package com.savemymoney.savemymoneyapi.repositories;

import com.savemymoney.savemymoneyapi.entities.QRenda;
import com.savemymoney.savemymoneyapi.entities.Renda;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RendaRepository
        extends CrudRepository<Renda, UUID>,
        PagingAndSortingRepository<Renda, UUID>,
        QuerydslPredicateExecutor<Renda>,
        QuerydslBinderCustomizer<QRenda> {

    @Override
    default public void customize(QuerydslBindings bindings, QRenda entidade) {
    }

}
