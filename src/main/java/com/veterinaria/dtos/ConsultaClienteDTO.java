package com.veterinaria.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.veterinaria.entities.Cachorro;

public class ConsultaClienteDTO {

	private Long id;
	private String cpf;
	private String nome;
	private String telefone;
	private Date dataNascimento;
	private Set<Cachorro> cachorros = new HashSet<Cachorro>();
	private EnderecoDTO endereco;
	
	public ConsultaClienteDTO() {
		
	}

	public ConsultaClienteDTO(Long id, String cpf, String nome, String telefone, Date dataNascimento,
			Set<Cachorro> cachorros, EnderecoDTO endereco) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.cachorros = cachorros;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<Cachorro> getCachorros() {
		return cachorros;
	}

	public void setCachorros(Set<Cachorro> cachorros) {
		this.cachorros = cachorros;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}
}