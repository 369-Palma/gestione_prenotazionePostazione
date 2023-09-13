package com.palma.com.gestione_prenotazione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.palma.com.gestione_prenotazione.model.Dipendente;


@Repository
public interface UserRepository extends JpaRepository<Dipendente, Long> {

	@Query(value = "SELECT u FROM User u ORDER BY RANDOM() LIMIT 1")
	Dipendente findByRandomUser();
	
	public boolean existByEmail(String email);
}