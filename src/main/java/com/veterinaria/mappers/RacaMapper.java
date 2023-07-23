package com.veterinaria.mappers;

import com.veterinaria.dtos.ConsultaRacaDTO;
import com.veterinaria.dtos.RegistroRacaDTO;
import com.veterinaria.entities.Raca;

public class RacaMapper {

	public static Raca fromDTO(RegistroRacaDTO dto) {
		return new Raca(null, dto.getNome());
	}
	
	public static ConsultaRacaDTO fromEntity(Raca raca) {
		return new ConsultaRacaDTO(raca.getId(), raca.getNome());
	}
}