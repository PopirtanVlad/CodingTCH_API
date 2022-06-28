package com.example.licenta.services;

import com.example.licenta.builders.ProblemBuilder;
import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.dtos.problem.ProblemPreview;
import com.example.licenta.entities.Problem;
import com.example.licenta.exceptions.BadRequestException;
import com.example.licenta.exceptions.ResourceAlreadyExistsException;
import com.example.licenta.exceptions.ResourceNotFoundException;
import com.example.licenta.services.interfaces.IProblemService;
import com.example.licenta.services.repositories.ProblemRepository;
import com.example.licenta.utils.ProblemDifficulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemServiceImpl implements IProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private StorageService storageService;

    private File createFile(String text, String title) throws IOException {
        File f = new File(title+".desc");
        if(text != null){
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write(text);
            fileWriter.close();
        }
        return f;
    }

    @Override
    @Transactional
    public Problem addNewProblem(ProblemInfo problemInfo) throws ResourceAlreadyExistsException {
        if(problemRepository.existsByTitle(problemInfo.getTitle())){
            throw new ResourceAlreadyExistsException("Problem","title",problemInfo.getTitle());
        }
        try {
            File problemStatement = createFile(problemInfo.getStatement(), problemInfo.getTitle());
            storageService.uploadFile(problemInfo.getTitle(),problemStatement);
        } catch (IOException e) {
            throw new BadRequestException("Problem in saving the problem statement");
        }
        Problem problem = ProblemBuilder.generateProblem(problemInfo);
        problemRepository.save(problem);
        return problem;
    }

    @Override
    public List<ProblemPreview> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream()
                .map(ProblemBuilder::generateProblemPreview)
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

    public ProblemInfo getProblemByTitle(String title){
        Problem problem = findProblemByTitle(title);
        String problemStatement = storageService.downloadFile(problem.getTitle(), problem.getTitle()+".desc");
        return ProblemBuilder.generateProblemInfo(problem, problemStatement);
    }

    @Override
    public Problem findProblemByTitle(String problemTitle) {
        Optional<Problem> problem = problemRepository.findByTitle(problemTitle);
        if (problem.isEmpty()){
            throw new ResourceNotFoundException("Problem", "title", problemTitle);
        }
        return problem.get();
    }

    public void deleteProblemByTitle(String problemTitle){
        Problem problem = findProblemByTitle(problemTitle);
        problemRepository.delete(problem);
    }

    @Override
    public List<Problem> getProblemsByDifficulty(ProblemDifficulty difficulty) {
        return problemRepository.getProblemsByDifficulty(difficulty);
    }
}
