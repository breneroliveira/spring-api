package com.veterinaria.mappers;

import com.veterinaria.dtos.ConsultaCachorroDTO;
import com.veterinaria.dtos.RegistroCachorroDTO;
import com.veterinaria.entities.Cachorro;

public class CachorroMapper {

	public static Cachorro fromDTO(RegistroCachorroDTO dto) {
		return new Cachorro(null, dto.getNumeroRegistro(), 
						   dto.getNome(), dto.getRaca(), dto.getPeso(),
						   dto.getAltura(),
						   dto.getDataNascimento(), dto.getCliente(), dto.getAtendimentos());
	}
	
	public static ConsultaCachorroDTO fromEntity(Cachorro cachorro) {
		return new ConsultaCachorroDTO(cachorro.getId(), cachorro.getNumeroRegistro(), 
								cachorro.getNome(), cachorro.getRaca(), cachorro.getPeso(),
								cachorro.getAltura(), 
								cachorro.getDataNascimento(), cachorro.getCliente(), cachorro.getAtendimentos());
	}
}