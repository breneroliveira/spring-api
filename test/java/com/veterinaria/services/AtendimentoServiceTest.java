/*package com.veterinaria.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.opentest4j.MultipleFailuresError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.veterinaria.dto.atendimento.AtendimentoMapper;
import com.veterinaria.dto.atendimento.ConsultaAtendimentoDTO;
import com.veterinaria.dto.atendimento.RegistroAtendimentoDTO;
import com.veterinaria.entities.Atendimento;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Endereco;
import com.veterinaria.entities.Especialidade;
import com.veterinaria.entities.Medico;
import com.veterinaria.repositories.AtendimentoRepository;
import com.veterinaria.repositories.MedicoRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste AtendimentoService")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class AtendimentoServiceTest {

	@Mock
	private AtendimentoRepository atendimentoRepository;
	
	@Mock
	private MedicoRepository medicoRepository;
	
	@InjectMocks
	private AtendimentoService atendimentoService;
	
	private Atendimento atendimento;
	private Medico medico;
	private Cachorro cachorro;
	private Endereco endereco;
	private Especialidade especialidade;
	private ConsultaAtendimentoDTO consultaAtendimentoDTO;
	private RegistroAtendimentoDTO registroAtendimentoDTO;
	
	String dataString = "2022-10-21";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	String dataHoraString = "2022-10-21T16:30";
	LocalDateTime dataHoraFormatada = LocalDateTime.parse(dataHoraString);
	
	private Page<Atendimento> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		endereco = new Endereco("l1", "111", "c1", "11111111");
		especialidade = new Especialidade("e1");
		dataFormatada = formatter.parse(dataString);
		consultaAtendimentoDTO = new ConsultaAtendimentoDTO(1L, "1111", medico, cachorro, 17.0, 0.7, dataHoraFormatada, "d1", null);
		registroAtendimentoDTO = new RegistroAtendimentoDTO("1111", medico, cachorro, dataHoraFormatada, 17.0, 0.7, "d1", null);
		medico = new Medico(1L, "11111", "m1", "11111111111", dataFormatada, endereco, especialidade);
		atendimento = new Atendimento(1L, "1111", medico, cachorro, 17.0, 0.7, dataHoraFormatada, "d1", null);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(atendimento), pageable, 5);
	}
	
	private void assertAtendimento(Atendimento response) throws MultipleFailuresError {
		assertAll(
				"Checa se não é null para a classe do objeto e valores de atributos"
				, () -> assertNotNull(response)
				, () -> assertEquals(Atendimento.class, response.getClass())
				, () -> assertEquals(1L, response.getId())
				, () -> assertEquals("1111", response.getTicket())
		);
	}
	
	@Test
	@DisplayName("1. Deve criar um novo atendimento")
	void deveRetornarSucessoQuandoCriarAtendimento() {	
		when(atendimentoRepository.save(any())).thenReturn(atendimento);
		
		assertAtendimento(atendimentoService.salvarAtendimento(AtendimentoMapper.fromDTO(registroAtendimentoDTO)));	
	}
}*/