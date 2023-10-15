package com.palma.com.gestione_prenotazione.configuration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.javafaker.Faker;
import com.palma.com.gestione_prenotazione.model.Building;
import com.palma.com.gestione_prenotazione.repository.CittaRepository;
import com.palma.com.gestione_prenotazione.service.CittaService;

@Configuration
public class BuildingConfiguration {

	@Autowired CittaService cityService;
	
	@Bean(name="buildingRandom")
	@Scope("prototype")
	public Building randomBuilding() {
		Faker fake = new Faker(new Locale("it-IT"));
		
		return Building.builder()
				.name(fake.company().name())
				.address(fake.address().streetAddress())
				.citta(cityService.createCittaRandom())
				.build();
	}
	
}
