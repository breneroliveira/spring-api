package com.veterinaria.dtos.responses;

public class UsuarioResponseDTO {

	private String email;
	private String nomePerfil;
	
	public UsuarioResponseDTO() {
		
	}

	public UsuarioResponseDTO(String email, String nomePerfil) {
		this.email = email;
		this.nomePerfil = nomePerfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
}