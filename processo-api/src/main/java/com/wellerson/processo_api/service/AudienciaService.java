package com.wellerson.processo_api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellerson.processo_api.model.Audiencia;
import com.wellerson.processo_api.model.ProcessoJudicial;
import com.wellerson.processo_api.model.StatusProcesso;
import com.wellerson.processo_api.repository.AudienciaRepository;
import com.wellerson.processo_api.repository.ProcessosRepository;

@Service
public class AudienciaService {

    @Autowired
    private AudienciaRepository audienciaRepository;

    @Autowired
    private ProcessosRepository processoRepo;

    public Audiencia agendar(Long processoId, Audiencia audiencia) {
        ProcessoJudicial processo = processoRepo.findById(processoId)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        if (!processo.getNumero().matches("^\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}$")) {
            throw new RuntimeException("Número do processo inválido");
        }
        
        if (processo.getStatus() != StatusProcesso.ATIVO) {
            throw new RuntimeException("Processo não está ativo");
        }

        if (audiencia.getDataHora().getDayOfWeek() == DayOfWeek.SATURDAY ||
            audiencia.getDataHora().getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new RuntimeException("Audiência só pode ser em dias úteis");
        }

        boolean conflito = !audienciaRepository.findConflitos(
            audiencia.getDataHora().withMinute(0),
            audiencia.getDataHora().withMinute(59),
            audiencia.getLocal(),
            processo.getVara()
        ).isEmpty();

        if (conflito) {
            throw new RuntimeException("Conflito de audiência na vara e local informados");
        }

        audiencia.setProcesso(processo);
        return audienciaRepository.save(audiencia);
    }

    public List<Audiencia> consultarAgenda(String comarca, LocalDate data) {
        LocalDateTime inicioDoDia = data.atStartOfDay(); // 00:00
        LocalDateTime fimDoDia = data.atTime(23, 59, 59); // 23:59:59

        return audienciaRepository.findByProcesso_ComarcaAndDataHoraBetween(comarca, inicioDoDia, fimDoDia);
    }

}
