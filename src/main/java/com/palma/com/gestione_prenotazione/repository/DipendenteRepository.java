package com.palma.com.gestione_prenotazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.palma.com.gestione_prenotazione.model.Dipendente;


@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {

	@Query(value = "SELECT d FROM Dipendente d ORDER BY RANDOM() LIMIT 1")
	Dipendente findByRandomUser();
	
	//public boolean existByEmail(String email);
}
