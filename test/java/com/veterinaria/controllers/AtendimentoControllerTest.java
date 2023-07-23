package com.veterinaria.controllers;

import static com.veterinaria.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.veterinaria.dtos.atendimento.ConsultaAtendimentoDTO;
import com.veterinaria.entities.Atendimento;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Endereco;
import com.veterinaria.entities.Especialidade;
import com.veterinaria.entities.Medico;
import com.veterinaria.services.AtendimentoService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste AtendimentoController")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class AtendimentoControllerTest {

	@InjectMocks
	private AtendimentoController atendimentoController;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private AtendimentoService atendimentoService;
	
	private MockMvc mockMvc;
	
	private Atendimento atendimento;
	private Medico medico;
	private Cachorro cachorro;
	private Endereco endereco;
	private Especialidade especialidade;
	private ConsultaAtendimentoDTO consultaAtendimentoDTO;
	
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
		medico = new Medico(1L, "11111", "m1", "11111111111", dataFormatada, endereco, especialidade);
		atendimento = new Atendimento(1L, "1111", medico, cachorro, 17.0, 0.7, dataHoraFormatada, "d1", null);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(atendimento), pageable, 5);
		
		mockMvc = MockMvcBuilders.standaloneSetup(atendimentoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
	}
	
	@Test
	@DisplayName("1. Deve criar um novo atendimento")
	void deveRetornarCriadoQuandoCriarAtendimento() throws URISyntaxException {
		when(atendimentoService.salvarAtendimento(any())).thenReturn(atendimento);		
		when(mapper.map(any(), any())).thenReturn(consultaAtendimentoDTO);
		
		try {
			mockMvc.perform(post("/v1/atendimentos")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(asJsonString(consultaAtendimentoDTO)))
				    .andExpectAll(
				    		status().isOk(),
				    		jsonPath("$.ticket", is(consultaAtendimentoDTO.getTicket()))
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	@DisplayName("2. Deve listar todos os atendimentos")
	void deveRetornarOsAtendimentosListados() {
		when(atendimentoService.listarTodosOsAtendimentos(pageable)).thenReturn(page);
		when(mapper.map(any(), any())).thenReturn(consultaAtendimentoDTO);
		
		ResponseEntity<Page<ConsultaAtendimentoDTO>> response = 
				atendimentoController.buscarTodosOsAtendimentos(pageable);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ConsultaAtendimentoDTO.class, response.getBody().getContent().get(0).getClass())
				, () -> assertEquals(1L, response.getBody().getContent().get(0).getId())
				, () -> assertEquals("1111", response.getBody().getContent().get(0).getTicket()));
	}
	
	@Test
	@DisplayName("3. Deve retornar um atendimento buscado por id")
	void deveRetornarSucessoQuandoEncontrarUmAtendimentoPorId() {
		when(atendimentoService.buscarAtendimento(1L)).thenReturn(atendimento);
		when(mapper.map(any(), any())).thenReturn(consultaAtendimentoDTO);
		
		ResponseEntity<ConsultaAtendimentoDTO> response = atendimentoController.buscarAtendimento(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("1111", response.getBody().getTicket()));
	}
}