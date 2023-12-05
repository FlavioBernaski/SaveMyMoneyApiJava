package com.savemymoney.savemymoneyapi.services;

import com.querydsl.core.types.Predicate;
import com.savemymoney.savemymoneyapi.entities.Cartao;
import com.savemymoney.savemymoneyapi.entities.Movimentacao;
import com.savemymoney.savemymoneyapi.entities.QMovimentacao;
import com.savemymoney.savemymoneyapi.repositories.MovimentacaoRepository;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MovimentacaoService {
    private final MovimentacaoRepository repository;
    private final CustomUserDetailsService customUserDetailsService;
    private final CartaoService cartaoService;

    @Autowired
    public MovimentacaoService(
            MovimentacaoRepository repository,
            CustomUserDetailsService customUserDetailsService,
            CartaoService cartaoService) {
        this.repository = repository;
        this.customUserDetailsService = customUserDetailsService;
        this.cartaoService = cartaoService;
    }

    public void salvar(Movimentacao entidade) {
        if (entidade.isAtivo() && entidade.getParcelas() != null &&
                entidade.getParcelas() > 1 && (entidade.getParcelaAtual() == null || entidade.getParcelaAtual() < entidade.getParcelas())) {
            if (entidade.getParcelaAtual() == null) {
                entidade.setParcelaAtual(1);
            }
            Cartao cartao = cartaoService.buscar(entidade.getCartao().getId());
            int parcelasRestantes = entidade.getParcelas() - entidade.getParcelaAtual();
            for (int i = 0; i < parcelasRestantes; i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(entidade.getDataEntrada());
                calendar.add(Calendar.MONTH, i + 1);
                calendar.set(Calendar.DAY_OF_MONTH, cartao.getVencimentoFatura());

                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setId(UUID.randomUUID());
                movimentacao.setConta(entidade.getConta());
                movimentacao.setDescricao(entidade.getDescricao());
                movimentacao.setValor(entidade.getValor());
                movimentacao.setDataEntrada(calendar.getTime());
                movimentacao.setParcelas(entidade.getParcelas());
                movimentacao.setParcelaAtual(entidade.getParcelaAtual() + i);
                movimentacao.setTipo(entidade.getTipo());
                movimentacao.setCartao(cartao);
                repository.save(movimentacao);
            }
        }
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

    public List<Movimentacao> listarFiltrandoData(int ano, int mes) {
        Date dataDe;
        Date dataAte;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        dataDe = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        dataAte = calendar.getTime();

        UUID idUsuarioLogado = customUserDetailsService.getUsuarioLogado().getId();
        Predicate predicate = QMovimentacao.movimentacao.ativo.eq(true)
                .and(QMovimentacao.movimentacao.conta.usuario.id.eq(idUsuarioLogado))
                .and(QMovimentacao.movimentacao.dataEntrada.goe(dataDe).and(QMovimentacao.movimentacao.dataEntrada.loe(dataAte)));

        Sort sort = Sort.by(Sort.Direction.ASC, "dataEntrada");
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        List<Movimentacao> content = repository.findAll(predicate, pageable).getContent();
        return content;
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
