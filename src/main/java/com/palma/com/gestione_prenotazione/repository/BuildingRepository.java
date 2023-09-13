package com.palma.com.gestione_prenotazione.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.palma.com.gestione_prenotazione.model.Building;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

	@Query(value = "SELECT b FROM Building b ORDER BY RANDOM() LIMIT 1")
	Building findByBuildingRandom();
	
}
