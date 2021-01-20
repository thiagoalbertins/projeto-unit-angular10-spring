package com.example.projetolojaunit.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.projetolojaunit.entities.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Integer>{

	boolean existsByCpf(String cpf);
	
	List<Cliente> findByNomeContaining(String nome);
}
