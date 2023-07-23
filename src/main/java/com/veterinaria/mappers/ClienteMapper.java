package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.ClienteResponseDTO;
import com.veterinaria.dtos.requests.ClienteRequestDTO;
import com.veterinaria.entities.Cliente;

public class ClienteMapper {

	public static Cliente fromDTO(ClienteRequestDTO dto) {
		return new Cliente(null, dto.getCpf(), dto.getNome(), 
						   dto.getTelefone(), dto.getDataNascimento(),
						   dto.getCachorros(), EnderecoMapper.fromDTO(dto.getEndereco()));
	}
	
	public static ClienteResponseDTO fromEntity(Cliente cliente) {
		return new ClienteResponseDTO(cliente.getId(), cliente.getCpf(), cliente.getNome(),
									  cliente.getTelefone(), cliente.getDataNascimento(),
									  cliente.getCachorros(), EnderecoMapper.fromEntity(cliente.getEndereco()));
	}
}