package com.example.projetolojaunit.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id) {
		super("Objeto com id: " + id + " não encontrado");
	}

}
