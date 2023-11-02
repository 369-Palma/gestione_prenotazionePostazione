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
	
	@Autowired PostazioneService postService;
	//@Autowired @Qualifier(PrenotazioneRandom) private ObjectProvider<Prenotazione> randomPrenotazioneProvider;
	
	//@Value("${gestioneprenotazioni.giornianticipoprenotazione}")
	//private Integer giorniAnticipoPrenotazione;
	
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
	@Value("${gestioneprenotazioni.giornianticipoprenotazione}")
	private Integer giorniAnticipoPrenotazione;
	public boolean checkDataPrenotazione(LocalDate dataPrenotata) {
        LocalDate oggi = LocalDate.now();
        LocalDate dataMinima = oggi.plusDays(giorniAnticipoPrenotazione);
        return dataPrenotata.isAfter(dataMinima);
    }
	
	
	public boolean prenotaPostazione(Long postazioneId, LocalDate dataPrenotata) {
	    // Recupera la postazione dal repository utilizzando l'ID
	    Postazione postazione = postService.getPostazione(postazioneId);

	    // Controlla se la postazione ha raggiunto il limite massimo di occupanti
	    if (postazione.getNumPrenotati() >= postazione.getMaxOccupanti()) {
	    	postazione.setAvailable(false);
	        throw new EntityNotFoundException("Non è possibile prenotare questa postazione in quanto ha raggiunto il limite massimo di occupanti.");
	    }

	    // Verifica la disponibilità della postazione per la data specifica
	    if (!postRepo.isPostazioneDisponibile(postazione, dataPrenotata)) {
	        return false; // La postazione non è disponibile
	    }

	    // Incrementa il contatore numPrenotati
	    int numPrenotati = postazione.getNumPrenotati() + 1;
	    postazione.setNumPrenotati(numPrenotati);
	    System.out.println("numero prenotati: " + numPrenotati);

	    // Salva la postazione nel repository
	    postRepo.save(postazione);

	    return true;
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
			if(repo.existsById(p.getId())) {
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
		

		public boolean checkPrenotazioniUtentePerData(Dipendente d, LocalDate dataPrenotata) {
			Pageable pageable = PageRequest.of(0, 1);

			Page<Prenotazione> findByDipendenteDataPrenotata = repo.findByDipendenteAndDataPrenotata(d,
					dataPrenotata, pageable);

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

		
	

