package com.palma.com.gestione_prenotazione.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.github.javafaker.Faker;
import com.palma.com.gestione_prenotazione.model.Building;
import com.palma.com.gestione_prenotazione.model.Citta;

@Configuration
public class CittaConfiguration {

	
	@Bean(name="CittaRandom")
	@Scope("prototype")
	public Citta cittaRandom() {
		Faker fake = new Faker(new Locale("it-IT"));
		
		return Citta.builder()
				.name(fake.address().cityName())
				.build();
	}
	
}
