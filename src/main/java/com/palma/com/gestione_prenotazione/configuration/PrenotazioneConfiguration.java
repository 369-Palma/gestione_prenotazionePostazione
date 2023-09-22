package com.palma.com.gestione_prenotazione.configuration;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.Random;

import com.github.javafaker.Faker;
import com.palma.com.gestione_prenotazione.model.Prenotazione;
import com.palma.com.gestione_prenotazione.service.PostazioneService;
import com.palma.com.gestione_prenotazione.service.UserService;



@Configuration
public class PrenotazioneConfiguration {

	@Autowired UserService userService;
	@Autowired PostazioneService postazioneService;
	
	@Bean(name = "PrenotazioneRandom")
	@Scope("prototype")
	public Prenotazione prenotazioneRandom(){
		Faker fake = new Faker(new Locale("it-IT"));
		
		LocalDate dataOggi = LocalDate.now();
		LocalDate dataPrenotata = dataOggi.plusWeeks(3); 
		Random random = new Random();
		long daysBetween = ChronoUnit.DAYS.between(dataOggi, dataPrenotata);
		long randomDays = random.nextLong() % daysBetween;
		LocalDate dataPrenotataConv = dataOggi.plusDays(randomDays);

		// List<User> users = new ArrayList<>();
	     //  users.add(userService.getUserRandom());
	        
	    //   List<Prenotazione> prenotazioni = new ArrayList<>();
	     //   prenotazioni.add(prenotazioneService.getPrenotazioneRandom());
		
		return Prenotazione.builder()
				.dipendente(userService.getUserRandom())
				.postazione(postazioneService.getPostazioneRandom())
				.dataPrenotata(dataPrenotataConv)
				.dataPrenotazione(dataOggi)
				.build();
		}
}
