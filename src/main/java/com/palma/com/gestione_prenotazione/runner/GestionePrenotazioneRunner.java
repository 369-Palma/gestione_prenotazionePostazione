package com.palma.com.gestione_prenotazione.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.palma.com.gestione_prenotazione.service.BuildingService;
import com.palma.com.gestione_prenotazione.service.CittaService;
import com.palma.com.gestione_prenotazione.service.PostazioneService;
import com.palma.com.gestione_prenotazione.service.UserService;


@Component
public class GestionePrenotazioneRunner implements ApplicationRunner{
	
@Autowired CittaService cityService;	
@Autowired BuildingService buildingService;
@Autowired PostazioneService postazioneService;
@Autowired UserService userService;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Run ...");
		
		
				//POPOLA IL DB
				//starterDB();
			
				
			}
			
			private void starterDB() {
				//genera CITTA
				for (int i = 0; i<20; i++) {	
					cityService.createCittaRandom();
				}
				
				//genera EDIFICIO
				for (int i = 0; i<20; i++) {	
					buildingService.createBuildingRandom();
				}
				
				//genera POSTAZIONI
				for (int i = 0; i<30; i++) {
				postazioneService.createPostazioneRandom();
				}
			
				//genera USERS
				for (int i = 0; i<30; i++) {
					userService.createRandomUser();
				}
			}
				


		}