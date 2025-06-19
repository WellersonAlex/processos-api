package com.wellerson.processo_api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wellerson.processo_api.model.Audiencia;
import com.wellerson.processo_api.model.ProcessoJudicial;
import com.wellerson.processo_api.model.StatusProcesso;
import com.wellerson.processo_api.repository.AudienciaRepository;
import com.wellerson.processo_api.repository.ProcessosRepository;

@ExtendWith(MockitoExtension.class)
public class AudienciaServiceTest {

    @InjectMocks
    private AudienciaService audienciaService;

    @Mock
    private AudienciaRepository audienciaRepository;

    @Mock
    private ProcessosRepository processoRepo;
    
    @Mock
    private FeriadoService feriadoService;

    private ProcessoJudicial processo;

    @BeforeEach
    public void setUp() {
        processo = new ProcessoJudicial();
        processo.setId(1L);
        processo.setNumero("1234567-89.2020.1.00.0001");
        processo.setStatus(StatusProcesso.ATIVO);
        processo.setVara("Vara Cível");
        processo.setComarca("Natal");
    }

    private Audiencia criarAudiencia(LocalDateTime dataHora) {
        Audiencia audiencia = new Audiencia();
        audiencia.setDataHora(dataHora);
        audiencia.setLocal("Sala 1");
        return audiencia;
    }

    @Test
    void deveAgendarAudienciaComSucesso() {
        Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 6, 18, 10, 0));
        when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));
        when(audienciaRepository.findConflitos(any(), any(), any(), any())).thenReturn(Collections.emptyList());
        when(audienciaRepository.save(any())).thenReturn(audiencia);

        Audiencia resultado = audienciaService.agendar(1L, audiencia);

        assertNotNull(resultado);
        assertEquals(processo, resultado.getProcesso());
    }

    @Test
    void deveLancarExcecaoParaNumeroDeProcessoInvalido() {
        processo.setNumero("123"); // inválido
        when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));
        Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 6, 18, 14, 0));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            audienciaService.agendar(1L, audiencia);
        });

        assertEquals("Número do processo inválido", ex.getMessage());
    }

    @Test
    void naoDeveAgendarAudienciaParaProcessoSuspensoOuArquivado() {
        for (StatusProcesso status : Arrays.asList(StatusProcesso.ARQUIVADO, StatusProcesso.SUSPENSO)) {
            processo.setStatus(status);
            when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));
            Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 6, 19, 10, 0));

            RuntimeException ex = assertThrows(RuntimeException.class, () -> {
                audienciaService.agendar(1L, audiencia);
            });

            assertEquals("Processo não está ativo", ex.getMessage());
        }
    }

    @Test
    void naoDeveAgendarAudienciaEmSabadoOuDomingo() {
        when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));

        // Sábado
        Audiencia audienciaSabado = criarAudiencia(LocalDateTime.of(2025, 6, 21, 14, 0));
        RuntimeException exSab = assertThrows(RuntimeException.class, () -> {
            audienciaService.agendar(1L, audienciaSabado);
        });
        assertEquals("Audiência só pode ser em dias úteis", exSab.getMessage());

        // Domingo
        Audiencia audienciaDomingo = criarAudiencia(LocalDateTime.of(2025, 6, 22, 14, 0));
        RuntimeException exDom = assertThrows(RuntimeException.class, () -> {
            audienciaService.agendar(1L, audienciaDomingo);
        });
        assertEquals("Audiência só pode ser em dias úteis", exDom.getMessage());
    }
    
    @Test
    void naoDeveAgendarAudienciaEmFeriado() {
        when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));
        when(feriadoService.isFeriado(LocalDate.of(2025, 12, 25))).thenReturn(true); // Natal

        Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 12, 25, 10, 0)); // Feriado

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            audienciaService.agendar(1L, audiencia);
        });

        assertEquals("Audiência não pode ser marcada em feriado", ex.getMessage());
    }

    
    @Test
    void naoDeveAgendarQuandoHaConflitoDeAudiencia() {
        Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 6, 18, 10, 30));
        when(processoRepo.findById(1L)).thenReturn(Optional.of(processo));

        // Simula conflito retornando uma lista com uma audiência existente
        when(audienciaRepository.findConflitos(
            any(LocalDateTime.class),
            any(LocalDateTime.class),
            eq("Sala 1"),
            eq("Vara Cível")
        )).thenReturn(List.of(new Audiencia()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            audienciaService.agendar(1L, audiencia);
        });

        assertEquals("Conflito de audiência na vara e local informados", ex.getMessage());
    }
    
    @Test
    void deveRetornarAudienciasPorComarcaEData() {
        String comarca = "Natal";
        LocalDate data = LocalDate.of(2025, 6, 18);
        LocalDateTime inicioDoDia = data.atStartOfDay();          // 2025-06-18T00:00
        LocalDateTime fimDoDia = data.atTime(23, 59, 59);         // 2025-06-18T23:59:59

        Audiencia audiencia = criarAudiencia(LocalDateTime.of(2025, 6, 18, 10, 0));
        List<Audiencia> audienciasEsperadas = List.of(audiencia);

        when(audienciaRepository.findByProcesso_ComarcaAndDataHoraBetween(comarca, inicioDoDia, fimDoDia))
            .thenReturn(audienciasEsperadas);

        List<Audiencia> resultado = audienciaService.consultarAgenda(comarca, data);

        assertEquals(1, resultado.size());
        assertEquals(audiencia.getDataHora(), resultado.get(0).getDataHora());
    }
}

