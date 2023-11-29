package com.savemymoney.savemymoneyapi.controllers;

import com.savemymoney.savemymoneyapi.entities.Meta;
import com.savemymoney.savemymoneyapi.entities.response.ErrorResponse;
import com.savemymoney.savemymoneyapi.services.MetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/metas")
@Slf4j
public class MetaController {

    @Autowired
    private MetaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void salvar(@RequestBody Meta meta) {
        service.salvar(meta);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Object> buscar(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(service.buscar(id));
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(service.listar());
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> excluir(@PathVariable("id") UUID id) {
        try {
            service.excluir(id);
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
