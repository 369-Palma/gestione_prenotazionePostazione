package com.palma.ecommerceArte.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.palma.ecommerceArte.auth.entity.ERole;
import com.palma.ecommerceArte.auth.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
	Optional<Role> findByRoleName(ERole roleName);

}
