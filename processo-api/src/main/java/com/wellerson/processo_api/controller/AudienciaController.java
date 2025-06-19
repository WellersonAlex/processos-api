package com.wellerson.processo_api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wellerson.processo_api.model.Audiencia;
import com.wellerson.processo_api.repository.AudienciaRepository;
import com.wellerson.processo_api.service.AudienciaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/audiencias")
public class AudienciaController {

    @Autowired
    private AudienciaService audienciaService;
    
    @Autowired
    private AudienciaRepository audienciaRepository;

    @PostMapping("/{processoId}")
    public Audiencia agendar(@PathVariable Long processoId, @RequestBody @Valid Audiencia audiencia) {
        return audienciaService.agendar(processoId, audiencia);
    }

    @GetMapping("/agenda")
    public List<Audiencia> consultarAgenda(@RequestParam String comarca,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return audienciaService.consultarAgenda(comarca, data);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Audiencia> buscarPorId(@PathVariable Long id) {
        return audienciaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    public List<Audiencia> listarTodas() {
        return audienciaRepository.findAll();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Audiencia> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid Audiencia novaAudiencia) {
        return audienciaRepository.findById(id)
                .map(audiencia -> {
                    audiencia.setDataHora(novaAudiencia.getDataHora());
                    audiencia.setLocal(novaAudiencia.getLocal());

                    Audiencia atualizada = audienciaRepository.save(audiencia);
                    return ResponseEntity.ok(atualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir audiência
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return audienciaRepository.findById(id)
                .map(audiencia -> {
                    audienciaRepository.delete(audiencia);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
