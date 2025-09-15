package com.treshart.Despesas.repository;

import com.treshart.Despesas.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>
{
    List<Despesa> findByCategoria(String categoria);
    List<Despesa> findByMomentoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Despesa> findByValorGreaterThanEqual(double valor);
    List<Despesa> findByValorLessThanEqual(double valor);
}