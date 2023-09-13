package com.palma.com.gestione_prenotazione.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.palma.com.gestione_prenotazione.model.Citta;

@Repository("cittaRepository")
public interface CittaRepository extends JpaRepository<Citta, Long> {

	@Query(value = "SELECT c FROM City c ORDER BY FUNCTION('RAND') LIMIT 1")
	Citta findByCittaRandom();
	
	
	//public boolean existByName(String name);
	@Query("SELECT c FROM Citta c WHERE c.name LIKE %:name% ")
	public Page<Citta> findByName(String name, Pageable page);
}
