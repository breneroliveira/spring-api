package com.veterinaria.dtos.requests;

public class UsuarioRequestDTO {

	private String email;
	private String senha;
	private Long perfilId;
	
	public UsuarioRequestDTO() {
		
	}

	public UsuarioRequestDTO(String email, String senha, Long perfilId) {
		this.email = email;
		this.senha = senha;
		this.perfilId = perfilId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Long getPerfilId() {
		return perfilId;
	}

	public void setPerfilId(Long perfilId) {
		this.perfilId = perfilId;
	}
}