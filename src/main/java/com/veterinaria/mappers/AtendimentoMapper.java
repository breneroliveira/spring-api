package com.veterinaria.mappers;

import com.veterinaria.dtos.ConsultaAtendimentoDTO;
import com.veterinaria.dtos.RegistroAtendimentoDTO;
import com.veterinaria.entities.Atendimento;

public class AtendimentoMapper {

	public static Atendimento fromDTO(RegistroAtendimentoDTO dto) {
		return new Atendimento(null, dto.getTicket(), dto.getMedico(), dto.getCachorro(),
						   dto.getPesoAtual(), dto.getAlturaAtual(), dto.getDataHora(),
						   dto.getDiagnostico(), dto.getComentario());
	}
	
	public static ConsultaAtendimentoDTO fromEntity(Atendimento atendimento) {
		return new ConsultaAtendimentoDTO(atendimento.getId(), atendimento.getTicket(), atendimento.getMedico(), 
							atendimento.getCachorro(), atendimento.getPesoAtual(), atendimento.getAlturaAtual(), 
							atendimento.getDataHora(), atendimento.getDiagnostico(), atendimento.getComentario());
	}
}