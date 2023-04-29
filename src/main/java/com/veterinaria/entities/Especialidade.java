package com.veterinaria.entities;

import javax.persistence.Embeddable;

@Embeddable
public class Especialidade {

	private String titulo;

	public Especialidade() {
		
	}

	public Especialidade(String titulo) {
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}