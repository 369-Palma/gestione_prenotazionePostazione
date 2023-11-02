package com.palma.com.gestione_prenotazione.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Data @Builder 
@Entity
public class Postazione {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(nullable = false)
private Long codice;

private String descrizione;

//@Column(nullable = false)
private Integer maxOccupanti;

@Column(nullable = false)
// @Enumerated(EnumType.STRING)
// private Stato statoPrenotazione;

@Builder.Default
private Boolean available = true;

@Column(nullable = false)
@Enumerated(EnumType.STRING)
private TipoPostazione tipo;

@ManyToOne
private Building building;

public Integer numPrenotati;

//@OneToMany(mappedBy ="postazione")
//@OrderBy(value = "dataPrenotata")
//private List<Prenotazione> prenotazioni;


public Integer getMaxOccupanti() {
    if (maxOccupanti == null || maxOccupanti < 1) {
        return 1; 
    }
    return maxOccupanti;
}




}