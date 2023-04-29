package com.veterinaria.dto.cachorro;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.veterinaria.entities.Atendimento;
import com.veterinaria.entities.Cliente;
import com.veterinaria.entities.Raca;

public class ConsultaCachorroDTO {

	private Long id;
	private String numeroRegistro;
	private String nome;
	private Raca raca;
	private Double peso;
	private Double altura;
	private Date dataNascimento;
	private Cliente cliente;
	private Set<Atendimento> atendimentos = new HashSet<>();
	
	public ConsultaCachorroDTO() {
		
	}
	
	public ConsultaCachorroDTO(Long id, String numeroRegistro, String nome, Raca raca, Double peso, Double altura,
			Date dataNascimento, Cliente cliente) {
		this.id = id;
		this.numeroRegistro = numeroRegistro;
		this.nome = nome;
		this.raca = raca;
		this.peso = peso;
		this.altura = altura;
		this.dataNascimento = dataNascimento;
		this.cliente = cliente;
	}

	public ConsultaCachorroDTO(Long id, String numeroRegistro, String nome, Raca raca, Double peso, Double altura,
			Date dataNascimento, Cliente cliente, Set<Atendimento> atendimentos) {
		this.id = id;
		this.numeroRegistro = numeroRegistro;
		this.nome = nome;
		this.raca = raca;
		this.peso = peso;
		this.altura = altura;
		this.dataNascimento = dataNascimento;
		this.cliente = cliente;
		this.atendimentos = atendimentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(Set<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
}