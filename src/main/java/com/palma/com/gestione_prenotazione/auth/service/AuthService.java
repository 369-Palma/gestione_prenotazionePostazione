package com.palma.com.gestione_prenotazione.auth.service;

import com.palma.com.gestione_prenotazione.auth.payload.LoginDto;
import com.palma.com.gestione_prenotazione.auth.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
