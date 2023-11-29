package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Conta;
import com.savemymoney.savemymoneyapi.entities.QConta;
import com.savemymoney.savemymoneyapi.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContaService {
    @Autowired
    private ContaRepository repository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void salvar(Conta entidade) {
        repository.save(entidade);
    }

    public Conta buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Conta entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
    }

    public List<Conta> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QConta.conta.ativo.eq(true)
                .and(QConta.conta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
