package com.treshart.Despesas.controller;

import com.treshart.Despesas.dto.DespesaDTO;
import com.treshart.Despesas.dto.ResumoDTO;
import com.treshart.Despesas.model.Despesa;
import com.treshart.Despesas.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController
{
    @Autowired
    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<DespesaDTO>> listarTodas()
    {
        List<DespesaDTO> lista = service
                .listarTodas()
                .stream()
                .map(DespesaDTO::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespesaDTO> buscarPorId(@PathVariable Long id)
    {
        Despesa despesa = service.buscarPorId(id);

        return ResponseEntity.ok(new DespesaDTO(despesa));
    }

    @GetMapping("/categoria")
    public ResponseEntity<ResumoDTO> buscarPorCategoria(@RequestParam String categoria)
    {
        ResumoDTO resumo = service.buscarPorCategoria(categoria);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/dia/{ano}/{mes}/{dia}")
    public ResponseEntity<ResumoDTO> buscarPorDia(@PathVariable int ano,
                                                  @PathVariable int mes,
                                                  @PathVariable int dia)
    {
        ResumoDTO resumo = service.buscarPorDia(ano, mes, dia);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/dia/{ano}/{mes}")
    public ResponseEntity<ResumoDTO> buscarPorMes(@PathVariable int ano,
                                                  @PathVariable int mes)
    {
        ResumoDTO resumo = service.buscarPorMes(ano, mes);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/dia/{ano}")
    public ResponseEntity<ResumoDTO> buscarPorAno(@PathVariable int ano)
    {
        ResumoDTO resumo = service.buscarPorAno(ano);
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/valor-minimo")
    public ResponseEntity<List<DespesaDTO>> buscarPorValorMinimo(@RequestParam double valor)
    {
        List<DespesaDTO> lista = service
                .buscarPorValorMinimo(valor)
                .stream()
                .map(DespesaDTO::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/valor-maximo")
    public ResponseEntity<List<DespesaDTO>> buscarPorValorMaximo(@RequestParam double valor)
    {
        List<DespesaDTO> lista = service
                .buscarPorValorMaximo(valor)
                .stream()
                .map(DespesaDTO::new)
                .toList();

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<DespesaDTO> salvar(@RequestBody Despesa despesa)
    {
        Despesa despesaSalva = service.salvar(despesa);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DespesaDTO(despesaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaDTO> atualizar(@PathVariable Long id,
                                                @RequestBody Despesa despesa)
    {
        Despesa despesaAtualizada = service.atualizar(id, despesa);
        return ResponseEntity.ok(new DespesaDTO(despesaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id)
    {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}