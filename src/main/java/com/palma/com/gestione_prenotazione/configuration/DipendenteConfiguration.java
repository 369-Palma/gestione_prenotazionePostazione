package com.palma.com.gestione_prenotazione.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


import com.github.javafaker.Faker;
import com.palma.com.gestione_prenotazione.model.Dipendente;

@Configuration
public class DipendenteConfiguration {

	@Bean(name = "RandomUser")
	@Scope("prototype")
	public Dipendente randomUser() {
		Faker fake = new Faker(new Locale("it-IT"));
		
		return Dipendente.builder()
		.name(fake.name().firstName())
		.lastname(fake.name().lastName())
		.username(fake.name().username())
		.email(fake.internet().emailAddress())
		.active(true)
		.password(fake.internet().password(8, 20, true))
		.build();
	}
}
