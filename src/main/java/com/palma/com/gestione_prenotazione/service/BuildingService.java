package com.palma.com.gestione_prenotazione.service;

import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.palma.com.gestione_prenotazione.model.Building;
import com.palma.com.gestione_prenotazione.repository.BuildingRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BuildingService {

	@Autowired BuildingRepository repo;
	@Autowired @Qualifier("buildingRandom")private ObjectProvider<Building> randomBuildingProvider;

	
	//CREA EDIFICI RANDOM 
public Building createBuildingRandom() {
	return createBuilding(randomBuildingProvider.getObject());
}

//METODI STANDARD PER API

public List<Building> getAllBuilding() {
	return (List<Building>) repo.findAll();
}

public Page<Building> getAllBuildingPageable(Pageable pageable) {
	return (Page<Building>) repo.findAll(pageable);
}

public Building getBuilding(Long id) {
	if(!repo.existsById(id)) {
		throw new EntityNotFoundException("The building with id" + id + "does not exist in the database!");
	}
	return repo.findById(id).get();
}

public Building getBuildingRandom() {
	return repo.findByBuildingRandom();
}

public Building createBuilding(Building building) {
	if(building.getId()!= null && repo.existsById(building.getId())) {
		throw new EntityExistsException("The building is already in the database ");
	} else {
		repo.save(building);
	}		
	return building;
}

public String removeBuilding(Long id) {
	if(!repo.existsById(id)) {
		throw new EntityExistsException("The building with id " + id + " does not exist in the database!");
	}
	repo.deleteById(id);
	return "The building with id " + id + " has been deleted from the database";
}

public Building updateBuilding(Building building ) {
	if(!repo.existsById(building.getId())) {
		throw new EntityExistsException("The chosen building does not exists in the database");
	}
	repo.save(building);
	return building;
}

// SPECIALI

//public Page<Building> filtraPerCitta(String city, Pageable page){
	//if(!repo.existByCity(city)) {
	//	throw new EntityExistsException("There are no building in" + city);
//}
//	return (Page<Building>) repo.findByCity(city, page);
//}


}
