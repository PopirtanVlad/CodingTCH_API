package com.example.licenta.builders;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.dtos.problem.ProblemPreview;
import com.example.licenta.entities.Problem;
import com.example.licenta.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ProblemBuilder {

    public static Problem generateProblem(ProblemInfo problemInfo) {
        return Problem.builder()
                .title(problemInfo.getTitle())
                .exampleInput(problemInfo.getExampleInput())
                .exampleOutput(problemInfo.getExampleOutput())
                .difficulty(problemInfo.getDifficulty())
                .memory_limit(problemInfo.getMemoryLimit())
                .time_limit(Duration.ofMillis(problemInfo.getTimeLimit()))
                .build();
    }

    public static ProblemInfo generateProblemInfo(Problem problem, String problemStatement) {
        return ProblemInfo.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .statement(problemStatement)
                .timeLimit(problem.getTime_limit().toMillis())
                .memoryLimit(problem.getMemory_limit())
                .exampleInput(problem.getExampleInput())
                .exampleOutput(problem.getExampleOutput())
                .build();
    }

    public static ProblemPreview generateProblemPreview(Problem problem){
        return ProblemPreview.builder()
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .build();
    }

}
