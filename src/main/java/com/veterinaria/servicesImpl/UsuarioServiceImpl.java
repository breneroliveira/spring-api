package com.veterinaria.servicesImpl;

import com.veterinaria.entities.Usuario;
import com.veterinaria.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public Usuario salvarUsuario(Usuario usuario) {
		
		return usuarioRepository.save(usuario);
	}
	
	public Usuario buscarUsuarioPorEmail(String email) {
		Optional<Usuario> optional = usuarioRepository.findByEmail(email);
		
		if(optional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return optional.get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarUsuarioPorEmail(username);
	}
	
	public Usuario buscarUsuarioPorId(Long idUsuario) {
		Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
		
		if(optional.isEmpty()) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		return optional.get();
	}
}