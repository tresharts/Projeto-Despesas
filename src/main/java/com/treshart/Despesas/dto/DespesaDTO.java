package com.treshart.Despesas.dto;

import com.treshart.Despesas.model.Despesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DespesaDTO
{
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime momento;
    private String categoria;

    public DespesaDTO(Despesa despesa)
    {
        this.descricao = despesa.getDescricao();
        this.valor = despesa.getValor();
        this.momento = despesa.getMomento();
        this.categoria = despesa.getCategoria();
    }
}