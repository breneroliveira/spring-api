package com.veterinaria.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(nullable = false, name = "cliente_nome")
	private String nome;
	
	@Column(nullable = false, length = 11)
	private String telefone;
	
	@Column(nullable = false, name = "data_nascimento_cliente")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private Date dataNascimento;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	private Set<Cachorro> cachorros = new HashSet<>();
	
	@Embedded
	private Endereco endereco;

	public Cliente() {
		
	}

	public Cliente(String cpf, String nome, String telefone, Date dataNascimento, Endereco endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}
	
	public Cliente(Long id, String cpf, String nome, String telefone, Date dataNascimento,
			Endereco endereco) {
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.dataNascimento = dataNascimento;
		this.endereco = endereco;
	}

	public Cliente(Long id, String cpf, String nome, String telefone, Date dataNascimento, Set<Cachorro> cachorros,
			Endereco endereco) {
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
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
	
	public void setCachorroLista(Cachorro cachorro){
        cachorros.add(cachorro);
    }

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	@Override
	public String toString() {
		return "\nCliente"
				+ "\n-- CPF: " + cpf
				+ "\n-- Nome: " + nome 
				+ "\n-- Telefone: " + telefone 
				+ "\n-- Data de nascimento: " + dataNascimento
				+ "\n-- Cachorros: " + cachorros 
				+ "\n-- Endereco: " + endereco + "\n";
	}
}