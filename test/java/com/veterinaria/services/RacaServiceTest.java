package com.veterinaria.servicesImpl;

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

import java.text.ParseException;
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

import com.veterinaria.dtos.raca.RacaMapper;
import com.veterinaria.dtos.raca.RegistroRacaDTO;
import com.veterinaria.entities.Raca;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.exceptions.VeterinariaException;
import com.veterinaria.repositories.RacaRepository;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Teste RacaService")
class RacaServiceTest {

	@Mock
	private RacaRepository racaRepository;
	
	@InjectMocks
	private RacaService racaService;
	
	private Raca raca;
	private RegistroRacaDTO registroRacaDTO;
	private Optional<Raca> optionalRaca;
	
	private Page<Raca> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() throws ParseException {
		raca = new Raca(1L, "r1");
		registroRacaDTO = new RegistroRacaDTO("r1");
		optionalRaca = Optional.of(raca);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(raca), pageable, 5);
	}
	
	private void assertRaca(Raca response) throws MultipleFailuresError {
		assertAll(
				"Checa se não é null para a classe do objeto e valores de atributos"
				, () -> assertNotNull(response)
				, () -> assertEquals(Raca.class, response.getClass())
				, () -> assertEquals(1L, response.getId())
				, () -> assertEquals("r1", response.getNome())
		);
	}
	
	@Test
	@DisplayName("1. Deve criar um novo raça")
	void deveRetornarSucessoQuandoCriarRaca() {	
		when(racaRepository.save(any())).thenReturn(raca);
		
		assertRaca(racaService.salvarRaca(RacaMapper.fromDTO(registroRacaDTO)));	
	}
	
	@Test
	@DisplayName("1.1. Quando criado um raça com um nome existente, "
			+ "deve retornar VeterinariaException com mensagem correta")
	void deveRetornarVeterinariaExceptionComMensagemCorretaQuandoCriadoRacaComNomeExistente() {
		when(racaRepository.findByNome(anyString())).thenReturn(optionalRaca);
		
		try {
			racaService.salvarRaca(RacaMapper.fromDTO(registroRacaDTO));
			fail("expected a VeterinariaException");
		} catch(VeterinariaException e) {
			assertEquals("Raça já cadastrada.", e.getMessage());
		}
	}
	
	@Test
	@DisplayName("2. Deve retonar uma página de raças")
	void deveRetornarPaginaDeRacasQuandoAcharTodosOsRacas() {
		when(racaRepository.findAll(pageable)).thenReturn(page);
		
		Page<Raca> response = racaService.listarTodosAsRacas(pageable);
		assertAll("Checa se a página não é null e conteúdo"
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(raca), response.getContent())
				, () -> assertEquals(5, response.getSize())
				, () -> assertEquals(5, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages())
		);
	}
	
	@Test
	@DisplayName("3. Deve retornar uma raça buscada por id")
	void deveRetornarInstanciaDeRacaQuandoBuscarPorId() {
		when(racaRepository.findById(anyLong())).thenReturn(optionalRaca);

		assertRaca(racaService.buscarRaca(1L));	
	}
	
	@Test
	@DisplayName("3.1. Deve lançar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoBuscarPorId() {
		when(racaRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> racaService.buscarRaca(1L)
				, "Checa se lança EntityNotFoundException");
	}
	
	@Test
	@DisplayName("4. Deve excluir um raça")
	void deveRetornarSucessoQuandoExcluir() {
		when(racaRepository.findById(anyLong())).thenReturn(optionalRaca);
		doNothing().when(racaRepository).delete(raca);

		racaService.excluirRaca(1L);
		
		assertAll(
				"Verifica se os métodos findById e deleteById foram executados"
				, () -> verify(racaRepository, times(1)).findById(anyLong())
				, () -> verify(racaRepository, times(1)).delete(raca)
		);
	}
	
	@Test
	@DisplayName("4.1. Deve lançar EntityNotFoundException quando tentar excluir um raça inexistente")
	void deveRetornarEntityNotFoundExceptionQuandoExcluir() {
		when(racaRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> racaService.excluirRaca(1L)
				, "Checa se lança EntityNotFoundException");
	}
	
	@Test
	@DisplayName("5. Deve atualizar um raça")
	void deveRetornarSucessoQuandoAtualizarRaca() {
		when(racaRepository.save(any())).thenReturn(raca);
		when(racaRepository.findById(anyLong())).thenReturn(optionalRaca);
		
		assertRaca(racaService.atualizarRaca(RacaMapper.fromDTO(registroRacaDTO), 1L));
	}
	
	@Test
	@DisplayName("5.1. Deve tentar atualizar uma raça e lançar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoAtualizar() {
		when(racaRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);	
		
		assertThrows(EntityNotFoundException.class
				, () -> racaService.atualizarRaca(RacaMapper.fromDTO(registroRacaDTO), 2L)
				, "Checa se lança EntityNotFoundException");
	}
}