package com.treshart.Despesas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ResumoDTO
{
    private List<DespesaDTO> despesas;
    private BigDecimal total;
}