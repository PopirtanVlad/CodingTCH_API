package com.example.licenta.services.repositories;

import com.example.licenta.entities.Problem;
import com.example.licenta.utils.ProblemDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, UUID> {

    Optional<Problem> findByTitle(String title);

    boolean existsByTitle(String title);

    @Query(value = "Select p " +
            "FROM Problem p " +
            "WHERE p.difficulty = :problemDifficulty" )
    List<Problem> getProblemsByDifficulty(@Param("problemDifficulty")ProblemDifficulty problemDifficulty);
}
