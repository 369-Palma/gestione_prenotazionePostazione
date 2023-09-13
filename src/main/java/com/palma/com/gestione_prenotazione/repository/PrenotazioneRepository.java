package com.palma.com.gestione_prenotazione.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.palma.com.gestione_prenotazione.model.Dipendente;
import com.palma.com.gestione_prenotazione.model.Prenotazione;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

public Page<Prenotazione> findByUserAndDataPrenotata(Dipendente d, LocalDate dataPrenotata, Pageable pageable);
	
	public Page<Prenotazione> findByUser(Dipendente d, Pageable pageable);
	
}