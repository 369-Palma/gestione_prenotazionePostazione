package com.palma.com.gestione_prenotazione.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.palma.com.gestione_prenotazione.common.PrenotazioneException;
import com.palma.com.gestione_prenotazione.model.Dipendente;
import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.Prenotazione;
import com.palma.com.gestione_prenotazione.repository.PrenotazioneRepository;
import com.palma.com.gestione_prenotazione.service.DipendenteService;
import com.palma.com.gestione_prenotazione.service.PostazioneService;
import com.palma.com.gestione_prenotazione.service.PrenotazioneService;

import jakarta.persistence.EntityExistsException;

//@CrossOrigin(origins =  "http://127.0.0.1:5500", maxAge = 360000)
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

	@Autowired PrenotazioneService service;
	@Autowired PrenotazioneRepository repo;
	@Autowired DipendenteService dipendenteService;
	@Autowired PostazioneService postazioneService;
	
	@GetMapping("/id/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> cercaPerId(@PathVariable("id") Long id){
		return new ResponseEntity<Prenotazione>(service.getPrenotazione(id), HttpStatus.FOUND);
	}
	
	
	@PostMapping("/form")
	public ResponseEntity<Prenotazione> createPrenotazione(@RequestBody Prenotazione prenotazioneForm) {
	    // Step 1: Controlla se il dipendente esiste già
	    String email = prenotazioneForm.getDipendente().getEmail();
	    Long existingDipendente = dipendenteService.getUserIdByEmail(email);

	    if (existingDipendente == null) {
	        // Se il dipendente non esiste, crea un nuovo dipendente
	        Dipendente dipendente = new Dipendente();
	        dipendente.setName(prenotazioneForm.getDipendente().getName());
	        dipendente.setLastname(prenotazioneForm.getDipendente().getLastname());
	        dipendente.setEmail(prenotazioneForm.getDipendente().getEmail());
	        Dipendente newDipendente = dipendenteService.createUser(dipendente);
	        
	        if (newDipendente == null) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	        // Step 2: Ottenimento dell'ID del dipendente appena creato
	        Long dipendenteId = dipendenteService.getUserIdByEmail(newDipendente.getEmail());
	        prenotazioneForm.getDipendente().setId(dipendenteId);
	    }

	    // Step 3: Creazione della prenotazione
	    Prenotazione newPrenotazione = service.createPrenotazione(prenotazioneForm);

	    if (newPrenotazione != null) {
	        return new ResponseEntity<>(newPrenotazione, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PostMapping
	public ResponseEntity<?> createPrenotazione2(@RequestBody Prenotazione prenotazione) {
		//try {
	    if (!service.checkDataPrenotazione(prenotazione.getDataPrenotata())) {
	    	return new ResponseEntity<>("Impossibile prenotare con così poco anticipo", HttpStatus.BAD_REQUEST);
	    }

	    else if (!service.checkPrenotazioniUtentePerData(prenotazione.getDipendente(), prenotazione.getDataPrenotazione())) {
	    	return new ResponseEntity<>("Impossibile effettuare due prenotazioni per la stessa data", HttpStatus.BAD_REQUEST);
	    }

	    else if (prenotazione.getId() != null && repo.existsById(prenotazione.getId())) {
	    	return new ResponseEntity<>("La prenotazione esiste già nel database", HttpStatus.BAD_REQUEST);
	    }

	    // Se tutto è a posto, salva la prenotazione
	    repo.save(prenotazione);

	    return new ResponseEntity<Prenotazione>(prenotazione, HttpStatus.OK);
		//} catch (Exception e) {
	    //    return new ResponseEntity<>("Errore interno del server", HttpStatus.INTERNAL_SERVER_ERROR);
	  //  }
	}
	
	@PutMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updatePrenotazione(@RequestBody Prenotazione prenotazione) {
		return new ResponseEntity<Prenotazione>(service.updatePrenotazione(prenotazione), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<String> deletePrenotazione(@PathVariable Long id){
		return new ResponseEntity<String>(service.removePrenotazione(id), HttpStatus.OK);
	}
	
	@GetMapping("/codice_postazione/{codice}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getByPrenotazione(@PathVariable("codice") Long codice) {
		return new ResponseEntity<>(service.getByCodice(codice), HttpStatus.OK);
	}
	
	@PostMapping("/nuova-prenotazione") 
    public ResponseEntity<Prenotazione> creaNuovaPrenotazione(
        @RequestBody Prenotazione prenotazione,
        @RequestParam Long dipendenteId, 
        @RequestParam Long postazioneId, 
        @RequestParam String dataPrenotazione 
    ) {
        // Puoi convertire dataPrenotazione in LocalDate se necessario
        LocalDate dataPrenotazioneLocal = LocalDate.parse(dataPrenotazione);

        // Recupera Dipendente e Postazione dal tuo database utilizzando i loro ID
        Dipendente dipendente = dipendenteService.getUser(dipendenteId);
        Postazione postazione = postazioneService.getPostazione(postazioneId);

        // Chiama il servizio per effettuare la prenotazione
        Prenotazione nuovaPrenotazione = service.prenota(dipendente, postazione, dataPrenotazioneLocal);

        return new ResponseEntity<>(nuovaPrenotazione, HttpStatus.OK);
    }
}


