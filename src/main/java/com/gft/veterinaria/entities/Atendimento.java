package com.gft.veterinaria.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_atendimento")
public class Atendimento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 4)
	private String ticket;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = false, name = "medico_id")
	private Medico medico;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = false, name = "cachorro_id")
	private Cachorro cachorro;
	
	@Column(nullable = false, precision = 2)
	private Double pesoAtual;
	
	@Column(nullable = false, precision = 2)
	private Double alturaAtual;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime dataHora;
	
	@Column(nullable = false)
	private String diagnostico;
	
	@Column(nullable = true)
	private String comentario;

	public Atendimento() {
		
	}

	public Atendimento(String ticket, Medico medico, Cachorro cachorro, Double pesoAtual, Double alturaAtual, LocalDateTime dataHora,
			String diagnostico, String comentario) {
		this.ticket = ticket;
		this.medico = medico;
		this.cachorro = cachorro;
		this.pesoAtual = pesoAtual;
		this.alturaAtual = alturaAtual;
		this.dataHora = dataHora;
		this.diagnostico = diagnostico;
		this.comentario = comentario;
	}

	public Atendimento(Long id, String ticket, Medico medico, Cachorro cachorro, Double pesoAtual, Double alturaAtual,
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

	public void setPesoAtual(Double peso) {
		this.pesoAtual = peso;
	}

	public Double getAlturaAtual() {
		return alturaAtual;
	}

	public void setAlturaAtual(Double alturaAtual) {
		this.alturaAtual = alturaAtual;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
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
	
	public void atualizaPesoAltura() {
		cachorro.setPeso(this.pesoAtual);
		cachorro.setAltura(this.alturaAtual);
	}

	@Override
	public String toString() {
		return "Atendimento [ticket=" + ticket
					+ ", medico=" + medico.getNome()
					+ ", cachorro=" + cachorro.getNome()
					+ ", peso=" + pesoAtual
					+ ", altura=" + alturaAtual
					+ ", dataHora=" + dataHora
					+ ", diagnostico=" + diagnostico 
					+ ", comentario=" + comentario + "]";
	}
}