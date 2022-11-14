/*package com.gft.veterinaria.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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

import com.gft.veterinaria.dto.cachorro.CachorroMapper;
import com.gft.veterinaria.dto.cachorro.ConsultaCachorroDTO;
import com.gft.veterinaria.dto.cachorro.RegistroCachorroDTO;
import com.gft.veterinaria.entities.Cachorro;
import com.gft.veterinaria.entities.Cliente;
import com.gft.veterinaria.entities.Endereco;
import com.gft.veterinaria.entities.Raca;
import com.gft.veterinaria.exceptions.VeterinariaException;
import com.gft.veterinaria.repositories.CachorroRepository;
import com.gft.veterinaria.repositories.RacaRepository;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Teste CachorroService")
class CachorroServiceTest {

	@Mock
	private CachorroRepository cachorroRepository;
	
	@Mock
	private RacaRepository racaRepository;
	
	@Mock
	private RacaService racaService;
	
	@InjectMocks
	private CachorroService cachorroService;
	
	private Cachorro cachorro;
	private Raca raca;
	private Cliente cliente;
	private Endereco endereco;
	private ConsultaCachorroDTO consultaCachorroDTO;
	private RegistroCachorroDTO registroCachorroDTO;
	private Optional<Cachorro> optionalCachorro;
	
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
		optionalCachorro = Optional.of(cachorro);
		pageable = PageRequest.of(0, 5);
		page = new PageImpl<>(List.of(cachorro), pageable, 5);
	}
	
	private void assertCachorro(Cachorro response) throws MultipleFailuresError {
		assertAll(
				"Checa se não é null para a classe do objeto e valores de atributos"
				, () -> assertNotNull(response)
				, () -> assertEquals(Cachorro.class, response.getClass())
				, () -> assertEquals(1L, response.getId())
				, () -> assertEquals("11111", response.getNumeroRegistro())
		);
	}
	
	@Test
	@DisplayName("1. Deve criar um novo cachorro")
	void deveRetornarSucessoQuandoCriarCachorro() {	
		when(cachorroRepository.save(any())).thenReturn(cachorro);
		
		assertCachorro(cachorroService.salvarCachorro(CachorroMapper.fromDTO(registroCachorroDTO)));	
	}
	
	@Test
	@DisplayName("1.1. Quando criado um cachorro com um nome existente, "
			+ "deve retornar VeterinariaException com mensagem correta")
	void deveRetornarVeterinariaExceptionComMensagemCorretaQuandoCriadoCachorroComNomeExistente() {
		when(cachorroRepository.findByNumeroRegistro(anyString())).thenReturn(optionalCachorro);
		
		try {
			cachorroService.salvarCachorro(CachorroMapper.fromDTO(registroCachorroDTO));
			fail("expected a DicionarioException");
		} catch(VeterinariaException e) {
			assertEquals("Cachorro já cadastrado.", e.getMessage());
		}
	}
}*/