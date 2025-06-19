package com.wellerson.processo_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Audiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private TipoAudiencia tipo;

    private String local;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ProcessoJudicial processo;

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public TipoAudiencia getTipo() {
		return tipo;
	}

	public void setTipo(TipoAudiencia tipo) {
		this.tipo = tipo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public ProcessoJudicial getProcesso() {
		return processo;
	}

	public void setProcesso(ProcessoJudicial processo) {
		this.processo = processo;
	}
}

