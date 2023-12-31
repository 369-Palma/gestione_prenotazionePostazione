package com.palma.com.gestione_prenotazione.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palma.com.gestione_prenotazione.model.Citta;
import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.TipoPostazione;
import com.palma.com.gestione_prenotazione.repository.PostazioneRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PostazioneService {

	@Autowired PostazioneRepository repo;
	@Autowired @Qualifier("PostazioneRandom") private ObjectProvider<Postazione> randomPostazioneProvider;

	public Integer numPrenotati;
	
	
	//CREA POSTAZIONI RANDOM 
	public Postazione createPostazioneRandom() {
		return createPostazione(randomPostazioneProvider.getObject());
	}

	//METODI STANDARD PER API

	public List<Postazione> getAllPostazioni() {
		return (List<Postazione>) repo.findAll();
	}

	public Page<Postazione> getAllPostazionePageable(Pageable pageable) {
		return (Page<Postazione>) repo.findAll(pageable);
	}

	public Postazione getPostazione(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("The station with id" + id + "does not exist in the database!");
		}
		return repo.findById(id).get();
	}

	public Postazione getPostazioneRandom() {
		return repo.findByPostazioneRandom();
	}

	public Postazione createPostazione(Postazione postazione) {
		if(postazione.getId()!= null && repo.existsById(postazione.getId())) {
			throw new EntityExistsException("The station is already in the database ");
		} else {
			repo.save(postazione);
		}		
		return postazione;
	}

	public String removePostazione(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityExistsException("The station with id " + id + " does not exist in the database!");
		}
		repo.deleteById(id);
		return "The Postazione with id " + id + " has been deleted from the database";
	}

	public Postazione updatePostazione(Postazione postazione ) {
		if(!repo.existsById(postazione.getId())) {
			throw new EntityExistsException("The chosen station does not exists in the database");
		}
		repo.save(postazione);
		return postazione;
	}

//SPECIALI
	public Page<Postazione> filtraPerTipo (TipoPostazione tipo, Pageable page){
		if(!repo.existsByTipo(tipo)){
			throw new EntityExistsException("The station type " + tipo + " does not exist in the database!");
		} 
		return (Page<Postazione>) repo.findByTipo(tipo, page);
	}
	
	public Postazione filtraPerCodice(Long codice){
		if(!repo.existsByCodice(codice)) { 
			throw new EntityExistsException("There are no station with code" + codice);
		}
		return repo.findByCodice(codice);
	}
	
	
	public Postazione filtraPerParteDiCodice(Long codice){
		if(!repo.existsByCodice(codice)) { 
			throw new EntityExistsException("There are no station with code" + codice);
		}
		return repo.findByCodice(codice);
	}
	
	public Page<Postazione> trovaPerCittaeTipo(String citta, TipoPostazione tipo, Pageable page){
		if(!repo.existsByTipo(tipo)) {
			throw new EntityExistsException("Non ci sono postazioni con tipo" + tipo + " in " + citta) ;
		}
		return (Page<Postazione>) repo.findLiberePerCittaETipo(citta, tipo, page);
	}
	
	//public Page<Postazione> filtraPerParteDiCodice(Long codice, Pageable page){
	//	if(!repo.existsByCodice(codice)) { 
	//		throw new EntityExistsException("There are no station with code" + codice);
	//	}
	//	return (Page<Postazione>) repo.findByCodice(codice, page);
	//}
}
