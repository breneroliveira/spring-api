package com.veterinaria.dtos;

public class RegistroRacaDTO {

	private String nome;
	
	public RegistroRacaDTO() {
		
	}

	public RegistroRacaDTO(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}