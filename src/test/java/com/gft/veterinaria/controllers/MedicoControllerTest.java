package com.gft.veterinaria.controllers;

import static com.gft.veterinaria.utils.JsonConvertionUtils.asJsonString;
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

import com.gft.veterinaria.dto.endereco.EnderecoDTO;
import com.gft.veterinaria.dto.especialidade.EspecialidadeDTO;
import com.gft.veterinaria.dto.medico.ConsultaMedicoDTO;
import com.gft.veterinaria.dto.medico.RegistroMedicoDTO;
import com.gft.veterinaria.entities.Endereco;
import com.gft.veterinaria.entities.Especialidade;
import com.gft.veterinaria.entities.Medico;
import com.gft.veterinaria.services.MedicoService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste MedicoController")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class MedicoControllerTest {

	@InjectMocks
	private MedicoController medicoController;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private MedicoService medicoService;
	
	private MockMvc mockMvc;
	
	private Medico medico;
	private Endereco endereco;
	private Especialidade especialidade;
	private ConsultaMedicoDTO consultaMedicoDTO;
	private RegistroMedicoDTO registroMedicoDTO;
	private EspecialidadeDTO especialidadeDTO;
	private EnderecoDTO enderecoDTO;
	
	String dataString = "1990-01-20";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	private Page<Medico> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		endereco = new Endereco("l1", "111", "c1", "11111111");
		especialidade = new Especialidade("e1");
		enderecoDTO = new EnderecoDTO("l1", "111", "c1", "11111111");
		especialidadeDTO = new EspecialidadeDTO("e1");
		dataFormatada = formatter.parse(dataString);
		consultaMedicoDTO = new ConsultaMedicoDTO(1L, "11111", "m1", "11111111111", dataFormatada, enderecoDTO, especialidadeDTO);
		registroMedicoDTO = new RegistroMedicoDTO("11111", "m1", "11111111111", dataFormatada, enderecoDTO, especialidadeDTO);
		medico = new Medico(1L, "11111", "m1", "11111111111", dataFormatada, endereco, especialidade);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(medico), pageable, 5);
		
		mockMvc = MockMvcBuilders.standaloneSetup(medicoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
	}
	
	@Test
	@DisplayName("1. Deve criar um novo médico")
	void deveRetornarCriadoQuandoCriarMedico() throws URISyntaxException {
		when(medicoService.salvarMedico(any())).thenReturn(medico);		
		when(mapper.map(any(), any())).thenReturn(consultaMedicoDTO);
		
		try {
			mockMvc.perform(post("/v1/medicos")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(asJsonString(consultaMedicoDTO)))
				    .andExpectAll(
				    		status().isOk(),
				    		jsonPath("$.crmv", is(consultaMedicoDTO.getCrmv()))
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	@DisplayName("2. Deve listar todos os médicos")
	void deveRetornarOsMedicosListados() {
		when(medicoService.listarTodosOsMedicos(pageable)).thenReturn(page);
		when(mapper.map(any(), any())).thenReturn(consultaMedicoDTO);
		
		ResponseEntity<Page<ConsultaMedicoDTO>> response = 
				medicoController.buscarTodosOsMedicos(pageable);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ConsultaMedicoDTO.class, response.getBody().getContent().get(0).getClass())
				, () -> assertEquals(1L, response.getBody().getContent().get(0).getId())
				, () -> assertEquals("m1", response.getBody().getContent().get(0).getNome()));
	}
	
	@Test
	@DisplayName("3. Deve retornar um médico buscado por id")
	void deveRetornarSucessoQuandoEncontrarUmMedicoPorId() {
		when(medicoService.buscarMedico(1L)).thenReturn(medico);
		when(mapper.map(any(), any())).thenReturn(consultaMedicoDTO);
		
		ResponseEntity<ConsultaMedicoDTO> response = medicoController.buscarMedico(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("m1", response.getBody().getNome()));
	}
	
	@Test
	@DisplayName("4. Deve excluir um médico pelo id")
	void deveRetornarNadaQuandoExcluir() {
		doNothing().when(medicoService).excluirMedico(anyLong());
		
		ResponseEntity<ConsultaMedicoDTO> response = medicoController.excluirMedico(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		
		verify(medicoService, times(1)).excluirMedico(anyLong());
	}
	
	@Test
	@DisplayName("5. Deve retonar um médico atualizado com sucesso")
	void deveRetornarSucessoQuandoAtualizarMedico() {
		when(medicoService.atualizarMedico(any(),anyLong())).thenReturn(medico);
		
		ResponseEntity<ConsultaMedicoDTO> response = medicoController
				.atualizarMedico(registroMedicoDTO, 1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("m1", response.getBody().getNome()));
	}
}