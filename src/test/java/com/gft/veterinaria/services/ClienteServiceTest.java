package com.gft.veterinaria.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.gft.veterinaria.dto.cliente.ClienteMapper;
import com.gft.veterinaria.dto.cliente.RegistroClienteDTO;
import com.gft.veterinaria.dto.endereco.EnderecoDTO;
import com.gft.veterinaria.entities.Cliente;
import com.gft.veterinaria.entities.Endereco;
import com.gft.veterinaria.exceptions.EntityNotFoundException;
import com.gft.veterinaria.exceptions.VeterinariaException;
import com.gft.veterinaria.repositories.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Teste ClienteService")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;
	
	@InjectMocks
	private ClienteService clienteService;
	
	private Cliente cliente;
	private Endereco endereco;
	private RegistroClienteDTO registroClienteDTO;
	private EnderecoDTO enderecoDTO;
	private Optional<Cliente> optionalCliente;
	
	String dataString = "1990-01-20";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	private Page<Cliente> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() {
		enderecoDTO = new EnderecoDTO("l1", "111", "c1", "11111111");
		registroClienteDTO = new RegistroClienteDTO("11111111111", "cl1", "11111111111", dataFormatada, enderecoDTO);
		endereco = new Endereco("l1", "111", "c1", "11111111");
		cliente = new Cliente(1L, "11111111111", "cl1", "11111111111", dataFormatada, endereco);
		optionalCliente = Optional.of(cliente);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(cliente), pageable, 5);
	}
	
	private void assertCliente(Cliente response) throws MultipleFailuresError {
		assertAll(
				"Checa se n??o ?? null para a classe do objeto e valores de atributos"
				, () -> assertNotNull(response)
				, () -> assertEquals(Cliente.class, response.getClass())
				, () -> assertEquals(1L, response.getId())
				, () -> assertEquals("11111111111", response.getCpf())
		);
	}
	
	@Test
	@DisplayName("1. Deve criar um novo cliente")
	void deveRetornarSucessoQuandoCriarCliente() {	
		when(clienteRepository.save(any())).thenReturn(cliente);
		
		assertCliente(clienteService.salvarCliente(ClienteMapper.fromDTO(registroClienteDTO)));	
	}
	
	@Test
	@DisplayName("1.1. Quando criado um cliente com um nome existente, "
			+ "deve retornar VeterinariaException com mensagem correta")
	void deveRetornarVeterinariaExceptionComMensagemCorretaQuandoCriadoClienteComNomeExistente() {
		when(clienteRepository.findByCpf(anyString())).thenReturn(optionalCliente);
		
		try {
			clienteService.salvarCliente(ClienteMapper.fromDTO(registroClienteDTO));
			fail("expected a VeterinariaException");
		} catch(VeterinariaException e) {
			assertEquals("Cliente j?? cadastrado.", e.getMessage());
		}
	}
	
	@Test
	@DisplayName("2. Deve retonar uma p??gina de clientes")
	void deveRetornarPaginaDeClientesQuandoAcharTodosOsClientes() {
		when(clienteRepository.findAll(pageable)).thenReturn(page);
		
		Page<Cliente> response = clienteService.listarTodosOsClientes(pageable);
		assertAll("Checa se a p??gina n??o ?? null e conte??do"
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(cliente), response.getContent())
				, () -> assertEquals(5, response.getSize())
				, () -> assertEquals(5, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages())
		);
	}
	
	@Test
	@DisplayName("3. Deve retornar um cliente buscado por id")
	void deveRetornarInstanciaDeClienteQuandoBuscarPorId() {
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);

		assertCliente(clienteService.buscarCliente(1L));	
	}
	
	@Test
	@DisplayName("3.1. Deve lan??ar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoBuscarPorId() {
		when(clienteRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> clienteService.buscarCliente(1L)
				, "Checa se lan??a EntityNotFoundException");
	}
	
	@Test
	@DisplayName("4. Deve excluir um cliente")
	void deveRetornarSucessoQuandoExcluir() {
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		doNothing().when(clienteRepository).delete(cliente);

		clienteService.excluirCliente(1L);
		
		assertAll(
				"Verifica se os m??todos findById e deleteById foram executados"
				, () -> verify(clienteRepository, times(1)).findById(anyLong())
				, () -> verify(clienteRepository, times(1)).delete(cliente)
		);
	}
	
	@Test
	@DisplayName("4.1. Deve lan??ar EntityNotFoundException quando tentar excluir um cliente inexistente")
	void deveRetornarEntityNotFoundExceptionQuandoExcluir() {
		when(clienteRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> clienteService.excluirCliente(1L)
				, "Checa se lan??a EntityNotFoundException");
	}
	
	@Test
	@DisplayName("5. Deve atualizar um cliente")
	void deveRetornarSucessoQuandoAtualizarCliente() {
		when(clienteRepository.save(any())).thenReturn(cliente);
		when(clienteRepository.findById(anyLong())).thenReturn(optionalCliente);
		
		assertCliente(clienteService.atualizarCliente(ClienteMapper.fromDTO(registroClienteDTO), 1L));
	}
	
	@Test
	@DisplayName("5.1. Deve tentar atualizar um cliente e lan??ar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoAtualizar() {
		when(clienteRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);	
		
		assertThrows(EntityNotFoundException.class
				, () -> clienteService.atualizarCliente(ClienteMapper.fromDTO(registroClienteDTO), 2L)
				, "Checa se lan??a EntityNotFoundException");
	}
}