package com.palma.com.gestione_prenotazione.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Prenotazione {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@JoinColumn
@ManyToOne
private Dipendente user;

@JoinColumn
@ManyToOne
private Postazione postazione;

@Column(nullable = false)
private LocalDate dataPrenotata;

@Column(nullable = false)
private LocalDate dataPrenotazione; 

}
