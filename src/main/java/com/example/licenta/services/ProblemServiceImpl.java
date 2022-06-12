package com.example.licenta.services;

import com.example.licenta.builders.ProblemBuilder;
import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
import com.example.licenta.exceptions.ResourceAlreadyExistsException;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.IProblemService;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.utils.ProblemDifficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements IProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    @Transactional
    public Problem addNewProblem(ProblemInfo problemInfo) throws ResourceAlreadyExistsException {
        if(problemRepository.existsByTitle(problemInfo.getTitle())){
            throw new ResourceAlreadyExistsException("Problem","title",problemInfo.getTitle());
        }
        Problem problem = ProblemBuilder.generateProblem(problemInfo);
        problemRepository.save(problem);
        return problem;
    }

    @Override
    public List<ProblemInfo> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream()
                .map(ProblemBuilder::generateProblemInfo)
                .collect(Collectors.toList());
    }

    @Override
    public Problem findProblemById(UUID id) {
        Optional<Problem> problem = problemRepository.findById(id);
        if(problem.isEmpty()){
            throw new ResourceNotFoundException("Problem", "id", id);
        }
        return problem.get();
    }

    @Override
    public Problem findProblemByTitle(String problemTitle) {
        Optional<Problem> problem = problemRepository.findByTitle(problemTitle);
        if (problem.isEmpty()){
            throw new ResourceNotFoundException("Problem", "title", problemTitle);
        }
        return problem.get();
    }

    @Override
    public List<Problem> getProblemsByDifficulty(ProblemDifficulty difficulty) {
        return problemRepository.getProblemsByDifficulty(difficulty);
    }
}
