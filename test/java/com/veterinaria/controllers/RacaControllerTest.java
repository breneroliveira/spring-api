package com.veterinaria.controllers;

import static com.veterinaria.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URISyntaxException;
import java.text.ParseException;
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

import com.veterinaria.dto.raca.ConsultaRacaDTO;
import com.veterinaria.dto.raca.RegistroRacaDTO;
import com.veterinaria.entities.Raca;
import com.veterinaria.services.RacaService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste RacaController")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class RacaControllerTest {

	@InjectMocks
	private RacaController racaController;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private RacaService racaService;
	
	private MockMvc mockMvc;
	
	private Raca raca;
	private ConsultaRacaDTO consultaRacaDTO;
	private RegistroRacaDTO registroRacaDTO;
	
	private Page<Raca> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		raca = new Raca(1L, "r1");
		consultaRacaDTO = new ConsultaRacaDTO(1L, "r1");
		registroRacaDTO = new RegistroRacaDTO("r1");
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(raca), pageable, 5);
		
		mockMvc = MockMvcBuilders.standaloneSetup(racaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
	}
	
	@Test
	@DisplayName("1. Deve criar uma nova raça")
	void deveRetornarCriadoQuandoCriarRaca() throws URISyntaxException {
		when(racaService.salvarRaca(any())).thenReturn(raca);		
		when(mapper.map(any(), any())).thenReturn(consultaRacaDTO);
		
		try {
			mockMvc.perform(post("/v1/racas")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(asJsonString(consultaRacaDTO)))
				    .andExpectAll(
				    		status().isOk(),
				    		jsonPath("$.nome", is(consultaRacaDTO.getNome()))
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	@DisplayName("2. Deve listar todas as raças")
	void deveRetornarAsRacasListadas() {
		when(racaService.listarTodosAsRacas(pageable)).thenReturn(page);
		when(mapper.map(any(), any())).thenReturn(consultaRacaDTO);
		
		ResponseEntity<Page<ConsultaRacaDTO>> response = 
				racaController.buscarTodasAsRacas(pageable);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ConsultaRacaDTO.class, response.getBody().getContent().get(0).getClass())
				, () -> assertEquals(1L, response.getBody().getContent().get(0).getId())
				, () -> assertEquals("r1", response.getBody().getContent().get(0).getNome()));
	}
	
	@Test
	@DisplayName("3. Deve retornar uma raça buscada por id")
	void deveRetornarSucessoQuandoEncontrarUmaRacaPorId() {
		when(racaService.buscarRaca(1L)).thenReturn(raca);
		when(mapper.map(any(), any())).thenReturn(consultaRacaDTO);
		
		ResponseEntity<ConsultaRacaDTO> response = racaController.buscarRaca(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("r1", response.getBody().getNome()));
	}
	
	@Test
	@DisplayName("4. Deve excluir uma raça pelo id")
	void deveRetornarNadaQuandoExcluir() {
		doNothing().when(racaService).excluirRaca(anyLong());
		
		ResponseEntity<ConsultaRacaDTO> response = racaController.excluirRaca(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		
		verify(racaService, times(1)).excluirRaca(anyLong());
	}
	
	@Test
	@DisplayName("5. Deve retonar um raça atualizada com sucesso")
	void deveRetornarSucessoQuandoAtualizarRaca() {
		when(racaService.atualizarRaca(any(),anyLong())).thenReturn(raca);
		
		ResponseEntity<ConsultaRacaDTO> response = racaController
				.atualizarRaca(registroRacaDTO, 1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("r1", response.getBody().getNome()));
	}
}