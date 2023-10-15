package com.palma.com.gestione_prenotazione.runner;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.palma.com.gestione_prenotazione.service.BuildingService;
import com.palma.com.gestione_prenotazione.service.CittaService;
import com.palma.com.gestione_prenotazione.service.DipendenteService;
import com.palma.com.gestione_prenotazione.service.PostazioneService;
import com.palma.com.gestione_prenotazione.auth.repository.RoleRepository;
import com.palma.com.gestione_prenotazione.auth.repository.UserRepository;
import com.palma.com.gestione_prenotazione.auth.service.AuthService;
import com.palma.com.gestione_prenotazione.auth.entity.ERole;
import com.palma.com.gestione_prenotazione.auth.entity.Role;

@Component
public class GestionePrenotazioneRunner implements ApplicationRunner{
	
@Autowired CittaService cityService;	
@Autowired BuildingService buildingService;
@Autowired PostazioneService postazioneService;
@Autowired DipendenteService userService;
//@Autowired PrenotazioneService prenotazioneService;

@Autowired
RoleRepository roleRepository;

@Autowired
UserRepository userRepository;

@Autowired
PasswordEncoder passwordEncoder;

@Autowired
AuthService authService;


private static Set<Role> adminRole;
private static Set<Role> userRole;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//System.out.println("Run ...");
		
		//SETTA RUOLI ADMIN/USER IN DB
		//setRoleDefault();
		
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
				
				//genera PRENOTAZIONI
			//	prenotazioneService.prenota(userService.createRandomUser(), postazioneService.createPostazioneRandom(), LocalDate.of(2023, 10, 15));
			}
				
			private void setRoleDefault() {
				Role admin = new Role();
				admin.setRoleName(ERole.ROLE_ADMIN);
				roleRepository.save(admin);

				Role user = new Role();
				user.setRoleName(ERole.ROLE_USER);
				roleRepository.save(user);

				adminRole = new HashSet<Role>();
				adminRole.add(admin);
				adminRole.add(user);

				userRole = new HashSet<Role>();
				userRole.add(user);
			}

		}