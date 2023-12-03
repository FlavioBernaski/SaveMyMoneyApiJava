package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Cartao;
import com.savemymoney.savemymoneyapi.entities.QCartao;
import com.savemymoney.savemymoneyapi.repositories.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartaoService {
    private final CartaoRepository repository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public CartaoService(
            CartaoRepository repository,
            CustomUserDetailsService customUserDetailsService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public void salvar(Cartao entidade) {
        repository.save(entidade);
    }

    public Cartao buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Cartao entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
    }

    public void excluirTodosDaConta(UUID idConta) {
        List<UUID> cartoes = repository.listarIdAtivosPorConta(idConta);
        cartoes.forEach(this::excluir);
    }

    public List<Cartao> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QCartao.cartao.ativo.eq(true)
                .and(QCartao.cartao.conta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
