package com.example.licenta.services.repositories;

import com.example.licenta.entities.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestCasesRepository extends JpaRepository<TestCase, UUID> {
}
