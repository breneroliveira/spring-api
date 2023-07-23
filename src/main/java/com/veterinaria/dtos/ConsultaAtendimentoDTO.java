package com.veterinaria.dtos;

import java.time.LocalDateTime;

import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Medico;

public class ConsultaAtendimentoDTO {

	private Long id;
	private String ticket;
	private Medico medico;
	private Cachorro cachorro;
	private Double pesoAtual;
	private Double alturaAtual;
	private LocalDateTime dataHora;
	private String diagnostico;
	private String comentario;
	
	public ConsultaAtendimentoDTO() {
		
	}

	public ConsultaAtendimentoDTO(Long id, String ticket, Medico medico, Cachorro cachorro, Double pesoAtual, Double alturaAtual,
			LocalDateTime dataHora, String diagnostico, String comentario) {
		this.id = id;
		this.ticket = ticket;
		this.medico = medico;
		this.cachorro = cachorro;
		this.pesoAtual = pesoAtual;
		this.alturaAtual = alturaAtual;
		this.dataHora = dataHora;
		this.diagnostico = diagnostico;
		this.comentario = comentario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Cachorro getCachorro() {
		return cachorro;
	}

	public void setCachorro(Cachorro cachorro) {
		this.cachorro = cachorro;
	}

	public Double getPesoAtual() {
		return pesoAtual;
	}

	public void setPesoAtual(Double pesoAtual) {
		this.pesoAtual = pesoAtual;
	}

	public Double getAlturaAtual() {
		return alturaAtual;
	}

	public void setAlturaAtual(Double alturaAtual) {
		this.alturaAtual = alturaAtual;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}