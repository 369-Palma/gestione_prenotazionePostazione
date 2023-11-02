package com.palma.com.gestione_prenotazione.configuration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.javafaker.Faker;
import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.TipoPostazione;
import com.palma.com.gestione_prenotazione.repository.BuildingRepository;
import com.palma.com.gestione_prenotazione.service.BuildingService;

@Configuration
public class PostazioneConfiguration {

	@Autowired BuildingService buildingService;
	
	@Bean("PostazioneRandom")
	@Scope("prototype")
	public Postazione randomPostazione() {
		
		Faker fake = new Faker(new Locale("it-IT"));
		
		TipoPostazione tipo = TipoPostazione.TipoPostazioneRandom();
		Integer maxOccupanti = fake.number().numberBetween(1, 15);
		
		return Postazione.builder()
				.codice(fake.number().numberBetween(000000000000001l, 9999999999999999l))
				.descrizione(tipo + ". Può ospitare un massimo di " + maxOccupanti + ".")
				.maxOccupanti(maxOccupanti)
				.available(true)
				.tipo(tipo)
				.building(buildingService.getBuildingRandom())
				.numPrenotati(0)
				.build();
	}
}
