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

import com.veterinaria.dto.cliente.ConsultaClienteDTO;
import com.veterinaria.dto.cliente.RegistroClienteDTO;
import com.veterinaria.dto.endereco.EnderecoDTO;
import com.veterinaria.entities.Cliente;
import com.veterinaria.entities.Endereco;
import com.veterinaria.services.ClienteService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste ClienteController")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class ClienteControllerTest {

	@InjectMocks
	private ClienteController clienteController;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private ClienteService clienteService;
	
	private MockMvc mockMvc;
	
	private Cliente cliente;
	private Endereco endereco;
	private ConsultaClienteDTO consultaClienteDTO;
	private RegistroClienteDTO registroClienteDTO;
	private EnderecoDTO enderecoDTO;
	
	String dataString = "2000-07-14";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	private Page<Cliente> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		endereco = new Endereco("l1", "111", "c1", "11111111");
		dataFormatada = formatter.parse(dataString);
		cliente = new Cliente(1L, "11111111111", "cl1", "11111111111", dataFormatada, endereco);
		enderecoDTO = new EnderecoDTO("l1", "111", "c1", "11111111");
		registroClienteDTO = new RegistroClienteDTO("11111111111", "cl1", "11111111111", dataFormatada, enderecoDTO);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(cliente), pageable, 5);
		
		mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
	}
	
	@Test
	@DisplayName("1. Deve criar um novo cliente")
	void deveRetornarCriadoQuandoCriarCliente() throws URISyntaxException {
		when(clienteService.salvarCliente(any())).thenReturn(cliente);		
		when(mapper.map(any(), any())).thenReturn(consultaClienteDTO);
		
		try {
			mockMvc.perform(post("/v1/clientes")
			        .contentType(MediaType.APPLICATION_JSON)
			        .content(asJsonString(consultaClienteDTO)))
				    .andExpectAll(
				    		status().isOk(),
				    		jsonPath("$.cpf", is(consultaClienteDTO.getCpf()))
				    );
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Test
	@DisplayName("2. Deve listar todos os clientes")
	void deveRetornarOsClientesListados() {
		when(clienteService.listarTodosOsClientes(pageable)).thenReturn(page);
		when(mapper.map(any(), any())).thenReturn(consultaClienteDTO);
		
		ResponseEntity<Page<ConsultaClienteDTO>> response = 
				clienteController.buscarTodosOsClientes(pageable);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ConsultaClienteDTO.class, response.getBody().getContent().get(0).getClass())
				, () -> assertEquals(1L, response.getBody().getContent().get(0).getId())
				, () -> assertEquals("cl1", response.getBody().getContent().get(0).getNome()));
	}
	
	@Test
	@DisplayName("3. Deve retornar um cliente buscado por id")
	void deveRetornarSucessoQuandoEncontrarUmClientePorId() {
		when(clienteService.buscarCliente(1L)).thenReturn(cliente);
		when(mapper.map(any(), any())).thenReturn(consultaClienteDTO);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.buscarCliente(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("cl1", response.getBody().getNome()));
	}
	
	@Test
	@DisplayName("4. Deve excluir um cliente pelo id")
	void deveRetornarNadaQuandoExcluir() {
		doNothing().when(clienteService).excluirCliente(anyLong());
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController.excluirCliente(1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		
		verify(clienteService, times(1)).excluirCliente(anyLong());
	}
	
	@Test
	@DisplayName("5. Deve retonar um cliente atualizado com sucesso")
	void deveRetornarSucessoQuandoAtualizarCliente() {
		when(clienteService.atualizarCliente(any(),anyLong())).thenReturn(cliente);
		
		ResponseEntity<ConsultaClienteDTO> response = clienteController
				.atualizarCliente(registroClienteDTO, 1L);
		
		assertAll("Checa se não é null, HttpStatus e response body"
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(1L, response.getBody().getId())
				, () -> assertEquals("cl1", response.getBody().getNome()));
	}
}