package com.example.projetolojaunit.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.projetolojaunit.entities.Cliente;
import com.example.projetolojaunit.repositories.ClienteRepository;
import com.example.projetolojaunit.services.exceptions.ResourceNotFoundException;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteRepository clienteRepository;

	@PostMapping(path = "/add")
	public ResponseEntity addNewCliente(@RequestBody Cliente cliente) {
		if (clienteRepository.existsByCpf(cliente.getCpf())) {
			return new ResponseEntity<>("Já existe um cliente cadastrado com o CPF informado!", HttpStatus.BAD_REQUEST);
		}
		
		if (cliente.getNome() == null || cliente.getNome().length() == 0) {
			return new ResponseEntity<>("Favor informar um nome válido!",HttpStatus.BAD_REQUEST);
		}

		if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
			return new ResponseEntity<>("Favor informar um CPF válido!", HttpStatus.BAD_REQUEST);
		}

		if (cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
			return new ResponseEntity<>("Favor informar um email válido!",HttpStatus.BAD_REQUEST);
		}
		
		if (cliente.getTelefone().length() > 13) {
			return new ResponseEntity<>("Favor informar um telefone válido!",HttpStatus.BAD_REQUEST);
		}
		
		Cliente c = new Cliente();
		c.setNome(cliente.getNome());
		c.setCpf(cliente.getCpf());
		c.setEmail(cliente.getEmail());
		c.setDataNascimento(cliente.getDataNascimento());
		c.setSexo(cliente.getSexo());
		c.setNomeSocial(cliente.getNomeSocial());
		c.setApelido(cliente.getApelido());
		c.setTelefone(cliente.getTelefone());
		clienteRepository.save(c);

		return new ResponseEntity<>(c, HttpStatus.CREATED);
	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<Cliente>> getAllClientes(@RequestParam(required = false) String nome) {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			if (nome == null)
				clienteRepository.findAll().forEach(clientes::add);
			
			else
				clienteRepository.findByNomeContaining(nome).forEach(clientes::add);

			if (clientes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(clientes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<Cliente> returnCliente(@PathVariable Integer id) {

		return ResponseEntity.ok()
				.body(clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCliente(@PathVariable Integer id) {
		try {
			clienteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException a) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "/update/{id}")
	public ResponseEntity updateCliente(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
		Optional<Cliente> clienteData = clienteRepository.findById(id);
		Cliente c = clienteData.get();
		
		if (clienteRepository.existsByCpf(cliente.getCpf()) && !(c.getCpf().equals(cliente.getCpf()))) {
			return new ResponseEntity<>("Já existe um cliente cadastrado com o CPF informado!", HttpStatus.BAD_REQUEST);
		}
		
		if (!clienteRepository.existsById(cliente.getId())) {
			return new ResponseEntity<>("Cliente não existe na base de dados!",HttpStatus.BAD_REQUEST);
		}

		if (cliente.getNome() == null || cliente.getNome().length() == 0) {
			return new ResponseEntity<>("Favor informar um nome válido!",HttpStatus.BAD_REQUEST);
		}

		if (cliente.getCpf() == null || cliente.getCpf().length() != 11) {
			return new ResponseEntity<>("Favor informar um CPF válido!", HttpStatus.BAD_REQUEST);
		}

		if (cliente.getEmail() == null || !cliente.getEmail().contains("@")) {
			return new ResponseEntity<>("Favor informar um email válido!",HttpStatus.BAD_REQUEST);
		}
		
		if (cliente.getTelefone().length() > 13) {
			return new ResponseEntity<>("Favor informar um telefone válido!",HttpStatus.BAD_REQUEST);
		}
		

//		Cliente c = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		c.setNome(cliente.getNome());
		c.setCpf(cliente.getCpf());
		c.setEmail(cliente.getEmail());
		c.setDataNascimento(cliente.getDataNascimento());
		c.setSexo(cliente.getSexo());
		c.setNomeSocial(cliente.getNomeSocial());
		c.setApelido(cliente.getApelido());
		c.setTelefone(cliente.getTelefone());
		
		return new ResponseEntity<>(clienteRepository.save(c), HttpStatus.OK);

	}
}
