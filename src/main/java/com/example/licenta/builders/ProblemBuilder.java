package com.example.licenta.builders;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
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
                .statement(problemInfo.getStatement())
                .memory_limit(problemInfo.getMemoryLimit())
                .time_limit(Duration.ofSeconds(problemInfo.getTimeLimit()))
                .build();
    }

    public static ProblemInfo generateProblemInfo(Problem problem) {
        return ProblemInfo.builder()
                .id(problem.getId())
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .statement(problem.getStatement())
                .timeLimit(problem.getTime_limit().getSeconds())
                .memoryLimit(problem.getMemory_limit())
                .exampleInput(problem.getExampleInput())
                .exampleOutput(problem.getExampleOutput())
                .build();
    }

}
