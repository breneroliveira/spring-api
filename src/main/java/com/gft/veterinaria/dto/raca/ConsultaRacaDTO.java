package com.gft.veterinaria.dto.raca;

import java.io.Serializable;

public class ConsultaRacaDTO implements Serializable {

	private static final long serialVersionUID = -1528364485370402404L;
	private Long id;
	private String nome;
	
	public ConsultaRacaDTO() {
		
	}

	public ConsultaRacaDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}