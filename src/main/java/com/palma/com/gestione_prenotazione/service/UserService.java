package com.palma.com.gestione_prenotazione.service;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palma.com.gestione_prenotazione.model.Dipendente;
import com.palma.com.gestione_prenotazione.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired UserRepository repo;
	@Autowired @Qualifier("RandomUser")private ObjectProvider<Dipendente> randomUserProvider;
	
	//CREA USERS RANDOM 
public Dipendente createRandomUser() {
	return createUser(randomUserProvider.getObject());
}

//METODI STANDARD PER API
	
	public List<Dipendente> getAllUsers() {
		return (List<Dipendente>) repo.findAll();
	}

	public Page<Dipendente> getAllUserPageable(Pageable pageable) {
		return (Page<Dipendente>) repo.findAll(pageable);
	}

	public Dipendente getUser(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("The User with id" + id + "does not exist in the database!");
		}
		return repo.findById(id).get();
	}

	public Dipendente getUserRandom() {
		return repo.findByRandomUser();
	}

	public Dipendente createUser(Dipendente dipendente) {
		if(dipendente.getId()!= null && repo.existsById(dipendente.getId())) {
			throw new EntityExistsException("The User is already in the database ");
		} else {
			repo.save(dipendente);
		}		
		return dipendente;
	}

	public String removeUser(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityExistsException("The User with id " + id + " does not exist in the database!");
		}
		repo.deleteById(id);
		return "The User with id " + id + " has been deleted from the database";
	}

	public Dipendente updateUser(Dipendente d ) {
		if(!repo.existsById(d.getId())) {
			throw new EntityExistsException("The chosen User does not exists in the database");
		}
		repo.save(d);
		return d;
	}


	
	
}
