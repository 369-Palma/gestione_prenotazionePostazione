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
		String nome = fake.name().firstName();
		String lastname = fake.name().lastName();
		
		return Dipendente.builder()
		.name(nome)
		.lastname(lastname)
		.email(nome +"."+ lastname + fake.number().numberBetween(1, 100) + fake.internet().domainName())
		.build();
	}
}
