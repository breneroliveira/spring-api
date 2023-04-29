package com.veterinaria.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "tb_raca")
public class Raca implements Serializable {

	private static final long serialVersionUID = -8556496299109663930L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonSetter("id")
	private Long id;
	
	@Column(nullable = false, name = "raca_nome", unique = true)
	@JsonSetter("name")
	private String nome;

	public Raca() {
		
	}

	public Raca(Long id, String nome) {
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

	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Raca other = (Raca) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome);
	}
}