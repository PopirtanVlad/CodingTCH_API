package com.example.licenta.entities;


import com.example.licenta.utils.ProblemDifficulty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "problems")
@AllArgsConstructor
public class Problem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "PROBLEM_TiTLE")
    private String title;

    @Column(name = "PROBLEM_EXAMPLE_INPUT")
    private String exampleInput;

    @Column(name = "PROBLEM_EXAMPLE_OUTPUT")
    private String exampleOutput;

    @Column(name = "PROBLEM_DIFFICULTY")
    private ProblemDifficulty difficulty;

    @OneToMany(mappedBy="problem")
    private Set<Submission> submissionSet;

    @Column(name = "TIME_LIMIT")
    private Duration time_limit;

    @Column(name = "MEMORY_LIMIT")
    private int memory_limit;

    @OneToMany(mappedBy = "problem")
    private Set<TestCase> testCases;

}
