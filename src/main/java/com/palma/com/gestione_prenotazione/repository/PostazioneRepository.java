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
import com.palma.com.gestione_prenotazione.model.TipoPostazione;

@Repository
public interface PostazioneRepository extends JpaRepository<Postazione, Long> {

	@Query( value = "SELECT p FROM Postazione p ORDER BY RANDOM() LIMIT 1")
	Postazione findByPostazioneRandom();
	
	public boolean existsByCodice(Long codice);
	//public boolean existsByCodice(String codice);
	public boolean existsByTipo(TipoPostazione tipo);
	
	//FILTRO PER TIPO DI POSTAZIONE
	@Query("SELECT p FROM Postazione p WHERE p.tipo = :tipo ")
	public Page<Postazione> findByTipo(TipoPostazione tipo, Pageable page);
	
	@Query("SELECT p FROM Postazione p WHERE p.codice LIKE %:codice% ")
	public Postazione findByCodice(Long codice);
	
	@Query("SELECT p FROM Postazione p WHERE CAST(p.codice AS string) LIKE %:codice%")
	public Page<Postazione> findByCodice(@Param("codice") Long codice, Pageable page);

	@Query(value = "SELECT * FROM Postazione post " +
            "WHERE post.edificio_citta_id = :cittaId " +
            "AND post.tipo = :tipo " +
            "AND post.id NOT IN (SELECT pre.postazione_id FROM Prenotazione pre WHERE pre.data_prenotata = :dataRichiesta)",
            nativeQuery = true)
    Page<Postazione> findLibereByCitta(@Param("cittaId") Long cittaId, @Param("tipo") TipoPostazione tipo, @Param("dataRichiesta") LocalDate dataRichiesta, Pageable pageable);
}
