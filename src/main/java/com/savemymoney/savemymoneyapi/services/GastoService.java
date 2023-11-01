package com.savemymoney.savemymoneyapi.services;

import com.savemymoney.savemymoneyapi.entities.Gasto;
import com.savemymoney.savemymoneyapi.repositories.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GastoService {
    @Autowired
    private GastoRepository repository;

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
}
