package com.dac.cmseventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dac.cmseventos.model.Atividade;
import com.dac.cmseventos.model.dto.AtividadeInput;
import com.dac.cmseventos.repository.AtividadeRepository;
import com.dac.cmseventos.service.AtividadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Atividade", description = "Endpoints atividade")
@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    
    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Operation(summary = "Lista todas as atividades")
    @GetMapping
    public ResponseEntity<List<Atividade>> listar() {
        List<Atividade> atividades = atividadeRepository.findAll();
        return ResponseEntity.ok(atividades);
    }

    @Operation(summary = "Cadastra uma atividade")
    @PostMapping
    public ResponseEntity<Atividade> cadastrar(@RequestBody AtividadeInput atividadeInput) {
        Atividade atividadeSalva = atividadeRepository.save(toDomain(atividadeInput));
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeSalva);
    }

    @Operation(summary = "Edita uma atividade")
    @PutMapping("/{id}")
    public ResponseEntity<Atividade> editar(@PathVariable Long id, @RequestBody Atividade atividade) {
        Atividade atividadeAtual = atividadeService.buscarOuFalhar(id);
        return ResponseEntity.ok(atividadeRepository.save(atividadeAtual));
    }

    @Operation(summary = "Busca uma atividade pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Atividade> buscar(@PathVariable Long id) {
        Atividade atividade = atividadeService.buscarOuFalhar(id);
        return ResponseEntity.ok(atividade);
    }

    @Operation(summary = "Deleta uma atividade")
    @DeleteMapping("/{id}/deletar")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atividadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    public Atividade toDomain(AtividadeInput atividadeInput) {
        Atividade atividade = new Atividade();
        atividade.setNome(atividadeInput.getNome());
        atividade.setDescricao(atividadeInput.getDescricao());
        atividade.setTipo(atividadeInput.getTipo());
        atividade.setData(atividadeInput.getData());
        atividade.setHorarioFinal(atividadeInput.getHorarioFinal());
        atividade.setHorarioInicial(atividadeInput.getHorarioInicial());
        atividade.setEspaco(atividadeInput.getEspaco());
        atividade.setEdicao(atividadeInput.getEdicao());

        return atividade;
    }
    

}
