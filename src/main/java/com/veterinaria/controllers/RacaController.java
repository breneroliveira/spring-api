package com.veterinaria.controllers;

import java.util.List;

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

import com.veterinaria.dto.imagem.ConsultaImagemDTO;
import com.veterinaria.dto.raca.ConsultaRacaDTO;
import com.veterinaria.dto.raca.RacaMapper;
import com.veterinaria.dto.raca.RegistroRacaDTO;
import com.veterinaria.entities.Raca;
import com.veterinaria.services.RacaService;

@RestController
@RequestMapping("v1/racas")
public class RacaController {

	private final RacaService racaService;

	public RacaController(RacaService racaService) {
		this.racaService = racaService;
	}
	
	@GetMapping("dog-api")
	public ResponseEntity<List<Raca>> getRacaDogApi() {
		List<Raca> raca = this.racaService.getRacaDogApi();
		 
		 return ResponseEntity.ok(raca);
	}
	
	@GetMapping("dog-api/{nomeRaca}")
	public ResponseEntity<List<Raca>> buscarRacaDogApi(@PathVariable String nomeRaca) {
		List<Raca> raca = this.racaService.buscarRacaDogApi(nomeRaca);
		 
		 return ResponseEntity.ok(raca);
	}
	
	@GetMapping("dog-api/imagens")
	public ResponseEntity<List<ConsultaImagemDTO>> getImagemDogApi() {
		List<ConsultaImagemDTO> imagem = this.racaService.getImagemDogApi();
		 
		 return ResponseEntity.ok(imagem);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<ConsultaRacaDTO>> buscarTodasAsRacas(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(racaService.listarTodosAsRacas(pageable).map(RacaMapper::fromEntity)); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaRacaDTO> salvarRaca(@RequestBody RegistroRacaDTO dto) {
		
		Raca raca = racaService.salvarRaca(RacaMapper.fromDTO(dto));
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<ConsultaRacaDTO> buscarRaca(@PathVariable Long id) {

		Raca raca = racaService.buscarRaca(id);
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaRacaDTO> atualizarRaca(@RequestBody RegistroRacaDTO dto,
			@PathVariable Long id) {
				
		Raca raca = racaService.atualizarRaca(RacaMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(RacaMapper.fromEntity(raca));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ConsultaRacaDTO> excluirRaca(@PathVariable Long id) {
		
			racaService.excluirRaca(id);
			
			return ResponseEntity.ok().build();
	}
}