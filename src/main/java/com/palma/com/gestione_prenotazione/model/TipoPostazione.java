package com.palma.com.gestione_prenotazione.model;

import java.util.Locale;

import com.github.javafaker.Faker;


public enum TipoPostazione {
PRIVATO,
OPENSPACE,
SALA_RIUNIONI;


public static TipoPostazione TipoPostazioneRandom() {
	Faker fake = new Faker(new Locale("it-IT"));
	int size = TipoPostazione.values().length;
	int position = fake.number().numberBetween(0, size);
	return TipoPostazione.values()[position];
}
}