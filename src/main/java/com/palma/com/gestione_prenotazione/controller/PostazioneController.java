package com.palma.com.gestione_prenotazione.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.TipoPostazione;
import com.palma.com.gestione_prenotazione.service.PostazioneService;

@Controller
@CrossOrigin(origins =  "http://127.0.0.1:5500", maxAge = 360000)
@RequestMapping("/api/postazione")
public class PostazioneController {
	
	@Autowired PostazioneService service;
	
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> cercaPerId(@PathVariable("id") Long id){
		return new ResponseEntity<Postazione>(service.getPostazione(id), HttpStatus.FOUND);
	}
	
	@GetMapping
		public ResponseEntity<?> getAll(){
		return new ResponseEntity<List<Postazione>>(service.getAllPostazioni(), HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<?> getAllPageable(Pageable page){
	return new ResponseEntity<Page<Postazione>>(service.getAllPostazionePageable(page), HttpStatus.OK);
}
	
	@GetMapping("/tipoPostazione/{tipo}")
	public ResponseEntity<?> cercaPerTipo(@PathVariable TipoPostazione tipo, Pageable page){
		return new ResponseEntity<Page<Postazione>>(service.filtraPerTipo(tipo, page), HttpStatus.FOUND);
	}
	
	@GetMapping("/codice/{codice}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> cercaPerCodice(@PathVariable Long codice){
		return new ResponseEntity<Postazione>(service.filtraPerCodice(codice), HttpStatus.FOUND);
	}
	
	@GetMapping("/citta/{citta}/tipo/{tipo}/data/{dataRichiesta}")
	public ResponseEntity<?> ricercaAvanzata(@PathVariable String citta, TipoPostazione tipo, LocalDate dataRichiesta, Pageable page){
		return new ResponseEntity<Page<Postazione>>(service.trovaDisponibili(citta, tipo, dataRichiesta, page), HttpStatus.OK);
	}

	
	@GetMapping("/citta/{citta}/tipo/{tipo}")
	public ResponseEntity<?> ricercaSenzaData(@PathVariable String citta, @PathVariable TipoPostazione tipo, Pageable page){
		return new ResponseEntity<Page<Postazione>>(service.trovaPerCittaeTipo(citta, tipo, page), HttpStatus.OK);
	}
	
}
