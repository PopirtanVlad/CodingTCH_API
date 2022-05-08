package com.example.licenta.services;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
import com.example.licenta.exceptions.ResourceAlreadyExistsException;
import com.example.licenta.utils.ProblemDifficulty;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProblemService {
    Problem addNewProblem(ProblemInfo problemInfo) throws ResourceAlreadyExistsException;

    List<ProblemInfo> getAllProblems();

    Optional<Problem> findProblemById(UUID id);

    List<Problem> getProblemsByDifficulty(ProblemDifficulty difficulty);


}
