package com.palma.com.gestione_prenotazione.model;

import jakarta.persistence.*;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Building {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
	private String name;
	private String address;
	
	@JoinColumn
	@ManyToOne
	private Citta citta;
}
