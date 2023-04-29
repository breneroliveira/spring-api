package com.veterinaria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.dto.atendimento.AtendimentoMapper;
import com.veterinaria.dto.atendimento.ConsultaAtendimentoDTO;
import com.veterinaria.dto.atendimento.RegistroAtendimentoDTO;
import com.veterinaria.entities.Atendimento;
import com.veterinaria.services.AtendimentoService;

@RestController
@RequestMapping("v1/atendimentos")
public class AtendimentoController {

	private final AtendimentoService atendimentoService;

	public AtendimentoController(AtendimentoService atendimentoService) {
		this.atendimentoService = atendimentoService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<ConsultaAtendimentoDTO>> buscarTodosOsAtendimentos(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(atendimentoService.listarTodosOsAtendimentos(pageable).map(AtendimentoMapper::fromEntity)); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaAtendimentoDTO> salvarAtendimento(@RequestBody RegistroAtendimentoDTO dto) {
		
		Atendimento atendimento = atendimentoService.salvarAtendimento(AtendimentoMapper.fromDTO(dto));
		
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimento));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<ConsultaAtendimentoDTO> buscarAtendimento(@PathVariable Long id) {

		Atendimento atendimento = atendimentoService.buscarAtendimento(id);
		
		return ResponseEntity.ok(AtendimentoMapper.fromEntity(atendimento));
	}
}