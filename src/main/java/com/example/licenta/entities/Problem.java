package com.example.licenta.entities;


import com.example.licenta.utils.ProblemDifficulty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "PROBLEM_TiTLE")
    private String title;

    @Column(name = "PROBLEM_STATEMENT")
    private String statement;

    @Column(name = "PROBLEM_DIFICULTY")
    private ProblemDifficulty difficulty;

    @OneToMany(mappedBy="problem")
    private Set<Solution> solutionSet;
}
