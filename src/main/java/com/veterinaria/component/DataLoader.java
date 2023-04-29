package com.veterinaria.component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.veterinaria.entities.Atendimento;
import com.veterinaria.entities.Cachorro;
import com.veterinaria.entities.Cliente;
import com.veterinaria.entities.Endereco;
import com.veterinaria.entities.Especialidade;
import com.veterinaria.entities.Medico;
import com.veterinaria.entities.Perfil;
import com.veterinaria.entities.Raca;
import com.veterinaria.entities.Usuario;
import com.veterinaria.repositories.AtendimentoRepository;
import com.veterinaria.repositories.CachorroRepository;
import com.veterinaria.repositories.ClienteRepository;
import com.veterinaria.repositories.MedicoRepository;
import com.veterinaria.repositories.PerfilRepository;
import com.veterinaria.repositories.RacaRepository;
import com.veterinaria.repositories.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

	private final ClienteRepository clienteRepository;
	private final CachorroRepository cachorroRepository;
	private final RacaRepository racaRepository;
	private final MedicoRepository medicoRepository;
	private final AtendimentoRepository atendimentoRepository;
	private final PerfilRepository perfilRepository;
	private final UsuarioRepository usuarioRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DataLoader(ClienteRepository clienteRepository, CachorroRepository cachorroRepository,
					  RacaRepository racaRepository, MedicoRepository medicoRepository, 
					  AtendimentoRepository atendimentoRepository, PerfilRepository perfilRepository, 
					  UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
		this.clienteRepository = clienteRepository;
		this.cachorroRepository = cachorroRepository;
		this.racaRepository = racaRepository;
		this.medicoRepository = medicoRepository;
		this.atendimentoRepository = atendimentoRepository;
		this.perfilRepository = perfilRepository;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String ... args) throws Exception {
		
		Raca raca1;
		
		Endereco endereco1;
		Endereco endereco2;
		
		Especialidade especialidade1;
		Especialidade especialidade2;
		
		Cliente cliente1;
		Cliente cliente2;
		Set<Cliente> clientes = new HashSet<>();
		
		Cachorro cachorro1;
		Cachorro cachorro2;
		Cachorro cachorro3;
		Set<Cachorro> cachorros = new HashSet<>();
		
		Medico medico1;
		Medico medico2;
		Set<Medico> medicos = new HashSet<>();
		
		Atendimento atendimento1;
		Atendimento atendimento2;
		Atendimento atendimento3;
		Set<Atendimento> atendimentos = new HashSet<>();
		
		if(cachorroRepository.findAll().isEmpty()) {
			
			raca1 = new Raca(null, "Dobermann");
			
			racaRepository.save(raca1);
			
			endereco1 = new Endereco("Rua 1", "111", "Complemento Rua 1", "11111111");
			endereco2 = new Endereco("Rua 2", "222", "Complemento Rua 2", "22222222");
			
			especialidade1 = new Especialidade("Especialidade 1");
			especialidade2 = new Especialidade("Especialidade 2");
			
			String dataCliente1 = "2001-06-22";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dataFormatada1 = formatter.parse(dataCliente1);
			
			String dataCliente2 = "1995-10-01";
			Date dataFormatada2 = formatter.parse(dataCliente2);
			
			cliente1 = new Cliente("11111111111", "Fulano", "11111111111", dataFormatada1,  endereco1);
			cliente2 = new Cliente("22222222222", "Ciclano", "22222222222", dataFormatada2, endereco2);
			
			clientes.add(cliente1);
			clientes.add(cliente2);
			
			clienteRepository.saveAll(clientes);
			
			String dataCachorro1 = "2016-05-14";
			Date dataFormatada3 = formatter.parse(dataCachorro1);
			
			String dataCachorro2 = "2019-05-11";
			Date dataFormatada4 = formatter.parse(dataCachorro2);
			
			String dataCachorro3 = "2022-01-07";
			Date dataFormatada5 = formatter.parse(dataCachorro3);
			
			cachorro1 = new Cachorro("11111", "Dog 1", raca1, 20.0, 0.7, dataFormatada3, cliente1);
			cachorro2 = new Cachorro("22222", "Dog 2", raca1, 15.0, 0.6, dataFormatada4, cliente1);
			cachorro3 = new Cachorro("33333", "Dog 3", raca1, 10.0, 0.5, dataFormatada5, cliente2);
			
			cachorros.add(cachorro1);
			cachorros.add(cachorro2);
			cachorros.add(cachorro3);
			
			cachorroRepository.saveAll(cachorros);
			
			String dataMedico1 = "1975-03-15";
			Date dataFormatada6 = formatter.parse(dataMedico1);
			
			String dataMedico2 = "1982-04-23";
			Date dataFormatada7 = formatter.parse(dataMedico2);
			
			medico1 = new Medico("11111", "Médico 1", "11111111111", dataFormatada6, endereco1, especialidade1);
			medico2 = new Medico("22222", "Médico 2", "22222222222", dataFormatada7, endereco2, especialidade2);
			
			medicos.add(medico1);
			medicos.add(medico2);
			
			medicoRepository.saveAll(medicos);
			
			String dataAtendimento1 = "2022-03-27T16:30";
			LocalDateTime dataHoraFormatada1 = LocalDateTime.parse(dataAtendimento1);
			
			String dataAtendimento2 = "2022-03-28T16:30";
			LocalDateTime dataHoraFormatada2 = LocalDateTime.parse(dataAtendimento2);

			String dataAtendimento3 = "2022-03-29T16:30";
			LocalDateTime dataHoraFormatada3 = LocalDateTime.parse(dataAtendimento3);
			
			atendimento1 = new Atendimento("1234", medico1, cachorro1, 19.0, 0.7, dataHoraFormatada1, "Diagnóstico 1", null);
			atendimento2 = new Atendimento("4321", medico2, cachorro2, 18.0, 0.6, dataHoraFormatada2, "Diagnóstico 2", null);
			atendimento3 = new Atendimento("5678", medico2, cachorro3, 14.0, 0.5, dataHoraFormatada3, "Diagnóstico 3", null);
			
			atendimentos.add(atendimento1);
			atendimentos.add(atendimento2);
			atendimentos.add(atendimento3);
			
			atendimentoRepository.saveAll(atendimentos);
		}

		if(usuarioRepository.findAll().isEmpty()) {

			perfilRepository.save(new Perfil("Admin"));
			perfilRepository.save(new Perfil("User"));

			Perfil adminPerfil = perfilRepository.findByNome("Admin");
			Perfil userPerfil = perfilRepository.findByNome("User");

			Usuario usuarioAdmin = new Usuario(null, "admin@gft.com", passwordEncoder.encode("Gft@1234"), adminPerfil);
			usuarioRepository.save(usuarioAdmin);

			Usuario usuarioUser = new Usuario(null, "user@gft.com", passwordEncoder.encode("Gft@1234"), userPerfil);
			usuarioRepository.save(usuarioUser);

		}
	}
}