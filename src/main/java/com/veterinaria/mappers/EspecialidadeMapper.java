package com.veterinaria.mappers;

import com.veterinaria.dto.especialidade.EspecialidadeDTO;
import com.veterinaria.entities.Especialidade;

public class EspecialidadeMapper {

	public static Especialidade fromDTO(EspecialidadeDTO dto) {
		return new Especialidade(dto.getTitulo());
	}
	
	public static EspecialidadeDTO fromEntity(Especialidade especialidade) {
		return new EspecialidadeDTO(especialidade.getTitulo());
	}
}