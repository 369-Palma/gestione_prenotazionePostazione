package com.palma.com.gestione_prenotazione.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.palma.com.gestione_prenotazione.model.Citta;
import com.palma.com.gestione_prenotazione.model.Postazione;
import com.palma.com.gestione_prenotazione.model.Prenotazione;
import com.palma.com.gestione_prenotazione.model.TipoPostazione;

@Repository
public interface PostazioneRepository extends JpaRepository<Postazione, Long> {

	@Query( value = "SELECT p FROM Postazione p ORDER BY RANDOM() LIMIT 1")
	Postazione findByPostazioneRandom();
	
	public boolean existsByCodice(Long codice);
	public boolean existsByTipo(TipoPostazione tipo);
	
	
	@Query("SELECT COUNT(p) = 0 FROM Prenotazione p " +
		       "WHERE p.postazione = :postazione " +
		       "AND :dataPrenotata = p.dataPrenotata")
		boolean isPostazioneDisponibile(@Param("postazione") Postazione postazione, @Param("dataPrenotata") LocalDate dataPrenotata);
	
	//FILTRO PER TIPO DI POSTAZIONE
	@Query("SELECT post FROM Postazione post WHERE post.tipo = :tipo ")
	public Page<Postazione> findByTipo(TipoPostazione tipo, Pageable page);
	
	@Query("SELECT p FROM Postazione p WHERE p.codice = :codice")
	public Postazione findByCodice(Long codice);
	
	@Query("SELECT p FROM Postazione p WHERE p.codice = :codice")
	public Prenotazione findByCodicePrenotazione(Long codice);

	@Query("SELECT post FROM Postazione post where"
			+ " post.building.citta = :citta AND post.tipo = :tipo"
			+ " AND post.id NOT IN (SELECT pre.postazione.id FROM Prenotazione pre where pre.dataPrenotata <> :dataRichiesta)")
	public Page<Postazione> findLibereByCitta(Citta citta, TipoPostazione tipo,  LocalDate dataRichiesta, Pageable pageable);
	
	//public Page<Postazione> findByBuildingCittaAndTipo(Citta citta, TipoPostazione tipoPostazione, Pageable pageable);

	
	//@Query("SELECT post FROM Postazione post where"
			//+ " post.building.citta.name = :citta AND post.tipo = :tipo")
			
	//public Page<Postazione> findLiberePerCittaETipo(String citta, TipoPostazione tipo, Pageable pageable);
	
	
	@Query("SELECT post FROM Postazione post JOIN post.building.citta WHERE citta.name = :citta AND post.tipo = :tipo")
	public Page<Postazione> findLiberePerCittaETipo(@Param("citta") String citta, @Param("tipo") TipoPostazione tipo, Pageable pageable);
	
	
	@Query("SELECT post FROM Postazione post where"
			+ " post.building.citta.name = :citta AND post.tipo = :tipo"
			+ " AND post.id NOT IN (SELECT pre.postazione.id FROM Prenotazione pre where pre.dataPrenotata <> :dataRichiesta)")
	public Page<Postazione> findLibere(String citta, TipoPostazione tipo,  LocalDate dataRichiesta, Pageable pageable);


	
}
