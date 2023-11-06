package com.palma.com.gestione_prenotazione.model;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Dipendente {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String lastname;

@Column(nullable = false)
private String email;

//private Boolean active;


}
