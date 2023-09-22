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


import com.palma.com.gestione_prenotazione.model.Prenotazione;
import com.palma.com.gestione_prenotazione.service.PrenotazioneService;

@CrossOrigin(origins =  "http://localhost:3000", allowCredentials = "true")
@Controller
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

	@Autowired PrenotazioneService service;
	
	@GetMapping("/id/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> cercaPerId(@PathVariable("id") Long id){
		return new ResponseEntity<Prenotazione>(service.getPrenotazione(id), HttpStatus.FOUND);
	}
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> createPrentazione(@RequestBody Prenotazione prenotazione) {
		return new ResponseEntity<Prenotazione>(service.createPrenotazione(prenotazione), HttpStatus.CREATED);
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
	
}
