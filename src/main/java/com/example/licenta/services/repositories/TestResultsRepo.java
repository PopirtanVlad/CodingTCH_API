package com.example.licenta.services.repositories;

import com.example.licenta.entities.Submission;
import com.example.licenta.entities.TestResult;
import com.example.licenta.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestResultsRepo extends JpaRepository<TestResult, UUID> {

    List<TestResult> findBySubmission(Submission submission);

}
