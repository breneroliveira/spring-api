package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.UsuarioResponseDTO;
import com.veterinaria.dtos.requests.UsuarioRequestDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.veterinaria.entities.Perfil;
import com.veterinaria.entities.Usuario;

public class UsuarioMapper {

	public static Usuario fromDTO(UsuarioRequestDTO dto) {
		
		Perfil perfil = new Perfil();
		perfil.setId(dto.getPerfilId());
		
		return new Usuario(null, dto.getEmail(), new BCryptPasswordEncoder().encode(dto.getSenha()), perfil);
	}
	
	public static UsuarioResponseDTO fromEntity(Usuario usuario) {
		
		return new UsuarioResponseDTO(usuario.getEmail(), usuario.getPerfil().getNome());
	}
}