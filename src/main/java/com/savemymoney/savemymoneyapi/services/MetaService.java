package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Meta;
import com.savemymoney.savemymoneyapi.entities.QCartao;
import com.savemymoney.savemymoneyapi.repositories.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MetaService {
    @Autowired
    private MetaRepository repository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void salvar(Meta entidade) {
        repository.save(entidade);
    }

    public Meta buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Meta entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
    }

    public List<Meta> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QCartao.cartao.ativo.eq(true)
                .and(QCartao.cartao.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
