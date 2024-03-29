package com.veterinaria.servicesImpl;

import com.veterinaria.entities.Cliente;
import com.veterinaria.exceptions.DuplicateEntityException;
import com.veterinaria.exceptions.EntityNotFoundException;
import com.veterinaria.exceptions.StringLengthException;
import com.veterinaria.repositories.ClienteRepository;
import com.veterinaria.services.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository clienteRepository;

	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public Cliente salvarCliente(Cliente cliente) {
		
		Optional<Cliente> optional = clienteRepository.findByCpf(cliente.getCpf());
		
		if(cliente.getCpf().length() == 11) {
			if(cliente.getTelefone().length() == 11) {
				if(cliente.getEndereco().getCep().length() == 8) {
					if(optional.isEmpty())
						return clienteRepository.save(cliente);
					else
						throw new DuplicateEntityException("Cliente já cadastrado.");
				} else {
					throw new StringLengthException("O CEP deve conter 8 dígitos.");
				}
			} else {
				throw new StringLengthException("O Telefone deve conter 11 dígitos.");
			}
		} else
			throw new StringLengthException("O CPF deve conter 11 dígitos.");
	}
	
	public Page<Cliente> listarTodosOsClientes(Pageable pageable) {
		
		return clienteRepository.findAll(pageable);
	}
	
	public Cliente buscarCliente(Long id) {

		Optional<Cliente> optional = clienteRepository.findById(id);
		
		return optional.orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
	}
	
	public Page<Cliente> buscaClientePorCachorro(Pageable pageable, String nomeCachorro) throws EntityNotFoundException {
		
		Page<Cliente> optional = clienteRepository.findByCachorrosNomeContains(pageable, nomeCachorro);
		
		if(optional != null)
			return optional;
		else
			throw new EntityNotFoundException("Cliente não encontrada.");
	}
	
	public Cliente atualizarCliente(Cliente cliente, Long id) {
		
		Cliente clienteOriginal = this.buscarCliente(id);
		
		cliente.setId(clienteOriginal.getId());
		
		Optional<Cliente> optional = clienteRepository.findByCpf(cliente.getCpf());
		
		if(cliente.getCpf().length() == 11) {
			if(cliente.getTelefone().length() == 11) {
				if(cliente.getEndereco().getCep().length() == 8) {
					if(optional.isEmpty())
						return clienteRepository.save(cliente);
					else
						throw new DuplicateEntityException("Cliente já cadastrado.");
				} else {
					throw new StringLengthException("O CEP deve conter 8 dígitos.");
				}
			} else {
				throw new StringLengthException("O Telefone deve conter 11 dígitos.");
			}
		} else
			throw new StringLengthException("O CPF deve conter 11 dígitos.");
	}
	
	public void excluirCliente(Long id) {
		Cliente clienteOriginal = this.buscarCliente(id);
		
		clienteRepository.delete(clienteOriginal);
	}
}