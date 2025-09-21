package com.treshart.Despesas.service;

import com.treshart.Despesas.dto.ResumoDTO;
import com.treshart.Despesas.exception.CategoriaInvalidaException;
import com.treshart.Despesas.exception.IdNaoEncontradoException;
import com.treshart.Despesas.exception.ValorInvalidoException;
import com.treshart.Despesas.model.Despesa;
import com.treshart.Despesas.repository.DespesaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DespesaServiceTest
{
    @InjectMocks
    private DespesaService service;

    @Mock
    private DespesaRepository repository;

    @Test
    public void deveListarTodas()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findAll())
                .thenReturn(Collections.singletonList(despesa));

        List<Despesa> resultado = service.listarTodas();
        assertEquals(1, resultado.size());
    }

    @Test
    public void deveBuscarId()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findById(1L))
                .thenReturn(Optional.of(despesa));

        Despesa resultado = service.buscarPorId(1L);
        assertEquals(1L, resultado.getId());
    }

    @Test
    public void deveRetornarIdNaoEncontradoException()
    {
        when(repository
                .findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(IdNaoEncontradoException.class, () -> service.buscarPorId(1L));
    }

    @Test
    public void deveBuscarPorCategoria()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findByCategoria("Alimentação"))
                .thenReturn(Collections.singletonList(despesa));

        ResumoDTO resultado = service.buscarPorCategoria("Alimentação");
        assertEquals("Alimentação", resultado.getCategoria());
    }

    @Test
    public void deveBuscarPorDia()
    {
        int ano = 2025;
        int mes = 9;
        int dia = 20;

        LocalDateTime inicio = LocalDate.of(ano, mes, dia).atStartOfDay();
        LocalDateTime fim = LocalDate.of(ano, mes, dia).atTime(LocalTime.MAX);

        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                inicio,
                "Alimentação");

        List<Despesa> despesas = Collections.singletonList(despesa);

        when(repository
                .findByMomentoBetween(inicio, fim))
                .thenReturn(despesas);

        ResumoDTO resultado = service.buscarPorDia(ano, mes, dia);

        assertEquals(0, resultado
                .getTotal()
                .compareTo(BigDecimal
                        .valueOf(50)));
    }

    @Test
    public void deveBuscarPorMes()
    {
        int ano = 2025;
        int mes = 9;

        LocalDateTime inicio = LocalDate.of(ano, mes, 1).atStartOfDay();
        LocalDateTime fim = YearMonth.of(ano, mes).atEndOfMonth().atTime(LocalTime.MAX);

        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                inicio,
                "Alimentação");

        List<Despesa> despesas = Collections.singletonList(despesa);

        when(repository
                .findByMomentoBetween(inicio, fim))
                .thenReturn(despesas);

        ResumoDTO resultado = service.buscarPorMes(ano, mes);

        assertEquals(0, resultado
                .getTotal()
                .compareTo(BigDecimal
                        .valueOf(50)));
    }

    @Test
    public void deveBuscarPorAno()
    {
        int ano = 2025;

        LocalDateTime inicio = LocalDate.of(ano, 1, 1).atStartOfDay();
        LocalDateTime fim = LocalDate.of(ano, 12, 31).atTime(LocalTime.MAX);

        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                inicio,
                "Alimentação");

        List<Despesa> despesas = Collections.singletonList(despesa);

        when(repository
                .findByMomentoBetween(inicio, fim))
                .thenReturn(despesas);

        ResumoDTO resultado = service.buscarPorAno(ano);

        assertEquals(0, resultado
                .getTotal()
                .compareTo(BigDecimal
                        .valueOf(50)));
    }

    @Test
    public void deveBuscarValorMinimo()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findByValorGreaterThanEqual(50))
                .thenReturn(Collections.singletonList(despesa));

        List<Despesa> resultado = service.buscarPorValorMinimo(50);
        assertEquals(1, resultado.size());
    }

    @Test
    public void deveBuscarValorMaximo()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findByValorLessThanEqual(50))
                .thenReturn(Collections.singletonList(despesa));

        List<Despesa> resultado = service.buscarPorValorMaximo(50);
        assertEquals(1, resultado.size());
    }

    @Test
    public void deveSalvar()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .save(any(Despesa.class)))
                .thenReturn(despesa);

        Despesa resultado = service.salvar(despesa);
        assertEquals(despesa, resultado);
    }

    @Test
    public void deveSalvarComMomentoAtual()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                null,
                "Alimentação");

        when(repository
                .save(any(Despesa.class)))
                .thenAnswer
                        (invocationOnMock
                                -> invocationOnMock
                                .getArgument(0));

        Despesa resultado = service.salvar(despesa);
        assertNotNull(resultado.getMomento());
    }

    @Test
    public void deveRetornarValorInvalidoException()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(-50),
                LocalDateTime.now(),
                "Alimentação");

        assertThrows(ValorInvalidoException.class, () -> service.salvar(despesa));
    }

    @Test
    public void deveRetornarCategoriaInvalidaException() {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                null);

        assertThrows(CategoriaInvalidaException.class, () -> service.salvar(despesa));
    }

    @Test
    public void deveAtualizar()
    {
        Despesa despesaVelha = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        Despesa despesaNova = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(80),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .findById(1L))
                .thenReturn(Optional.of(despesaVelha));

        when(repository
                .save(any(Despesa.class)))
                .thenReturn(despesaNova);

        Despesa resultado = service.atualizar(1L, despesaNova);
        assertEquals(BigDecimal.valueOf(80), resultado.getValor());
    }

    @Test
    public void deveDeletar()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                "Alimentação");

        when(repository
                .existsById(1L))
                .thenReturn(true);

        service.deletar(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    public void deveRetornarIdNaoEncontradoExceptionAoDeletar()
    {
        when(repository
                .existsById(1L))
                .thenReturn(false);

        assertThrows(IdNaoEncontradoException.class, () -> service.deletar(1L));
    }

    @Test
    public void deveCalcularTotal()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(51),
                LocalDateTime.now(),
                "Alimentação");

        Despesa despesa2 = new Despesa(
                2L,
                "Jantar",
                BigDecimal.valueOf(49),
                LocalDateTime.now(),
                "Alimentação");

        List<Despesa> despesas = List.of(despesa, despesa2);
        BigDecimal resultado = service.total(despesas);
        assertEquals(new BigDecimal(100).setScale(2), resultado);
    }

    @Test
    public void deveRetornarResumo()
    {
        Despesa despesa = new Despesa(
                1L,
                "Almoço",
                BigDecimal.valueOf(51),
                LocalDateTime.now(),
                "Alimentação");

        Despesa despesa2 = new Despesa(
                2L,
                "Jantar",
                BigDecimal.valueOf(49),
                LocalDateTime.now(),
                "Alimentação");

        List<Despesa> despesas = List.of(despesa, despesa2);
        String categoria = "Alimentação";
        ResumoDTO resultado = service.resumo(despesas, categoria);

        assertEquals(2, resultado
                .getDespesas()
                .size());

        assertEquals(BigDecimal.valueOf(100)
                .setScale(2), resultado
                .getTotal());

        assertEquals(categoria, resultado.getCategoria());
    }
}