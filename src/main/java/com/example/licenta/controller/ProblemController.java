package com.example.licenta.controller;

import com.example.licenta.dtos.problem.ProblemInfo;
import com.example.licenta.services.ProblemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    @Autowired
    private ProblemServiceImpl problemService;

    @GetMapping("/all")
    public ResponseEntity<List<ProblemInfo>> getProblems(){
        List<ProblemInfo> problemInfos = problemService.getAllProblems();
        return new ResponseEntity<>(problemInfos, HttpStatus.OK);
    }

}
