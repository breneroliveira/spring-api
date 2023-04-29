package com.veterinaria.dto.especialidade;

public class EspecialidadeDTO {

	private String titulo;

	public EspecialidadeDTO() {
		
	}

	public EspecialidadeDTO(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}