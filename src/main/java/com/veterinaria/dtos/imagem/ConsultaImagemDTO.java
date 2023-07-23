package com.veterinaria.dtos.imagem;

import java.util.HashSet;
import java.util.Set;

import com.veterinaria.entities.Raca;

public class ConsultaImagemDTO {

	private String id;
	private Set<Raca> breeds = new HashSet<>();
	private String url;
	private Double width;
	private Double height;
	
	public ConsultaImagemDTO() {
		
	}

	public ConsultaImagemDTO(String id, Set<Raca> breeds, String url, Double width, Double height) {
		this.id = id;
		this.breeds = breeds;
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Raca> getBreeds() {
		return breeds;
	}

	public void setBreeds(Set<Raca> breeds) {
		this.breeds = breeds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
}