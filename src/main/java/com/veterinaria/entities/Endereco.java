package com.veterinaria.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {

	@Column(nullable = false)
	private String logradouro;
	
	@Column(nullable = false)
	private String numero;
	
	@Column(nullable = true)
	private String complemento;
	
	@Column(nullable = false, length = 8)
	private String cep;
	
	public Endereco() {
		
	}

	public Endereco(String logradouro, String numero, String complemento, String cep) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}