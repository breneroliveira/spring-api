package com.veterinaria.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "tb_medico")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 5)
	private String crmv;
	
	@Column(nullable = false, name = "medico_nome")
	private String nome;
	
	@Column(nullable = false, length = 11)
	private String telefone;
	
	@Column(nullable = false, name = "data_nascimento_medico")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private Date dataNascimento;
	
	@Embedded
	private Endereco endereco;
	
	@Embedded
	private Especialidade especialidade;
	
	@OneToMany(mappedBy = "medico")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	private Set<Atendimento> atendimentos = new HashSet<>();

	public Medico() {
		
	}
	
	public Medico(String crmv, String nome, String telefone, Date dataNascimento, Endereco endereco,
			Especialidade especialidade) {
		this.crmv = crmv;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.especialidade = especialidade;
	}
	
	public Medico(Long id, String crmv, String nome, String telefone, Date dataNascimento, Endereco endereco,
			Especialidade especialidade) {
		this.id = id;
		this.crmv = crmv;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
		this.especialidade = especialidade;
	}

	public Medico(Long id, String crmv, String nome, String telefone, Date dataNascimento, Endereco endereco,
			Especialidade especialidade, Set<Atendimento> atendimentos) {
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Set<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void setAtendimentos(Set<Atendimento> atendimentos) {
		this.atendimentos = atendimentos;
	}
}