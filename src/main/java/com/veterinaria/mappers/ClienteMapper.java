package com.veterinaria.mappers;

import com.veterinaria.dto.cliente.ConsultaClienteDTO;
import com.veterinaria.dto.cliente.RegistroClienteDTO;
import com.veterinaria.entities.Cliente;

public class ClienteMapper {

	public static Cliente fromDTO(RegistroClienteDTO dto) {
		return new Cliente(null, dto.getCpf(), dto.getNome(), 
						   dto.getTelefone(), dto.getDataNascimento(),
						   dto.getCachorros(), EnderecoMapper.fromDTO(dto.getEndereco()));
	}
	
	public static ConsultaClienteDTO fromEntity(Cliente cliente) {
		return new ConsultaClienteDTO(cliente.getId(), cliente.getCpf(), cliente.getNome(), 
									  cliente.getTelefone(), cliente.getDataNascimento(),
									  cliente.getCachorros(), EnderecoMapper.fromEntity(cliente.getEndereco()));
	}
}