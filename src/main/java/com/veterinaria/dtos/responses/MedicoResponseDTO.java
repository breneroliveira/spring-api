package com.veterinaria.dtos.responses;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.veterinaria.dtos.EnderecoDTO;
import com.veterinaria.dtos.EspecialidadeDTO;
import com.veterinaria.entities.Atendimento;

public class MedicoResponseDTO {

	private Long id;
	private String crmv;
	private String nome;
	private String telefone;
	private Date dataNascimento;
	private EnderecoDTO endereco;
	private EspecialidadeDTO especialidade;
	private Set<Atendimento> atendimentos = new HashSet<>();
	
	public MedicoResponseDTO() {
		
	}
	
	public MedicoResponseDTO(Long id, String crmv, String nome, String telefone, Date dataNascimento,
							 EnderecoDTO endereco, EspecialidadeDTO especialidade) {
		this.id = id;
		this.crmv = crmv;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.especialidade = especialidade;
	}

	public MedicoResponseDTO(Long id, String crmv, String nome, String telefone, Date dataNascimento,
							 EnderecoDTO endereco, EspecialidadeDTO especialidade, Set<Atendimento> atendimentos) {
		this.id = id;
		this.crmv = crmv;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.especialidade = especialidade;
		this.atendimentos = atendimentos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCrmv() {
		return crmv;
	}

	public void setCrmv(String crmv) {
		this.crmv = crmv;
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

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	public EspecialidadeDTO getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(EspecialidadeDTO especialidade) {
		this.especialidade = especialidade;
	}

	public Set<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(Set<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
}