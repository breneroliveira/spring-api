package com.veterinaria.mappers;

import com.veterinaria.dtos.responses.RacaResponseDTO;
import com.veterinaria.dtos.requests.RacaRequestDTO;
import com.veterinaria.entities.Raca;

public class RacaMapper {

	public static Raca fromDTO(RacaRequestDTO dto) {
		return new Raca(null, dto.getNome());
	}
	
	public static RacaResponseDTO fromEntity(Raca raca) {
		return new RacaResponseDTO(raca.getId(), raca.getNome());
	}
}