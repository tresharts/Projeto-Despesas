package com.treshart.Despesas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "despesas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despesa
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @Column(precision = 19, scale = 2)
    private BigDecimal valor;
    private LocalDateTime momento;
    private String categoria;

    public void atualizarDespesa(Despesa nova)
    {
        this.descricao = nova.getDescricao();
        this.valor = nova.getValor();
        this.momento = nova.getMomento();
        this.categoria = nova.getCategoria();
    }
}