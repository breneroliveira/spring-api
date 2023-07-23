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

import com.veterinaria.mappers.ClienteMapper;
import com.veterinaria.dtos.responses.ClienteResponseDTO;
import com.veterinaria.dtos.requests.ClienteRequestDTO;
import com.veterinaria.entities.Cliente;
import com.veterinaria.services.ClienteService;

@RestController
@RequestMapping("v1/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<Page<ClienteResponseDTO>> buscarTodosOsClientes(@PageableDefault Pageable pageable) {
		
		return ResponseEntity.ok(clienteService.listarTodosOsClientes(pageable).map(ClienteMapper::fromEntity)); 
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ClienteResponseDTO> salvarCliente(@RequestBody ClienteRequestDTO dto) {
		
		Cliente cliente = clienteService.salvarCliente(ClienteMapper.fromDTO(dto));
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyAuthority('Admin', 'User')")
	public ResponseEntity<ClienteResponseDTO> buscarCliente(@PathVariable Long id) {

		Cliente cliente = clienteService.buscarCliente(id);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ClienteResponseDTO> atualizarCliente(@RequestBody ClienteRequestDTO dto,
                                                               @PathVariable Long id) {
				
		Cliente cliente = clienteService.atualizarCliente(ClienteMapper.fromDTO(dto), id);
		
		return ResponseEntity.ok(ClienteMapper.fromEntity(cliente));
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<ClienteResponseDTO> excluirCliente(@PathVariable Long id) {
		
			clienteService.excluirCliente(id);
			
			return ResponseEntity.ok().build();
	}
}