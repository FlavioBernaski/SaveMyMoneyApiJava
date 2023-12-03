package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Meta;
import com.savemymoney.savemymoneyapi.entities.QMeta;
import com.savemymoney.savemymoneyapi.repositories.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MetaService {
    private final MetaRepository repository;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public MetaService(
            MetaRepository repository,
            CustomUserDetailsService customUserDetailsService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
    }

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

    public void excluirTodasDoUsuario(UUID idUsuario) {
        List<UUID> metas = repository.listarIdAtivasPorUsuario(idUsuario);
        metas.forEach(this::excluir);
    }

    public List<Meta> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QMeta.meta.ativo.eq(true)
                .and(QMeta.meta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
