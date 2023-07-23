package com.veterinaria.dtos.requests;

public class RacaRequestDTO {

	private String nome;
	
	public RacaRequestDTO() {
		
	}

	public RacaRequestDTO(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}