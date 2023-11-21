package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Gasto;
import com.savemymoney.savemymoneyapi.entities.QGasto;
import com.savemymoney.savemymoneyapi.repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GastoService {
    @Autowired
    private GastoRepository repository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    ;

    public void salvar(Gasto gasto) {
        repository.save(gasto);
    }

    public Gasto buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public void excluir(UUID id) {
        Gasto gasto = buscar(id);
        gasto.setAtivo(false);
        gasto.setVersao(System.currentTimeMillis());
        salvar(gasto);
    }

    public List<Gasto> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QGasto.gasto.ativo.eq(true)
                .and(QGasto.gasto.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
