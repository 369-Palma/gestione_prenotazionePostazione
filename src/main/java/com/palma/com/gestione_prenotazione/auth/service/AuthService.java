package com.palma.ecommerceArte.auth.service;

import com.palma.ecommerceArte.auth.payload.LoginDto;
import com.palma.ecommerceArte.auth.payload.RegisterDto;

public interface AuthService {
    
	String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
    
}
