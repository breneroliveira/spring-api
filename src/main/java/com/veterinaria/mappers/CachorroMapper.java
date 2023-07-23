package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.CachorroResponseDTO;
import com.veterinaria.dtos.requests.CachorroRequestDTO;
import com.veterinaria.entities.Cachorro;

public class CachorroMapper {

	public static Cachorro fromDTO(CachorroRequestDTO dto) {
		return new Cachorro(null, dto.getNumeroRegistro(), 
						   dto.getNome(), dto.getRaca(), dto.getPeso(),
						   dto.getAltura(),
						   dto.getDataNascimento(), dto.getCliente(), dto.getAtendimentos());
	}
	
	public static CachorroResponseDTO fromEntity(Cachorro cachorro) {
		return new CachorroResponseDTO(cachorro.getId(), cachorro.getNumeroRegistro(),
								cachorro.getNome(), cachorro.getRaca(), cachorro.getPeso(),
								cachorro.getAltura(), 
								cachorro.getDataNascimento(), cachorro.getCliente(), cachorro.getAtendimentos());
	}
}