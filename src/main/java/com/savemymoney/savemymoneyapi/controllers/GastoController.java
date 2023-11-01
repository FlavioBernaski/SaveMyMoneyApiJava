package com.savemymoney.savemymoneyapi.controllers;

import com.savemymoney.savemymoneyapi.entities.Gasto;
import com.savemymoney.savemymoneyapi.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private GastoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void salvar(@RequestBody Gasto gasto) {
        service.salvar(gasto);
    }
    @GetMapping("/{id}")
    @ResponseBody
    public Gasto buscar(@PathVariable("id") UUID id) {
        return service.buscar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") UUID id) {
        service.excluir(id);
    }
}
