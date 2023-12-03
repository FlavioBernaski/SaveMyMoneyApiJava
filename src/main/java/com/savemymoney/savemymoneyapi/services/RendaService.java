package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.QRenda;
import com.savemymoney.savemymoneyapi.entities.Renda;
import com.savemymoney.savemymoneyapi.repositories.RendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RendaService {
    private final RendaRepository repository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public RendaService(
            RendaRepository repository,
            CustomUserDetailsService customUserDetailsService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
    }

    public void salvar(Renda entidade) {
        repository.save(entidade);
    }

    public Renda buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Renda entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
    }

    public List<Renda> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QRenda.renda.ativo.eq(true)
                .and(QRenda.renda.conta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }

    public void excluirTodasDaConta(UUID idConta) {
        List<UUID> rendas = repository.listarIdAtivasPorConta(idConta);
        rendas.forEach(this::excluir);
    }
}
