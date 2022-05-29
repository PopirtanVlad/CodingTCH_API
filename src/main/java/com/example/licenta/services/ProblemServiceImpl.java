package com.example.licenta.services;

import com.example.licenta.builders.ProblemBuilder;
import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
import com.example.licenta.exceptions.ResourceAlreadyExistsException;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.utils.ProblemDifficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements IProblemService{

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    @Transactional
    public Problem addNewProblem(ProblemInfo problemInfo) throws ResourceAlreadyExistsException {
        if(!problemRepository.existsByTitle(problemInfo.getTitle())){
            throw new ResourceAlreadyExistsException("Problem","title",problemInfo.getTitle());
        }
        return ProblemBuilder.generateProblem(problemInfo);
    }

    @Override
    public List<ProblemInfo> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream()
                .map(ProblemBuilder::generateProblemInfo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Problem> findProblemById(UUID id) {
        return problemRepository.findById(id);
    }

    @Override
    public List<Problem> getProblemsByDifficulty(ProblemDifficulty difficulty) {
        return problemRepository.getProblemsByDifficulty(difficulty);
    }
}
