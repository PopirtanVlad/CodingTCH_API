package com.example.licenta.services.repositories;

import com.example.licenta.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
