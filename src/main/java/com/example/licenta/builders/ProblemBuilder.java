package com.example.licenta.builders;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.entities.Problem;
import org.springframework.stereotype.Component;

@Component
public class ProblemBuilder {
    public static Problem generateProblem(ProblemInfo problemInfo) {
        return Problem.builder()
                .title(problemInfo.getTitle())
                .difficulty(problemInfo.getDifficulty())
                .statement(problemInfo.getStatement())
                .build();
    }

    public static ProblemInfo generateProblemInfo(Problem problem) {
        return ProblemInfo.builder()
                .title(problem.getTitle())
                .difficulty(problem.getDifficulty())
                .statement(problem.getStatement())
                .build();
    }

}
