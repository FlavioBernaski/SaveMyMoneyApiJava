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
    private final ContaRepository repository;
    private final CustomUserDetailsService customUserDetailsService;
    private final RendaService rendaService;
    private final CartaoService cartaoService;
    private final MovimentacaoService movimentacaoService;

    @Autowired
    public ContaService(
            ContaRepository repository,
            CustomUserDetailsService customUserDetailsService,
            RendaService rendaService,
            CartaoService cartaoService,
            MovimentacaoService movimentacaoService
    ) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
        this.rendaService = rendaService;
        this.cartaoService = cartaoService;
        this.movimentacaoService = movimentacaoService;
    }

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

    public void excluirTodasDoUsuario(UUID idUsuario) {
        List<UUID> contas = repository.listarIdAtivasDoUsuario(idUsuario);
        contas.forEach(this::excluir);
        contas.forEach(rendaService::excluirTodasDaConta);
        contas.forEach(cartaoService::excluirTodosDaConta);
        contas.forEach(movimentacaoService::excluirTodasDaConta);
    }

    public List<Conta> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QConta.conta.ativo.eq(true)
                .and(QConta.conta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
