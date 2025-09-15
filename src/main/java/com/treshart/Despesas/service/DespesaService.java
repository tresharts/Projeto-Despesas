package com.treshart.Despesas.service;

import com.treshart.Despesas.dto.DespesaDTO;
import com.treshart.Despesas.dto.ResumoDTO;
import com.treshart.Despesas.exception.CategoriaInvalidaException;
import com.treshart.Despesas.exception.IdNaoEncontradoException;
import com.treshart.Despesas.exception.ValorInvalidoException;
import com.treshart.Despesas.model.Despesa;
import com.treshart.Despesas.repository.DespesaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaService
{
    @Autowired
    private final DespesaRepository repository;

    public DespesaService(DespesaRepository repository)
    {
        this.repository = repository;
    }

    public List<Despesa> listarTodas()
    {
        return repository.findAll();
    }

    public Despesa buscarPorId(Long id)
    {
        return repository
                .findById(id)
                .orElseThrow (() -> new IdNaoEncontradoException("ID: " + id + " não encontrado."));
    }

    public ResumoDTO buscarPorCategoria(String categoria)
    {
        List<Despesa> despesas = repository.findByCategoria(categoria);

        return resumo(despesas);
    }

    public ResumoDTO buscarPorDia(int ano, int mes, int dia)
    {
        LocalDate data = LocalDate.of(ano, mes, dia);
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);

        List<Despesa> despesas = repository.findByMomentoBetween(inicio, fim);

        return resumo(despesas);
    }

    public ResumoDTO buscarPorMes(int ano, int mes)
    {
        LocalDateTime inicio = LocalDate
                .of(ano, mes, 1)
                .atStartOfDay();

        LocalDateTime fim = LocalDate
                .of(ano, mes, YearMonth
                        .of(ano, mes)
                        .lengthOfMonth())
                .atTime(LocalTime.MAX);

        List<Despesa> despesas = repository.findByMomentoBetween(inicio, fim);

        return resumo(despesas);
    }

    public ResumoDTO buscarPorAno(int ano)
    {
        LocalDateTime inicio = LocalDate.of(ano, 1, 1).atStartOfDay();
        LocalDateTime fim = LocalDate.of(ano, 12, 31).atTime(LocalTime.MAX);

        List<Despesa> despesas = repository.findByMomentoBetween(inicio, fim);

        return resumo(despesas);
    }

    public List<Despesa> buscarPorValorMinimo(double valor)
    {
        return repository.findByValorGreaterThanEqual(valor);
    }

    public List<Despesa> buscarPorValorMaximo(double valor)
    {
        return repository.findByValorLessThanEqual(valor);
    }

    public Despesa salvar(@NotNull Despesa despesa)
    {
        if (despesa.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException();
        }
        if (despesa.getCategoria() == null) {
           throw new CategoriaInvalidaException();
        }
        if (despesa.getMomento() == null) {
            despesa.setMomento(LocalDateTime.now());
        }
        return repository.save(despesa);
    }

    public Despesa atualizar(Long id, Despesa despesaAtualizada)
    {
        Despesa despesaAntiga = buscarPorId(id);
        despesaAntiga.atualizarDespesa(despesaAtualizada);

        return repository.save(despesaAntiga);
    }

    public void deletar(Long id)
    {
        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException("ID: " + id + " não encontrado.");
        }
    }

    public ResumoDTO resumo(List<Despesa> despesas)
    {
        BigDecimal total = total(despesas);
        List<DespesaDTO> lista = despesas
                .stream()
                .map(DespesaDTO::new)
                .toList();

        return new ResumoDTO(lista, total);
    }

    public BigDecimal total(@NotNull List<Despesa> despesas)
    {
        return despesas
                .stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}