package com.palma.com.gestione_prenotazione.service;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palma.com.gestione_prenotazione.model.Citta;
import com.palma.com.gestione_prenotazione.repository.CittaRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CittaService {

	@Autowired CittaRepository repo;
	@Autowired @Qualifier("CittaRandom") private ObjectProvider<Citta> randomCittaProvider;

	//CREA CITTA' RANDOM 
		public Citta createCittaRandom() {
			return createCitta(randomCittaProvider.getObject());
		}

		//METODI STANDARD PER API

		public List<Citta> getAllCitta() {
			return (List<Citta>) repo.findAll();
		}

		public Page<Citta> getAllCittaPageable(Pageable pageable) {
			return (Page<Citta>) repo.findAll(pageable);
		}

		public Citta getCitta(Long id) {
			if(!repo.existsById(id)) {
				throw new EntityNotFoundException("The city with id" + id + "does not exist in the database!");
			}
			return repo.findById(id).get();
		}

		public Citta getCittaRandom() {
			return repo.findByCittaRandom();
		}

		public Citta createCitta(Citta city) {
			if(city.getId()!= null && repo.existsById(city.getId())) {
				throw new EntityExistsException("The city is already in the database ");
			} else {
				repo.save(city);
			}		
			return city;
		}

		public String removeCitta(Long id) {
			if(!repo.existsById(id)) {
				throw new EntityExistsException("The city with id " + id + " does not exist in the database!");
			}
			repo.deleteById(id);
			return "The Citta with id " + id + " has been deleted from the database";
		}

		public Citta updateCitta(Citta city ) {
			if(!repo.existsById(city.getId())) {
				throw new EntityExistsException("The chosen city does not exists in the database");
			}
			repo.save(city);
			return city;
		}

}
