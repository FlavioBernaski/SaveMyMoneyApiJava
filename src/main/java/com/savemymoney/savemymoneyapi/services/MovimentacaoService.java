package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QMovimentacao;
import com.savemymoney.savemymoneyapi.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovimentacaoService {
    private final MovimentacaoRepository repository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public MovimentacaoService(
            MovimentacaoRepository repository,
            CustomUserDetailsService customUserDetailsService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
    }

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

    public void excluirTodasDaConta(UUID idConta) {
        List<UUID> movimentacoes = repository.listarIdAtivosPorConta(idConta);
        movimentacoes.forEach(this::excluir);
    }

    public void excluirTodasDoCartao(UUID idCartao) {
        List<UUID> movimentacoes = repository.listarIdAtivosPorCartao(idCartao);
        movimentacoes.forEach(this::excluir);
    }

    public List<Movimentacao> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QMovimentacao.movimentacao.ativo.eq(true)
                .and(QMovimentacao.movimentacao.conta.usuario.id.eq(idUsuarioLogado));
        Sort sort = Sort.by(Sort.Direction.ASC, "dataEntrada");
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        return repository.findAll(predicate, pageable).getContent();
    }
}
