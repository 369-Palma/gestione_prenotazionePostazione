package com.palma.com.gestione_prenotazione.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.palma.com.gestione_prenotazione.model.Dipendente;

import com.palma.com.gestione_prenotazione.service.DipendenteService;


@Controller
@CrossOrigin(origins = "*", maxAge= 3600)
@RequestMapping("/api/dipendente")
public class DipendenteController {

	@Autowired DipendenteService service;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		return new ResponseEntity<Dipendente>(service.getUser(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Dipendente> createDipendente(@RequestBody Dipendente d) {
	return new ResponseEntity<Dipendente>(service.createUser(d), HttpStatus.OK);
	}
	
	@GetMapping("/dipendenteid/email/{email}")
	public ResponseEntity<Long> getIdDipendenteByEmail(@PathVariable String email) {
	    Long dipendenteId = service.getUserIdByEmail(email);
	    return new ResponseEntity<>(dipendenteId, HttpStatus.OK);
	}
}
