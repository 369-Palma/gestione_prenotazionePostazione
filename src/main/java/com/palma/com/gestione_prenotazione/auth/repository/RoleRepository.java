package com.palma.com.gestione_prenotazione.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palma.com.gestione_prenotazione.auth.entity.ERole;
import com.palma.com.gestione_prenotazione.auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}
