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
import java.text.SimpleDateFormat;
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

import com.veterinaria.dtos.cachorro.ConsultaCachorroDTO;
import com.veterinaria.dtos.cachorro.RegistroCachorroDTO;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Cliente;
import com.veterinaria.entities.Endereco;
import com.veterinaria.entities.Raca;
import com.veterinaria.services.CachorroService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste CachorroController")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class CachorroControllerTest {

	@InjectMocks
	private CachorroController cachorroController;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private CachorroService cachorroService;
	
	private MockMvc mockMvc;
	
	private Cachorro cachorro;
	private Raca raca;
	private Cliente cliente;
	private Endereco endereco;
	private ConsultaCachorroDTO consultaCachorroDTO;
	private RegistroCachorroDTO registroCachorroDTO;
	
	String dataString = "2018-02-10";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	String dataString1 = "2000-07-14";
	Date dataFormatada1;
	
	private Page<Cachorro> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		raca = new Raca(1L, "r1");
		dataFormatada1 = formatter.parse(dataString1);
		endereco = new Endereco("l1", "111", "c1", "11111111");
		cliente = new Cliente("11111111111", "cl1", "11111111111", dataFormatada1, endereco);
		dataFormatada = formatter.parse(dataString);
		cachorro = new Cachorro(1L, "11111", "c1", raca, 20.0, 0.6, dataFormatada, cliente);
		consultaCachorroDTO = new ConsultaCachorroDTO(1L, "11111", "c1", raca, 20.0, 0.6, dataFormatada, cliente);
		registroCachorroDTO = new RegistroCachorroDTO("11111", "c1", raca, 20.0, 0.6, dataFormatada, cliente);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(cachorro), pageable, 5);
		
		mockMvc = MockMvcBuilders.standaloneSetup(cachorroController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
	}
	
	@Test
	@DisplayName("1. Deve criar um novo cachorro")
	void deveRetornarCriadoQuandoCriarCachorro() throws URISyntaxException {
		when(cachorroService.salvarCachorro(any())).thenReturn(cachorro);		
		when(mapper.map(any(), any())).thenReturn(consultaCachorroDTO);
		
		try {
			mockMvc.perform(post("/v1/cachorros")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(asJsonString(consultaCachorroDTO)))
				    .andExpectAll(
				    		status().isOk(),
				    		jsonPath("$.numeroRegistro", is(consultaCachorroDTO.getNumeroRegistro()))
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	@DisplayName("2. Deve listar todos os cachorros")
	void deveRetornarOsCachorroListados() {
		when(cachorroService.listarTodosOsCachorros(pageable)).thenReturn(page);
		when(mapper.map(any(), any())).thenReturn(consultaCachorroDTO);
		
		ResponseEntity<Page<ConsultaCachorroDTO>> response = 
				cachorroController.buscarTodosOsCachorros(pageable);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ConsultaCachorroDTO.class, response.getBody().getContent().get(0).getClass())
				, () -> assertEquals(1L, response.getBody().getContent().get(0).getId())
				, () -> assertEquals("c1", response.getBody().getContent().get(0).getNome()));
	}
	
	@Test
	@DisplayName("3. Deve retornar um cachorro buscado por id")
	void deveRetornarSucessoQuandoEncontrarUmCachorroPorId() {
		when(cachorroService.buscarCachorro(1L)).thenReturn(cachorro);
		when(mapper.map(any(), any())).thenReturn(consultaCachorroDTO);
		
		ResponseEntity<ConsultaCachorroDTO> response = cachorroController.buscarCachorro(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("c1", response.getBody().getNome()));
	}
	
	@Test
	@DisplayName("4. Deve excluir um cachorro pelo id")
	void deveRetornarNadaQuandoExcluir() {
		doNothing().when(cachorroService).excluirCachorro(anyLong());
		
		ResponseEntity<ConsultaCachorroDTO> response = cachorroController.excluirCachorro(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		
		verify(cachorroService, times(1)).excluirCachorro(anyLong());
	}
	
	@Test
	@DisplayName("5. Deve retonar um cachorro atualizado com sucesso")
	void deveRetornarSucessoQuandoAtualizarCachorro() {
		when(cachorroService.atualizarCachorro(any(),anyLong())).thenReturn(cachorro);
		
		ResponseEntity<ConsultaCachorroDTO> response = cachorroController
				.atualizarCachorro(registroCachorroDTO, 1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("c1", response.getBody().getNome()));
	}
}