package com.veterinaria.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_cachorro")
public class Cachorro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 5)
	private String numeroRegistro;
	
	@Column(nullable = false, name = "cachorro_nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "raca_id")
	private Raca raca;
	
	@Column(nullable = false, precision = 2)
	private Double peso;
	
	@Column(nullable = false, precision = 2)
	private Double altura;
	
	@Column(nullable = false, name = "data_nascimento_cachorro")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
	private Date dataNascimento;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(nullable = false, name = "cliente_id")
	private Cliente cliente;
	
	@OneToMany(mappedBy = "cachorro")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	private Set<Atendimento> atendimentos = new HashSet<>();

	public Cachorro() {
		
	}

	public Cachorro(String numeroRegistro, String nome, Raca raca, Double peso, Double altura,
			Date dataNascimento, Cliente cliente) {
		this.numeroRegistro = numeroRegistro;
		this.nome = nome;
		this.raca = raca;
		this.peso = peso;
		this.altura = altura;
		this.dataNascimento = dataNascimento;
		this.cliente = cliente;
	}
	
	public Cachorro(Long id, String numeroRegistro, String nome, Raca raca, Double peso, Double altura,
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

	public Cachorro(Long id, String numeroRegistro, String nome, Raca raca, Double peso, Double altura,
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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-3")
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

	@Override
	public String toString() {
		return "Cachorro [id=" + id + ", numeroRegistro=" + numeroRegistro + ", nome=" + nome + ", raca=" + raca
				+ ", peso=" + peso + ", altura=" + altura + ", dataNascimento=" + dataNascimento + ", cliente="
				+ cliente + ", atendimentos=" + atendimentos + "]";
	}
}