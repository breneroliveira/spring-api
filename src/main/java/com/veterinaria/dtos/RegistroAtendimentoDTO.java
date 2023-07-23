package com.veterinaria.dtos;

import java.time.LocalDateTime;

import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Medico;

public class RegistroAtendimentoDTO {

	private String ticket;
	private Medico medico;
	private Cachorro cachorro;
	private Double pesoAtual;
	private Double alturaAtual;
	private LocalDateTime dataHora;
	private String diagnostico;
	private String comentario;
	
	public RegistroAtendimentoDTO() {
		
	}

	public RegistroAtendimentoDTO(String ticket, Medico medico, Cachorro cachorro, LocalDateTime dataHora, Double pesoAtual,
			Double alturaAtual, String diagnostico, String comentario) {
		this.ticket = ticket;
		this.medico = medico;
		this.cachorro = cachorro;
		this.dataHora = dataHora;
		this.pesoAtual = pesoAtual;
		this.alturaAtual = alturaAtual;
		this.diagnostico = diagnostico;
		this.comentario = comentario;
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