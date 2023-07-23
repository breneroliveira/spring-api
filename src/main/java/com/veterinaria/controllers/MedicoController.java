package com.veterinaria.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veterinaria.dto.medico.ConsultaMedicoDTO;
import com.veterinaria.mappers.MedicoMapper;
import com.veterinaria.dto.medico.RegistroMedicoDTO;
import com.veterinaria.entities.Medico;
import com.veterinaria.services.MedicoService;

@RestController
@RequestMapping("v1/medicos")
public class MedicoController {

	private final MedicoService medicoService;

	public MedicoController(MedicoService medicoService) {
		this.medicoService = medicoService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<ConsultaMedicoDTO>> buscarTodosOsMedicos(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(medicoService.listarTodosOsMedicos(pageable).map(MedicoMapper::fromEntity)); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaMedicoDTO> salvarMedico(@RequestBody RegistroMedicoDTO dto) {
		
		Medico medico = medicoService.salvarMedico(MedicoMapper.fromDTO(dto));
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<ConsultaMedicoDTO> buscarMedico(@PathVariable Long id) {

		Medico medico = medicoService.buscarMedico(id);
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaMedicoDTO> atualizarMedico(@RequestBody RegistroMedicoDTO dto,
			@PathVariable Long id) {
				
		Medico medico = medicoService.atualizarMedico(MedicoMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(MedicoMapper.fromEntity(medico));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaMedicoDTO> excluirMedico(@PathVariable Long id) {
		
			medicoService.excluirMedico(id);
			
			return ResponseEntity.ok().build();
	}
}