package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.QCartao;
import com.savemymoney.savemymoneyapi.entities.Usuario;
import com.savemymoney.savemymoneyapi.repositories.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UsuarioService {
    private final UsuarioRepository repository;
    private final CustomUserDetailsService customUserDetailsService;
    private final ContaService contaService;

    @Autowired
    public UsuarioService(
            UsuarioRepository repository,
            CustomUserDetailsService customUserDetailsService,
            ContaService contaService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
        this.contaService = contaService;
    }

    public void salvar(Usuario entidade) {
        repository.save(entidade);
    }

    public Usuario buscar(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Id não consta no banco de dados"));
    }

    public Usuario localizarPorEmail(String email) {
        try {
            return repository.localizarPorEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public void excluir(UUID id) {
        Usuario entidade = buscar(id);
        entidade.setAtivo(false);
        entidade.setVersao(System.currentTimeMillis());
        salvar(entidade);
        contaService.excluirTodasDoUsuario(id);
    }

    public List<Usuario> listar() {
        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QCartao.cartao.ativo.eq(true)
                .and(QCartao.cartao.conta.usuario.id.eq(idUsuarioLogado));
        return repository.findAll(predicate, Pageable.unpaged()).getContent();
    }
}
