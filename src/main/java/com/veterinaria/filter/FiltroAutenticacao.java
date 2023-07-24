package com.veterinaria.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.veterinaria.entities.Usuario;
import com.veterinaria.servicesImpl.AutenticacaoServiceImpl;
import com.veterinaria.servicesImpl.UsuarioServiceImpl;

public class FiltroAutenticacao extends OncePerRequestFilter {
	
	private AutenticacaoServiceImpl autenticacaoServiceImpl;
	private UsuarioServiceImpl usuarioServiceImpl;
	
	public FiltroAutenticacao(AutenticacaoServiceImpl autenticacaoServiceImpl, UsuarioServiceImpl usuarioServiceImpl) {
		this.autenticacaoServiceImpl = autenticacaoServiceImpl;
		this.usuarioServiceImpl = usuarioServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		String token = null;
		if(header != null && header.startsWith("Bearer ")) {
			token = header.substring(7, header.length());
		}
		
		if(autenticacaoServiceImpl.verificaToken(token)) {
			Long idUsuario = autenticacaoServiceImpl.retornarIdUsuario(token);
			Usuario usuario = usuarioServiceImpl.buscarUsuarioPorId(idUsuario);
			SecurityContextHolder.getContext()
								 .setAuthentication(new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()));
		}
		
		filterChain.doFilter(request, response);
	}
}