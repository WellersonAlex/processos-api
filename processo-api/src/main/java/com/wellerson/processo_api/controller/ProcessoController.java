package com.wellerson.processo_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellerson.processo_api.model.ProcessoJudicial;
import com.wellerson.processo_api.model.StatusProcesso;
import com.wellerson.processo_api.repository.ProcessosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/processos")
public class ProcessoController {

	@Autowired
	private ProcessosRepository processoRepository;

	@GetMapping("/listar")
	public List<ProcessoJudicial> listar(@RequestParam(required = false) StatusProcesso status,
			@RequestParam(required = false) String comarca) {
		if (status != null && comarca != null) {
			return processoRepository.findByStatusAndComarca(status, comarca);
		}
		return processoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProcessoJudicial> buscarPorId(@PathVariable Long id) {
		return processoRepository.findById(id).map(processo -> ResponseEntity.ok(processo))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ProcessoJudicial criar(@Valid @RequestBody ProcessoJudicial processo) {
		return processoRepository.save(processo);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProcessoJudicial> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProcessoJudicial processoAtualizado) {
		return processoRepository.findById(id).map(processo -> {
			// Atualiza campos (exemplo)
			processo.setNumero(processoAtualizado.getNumero());
			processo.setStatus(processoAtualizado.getStatus());
			processo.setComarca(processoAtualizado.getComarca());
			// Atualize outros campos conforme sua entidade

			ProcessoJudicial atualizado = processoRepository.save(processo);
			return ResponseEntity.ok(atualizado);
		}).orElse(ResponseEntity.notFound().build());
	}
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return processoRepository.findById(id)
                .map(processo -> {
                    processoRepository.delete(processo);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
