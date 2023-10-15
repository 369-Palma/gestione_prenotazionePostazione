package com.palma.com.gestione_prenotazione.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palma.com.gestione_prenotazione.common.PrenotazioneException;
import com.palma.com.gestione_prenotazione.model.Dipendente;
import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.Prenotazione;
import com.palma.com.gestione_prenotazione.repository.PostazioneRepository;
import com.palma.com.gestione_prenotazione.repository.PrenotazioneRepository;


import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PrenotazioneService {

	@Autowired PrenotazioneRepository repo;
	@Autowired PostazioneRepository postRepo;
	//@Autowired @Qualifier(PrenotazioneRandom) private ObjectProvider<Prenotazione> randomPrenotazioneProvider;
	
	@Value("${gestioneprenotazioni.giornianticipoprenotazione}")
	private Integer giorniAnticipoPrenotazione;
	
	public Prenotazione prenota(Dipendente d, Postazione postazione, LocalDate dataPrenotazione) {
		if (checkDataPrenotazione(dataPrenotazione) == false) {
			throw new PrenotazioneException(
					"Impossibile prenotare una postazione meno di " + giorniAnticipoPrenotazione + " giorni prima");
		}

		if (!checkPrenotazioniUtentePerData(d, dataPrenotazione)) {
			throw new PrenotazioneException("Impossibile effettuare due prenotazioni per la stessa data");
		}

		Prenotazione prenotazione = new Prenotazione(null,d, postazione, dataPrenotazione, LocalDate.now());

		return repo.save(prenotazione);

	}
	//METODI STANDARD PER API

		public List<Prenotazione> getAllPrenotazione() {
			return (List<Prenotazione>) repo.findAll();
		}

		public Page<Prenotazione> getAllPrenotazionePageable(Pageable pageable) {
			return (Page<Prenotazione>) repo.findAll(pageable);
		}

		public Prenotazione getPrenotazione(Long id) {
			if(!repo.existsById(id)) {
				throw new EntityNotFoundException("The prenotation with id " + id + " does not exist in the database!");
			}
			return repo.findById(id).get();
		}

		//public Prenotazione getPrenotazioneRandom() {
		//	return repo.findByPrenotazioneRandom();
		//}

		public Prenotazione createPrenotazione(Prenotazione p) {
			if(p.getId()!= null && repo.existsById(p.getId())) {
				throw new EntityExistsException("The prenotation is already in the database ");
			} else {
				repo.save(p);
			}		
			return p;
		}

		public String removePrenotazione(Long id) {
			if(!repo.existsById(id)) {
				throw new EntityExistsException("The prenotation with id " + id + " does not exist in the database!");
			}
			repo.deleteById(id);
			return "The Prenotazione with id " + id + " has been deleted from the database";
		}

		public Prenotazione updatePrenotazione(Prenotazione p ) {
			if(!repo.existsById(p.getId())) {
				throw new EntityExistsException("The chosen prenotation does not exists in the database");
			}
			repo.save(p);
			return p;
		}

	
	//SPECIALI
		private boolean checkDataPrenotazione(LocalDate dataPrenotazione) {
			LocalDate now = LocalDate.now();
			return dataPrenotazione.minus(giorniAnticipoPrenotazione, ChronoUnit.DAYS).isAfter(now);
		}

		private boolean checkPrenotazioniUtentePerData(Dipendente d, LocalDate dataPrenotazione) {

			Pageable pageable = PageRequest.of(0, 1);

			Page<Prenotazione> findByDipendenteDataPrenotata = repo.findByDipendenteAndDataPrenotata(d,
					dataPrenotazione, pageable);

			return findByDipendenteDataPrenotata.isEmpty();

		}
		
		public Optional<Prenotazione> findById(Long id) {
			return repo.findById(id);
		}
		
		public Prenotazione getByCodice(Long codice) {
			if(!postRepo.existsByCodice(codice)) {
				throw new EntityExistsException("Non ci sono prenotazioni con codice di postazione " + codice);
			} 
			return postRepo.findByCodicePrenotazione(codice);
		}	
		}

		//public Page<Prenotazione> findByDipendente(Dipendente d, Pageable pageable) {
		//	return repo.findByDipendente(d, pageable);
		//}
	

