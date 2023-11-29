package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QMovimentacao;
import com.savemymoney.savemymoneyapi.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository repository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void salvar(Movimentacao entidade) {
        repository.save(entidade);
    }

    public Movimentacao buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Movimentacao entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
    }

    public List<Movimentacao> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QMovimentacao.movimentacao.ativo.eq(true)
                .and(QMovimentacao.movimentacao.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
