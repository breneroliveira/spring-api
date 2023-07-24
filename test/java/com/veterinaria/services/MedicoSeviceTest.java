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

import com.veterinaria.dtos.endereco.EnderecoDTO;
import com.veterinaria.dtos.especialidade.EspecialidadeDTO;
import com.veterinaria.dtos.medico.MedicoMapper;
import com.veterinaria.dtos.medico.RegistroMedicoDTO;
import com.veterinaria.entities.Endereco;
import com.veterinaria.entities.Especialidade;
import com.veterinaria.entities.Medico;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.exceptions.VeterinariaException;
import com.veterinaria.repositories.MedicoRepository;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Teste MedicoService")
class MedicoSeviceTest {

	@Mock
	private MedicoRepository medicoRepository;
	
	@InjectMocks
	private MedicoService medicoService;
	
	private Medico medico;
	private Endereco endereco;
	private Especialidade especialidade;
	private RegistroMedicoDTO registroMedicoDTO;
	private EspecialidadeDTO especialidadeDTO;
	private EnderecoDTO enderecoDTO;
	private Optional<Medico> optionalMedico;
	
	String dataString = "1990-01-20";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	Date dataFormatada;
	
	private Page<Medico> page;
	private Pageable pageable;
	
	@BeforeEach
	void setup() {
		enderecoDTO = new EnderecoDTO("l1", "111", "c1", "11111111");
		especialidadeDTO = new EspecialidadeDTO("e1");
		registroMedicoDTO = new RegistroMedicoDTO("11111", "m1", "11111111111", dataFormatada, enderecoDTO, especialidadeDTO);
		endereco = new Endereco("l1", "111", "c1", "11111111");
		especialidade = new Especialidade("e1");
		medico = new Medico(1L, "11111", "m1", "11111111111", dataFormatada, endereco, especialidade);
		optionalMedico = Optional.of(medico);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(medico), pageable, 5);
	}
	
	private void assertMedico(Medico response) throws MultipleFailuresError {
		assertAll(
				"Checa se não é null para a classe do objeto e valores de atributos"
				, () -> assertNotNull(response)
				, () -> assertEquals(Medico.class, response.getClass())
				, () -> assertEquals(1L, response.getId())
				, () -> assertEquals("11111", response.getCrmv())
		);
	}
	
	@Test
	@DisplayName("1. Deve criar um novo médico")
	void deveRetornarSucessoQuandoCriarMedico() {	
		when(medicoRepository.save(any())).thenReturn(medico);
		
		assertMedico(medicoService.salvarMedico(MedicoMapper.fromDTO(registroMedicoDTO)));	
	}
	
	@Test
	@DisplayName("1.1. Quando criado um médico com um nome existente, "
			+ "deve retornar VeterinariaException com mensagem correta")
	void deveRetornarVeterinariaExceptionComMensagemCorretaQuandoCriadoMedicoComNomeExistente() {
		when(medicoRepository.findByCrmv(anyString())).thenReturn(optionalMedico);
		
		try {
			medicoService.salvarMedico(MedicoMapper.fromDTO(registroMedicoDTO));
			fail("expected a DicionarioException");
		} catch(VeterinariaException e) {
			assertEquals("Médico já cadastrado.", e.getMessage());
		}
	}
	
	@Test
	@DisplayName("2. Deve retonar uma página de médicos")
	void deveRetornarPaginaDeMedicosQuandoAcharTodosOsMedicos() {
		when(medicoRepository.findAll(pageable)).thenReturn(page);
		
		Page<Medico> response = medicoService.listarTodosOsMedicos(pageable);
		assertAll("Checa se a página não é null e conteúdo"
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(medico), response.getContent())
				, () -> assertEquals(5, response.getSize())
				, () -> assertEquals(5, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages())
		);
	}
	
	@Test
	@DisplayName("3. Deve retornar um médico buscado por id")
	void deveRetornarInstanciaDeMedicoQuandoBuscarPorId() {
		when(medicoRepository.findById(anyLong())).thenReturn(optionalMedico);

		assertMedico(medicoService.buscarMedico(1L));	
	}
	
	@Test
	@DisplayName("3.1. Deve lançar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoBuscarPorId() {
		when(medicoRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> medicoService.buscarMedico(1L)
				, "Checa se lança EntityNotFoundException");
	}
	
	@Test
	@DisplayName("4. Deve excluir um médico")
	void deveRetornarSucessoQuandoExcluir() {
		when(medicoRepository.findById(anyLong())).thenReturn(optionalMedico);
		doNothing().when(medicoRepository).delete(medico);

		medicoService.excluirMedico(1L);
		
		assertAll(
				"Verifica se os métodos findById e deleteById foram executados"
				, () -> verify(medicoRepository, times(1)).findById(anyLong())
				, () -> verify(medicoRepository, times(1)).delete(medico)
		);
	}
	
	@Test
	@DisplayName("4.1. Deve lançar EntityNotFoundException quando tentar excluir um médico inexistente")
	void deveRetornarEntityNotFoundExceptionQuandoExcluir() {
		when(medicoRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(EntityNotFoundException.class
				, () -> medicoService.excluirMedico(1L)
				, "Checa se lança EntityNotFoundException");
	}
	
	@Test
	@DisplayName("5. Deve atualizar um médico")
	void deveRetornarSucessoQuandoAtualizarMedico() {
		when(medicoRepository.save(any())).thenReturn(medico);
		when(medicoRepository.findById(anyLong())).thenReturn(optionalMedico);
		
		assertMedico(medicoService.atualizarMedico(MedicoMapper.fromDTO(registroMedicoDTO), 1L));
	}
	
	@Test
	@DisplayName("5.1. Deve tentar atualizar um médico e lançar uma EntityNotFoundException")
	void deveRetornarEntityNotFoundExceptionQuandoAtualizar() {
		when(medicoRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);	
		
		assertThrows(EntityNotFoundException.class
				, () -> medicoService.atualizarMedico(MedicoMapper.fromDTO(registroMedicoDTO), 2L)
				, "Checa se lança EntityNotFoundException");
	}
}